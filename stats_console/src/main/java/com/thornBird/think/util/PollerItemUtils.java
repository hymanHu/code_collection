package com.thornBird.think.util;

import com.thornBird.think.model.zkModel.Cluster;
import com.thornBird.think.model.zkModel.ClusterNode;

/**
 * 生成pollerItem的名称以及命令
 * @author hyman
 *
 */
public class PollerItemUtils {
	
	public static String toPollerName(Cluster cluster, ClusterNode clusterNode) {
		return String.format("%s_%s:%d", cluster.getClusterName(), clusterNode.getHost(), clusterNode.getPort());
	}
	
	public static String toPollerCommond(Cluster cluster, ClusterNode clusterNode){
		String pollerCommond = "";
		switch (cluster.getType()) {
		case JVM:
			pollerCommond = String.format("http://%s:%d/stats", clusterNode.getHost(), clusterNode.getPort());
			break;
		case MYSQL:
			pollerCommond = String.format("%s:%d", clusterNode.getHost(), clusterNode.getPort());
			break;
		case REDIS:
			pollerCommond = String.format("%s:%d", clusterNode.getHost(), clusterNode.getPort());
			break;
		case MEMCACHED:
			pollerCommond = String.format("%s:%d", clusterNode.getHost(), clusterNode.getPort());
			break;
		default:
			break;
		}
		return pollerCommond;
	}
}
