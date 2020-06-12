package com.thornBird.base.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ScheduleTask implements Runnable {

	public static final int CYCLE = 0;
	public static final int ONE_TIME = 1;

	private int taskType;
	private int delay;
	private int period;
	private TimeUnit timeUnit;

	private static AtomicLong counter = new AtomicLong(0);

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public ScheduleTask(int taskType, int delay, int period, TimeUnit timeUnit) {
		this.taskType = taskType;
		this.delay = delay;
		this.period = period;
		this.timeUnit = timeUnit;
	}

	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		System.out.println(String.format("%s%s%s", sdf.format(date), " 执行", taskType == 0 ? "循环任务。" : "单次任务。"));
	}

}
