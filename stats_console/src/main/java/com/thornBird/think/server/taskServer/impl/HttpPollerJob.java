package com.thornBird.think.server.taskServer.impl;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thornBird.think.server.datasourceServer.impl.HttpDataSource;
import com.thornBird.think.server.taskServer.AbstractPollerJob;

/**
 * 根据http stats uri，从HttpDataSource中得到stats json字符串
 * @author hyman
 *
 */
public class HttpPollerJob extends AbstractPollerJob {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpPollerJob.class);

	@Override
	protected String queryStats(JobExecutionContext context) {
		logger.debug("Start http poller job.");
		
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String uri = jobDataMap.getString(AbstractPollerJob.URI);
		logger.debug("Http poller job uri {}.", uri);
		
		HttpDataSource httpDataSource = new HttpDataSource(uri);
		return httpDataSource.toJson();
	}

}
