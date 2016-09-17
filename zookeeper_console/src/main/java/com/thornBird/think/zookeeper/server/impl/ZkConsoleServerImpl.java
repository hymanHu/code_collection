package com.thornBird.think.zookeeper.server.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.thornBird.think.zookeeper.common.util.Configuration;
import com.thornBird.think.zookeeper.common.util.JSonUtil;
import com.thornBird.think.zookeeper.model.JsTreeObject;
import com.thornBird.think.zookeeper.model.TreeNode;
import com.thornBird.think.zookeeper.server.IZkConsoleServer;
import com.thornbird.framework.zookeeper.exception.ZookeeperException;
import com.thornbird.framework.zookeeper.service.INode;
import com.thornbird.framework.zookeeper.service.IZkService;
import com.thornbird.framework.zookeeper.service.impl.ZkServiceImpl;
import com.thornbird.framework.zookeeper.util.ZkStringSerializer;

public class ZkConsoleServerImpl implements IZkConsoleServer {

	private static final Log log = LogFactory.getLog(ZkConsoleServerImpl.class);

	private static ZkConsoleServerImpl zkConsoleServer;
	private IZkService zkService;
	private String zkHostString;

	public String getZkHostString() {
		return zkHostString;
	}

	public void setZkHostString(String zkHostString) {
		this.zkHostString = zkHostString;
	}

	private ZkConsoleServerImpl() {
		String configPath = "/opt/local/jetty/config/configuration.properties";
		Configuration config = Configuration.getConfiguration(configPath);
		String zkServerString = config.getValue("zkServers");
		int sessionTimeout = Integer
				.parseInt(config.getValue("sessionTimeout"));
		int connectionTimeout = Integer.parseInt(config
				.getValue("connectionTimeout"));
		ZkStringSerializer zkSerializer = new ZkStringSerializer();
		setZkHostString(zkServerString);

		log.info("load config file: " + configPath);
		log.info("zkServerString: " + zkServerString);
		log.info("sessionTimeout: " + sessionTimeout);
		log.info("connectionTimeout: " + connectionTimeout);

		zkService = new ZkServiceImpl(zkSerializer, zkServerString,
				sessionTimeout, connectionTimeout);
		zkService.start();
	}

	public static ZkConsoleServerImpl getZkConsoleServer() {
		if (zkConsoleServer == null) {
			synchronized (ZkConsoleServerImpl.class) {
				if (zkConsoleServer == null) {
					try {
						zkConsoleServer = new ZkConsoleServerImpl();
					} catch (Exception e) {
						log.debug("getZkService:", e);
						return null;
					}
				}
			}
		}
		return zkConsoleServer;
	}

	/**
	 * 递归添加节点及其子节点到TreeNode对象
	 * 
	 * @param treeNode
	 * @param identifier
	 */
	public void setNodeChildren(TreeNode treeNode, String identifier) {
		INode node = zkService.getNode(identifier);
		treeNode.setIdentifier(node.getIdentifier());
		treeNode.setNodeKey(node.getNodeName());
		treeNode.setNodeValue(node.getNodeValue() == null
				|| node.getNodeValue() == "" ? "" : (String) node
				.getNodeValue());

		INode[] nodesTemp = node.getChildren();
		if (nodesTemp != null && nodesTemp.length > 0) {
			for (INode nodeTemp : nodesTemp) {
				if (!nodeTemp.getIdentifier().equals("/zookeeper")) {
					TreeNode treeNodeTemp = new TreeNode();
					treeNodeTemp.setIdentifier(nodeTemp.getIdentifier());
					treeNodeTemp.setNodeKey(nodeTemp.getNodeName());
					treeNodeTemp.setNodeValue(nodeTemp.getNodeValue() == null
							|| nodeTemp.getNodeValue() == "" ? ""
							: (String) nodeTemp.getNodeValue());

					treeNode.getChildren().add(treeNodeTemp);

					setNodeChildren(treeNodeTemp, nodeTemp.getIdentifier());
				}
			}
		}
	}

	/**
	 * 将node对象转换为JsTreeObject对象
	 * 
	 * @param node
	 * @return
	 */
	public JsTreeObject transformNodeToJsTreeObject(INode node) {
		JsTreeObject tree = new JsTreeObject();

		INode[] children = node.getChildren();
		if (children == null || children.length == 0) {
			tree.setState("leaf");
		} else {
			tree.setState("closed");
		}
		tree.setData(node.getNodeName() == "" || node.getNodeName() == null ? "Root"
				: node.getNodeName());
		tree.setAttr("id", node.getIdentifier().replaceAll("/", "_").replace(".", "_"));
		tree.setAttr("identifier", node.getIdentifier());
		tree.setAttr("nodeKey", node.getNodeName() == ""
				|| node.getNodeName() == null ? "Root" : node.getNodeName());
		tree.setAttr(
				"nodeValue",
				node.getNodeValue() == null || node.getNodeValue() == "" ? "Root"
						: (String) node.getNodeValue());
		tree.setAttr("parentIdentifier", node.getIdentifier().equals("/") ? ""
				: node.getParent().getIdentifier());

		return tree;
	}

	/**
	 * 将node子节点转换为List<JsTreeObject>
	 * 
	 * @param node
	 * @return
	 */
	public List<JsTreeObject> transformNodeToJsTreeObjects(INode node) {
		List<JsTreeObject> children = new ArrayList<JsTreeObject>();

		INode[] childrenNodes = node.getChildren();
		if (childrenNodes != null && childrenNodes.length > 0) {
			for (INode child : childrenNodes) {
				if (!child.getNodeName().equals("zookeeper")) {
					JsTreeObject tree = transformNodeToJsTreeObject(child);
					children.add(tree);
				}
			}
		}

		Collections.sort(children, new Comparator<JsTreeObject>() {
			public int compare(JsTreeObject o1, JsTreeObject o2) {
				return o1.getData().compareTo(o2.getData());
			}
		});

		return children;
	}

	/*
	 * 递归添加treeNode
	 */
	public void addTreeNode(TreeNode tree, String parentIdentifier)
			throws Exception {
		if (!tree.getIdentifier().equals("/")
				&& !tree.getIdentifier().equals("zookeeper")) {
			addNode(parentIdentifier, tree.getNodeKey(), tree.getNodeValue());
		}
		if (tree.getChildren() != null && tree.getChildren().size() > 0) {
			parentIdentifier = parentIdentifier.equals("/") ? tree
					.getIdentifier() : String.format("%s%s", parentIdentifier,
					tree.getIdentifier());
			for (TreeNode child : tree.getChildren()) {
				addTreeNode(child, parentIdentifier);
			}
		}
	}

	public void start() throws Exception {
		zkService.start();
	}

	public void stop() throws Exception {
		zkService.stop();
	}

	public boolean addNode(String identifier, String key, String value)
			throws Exception {
		if (key == null || key.equals("")) {
			throw new ZookeeperException("node key incorrect.");
		}

		value = value.equals("") || value == null ? key : value;
		INode node = zkService.getNode(identifier);
		String newIdentifier = node.addChildNode(key.trim(), value.trim());

		if (!newIdentifier.equals(String.format("%s%s%s",
				identifier.equals("/") ? "" : identifier, "/", key))) {
			return false;
		}

		return true;
	}

	public boolean deleteNode(String identifier) throws Exception {
		if (identifier.equals("/")) {
			throw new ZookeeperException("'/' node can not deleted.");
		}

		INode currentNode = zkService.getNode(identifier);
		INode parentNode = currentNode.getParent();
		return parentNode.deleteChildNode(currentNode.getNodeName(), true);
	}

	public boolean updateNode(String identifier, String value) throws Exception {
		INode node = zkService.getNode(identifier);
		node.updateNode(value);

		return true;
	}

	public INode getNode(String identifier) throws Exception {
		return zkService.getNode(identifier);
	}

	public String getNodeValue(String identifier) throws Exception {
		return (String) zkService.getNode(identifier).getNodeValue();
	}

	/*
	 * 获取多个节点转化为json字符串，identifier用";"分隔
	 */
	public String getAllNodeJson(String identifiers) throws Exception {
		TreeNode treeNodes = new TreeNode();

		String[] nodesIdentifier = identifiers.split(";");
		for (String identifier : nodesIdentifier) {
			if (!identifier.equals("")) {
				TreeNode treeNode = new TreeNode();
				setNodeChildren(treeNode, identifier);
				treeNodes.getChildren().add(treeNode);
			}
		}

		return JSonUtil.transformToJSon(treeNodes);
	}

	public String getCurrentNodesJson(String identifier) throws Exception {
		JsTreeObject currentTree = transformNodeToJsTreeObject(zkService
				.getNode(identifier));
		return JSonUtil.transformToJSon(currentTree);
	}

	public String getChildrenNodesJson(String identifier) throws Exception {
		return JSonUtil.transformToJSon(transformNodeToJsTreeObjects(zkService
				.getNode(identifier)));
	}

	public boolean copyNode(String fromIdentifier, String toIdentifier)
			throws Exception {
		INode newNode = zkService.copyNode(fromIdentifier, toIdentifier);
		if (newNode == null) {
			throw new ZookeeperException(
					"Original node not exists or new node exists.");
		} else {
			return true;
		}
	}

	public boolean importNode(String identifier, String data) throws Exception {
		if (data == null || data.length() == 0) {
			return false;
		}

		TreeNode tree = new TreeNode();
		tree = (TreeNode) JSonUtil.transformToObject(data, TreeNode.class);
		if (tree == null || tree.getChildren() == null
				|| tree.getChildren().isEmpty()) {
			return false;
		}

		if (tree.getIdentifier() == null) {
			for (TreeNode child : tree.getChildren()) {
				addTreeNode(child, identifier);
			}
		} else {
			addTreeNode(tree, identifier);
		}

		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
