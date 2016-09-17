package com.thornBird.think.server.taskServer.impl;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thornBird.think.server.datasourceServer.impl.MysqlDataSource;
import com.thornBird.think.server.taskServer.AbstractPollerJob;

/**
 * 根据mysql参数，从MysqlPollerJob中得到stats json字符串
 * @author hyman
 *
 */
public class MysqlPollerJob extends AbstractPollerJob {
	
	private static final Logger logger = LoggerFactory.getLogger(MysqlPollerJob.class);

	@Override
	protected String queryStats(JobExecutionContext context) {
		logger.debug("Start mysql poller job.");
		
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String host = jobDataMap.getString(AbstractPollerJob.HOST);
		int port = jobDataMap.getIntFromString(AbstractPollerJob.PORT);
		
		MysqlDataSource mysqlDataSource = new MysqlDataSource(host, port);
		mysqlDataSource.loadStats();
		return mysqlDataSource.toJson();
	}

}
