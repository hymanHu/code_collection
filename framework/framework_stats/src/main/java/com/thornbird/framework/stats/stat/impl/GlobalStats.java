package com.thornbird.framework.stats.stat.impl;

import java.util.Map;

import com.thornbird.framework.stats.stat.Histogram;
import com.thornbird.framework.stats.stat.Piegram;
import com.thornbird.framework.stats.stat.Stats;
import com.thornbird.framework.stats.stat.StatsFactory;


/**
 * stats为外部提供的调用类，静态方法方便运用时候直接调用
 * 不同的stats创建不同的类，"GlobalStats", "DefaultStats"等
 * @author hyman
 */
public class GlobalStats {
	
	private static Stats stats = null;
	
	static {
		synchronized (Stats.class) {
			stats = StatsFactory.getInstance().getStats("GlobalStats");
		}
	}
	
	/*
	 * contains操作
	 */
	public static boolean containsCounter(String key) {
		return stats.getCounters().containsKey(key);
	}
	
	public static boolean containsGauge(String key) {
		return stats.getGauges().containsKey(key);
	}
	
	public static boolean containsLabel(String key) {
		return stats.getLabels().containsKey(key);
	}
	
	public static boolean containsPiegram(String key) {
		return stats.getPies().containsKey(key);
	}
	
	public static boolean containsHistogram(String key) {
		return stats.getHistograms().containsKey(key);
	}
	
	/*
	 * get操作
	 */
	public static Long getCounter(String key) {
		return stats.getCounter(key);
	}
	
	public static Double getGauge(String key) {
		return stats.getGauge(key);
	}
	
	public static String getLabel(String key) {
		return stats.getLabel(key);
	}
	
	public static Piegram getPiegram(String key) {
		return stats.getPie(key);
	}
	
	public static Histogram getHistogram(String key) {
		return stats.getHistogram(key);
	}
	
	/*
	 * set操作
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
	
	public static void addPiegram(String key, String itemName, Double itemValue){
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
