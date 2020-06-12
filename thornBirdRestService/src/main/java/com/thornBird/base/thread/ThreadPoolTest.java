package com.thornBird.base.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Description: Thread Pool Test
 * @author: HymanHu
 * @date: 2019-08-03 15:07:07
 */
public class ThreadPoolTest {
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		IntStream.range(0, 10)
			.forEach(i -> {
				System.out.println("创建线程：" + i);
				// 有值返回
				threadPool.submit(() -> {
					System.out.println("线程启动");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("线程结束");
				});
				// 无值返回
//				threadPool.execute(runnable);
			});
		threadPool.shutdown();
		
		/*
		 * 主线程等线程池所有线程执行完毕后，再执行后面的代码
		 * 每隔一段时间去检查ExecutorService是否关闭，和shutdown配合使用
		 */
		try {
			while (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
				System.out.println("线程池没有关闭");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("线程池已经关闭");
		
		// 添加JVM停止时候需要处理的事件，一般进行内存清理，销毁对象等操作。
		Runnable finalOperation = new Runnable() {
			public void run() {
				System.out.println("最后操作");
			}
		};
		Runtime.getRuntime().addShutdownHook(new Thread(finalOperation));
	}
}
