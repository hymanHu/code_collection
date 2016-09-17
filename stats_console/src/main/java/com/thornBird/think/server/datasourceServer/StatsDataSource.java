package com.thornBird.think.server.datasourceServer;

import com.thornbird.framework.stats.stat.Stats;
import com.thornbird.framework.stats.stat.StatsFactory;

/**
 * 引入stats
 * @author hyman
 */
public abstract class StatsDataSource implements IDataSource {
	
	protected Stats stats;
	
	public StatsDataSource() {
		stats = StatsFactory.getInstance().getStats(null);
	}

	public abstract void loadStats();

	public String toJson() {
		return stats.toJson();
	}

}
