package com.thornBird.think.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thornBird.think.model.StatsType;
import com.thornBird.think.model.jsTree.JsTreeObject;
import com.thornBird.think.model.mysqlModel.PollerGroup;
import com.thornBird.think.model.mysqlModel.PollerItem;
import com.thornBird.think.server.daoServer.IPollerGroupServer;
import com.thornBird.think.server.daoServer.IPollerItemServer;
import com.thornBird.think.server.datasourceServer.impl.HttpDataSource;
import com.thornBird.think.server.datasourceServer.impl.MemcachedDataSource;
import com.thornBird.think.server.datasourceServer.impl.MysqlDataSource;
import com.thornBird.think.server.datasourceServer.impl.RedisDataSource;
import com.thornBird.think.util.JSonUtil;

/**
 * 实时监控控制器，加载tree，点击tree上每个item得到相应的监控数据
 * @author hyman
 *
 */
@Controller("realtimeStatsController")
public class RealtimeStatsController {

	private static final Logger logger = LoggerFactory.getLogger(RealtimeStatsController.class);
	
	@Resource(name="pollerGroupServer")
	private IPollerGroupServer pollerGroupServer;
	@Resource(name="pollerItemServer")
	private IPollerItemServer pollerItemServer;
	
	@RequestMapping(value = "rt/items", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String loadStatsItems(@RequestParam(required = false) String callback) {
		String response = null;
		
		try {
			List<PollerGroup> pollerGroups = pollerGroupServer.loadAll();
			if (pollerGroups == null || pollerGroups.isEmpty()) {
				return response;
			}
			
			List<JsTreeObject> trees = new ArrayList<JsTreeObject>();
			for (PollerGroup pg : pollerGroups) {
				List<PollerItem> pollerItems = pollerItemServer.loadByGroupId(pg.getId());
				if (pollerItems == null || pollerItems.isEmpty()) {
					continue;
				}
				
				JsTreeObject tree = new JsTreeObject();
				tree.setData(pg.getGroupName());
				tree.setAttr(null);
				for (PollerItem pi : pollerItems) {
					JsTreeObject childTree = new JsTreeObject();
					childTree.setData(pi.getPollerName());
					childTree.setChildren(null);
					childTree.getAttr().put("id", String.valueOf(pi.getId()));
					childTree.getAttr().put("nodeKey", pi.getPollerCommand().substring(pi.getPollerCommand().indexOf("//") + 2));
					childTree.getAttr().put("nodeValue", 
							String.format("rt/stats?id=%d&statsType=%s&pollerCmd=%s&interval=%d", 
									pi.getId(), pi.getStatsType().toString(), URLEncoder.encode(pi.getPollerCommand(), "UTF-8"), 
									pi.getPollerInterval()));
					tree.getChildren().add(childTree);
				}
				
				trees.add(tree);
			}
			
			response = JSonUtil.transformToJSon(trees);
			if (!StringUtils.isBlank(callback)) {
				response = String.format("%s%s%s%s", callback, "(", response, ")");
			}
		} catch (Exception e) {
			logger.error("Load stats items error.", e);
			return response;
		}
		
		logger.debug("Stats items: " + response);
		return response;
	}
	
	@RequestMapping(value = "rt/stats", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String loadRealTimeStats(
			@RequestParam String statsType, 
			@RequestParam String pollerCmd, 
			@RequestParam(required = false) String callback) {
		String response = null;
		
		try {
			StatsType type = StatsType.valueOf(statsType);
			switch (type) {
			case JVM: {
				HttpDataSource httpDataSource = new HttpDataSource(pollerCmd);
				response = httpDataSource.toJson();
			}
				break;
			case MYSQL: {
				String[] hostAndPort = pollerCmd.split(":");
				MysqlDataSource mysqlDataSource = 
						new MysqlDataSource(hostAndPort[0], Integer.parseInt(hostAndPort[1].trim()));
				mysqlDataSource.loadStats();
				response = mysqlDataSource.toJson();
			}
				break;
			case REDIS: {
				String[] hostAndPort = pollerCmd.split(":");
				RedisDataSource redisDataSource = 
						new RedisDataSource(hostAndPort[0], Integer.parseInt(hostAndPort[1].trim()));
				redisDataSource.loadStats();
				response = redisDataSource.toJson();
			}
				break;
			case MEMCACHED: {
				String[] hostAndPort = pollerCmd.split(":");
				MemcachedDataSource memcachedDataSource = 
						new MemcachedDataSource(hostAndPort[0], Integer.parseInt(hostAndPort[1].trim()));
				memcachedDataSource.loadStats();
				response = memcachedDataSource.toJson();
			}
				break;
			default:
				break;
			}
			
			if (!StringUtils.isBlank(callback)) {
				response = String.format("%s%s%s%s", callback, "(", response, ")");
			}
		} catch (Exception e) {
			logger.error("Load real time stats error: ", e);
		}
		
		logger.debug("Load real time stats: {}", response);
		return response;
	}
}
