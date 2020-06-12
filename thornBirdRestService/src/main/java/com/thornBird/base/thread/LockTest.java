package com.thornBird.base.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: Lock Test
 * @author: HymanHu
 * @date: 2019-08-03 22:39:58
 */
public class LockTest {

	private Lock lock = new ReentrantLock();
	
	private void synchronizedMethod() {
		if (lock.tryLock()) {
			try {
//				lock.lock();
				System.out.println(Thread.currentThread().getName() + "获取锁");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(Thread.currentThread().getName() + "释放锁");
				lock.unlock();
			}
		} else {
			System.out.println(Thread.currentThread().getName() + "锁被占用，放弃锁");
		}
	}
	
	public static void main(String[] args) {
		LockTest lockTest = new LockTest();
		
		Thread thread1 = new Thread(() -> {
			lockTest.synchronizedMethod();
		},"Thread1") ;
		thread1.start();
		
		Thread thread2 = new Thread(() -> {
			lockTest.synchronizedMethod();
		},"Thread2") ;
		thread2.start();
	}
}
