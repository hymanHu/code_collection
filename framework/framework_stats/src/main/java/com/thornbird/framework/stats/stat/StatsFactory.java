package com.thornbird.framework.stats.stat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * stats工厂，单列模式，将stats放入ConcurrentHashMap(线程安全)中，为外部提供stats
 * 
 * @author hyman
 */
public class StatsFactory {

	private Map<String, Stats> statsMap = new ConcurrentHashMap<String, Stats>();

	private static StatsFactory instance = null;

	private StatsFactory() {
	}

	public static StatsFactory getInstance() {
		if (instance == null) {
			synchronized (StatsFactory.class) {
				if (instance == null) {
					instance = new StatsFactory();
				}
			}
		}
		return instance;
	}
	
	public Stats getStats(String key) {
		if (key == null || key.equals("")) {
			return new Stats();
		}
		Stats stats = statsMap.get(key);
		if (stats == null) {
			stats = new Stats();
			statsMap.put(key, stats);
		}
		return stats;
	}

	public static void main(String[] args) {
	}

}
