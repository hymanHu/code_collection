package com.thornBird.base.thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Description: Thread Counter, 多线程操作，CyclicBarrier可以让一组线程相互等待，直到最后线程到达，常运用于统计任务中
 * @author: HymanHu
 * @date: 2019-08-03 12:29:29
 */
public class ThreadCounter implements Runnable {
	
	public static AtomicInteger atomicInteger = new AtomicInteger();
	public CyclicBarrier barrier;
	public Random random;
	public static int COUNT = 0;
	public static int THREAD_NUM = 10;
	
	public ThreadCounter(CyclicBarrier barrier) {
		this.barrier = barrier;
		this.random = new Random();
	}

	@Override
	public void run() {
		int atomicCount = atomicInteger.getAndDecrement();
		String threadName = Thread.currentThread().getName();
		COUNT += random.nextInt(THREAD_NUM);
		System.out.println(threadName + "--------" + atomicCount + "--------" + COUNT);
		
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Runnable collector = new Runnable() {
			public void run() {
				System.out.println("The thread counter: " + COUNT);
			}
		};
		CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUM, collector);
		
		IntStream.range(0, THREAD_NUM)
			.forEach(i -> new Thread(new ThreadCounter(cyclicBarrier), String.format("%s%s", "Thread", i)).start());
	}

}
