package com.thornBird.think.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thornbird.framework.zookeeper.service.INode;

/**
 * zookeeper操作工具类
 * @author hyman
 */
public class ZkUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ZkUtils.class);
	
	/**
	 * 查找某节点(包含本节点)下子节点名称结尾和指定name相同的节点集合
	 * @return
	 */
	public static List<INode> findNodesByEndName(INode parentNode, String name) {
		if (parentNode == null || !parentNode.exists()) {
			return null;
		}
		
		List<INode> nodes = new ArrayList<INode>();
		List<INode> temp = new ArrayList<INode>();
		temp.add(parentNode);
		
		while (temp.size() > 0) {
			INode node = temp.remove(0);
			if (node.getChildren() != null) {
				for (INode child : node.getChildren()) {
					temp.add(child);
				}
			}
			if (node.getNodeName().endsWith(name)) {
				nodes.add(node);
			}
		}
		
		logger.debug("{} find nodes: {}", parentNode.getIdentifier(), nodes.toString());
		return nodes;
	}
	
}
