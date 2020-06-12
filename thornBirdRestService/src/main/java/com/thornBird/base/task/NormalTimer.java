package com.thornBird.base.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: Normal Timer
 * @author: HymanHu
 * @date: 2019-08-24 15:09:21
 */
public class NormalTimer {
	//timer构造方法参数true--timer属于一个守护线程，应用程序结束timer也会死亡
	private static final Timer timer = new Timer();
	
	private void closeTimer() {
		timer.cancel();
	}
	/*
	 * Timer.schedule(TimerTask task,Date time)安排在制定的时间执行指定的任务。
	 * Timer.schedule(TimerTask task,DatefirstTime ,long period)安排指定的任务在指定的时间开始进行重复的固定延迟执行．
	 * Timer.schedule(TimerTask task,longdelay)安排在指定延迟后执行指定的任务．
	 * Timer.schedule(TimerTask task,longdelay,long period)安排指定的任务从指定的延迟后开始进行重复的固定延迟执行．
	 * Timer.scheduleAtFixedRate(TimerTasktask,Date firstTime,long period)安排指定的任务在指定的时间开始进行重复的固定速率执行．
	 * Timer.scheduleAtFixedRate(TimerTasktask,long delay,long period)安排指定的任务在指定的延迟后开始进行重复的固定速率执行．
	 */
	private void schedule1(Date date) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Do something in timer.");
			}
		}, date);
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		NormalTimer normal = new NormalTimer();
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 10);
		
		System.out.println("Start Timer: " + sdf.format(calendar.getTime()));
		normal.schedule1(calendar.getTime());
	}
}
