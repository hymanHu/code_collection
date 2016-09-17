package com.thornBird.think.model.zkModel;

import java.util.Map;

/**
 * /prod-1.7.0-stats 
 * 		/prod-1.7.0-stats/rs-cluster
 * 		/prod-1.7.0-stats/rs-dependencies 
 * 该类对应 /prod-1.7.0-stats/rs-cluster & /prod-1.7.0-stats/rs-dependencies节点 
 * 该类节点类似指针，其value为cluster的父节点
 * @author hyman
 */
public class StatsNode {
	private String nodeKey;
	private String nodeValue;
	private Map<String, Cluster> clusters;

	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public Map<String, Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(Map<String, Cluster> clusters) {
		this.clusters = clusters;
	}

	@Override
	public String toString() {
		return "StatsNode [nodeKey=" + nodeKey + ", nodeValue=" + nodeValue + ", clusters=" + clusters + "]";
	}

}
