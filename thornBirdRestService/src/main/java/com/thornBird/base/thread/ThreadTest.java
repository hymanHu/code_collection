package com.thornBird.base.thread;

import java.util.stream.IntStream;

/**
 * @Description: Thread Test
 * @author: HymanHu
 * @date: 2019-08-03 12:30:35
 */
public class ThreadTest {
	public static void main(String[] args) {
		// 设置线程优先级，但执行结果和设想的不一致
		IntStream.range(0, 10).forEach(i -> {
			Thread thread = new Thread(() -> {
				Thread.currentThread().setPriority(i % 2 == 0 ? Thread.MAX_PRIORITY : Thread.MIN_PRIORITY);
				System.out.println(Thread.currentThread().getName() + "--" + Thread.currentThread().getPriority());}
			);
			if (i % 2 == 0) {
				thread.setPriority(Thread.MIN_PRIORITY);
				thread.start();
			} else {
				thread.setPriority(Thread.MAX_PRIORITY);
			}
			thread.start();
		});
	}
}
