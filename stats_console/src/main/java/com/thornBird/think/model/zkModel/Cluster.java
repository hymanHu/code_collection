package com.thornBird.think.model.zkModel;

import java.util.ArrayList;
import java.util.List;

import com.thornBird.think.model.StatsType;

/**
 * cluster节点
 * @author hyman
 */
public class Cluster {

	private static final int DEFAULT_STATS_PERIOD = 60;
	private int statsPeriodRealTime = DEFAULT_STATS_PERIOD;
	private int statsPeriodHistory = DEFAULT_STATS_PERIOD;
	private String clusterIdentifier;
	private String clusterName;
	private List<ClusterNode> nodes;
	private StatsType type;

	public synchronized void addNode(ClusterNode clusterNode) {
		if (nodes == null) {
			nodes = new ArrayList<ClusterNode>();
		}
		nodes.add(clusterNode);
	}
	
	public int getStatsPeriodRealTime() {
		return statsPeriodRealTime;
	}

	public void setStatsPeriodRealTime(int statsPeriodRealTime) {
		this.statsPeriodRealTime = statsPeriodRealTime;
	}

	public int getStatsPeriodHistory() {
		return statsPeriodHistory;
	}

	public void setStatsPeriodHistory(int statsPeriodHistory) {
		this.statsPeriodHistory = statsPeriodHistory;
	}

	public String getClusterIdentifier() {
		return clusterIdentifier;
	}

	public void setClusterIdentifier(String clusterIdentifier) {
		this.clusterIdentifier = clusterIdentifier;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public List<ClusterNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<ClusterNode> nodes) {
		this.nodes = nodes;
	}

	public StatsType getType() {
		return type;
	}

	public void setType(StatsType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Cluster [statsPeriodRealTime=" + statsPeriodRealTime + ", statsPeriodHistory=" + statsPeriodHistory
				+ ", clusterIdentifier=" + clusterIdentifier + ", clusterName=" + clusterName + ", nodes=" + nodes
				+ ", type=" + type + "]";
	}
}
