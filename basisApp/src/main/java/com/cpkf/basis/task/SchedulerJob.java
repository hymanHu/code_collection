package com.cpkf.basis.task;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 创建一个job
 * @author hyman
 *
 */
public class SchedulerJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 获取job参数，map中可以是基本类型，也可以是对象引用
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
		String timerName = jobDataMap.getString("timerName");
		System.out.println("====" + timerName);
	}
	
}
