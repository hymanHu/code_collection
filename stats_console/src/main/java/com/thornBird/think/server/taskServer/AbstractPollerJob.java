package com.thornBird.think.server.taskServer;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;
import com.thornBird.think.model.mysqlModel.PollerOutputJSON;
import com.thornBird.think.model.mysqlModel.PollerOutputKeyValue;
import com.thornBird.think.server.daoServer.IPollerOutputJSONServer;
import com.thornBird.think.server.daoServer.IPollerOutputKeyValueServer;
import com.thornbird.framework.stats.stat.StatSummary;

/**
 * 我们采用quartz来实现定时器功能
 * 该类为抽象Job类
 * 定时器调度job--根据job上下文参数信息查询出stats字符串--将stats写入poller_output & poller_output_detail表中
 * @author hyman
 *
 */
public abstract class AbstractPollerJob implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractPollerJob.class);
	
	// job上下文参数map中的key
	public static final String POLLER_ITEM_ID = "pollerItemId";
	public static final String POLLER_OUTPUT_JSON_SERVER = "pollerOutputJSONServer";
	public static final String POLLER_OUTPUT_KEY_VALUE_SERVER = "pollerOutputKeyValueServer";
	public static final String URI = "httpStatsUri";
	public static final String HOST = "host";
	public static final String PORT = "port";
	public static final String SOCKET_TYPE = "socketType";
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 根据job上下文参数信息查询stats
		String stats = queryStats(context);
		if (StringUtils.isBlank(stats)) {
			logger.warn("Stats is empty.");
			return;
		}
		
		// 将stats写入poller_output & poller_output_detail表中
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
		IPollerOutputJSONServer pollerOutputJSONServer = (IPollerOutputJSONServer) jobDataMap.get(POLLER_OUTPUT_JSON_SERVER);
		IPollerOutputKeyValueServer pollerOutputKeyValueServer = (IPollerOutputKeyValueServer) jobDataMap.get(POLLER_OUTPUT_KEY_VALUE_SERVER);
		int itemId = Integer.parseInt(jobDataMap.getString(POLLER_ITEM_ID));
		Date now = new Date();
		
		PollerOutputJSON pollerOutputJSON = new PollerOutputJSON();
		pollerOutputJSON.setItemId(itemId);
		pollerOutputJSON.setOutput(stats);
		pollerOutputJSON.setCreateTime(now);
		pollerOutputJSONServer.insert(pollerOutputJSON);
		
		StatSummary statSummary = new GsonBuilder().create().fromJson(stats, StatSummary.class);
		Map<String, String> statsMap = new HashMap<String, String>();
		statsMap.putAll(convertMap(statSummary.getCounters()));
		statsMap.putAll(convertMap(statSummary.getGauges()));
		statsMap.putAll(convertMap(statSummary.getJvm()));
		if (statsMap.isEmpty()) {
			return;
		}
		for (Entry<String, String> entry : statsMap.entrySet()) {
			PollerOutputKeyValue pollerOutputKeyValue = new PollerOutputKeyValue();
			pollerOutputKeyValue.setItemId(itemId);
			pollerOutputKeyValue.setStatsKey(entry.getKey());
			pollerOutputKeyValue.setStatsValue(entry.getValue());
			pollerOutputKeyValue.setCreateTime(now);
			
			pollerOutputKeyValueServer.insert(pollerOutputKeyValue);
		}
		
	}
	
	protected abstract String queryStats(JobExecutionContext context);
	
	private Map<String, String> convertMap(Map<?, ?> originMap) {
		Map<String, String> map = new HashMap<String, String>();
		
		if (originMap == null || originMap.isEmpty()) {
			return map;
		}
		
		DecimalFormat df = new DecimalFormat("#.###");
		originMap.entrySet();
		for (Entry<?, ?> entry : originMap.entrySet()) {
			map.put(String.valueOf(entry.getKey()), df.format(entry.getValue()));
		}
		
		return map;
	}

}
