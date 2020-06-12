package com.thornBird.base.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 计划任务，单次执行和循环执行
 * 
 * @author hyman
 */
public class ScheduleTaskServer {
	private List<ScheduleTask> scheduleTasks;
	private ScheduledExecutorService scheduledExecutorService;

	public List<ScheduleTask> getScheduleTasks() {
		return scheduleTasks;
	}

	public void setScheduleTasks(List<ScheduleTask> scheduleTasks) {
		this.scheduleTasks = scheduleTasks;
	}

	public ScheduleTaskServer() {
		this.scheduleTasks = new ArrayList<ScheduleTask>();
		// 创建固定大小可重用的线程池
		this.scheduledExecutorService = Executors.newScheduledThreadPool(4);
	}

	private void scheduleProcess(ScheduleTask scheduleTask) {
		if (scheduleTask.getTaskType() == ScheduleTask.ONE_TIME) {
			// 单次计划任务
			scheduledExecutorService.schedule(scheduleTask, scheduleTask.getDelay(), scheduleTask.getTimeUnit());
		} else if (scheduleTask.getTaskType() == ScheduleTask.CYCLE) {
			// 循环计划任务(到点执行任务，不管上次任务是否执行完成)
			// scheduledExecutorService.scheduleAtFixedRate(scheduleTask,
			// scheduleTask.getDelay(),
			// scheduleTask.getPeriod(), scheduleTask.getTimeUnit());
			// 循环计划任务(等待上次任务完成后，再按间隔时间来执行下次任务)
			scheduledExecutorService.scheduleWithFixedDelay(scheduleTask, scheduleTask.getDelay(),
					scheduleTask.getPeriod(), scheduleTask.getTimeUnit());
		}
	}

	public static void main(String[] args) {
		ScheduleTaskServer taskServer = new ScheduleTaskServer();

		ScheduleTask task1 = new ScheduleTask(0, 0, 1, TimeUnit.MINUTES);
		ScheduleTask task2 = new ScheduleTask(1, 1, 2, TimeUnit.MINUTES);
		taskServer.getScheduleTasks().add(task1);
		taskServer.getScheduleTasks().add(task2);

		for (ScheduleTask task : taskServer.getScheduleTasks()) {
			taskServer.scheduleProcess(task);
		}
	}

}
