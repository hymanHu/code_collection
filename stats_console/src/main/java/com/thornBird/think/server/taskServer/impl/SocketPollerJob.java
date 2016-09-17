package com.thornBird.think.server.taskServer.impl;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thornBird.think.model.StatsType;
import com.thornBird.think.server.datasourceServer.impl.MemcachedDataSource;
import com.thornBird.think.server.datasourceServer.impl.RedisDataSource;
import com.thornBird.think.server.taskServer.AbstractPollerJob;

/**
 * 根据socket参数以及类型，从RedisDataSource || MemcachedDataSource中得到stats json字符串
 * @author hyman
 *
 */
public class SocketPollerJob extends AbstractPollerJob {
	
	private static final Logger logger = LoggerFactory.getLogger(SocketPollerJob.class);

	@Override
	protected String queryStats(JobExecutionContext context) {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String host = jobDataMap.getString(AbstractPollerJob.HOST);
		int port = jobDataMap.getIntFromString(AbstractPollerJob.PORT);
		StatsType socketType = StatsType.valueOf(jobDataMap.getString(AbstractPollerJob.SOCKET_TYPE));
		logger.debug("Start {} poller job", socketType.toString());
		
		String stats = null;
		switch (socketType) {
		case REDIS:
			RedisDataSource redisDataSource = new RedisDataSource(host, port);
			redisDataSource.loadStats();
			stats = redisDataSource.toJson();
			break;
		case MEMCACHED:
			MemcachedDataSource memcachedDataSource = new MemcachedDataSource(host, port);
			memcachedDataSource.loadStats();
			stats = memcachedDataSource.toJson();
			break;
		default:
			break;
		}
		
		return stats;
	}

}
