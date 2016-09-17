package com.cpkf.basis.thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程操作，CyclicBarrier可以让一组线程相互等待，直到最后线程到达，常运用于统计任务中
 * 
 * @author hyman
 */
public class ThreadCounter implements Runnable {

	public static AtomicInteger atomicInteger = new AtomicInteger(1);
	public CyclicBarrier barrier;
	public Random random;

	public static int COUNT = 0;
	public static int THREAD_NUM = 10;

	public ThreadCounter(CyclicBarrier barrier) {
		this.barrier = barrier;
		random = new Random();
	}

	public void run() {
		int atomicCount = atomicInteger.getAndIncrement();
		String threadName = Thread.currentThread().getName();
		COUNT += random.nextInt(10);
		System.out.println(threadName + "|" + atomicCount + "|" + COUNT);

		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Runnable collector = new Runnable() {
			public void run() {
				System.out.println("the total thread counter:" + COUNT);
			}
		};

		CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUM, collector);
		for (int i = 0; i < THREAD_NUM; i++) {
			new Thread(new ThreadCounter(cyclicBarrier), String.format("%s%s",
					"thread", i)).start();
		}
	}

}
