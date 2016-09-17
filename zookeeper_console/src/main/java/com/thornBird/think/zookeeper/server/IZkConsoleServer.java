package com.thornBird.think.zookeeper.server;

import com.thornbird.framework.zookeeper.service.INode;

public interface IZkConsoleServer {
	public void start() throws Exception;

	public void stop() throws Exception;

	public boolean addNode(String identifier, String key, String value)
			throws Exception;

	public boolean deleteNode(String identifier) throws Exception;

	public boolean updateNode(String identifier, String value)
			throws Exception;

	public INode getNode(String identifier) throws Exception;

	public String getNodeValue(String identifier) throws Exception;

	public String getAllNodeJson(String identifiers) throws Exception;

	public String getCurrentNodesJson(String identifier) throws Exception;

	public String getChildrenNodesJson(String identifier) throws Exception;

	public boolean copyNode(String fromIdentifier, String toIdentifier)
			throws Exception;

	public boolean importNode(String identifier, String data) throws Exception;
	
}
