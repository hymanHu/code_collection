package com.thornBird.config.schedule;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: My Job Config
 * @author: HymanHu
 * @date: 2019-08-24 18:44:15
 */
@Configuration
public class MyJobConfig {

	@Bean
	public JobDetail getMyJobDetail() {
		return JobBuilder.newJob(MyJob.class)
				.withIdentity("MyJob") // jobKey or string
				.usingJobData("myJobKey", "myJobValue")
				.storeDurably() // 即使没有Trigger关联时，也不需要删除该JobDetail
				.build();
	}
	
	@Bean
	public Trigger getMyJobTrigger() {
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 * * * * ?");
		return TriggerBuilder.newTrigger()
				.forJob(getMyJobDetail())
				.withIdentity("MyJobTrigger")
				.withSchedule(cronScheduleBuilder)
				.build();
	}
}
