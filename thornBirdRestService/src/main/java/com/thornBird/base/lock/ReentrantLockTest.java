package com.thornBird.base.lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockTest {
	// 公平锁
	public static ReentrantLock reentrantLock = new ReentrantLock(true);
	
	public static void main(String[] args) {
		
//		for(int i = 0; i <= 5; i ++) {
//			new Thread(() -> {
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				for(int j = 0; j < 2; j++) {
//					reentrantLock.lock();
//					System.out.println("获取锁的线程：" + Thread.currentThread().getName());
//					reentrantLock.unlock();
//				}
//			}, "Thread" + i).start();
//		}
		
		IntStream.range(0, 10).forEach(item -> {
			new Thread(() -> {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				IntStream.range(0, 5).forEach(i -> {
					reentrantLock.lock();
					System.out.println("获取锁的线程：" + Thread.currentThread().getName());
					reentrantLock.unlock();
				});
			}, "" + item).start();
		});
	}
}
