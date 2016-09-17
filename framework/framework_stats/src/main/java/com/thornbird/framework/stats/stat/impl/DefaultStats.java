package com.thornbird.framework.stats.stat.impl;

import java.util.Map;

import com.thornbird.framework.stats.stat.Stats;
import com.thornbird.framework.stats.stat.StatsFactory;

public class DefaultStats {

	private static Stats stats;

	static {
		synchronized (Stats.class) {
			stats = StatsFactory.getInstance().getStats("DefaultStats");
		}
	}

	/*
	 * set
	 */
	public static Long incr(String key) {
		return stats.incr(key);
	}

	public static Long incr(String key, int value) {
		return stats.incr(key, value);
	}

	public static Double addGauge(String key, Double value) {
		return stats.addGauge(key, value);
	}

	public static String setLabel(String key, String value) {
		return stats.setLabel(key, value);
	}

	public static void addPiegram(String key, String itemName, Double itemValue) {
		stats.addPiegram(key, itemName, itemValue);
	}

	public static void addPiegram(String key, Map<String, Double> items) {
		stats.addPiegram(key, items);
	}

	public static boolean clear() {
		return stats.clearAll();
	}

	public static boolean reset() {
		return stats.resetCounterMap();
	}

	/*
	 * to string操作
	 */
	public static String toJson() {
		return stats.toJsonWithJvm();
	}

	public static String toSimpleString() {
		return stats.toSimpleString();
	}

	public static String toSimpleHtmlString() {
		return stats.toSimpleHtmlString();
	}

	public static void main(String[] args) {
	}
}
