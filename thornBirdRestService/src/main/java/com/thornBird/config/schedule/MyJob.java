package com.thornBird.config.schedule;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.thornBird.commons.utils.DateUtil;

/**
 * @Description: My Job ---- 定时器业务类
 * @author: HymanHu
 * @date: 2019-08-24 16:31:16
 */
public class MyJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		String myJobValue = (String) context.getJobDetail().getJobDataMap().get("myJobKey");
		System.out.println("My Job Start at: " + 
				DateUtil.formatDate(new Date(), DateUtil.DATE_TIME_PATTERN) + "------" + myJobValue);
	}

}
