package com.thornBird.think.server.configServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.thornBird.think.model.StatsType;
import com.thornBird.think.model.zkModel.Cluster;
import com.thornBird.think.model.zkModel.ClusterNode;
import com.thornBird.think.model.zkModel.StatsNode;
import com.thornBird.think.model.zkModel.ZkConnectionInfo;
import com.thornBird.think.server.datasourceServer.mysql.MysqlStats;
import com.thornBird.think.util.Configuration;
import com.thornBird.think.util.ZkUtils;
import com.thornbird.framework.zookeeper.service.INode;
import com.thornbird.framework.zookeeper.service.IZkService;
import com.thornbird.framework.zookeeper.service.impl.ZkServiceImpl;

/**
 * 加载statsConfig下的statsNodes
 * 
 * @author hyman
 */
@Service("statsNodeServer")
public class StatsNodeServer {

	private static final Logger logger = LoggerFactory.getLogger(StatsNodeServer.class);

	private static String PROPERTIES_PATH = "/opt/local/jetty/config/configuration.properties";
	private ZkConnectionInfo zkInfo;
	private IZkService zkService;
	public static final String NODE_NOTIFICATION = "notification";

	public ZkConnectionInfo getZkInfo() {
		return zkInfo;
	}

	public IZkService getZkService() {
		return zkService;
	}

	// 加载zookeeper配置信息，并启动zookeeper
	public IZkService initZkInfo() {
		Configuration config = Configuration.getConfiguration(PROPERTIES_PATH);
		String zkHost = config.getValue("zkServers");
		String sessionTimeout = config.getValue("sessionTimeout");
		String connectionTimeout = config.getValue("connectionTimeout");
		String statsConfig = config.getValue("stats.rootConfigNode");

		if (StringUtils.isBlank(zkHost)) {
			throw new IllegalArgumentException("zkServers is empty.");
		}
		if (StringUtils.isBlank(sessionTimeout) || !StringUtils.isNumeric(sessionTimeout)) {
			throw new IllegalArgumentException("sessionTimeout is empty or is not number.");
		}
		if (StringUtils.isBlank(connectionTimeout) || !StringUtils.isNumeric(connectionTimeout)) {
			throw new IllegalArgumentException("connectionTimeout is empty or is not number.");
		}
		if (StringUtils.isBlank(statsConfig)) {
			throw new IllegalArgumentException("stats.rootConfigNode is empty.");
		}

		zkInfo = new ZkConnectionInfo();
		zkInfo.setZkHost(zkHost);
		zkInfo.setSessionTimeout(Integer.parseInt(sessionTimeout));
		zkInfo.setConnectionTimeout(Integer.parseInt(connectionTimeout));
		zkInfo.setStatsConfig(statsConfig);
		logger.debug("zookeeper config: " + zkInfo.toString());

		zkService = new ZkServiceImpl(zkInfo.getZkHost(), zkInfo.getSessionTimeout(), zkInfo.getConnectionTimeout());
		zkService.start();
		
		return zkService;
	}

	// 加载statsNodes list
	public List<StatsNode> loadStatsNodes() {
		List<StatsNode> statsNodes = new ArrayList<StatsNode>();
		INode statsRootNode = zkService.getNode(zkInfo.getStatsConfig());
		if (!statsRootNode.exists()) {
			logger.error("Stats root node not exist: {}", zkInfo.getStatsConfig());
			return null;
		}

		for (INode node : statsRootNode.getChildren()) {
			// 忽略大小写比较
			if (node.getNodeName().trim().equalsIgnoreCase(NODE_NOTIFICATION)) {
				continue;
			}
			StatsNode sn = new StatsNode();
			sn.setNodeKey(node.getNodeName().trim());
			sn.setNodeValue((String) node.getNodeValue());

			statsNodes.add(sn);
		}

		for (StatsNode statsNode : statsNodes) {
			statsNode.setClusters(loadClusters(statsNode.getNodeValue()));
		}

		logger.debug("Stats nodes count: {}", statsNodes == null ? 0 : statsNodes.size());
		logger.debug("Stats nodes: {}", statsNodes == null ? "null" : statsNodes.toString());
		logger.debug("Mysql user name: {}", MysqlStats.USER_NAME);
		logger.debug("Mysql password: {}", MysqlStats.PASSWORD);
		
		return statsNodes;
	}

	// 加载clusters
	public Map<String, Cluster> loadClusters(String statsNodeValue) {

		if (StringUtils.isBlank(statsNodeValue) || !statsNodeValue.startsWith("/")) {
			logger.warn("Invalid stats node: {}", statsNodeValue);
			return null;
		}

		INode selfParentNode = zkService.getNode(statsNodeValue);
		if (!selfParentNode.exists()) {
			logger.warn("Node not exist: {}", statsNodeValue);
			return null;
		}

		Map<String, Cluster> clusters = new HashMap<String, Cluster>();
		List<INode> temp = new ArrayList<INode>();

		// 将需要遍历的节点加入临时集合，包括statsNode指针指向的节点，以及该节点下include的节点
		temp.add(selfParentNode);
		INode includeNode = selfParentNode.getChild("include");
		if (includeNode != null && includeNode.exists()) {
			INode includeParentNode = zkService.getNode((String) includeNode.getNodeValue());
			if (includeParentNode.exists()) {
				temp.add(includeParentNode);
			}
		}

		// 遍历临时集合，找出每个节点下所有的cluster节点，放入clusters中
		for (INode node : temp) {
			List<INode> clusterNodes = ZkUtils.findNodesByEndName(node, "_cluster");
			if (clusterNodes == null || clusterNodes.isEmpty()) {
				continue;
			} else {
				for (INode clusterNode : clusterNodes) {
					Cluster cluster = loadCluster(clusterNode);
					if (cluster != null) {
						clusters.put(cluster.getClusterIdentifier(), cluster);
					}
				}
			}
		}

		return clusters;
	}

	public Cluster loadCluster(INode node) {
		Cluster cluster = new Cluster();
		cluster.setClusterIdentifier(node.getIdentifier());
		cluster.setClusterName(node.getNodeName());
		cluster.setType(StatsType.valueOf(((String) node.getNodeValue()).toUpperCase()));

		// 设置stats_period_history & stats_period_realtime
		// 如果是mysql cluster节点，还要设置userName &　password
		INode configNode = zkService.getNode(String.format("%s%s", node.getIdentifier(), "/pool_config"));
		if (configNode != null && configNode.exists()) {
			cluster.setStatsPeriodHistory(configNode.getChild("stats_period_history").getIntValue() == null ? cluster
					.getStatsPeriodHistory() : configNode.getChild("stats_period_history").getIntValue());
			cluster.setStatsPeriodRealTime(configNode.getChild("stats_period_realtime").getIntValue() == null ? cluster
					.getStatsPeriodRealTime() : configNode.getChild("stats_period_realtime").getIntValue());
			
			INode userNameNode = configNode.getChild("userName");
			INode passwordNode = configNode.getChild("password");
			if (userNameNode != null && userNameNode.exists() && !StringUtils.isBlank(userNameNode.getStringValue())) {
				MysqlStats.USER_NAME = userNameNode.getStringValue();
			}
			if (passwordNode != null && passwordNode.exists() && !StringUtils.isBlank(passwordNode.getStringValue())) {
				MysqlStats.PASSWORD = passwordNode.getStringValue();
			}
		}

		// 设置clusterNode信息
		INode nodes = node.getChild("nodes");
		if (nodes.exists()) {
			INode[] children = nodes.getChildren();
			for (INode child : children) {
				ClusterNode clusterNode = new ClusterNode();
				clusterNode.setHost(((String) child.getChild("host").getNodeValue()).trim());
				clusterNode.setPort(child.getChild("port").getIntValue());
				
				if (cluster.getNodes() == null || !cluster.getNodes().contains(clusterNode)) {
					cluster.addNode(clusterNode);
				}
			}
		}

		return cluster;
	}

	public static void main(String[] args) {
		StatsNodeServer statsNodeServer = new StatsNodeServer();
		statsNodeServer.initZkInfo();
		statsNodeServer.loadStatsNodes();
	}
}
