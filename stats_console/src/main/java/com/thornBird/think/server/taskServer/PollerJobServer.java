package com.thornBird.think.server.taskServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.thornBird.think.model.StatsType;
import com.thornBird.think.model.mysqlModel.PollerGroup;
import com.thornBird.think.model.mysqlModel.PollerItem;
import com.thornBird.think.model.zkModel.Cluster;
import com.thornBird.think.model.zkModel.ClusterNode;
import com.thornBird.think.model.zkModel.StatsNode;
import com.thornBird.think.server.configServer.StatsNodeServer;
import com.thornBird.think.server.daoServer.IPollerGroupServer;
import com.thornBird.think.server.daoServer.IPollerItemServer;
import com.thornBird.think.server.daoServer.IPollerOutputJSONServer;
import com.thornBird.think.server.daoServer.IPollerOutputKeyValueServer;
import com.thornBird.think.server.datasourceServer.mysql.MySqlConnectionPool;
import com.thornBird.think.server.taskServer.impl.HttpPollerJob;
import com.thornBird.think.server.taskServer.impl.MysqlPollerJob;
import com.thornBird.think.server.taskServer.impl.SocketPollerJob;
import com.thornBird.think.util.PollerItemUtils;
import com.thornbird.framework.zookeeper.listener.INodeListener;
import com.thornbird.framework.zookeeper.service.IZkService;

/**
 * <B>1.</B> 初始化定时器 <br/>
 * 2. 从StatsNodeService中读取zookeeper上stats的配置信息 <br/>
 * 3. 同步数据库`poller_group` & `poller_item`表 <br/>
 * 4. 根据StatsNodeService获得新job的JobKeyStrings，删除定时器里无关的job(JobKeyStrings里不包含的job) <br/>
 * 5. 清理mysql连接池 <br/>
 * 6. 定时器绑定新的pollerJob <br/>
 * 7. 开始定时器 <br/>
 * @author hyman
 */
@Service("pollerJobServer")
public class PollerJobServer {

	private static final Logger logger = LoggerFactory.getLogger(PollerJobServer.class);

	@Resource(name="statsNodeServer")
	private StatsNodeServer statsNodeServer;
	@Resource(name="pollerGroupServer")
	private IPollerGroupServer pollerGroupServer;
	@Resource(name="pollerItemServer")
	private IPollerItemServer pollerItemServer;
	@Resource(name="pollerOutputJSONServer")
	private IPollerOutputJSONServer pollerOutputJSONServer;
	@Resource(name="pollerOutputKeyValueServer")
	private IPollerOutputKeyValueServer pollerOutputKeyValueServer;

	// 启动zookeeper，调用startPollerJobServer，并监听notification节点
	@PostConstruct
	public void startZkAndZkListener(){
		IZkService zkService = statsNodeServer.initZkInfo();
		startPollerJobServer();
		
		zkService.subscribeNodeValueChange(String.format("%s/%s", 
				statsNodeServer.getZkInfo().getStatsConfig(), StatsNodeServer.NODE_NOTIFICATION), 
				new INodeListener() {
					public void handleValueChange(String arg0, Object arg1) throws Exception {
						logger.info("changed: {}", arg1.toString());
						startPollerJobServer();
					}
					public void handleNodeDelete(String arg0) throws Exception {
					}
				});
	}
	
	public void startPollerJobServer() {
		try {
			// 初始化定时器
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// 从StatsNodeService中读取zookeeper上stats的配置信息
			List<StatsNode> statsNodes = statsNodeServer.loadStatsNodes();

			// 同步数据库`poller_group` & `poller_item`表
			List<PollerGroup> pollerGroups = syncPollerGroupInDB(statsNodes);
			List<PollerItem> pollerItems = syncPollerItemInDB(statsNodes,
					pollerGroups);
			
			logger.debug("pollerGroups : {}.", pollerGroups.toString());
			logger.debug("pollerItems : {}.", pollerItems.toString());

			// 根据StatsNodeService获得新job的JobKeyStrings，删除定时器里无关的job(JobKeyStrings里不包含的job)
			List<String> jobKeyStrings = new ArrayList<String>();
			for (PollerItem pi : pollerItems) {
				String jobKeyString = toJobAndTriggerKey(pi);
				if (!StringUtils.isBlank(jobKeyString)) {
					jobKeyStrings.add(jobKeyString);
				}
			}
			deleteNotExistPollerJob(scheduler, jobKeyStrings);
			
			MySqlConnectionPool.getInstance().clear();

			// 定时器绑定新的pollerJob
			for (PollerItem pi : pollerItems) {
				initSchedulerJob(scheduler, pi);
			}

			// 开始定时器
			scheduler.start();
		} catch (Exception e) {
			logger.error("Start poller job server failed.", e);
		}
	}

	/*
	 * 根据zookeeper上stats的配置信息，同步到数据库中`poller_group`表，返回最新的List<PollerGroup>
	 * 数据库包含statsNode，update操作
	 * 数据库不包含statsNode，insert操作
	 * 删除数据库没有被操作的数据
	 */
	private List<PollerGroup> syncPollerGroupInDB(List<StatsNode> statsNodes) {
		
		List<PollerGroup> pollerGroupsInDB = pollerGroupServer.loadAll();
		
		List<String> updateList = new ArrayList<String>();
		for (StatsNode statsNode : statsNodes) {
			PollerGroup pg = findPollerGroupByName(pollerGroupsInDB, statsNode.getNodeKey());
			if (pg == null) {
				pollerGroupServer.insertGroup(statsNode.getNodeKey(), statsNode.getNodeValue());
			} else {
				pollerGroupServer.updateGroup(pg.getId(), statsNode.getNodeKey(), statsNode.getNodeValue());
				updateList.add(statsNode.getNodeKey());
			}
		}
		
		// pollerGroupsInDB是在insert之前获得的，所以此处只需判断updateList
		if (pollerGroupsInDB != null && !pollerGroupsInDB.isEmpty()) {
			for (PollerGroup pollerGroup : pollerGroupsInDB) {
				if (!updateList.contains(pollerGroup.getGroupName())) {
					pollerGroupServer.deleteById(pollerGroup.getId());
				}
			}
		}
		
		return pollerGroupServer.loadAll();
	}

	/*
	 * 根据zookeeper上的stats配置信息，以及最新的List<PollerGroup>，同步到数据库中`poller_item`表，返回最新List<PollerItem>
	 * List<PollerGroup>为空，删除items，return
	 * 根据statsNodes & pollerGroups，得出newPollerItems(去重：以itemIdentifier为标准)
	 * 根据pollerGroups，得出oldPollerItems
	 * 如果oldPollerItems中包含newPollerItem(以itemIdentifier为标准)，update操作
	 * 如果oldPollerItems不中包含newPollerItem，insert操作
	 * 删除数据库poller_item表中没有被操作的数据
	 */
	private List<PollerItem> syncPollerItemInDB(List<StatsNode> statsNodes, List<PollerGroup> pollerGroups) {
		
		if (pollerGroups == null || pollerGroups.isEmpty()) {
			pollerItemServer.deleteAll();
			return new ArrayList<PollerItem>();
		}
		
		List<Integer> updateList = new ArrayList<Integer>();
		List<PollerItem> pollerItems = pollerItemServer.loadAll();
		List<PollerItem> newPollerItems = new ArrayList<PollerItem>();
		List<PollerItem> oldPollerItems = new ArrayList<PollerItem>();
		
		for (StatsNode statsNode : statsNodes) {
			// 找到groupId
			PollerGroup pollerGroup = findPollerGroupByName(pollerGroups, statsNode.getNodeKey());
			if (pollerGroup == null) {
				continue;
			}
			int groupId = pollerGroup.getId();
			
			Map<String, Cluster> clusterMap = statsNode.getClusters();
			if (clusterMap == null || clusterMap.isEmpty()) {
				continue;
			}
			Collection<Cluster> clusters = clusterMap.values();
			for (Cluster cluster : clusters) {
				for (ClusterNode clusterNode : cluster.getNodes()) {
					PollerItem pollerItem = new PollerItem();
					pollerItem.setGroupId(groupId);
					pollerItem.setPollerName(PollerItemUtils.toPollerName(cluster, clusterNode));
					pollerItem.setPollerInterval(cluster.getStatsPeriodHistory());
					switch (cluster.getType()) {
					case JVM:
						pollerItem.setStatsType(StatsType.JVM);
						break;
					case MYSQL:
						pollerItem.setStatsType(StatsType.MYSQL);
						break;
					case REDIS:
						pollerItem.setStatsType(StatsType.REDIS);
						break;
					case MEMCACHED:
						pollerItem.setStatsType(StatsType.MEMCACHED);
						break;
					default:
						break;
					}
					pollerItem.setPollerCommand(PollerItemUtils.toPollerCommond(cluster, clusterNode));
					
					if (findPollerItem(newPollerItems, pollerItem) == null) {
						newPollerItems.add(pollerItem);
					}
				}
			}
		}
		
		for (PollerGroup pg : pollerGroups) {
			List<PollerItem> pollItemsTemp = pollerItemServer.loadByGroupId(pg.getId());
			if (pollItemsTemp == null || pollItemsTemp.isEmpty()) {
				continue;
			}
			oldPollerItems.addAll(pollItemsTemp);
		}
		
		for (PollerItem pi : newPollerItems) {
			PollerItem oldPi = findPollerItem(oldPollerItems, pi);
			if (oldPi == null) {
				pollerItemServer.insertItem(pi);
			} else {
				// item标识一样，实际上不需要update，加入updateList即可
				//pollerItemServer.updateItem(pi, oldPi.getId());
				updateList.add(oldPi.getId());
			}
		}
		
		logger.debug("new items: " + newPollerItems.toString());
		logger.debug("old items: " + oldPollerItems.toString());
		logger.debug("update list: " + updateList.toString());
		
		if (pollerItems != null && !pollerItems.isEmpty()) {
			for (PollerItem pi : pollerItems) {
				if (!updateList.contains(pi.getId())) {
					pollerItemServer.deleteById(pi.getId());
				}
			}
		}
		
		return pollerItemServer.loadAll();
	}

	// 删除定时器里与新jobs无关的job
	private void deleteNotExistPollerJob(Scheduler scheduler,
			List<String> jobKeyStrings) throws SchedulerException {
		List<JobExecutionContext> jobs = scheduler.getCurrentlyExecutingJobs();
		for (JobExecutionContext job : jobs) {
			if (!jobKeyStrings.contains(job.getJobDetail().getKey().getName())) {
				scheduler.deleteJob(job.getJobDetail().getKey());
			}
		}
	}

	// 定时器绑定job
	private void initSchedulerJob(Scheduler scheduler, PollerItem pi) throws SchedulerException {
		// 根据参数生成jobKey & triggerKey
		String key = toJobAndTriggerKey(pi);
		JobKey jobKey = new JobKey(key);
		TriggerKey triggerKey = new TriggerKey(key);
		if (scheduler.checkExists(jobKey) || scheduler.checkExists(triggerKey) 
				|| scheduler.getJobDetail(jobKey) != null) {
			return;
		}
		
		// 创建jobDetail，并保存特有的schedule参数
		JobDetail jobDetail = null;
		switch (pi.getStatsType()) {
		case JVM:
			jobDetail = JobBuilder.newJob(HttpPollerJob.class).withIdentity(jobKey).build();
			jobDetail.getJobDataMap().put(AbstractPollerJob.URI, pi.getPollerCommand());
			break;
		case MYSQL:
			jobDetail = JobBuilder.newJob(MysqlPollerJob.class).withIdentity(jobKey).build();
			String[] mysqlInfo = pi.getPollerCommand().split(":");
			jobDetail.getJobDataMap().put(AbstractPollerJob.HOST, mysqlInfo[0]);
			jobDetail.getJobDataMap().put(AbstractPollerJob.PORT, mysqlInfo[1]);
			break;
		case REDIS:
			jobDetail = JobBuilder.newJob(SocketPollerJob.class).withIdentity(jobKey).build();
			String[] redisInfo = pi.getPollerCommand().split(":");
			jobDetail.getJobDataMap().put(AbstractPollerJob.HOST, redisInfo[0]);
			jobDetail.getJobDataMap().put(AbstractPollerJob.PORT, redisInfo[1]);
			jobDetail.getJobDataMap().put(AbstractPollerJob.SOCKET_TYPE, pi.getStatsType().toString());
			break;
		case MEMCACHED:
			jobDetail = JobBuilder.newJob(SocketPollerJob.class).withIdentity(jobKey).build();
			String[] memcachedInfo = pi.getPollerCommand().split(":");
			jobDetail.getJobDataMap().put(AbstractPollerJob.HOST, memcachedInfo[0]);
			jobDetail.getJobDataMap().put(AbstractPollerJob.PORT, memcachedInfo[1]);
			jobDetail.getJobDataMap().put(AbstractPollerJob.SOCKET_TYPE, pi.getStatsType().toString());
			break;
		default:
			break;
		}
		if (jobDetail == null) {
			return;
		}
		
		// 保存公用的schedule参数
		jobDetail.getJobDataMap().put(AbstractPollerJob.POLLER_ITEM_ID, String.valueOf(pi.getId()));
		jobDetail.getJobDataMap().put(AbstractPollerJob.POLLER_OUTPUT_JSON_SERVER, pollerOutputJSONServer);
		jobDetail.getJobDataMap().put(AbstractPollerJob.POLLER_OUTPUT_KEY_VALUE_SERVER, pollerOutputKeyValueServer);
		
		// 创建触发器
		System.out.println("--------------" + pi.getPollerInterval());
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(pi.getPollerInterval()).repeatForever())
				.startNow().build();
		
		// 绑定job
		scheduler.scheduleJob(jobDetail, trigger);
	}
	
	// 找出pollerGroups里是否有name与传入字符串相同的PollerGroup对象，有返回第一个
	private PollerGroup findPollerGroupByName(List<PollerGroup> pollerGroups, String groupName) {
		if (pollerGroups == null || pollerGroups.isEmpty()) {
			return null;
		}
		
		for (PollerGroup pg : pollerGroups) {
			if (pg.getGroupName().equals(groupName)) {
				return pg;
			}
		}
		return null;
	}
	
	// 找出pollerItems是否有标识与传入对象相同的对象，有则返回第一个
	private PollerItem findPollerItem(List<PollerItem> pollerItems, PollerItem pi) {
		if (pollerItems == null || pollerItems.isEmpty()) {
			return null;
		}
		
		for (PollerItem pollerItem : pollerItems) {
			if (toPollerItemIdentifier(pollerItem).equals(toPollerItemIdentifier(pi))) {
				return pollerItem;
			}
		}
		return null;
	}
	
	// 生成jobKey || triggerKey字符串
	private String toJobAndTriggerKey(PollerItem pi) {
		if (pi == null) {
			return "";
		}
		return String.format("%d-%d-%s-%d-%s", pi.getId(), pi.getGroupId(), pi
				.getPollerCommand(), pi.getPollerInterval(), pi.getStatsType()
				.toString());
	}
		
	// 生成pollerItem标识
	private String toPollerItemIdentifier(PollerItem pollerItem) {
		return String.format(
	            "%s-%d-%s",
	            pollerItem.getPollerCommand(),
	            pollerItem.getPollerInterval(),
	            pollerItem.getStatsType().toString());
	}
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("test/applicationContext.xml");
	}
}
