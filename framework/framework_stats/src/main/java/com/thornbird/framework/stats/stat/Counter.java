package com.thornbird.framework.stats.stat;

import java.util.concurrent.atomic.AtomicLong;

/**
 * stats 计数器
 * 
 * @author hyman
 */
public class Counter {

	private AtomicLong value = new AtomicLong(0);

	// 增量计数
	public long incr() {
		return incr(1);
	}

	public long incr(int n) {
		return value.addAndGet(n);
	}

	public void update(int n) {
		value.set(n);
	}

	public void reset() {
		value.set(0);
	}

	public long get() {
		return value.get();
	}

	public String toString() {
		return String.format("Counter(%d)", value.get());
	}

}
