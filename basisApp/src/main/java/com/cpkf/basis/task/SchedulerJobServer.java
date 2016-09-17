package com.cpkf.basis.task;

import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Scheduler代表一个Quartz的独立运行容器，Trigger和JobDetail可以注册到Scheduler中，两者在Scheduler中拥有各自的组及名称。
 * Trigger的组及名称必须唯一，JobDetail的组和名称也必须唯一，两者可以相同。
 * Scheduler可以将Trigger绑定到某一JobDetail中，这样当Trigger触发时，对应的Job就被执行。
 * 一个Job可以对应多个Trigger，但一个Trigger只能对应一个Job。
 * Scheduler拥有一个SchedulerContext，它类似于ServletContext，保存着Scheduler上下文信息，Job和Trigger都可以访问SchedulerContext内的信息。
 * SchedulerContext内部通过一个Map，以键值对的方式维护这些上下文数据。
 * @author Administrator
 *
 */
public class SchedulerJobServer {

	// 启动定时器
	public void startScheduler() throws SchedulerException {
		// 从定时器工厂里获得一个定时器
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		for (int i = 0; i < 10; i++) {
			initSchedulerJob(scheduler, String.format("%s%s", "schedulerJob_", i));
		}
		scheduler.start();
	}

	// 初始化定时器各个job
	public void initSchedulerJob(Scheduler scheduler, String key) throws SchedulerException {
		// 根据参数生成job以及触发器的key(标识)
		JobKey jobkey = new JobKey(key);
		TriggerKey triggerKey = new TriggerKey(key);
		// 查看定时器里是否已经存在job以及trigger
		if (scheduler.checkExists(jobkey) || scheduler.checkExists(triggerKey)
				|| scheduler.getJobDetail(jobkey) != null) {
			return;
		}

		// 创建jobDetail，每个job都需要绑定一个jobDetail，以保存scheduler的上下文信息以及自定义的参数
		JobDetail jobDetail = JobBuilder.newJob(SchedulerJob.class).withIdentity(jobkey).build();

		// 保存scheduler参数
		jobDetail.getJobDataMap().put("timerName", jobkey.getName());
		jobDetail.getJobDataMap().put("otheParam", "otheParam");

		// 创建触发器(从现在开始，每隔10秒执行一次job)
//		SimpleTrigger trigger = new SimpleTriggerImpl(String.format("%s%s", key, "Trigger"), null, new Date(), null,
//				SimpleTrigger.REPEAT_INDEFINITELY, 10L * 1000L);
		SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();

		// 将job信息以及触发器加载到定时器里
		scheduler.scheduleJob(jobDetail, trigger);
	}
	
	// 删除定时器里所有的job
	public void deleteSchedulerJobs(Scheduler scheduler) throws SchedulerException {
		List<JobExecutionContext> jobs = scheduler.getCurrentlyExecutingJobs();
		for (JobExecutionContext job : jobs) {
			scheduler.deleteJob(job.getJobDetail().getKey());
		}
	}
	
	// 根据jobKeys删除定时器里的job
	public void deleteSchedulerJobsByJobKeys(Scheduler scheduler, List<String> jobKeyStrings) throws SchedulerException {
		List<JobExecutionContext> jobs = scheduler.getCurrentlyExecutingJobs();
		for (JobExecutionContext job : jobs) {
			if (jobKeyStrings.contains(job.getJobDetail().getKey().getName())) {
				scheduler.deleteJob(job.getJobDetail().getKey());
			}
		}
	}
	
	// 删除定时器里jobKeys没有的job
	public void deleteNotExistSchedulerJobsByJobKeys(Scheduler scheduler, List<String> jobKeyStrings) throws SchedulerException{
		List<JobExecutionContext> jobs = scheduler.getCurrentlyExecutingJobs();
		for (JobExecutionContext job : jobs) {
			if (!jobKeyStrings.contains(job.getJobDetail().getKey().getName())) {
				scheduler.deleteJob(job.getJobDetail().getKey());
			}
		}
	}

	public static void main(String[] args) throws SchedulerException {
		SchedulerJobServer schedulerJobServer = new SchedulerJobServer();
		schedulerJobServer.startScheduler();
	}

}
