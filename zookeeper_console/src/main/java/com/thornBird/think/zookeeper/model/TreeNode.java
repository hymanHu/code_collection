package com.thornBird.think.zookeeper.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点对象
 * @author hyman
 */
public class TreeNode {
	private String identifier;
	private String nodeKey;
	private String nodeValue;
	private List<TreeNode> children = new ArrayList<TreeNode>();

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

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

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "TreeNode [identifier=" + identifier + ", nodeKey=" + nodeKey
				+ ", nodeValue=" + nodeValue + ", children=" + children + "]";
	}

}
