package com.cpkf.basis.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	public static void main(String[] args) throws InterruptedException {
		/*
		 * 创建一个固定大小的线程池
		 * 线程池大小为3，实际创建线程10，则该线程池每次装载3个线程执行，直到完成
		 */
		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {
			System.out.println("创建线程：" + i);
			
			Runnable run = new Runnable() {
				public void run() {
					System.out.println("线程启动");
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("---线程结束");
				}
			};
			
			// 提交一个runnable任务
			threadPool.submit(run);
			// 在未来某个时间执行runnable
//			threadPool.execute(run);
		}
		
		// 线程池所有线程执行完成后才能关闭
		threadPool.shutdown();
		/*
		 * 主线程等待线程池所有线程完成，再执行后面代码
		 * 两个参数，超时时间 & 时间单位
		 * 主线程每隔一个超时时间去检查 ExecutorService 是否关闭，一般和 shutdown() 配合使用
		 * 
		 */
		while (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
			System.out.println("线程池没有关闭。");
		}
		System.out.println("All thread complete.");
		
		/*
		 * 添加JVM停止时候需要处理的事件，一般进行内存清理，销毁对象等操作。
		 */
		Runnable finalOperation = new Runnable() {
			public void run() {
				System.out.println("最后操作。");
			}
		};
		Runtime.getRuntime().addShutdownHook(new Thread(finalOperation));
	}
}
