package com.thornbird.framework.zookeeper.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.thornbird.framework.zookeeper.service.impl.ZkServiceImpl;

public class ZkServiceTest {
	private IZkService zkService;
	private static String TEST_IDENTIFIER = "/test";
	private static String TEST_NODE_VALUE = "test";

	@Before
	public void setUp() throws Exception {
		zkService = new ZkServiceImpl("127.0.0.1:2181", 120000, 120000);
		zkService.start();
		zkService.deleteNode(TEST_IDENTIFIER, true);
	}

	@After
	public void tearDown() throws Exception {
		zkService.stop();
	}

	@Test
	public void getRootTest() {
		INode node = zkService.getRoot();
		Assert.assertEquals("/", node.getIdentifier());
	}
	
	@Test
	public void getNodeTest() {
		INode node = zkService.getNode(TEST_IDENTIFIER);
		boolean exist = node.exists();
		Assert.assertEquals(false, exist);
		
		zkService.createNode(TEST_IDENTIFIER, TEST_NODE_VALUE);
		Assert.assertEquals(TEST_IDENTIFIER, node.getIdentifier());
		
		zkService.deleteNode(TEST_IDENTIFIER, true);
	}
	
	@Test
	public void copyNodeTest() {
		INode node = zkService.getNode(TEST_IDENTIFIER);
		if (!node.exists()) {
			zkService.createNode(TEST_IDENTIFIER, TEST_NODE_VALUE);
		}
		
		node = zkService.getNode(String.format("%s-%s", TEST_IDENTIFIER, "back"));
		if (!node.exists()) {
			node = zkService.copyNode(TEST_IDENTIFIER, String.format("%s-%s", TEST_IDENTIFIER, "back"));
			Assert.assertEquals(TEST_NODE_VALUE, node.getNodeValue());
		}
		
		zkService.deleteNode(TEST_IDENTIFIER, true);
		zkService.deleteNode(node.getIdentifier(), true);
	}
	
	@Test
	public void updateNodeTest() {
		INode node = zkService.getNode(TEST_IDENTIFIER);
		if (!node.exists()) {
			zkService.createNode(TEST_IDENTIFIER, TEST_NODE_VALUE);
		}
		
		node.updateNode("update");
		Assert.assertEquals("update", node.getNodeValue());
		
		zkService.deleteNode(TEST_IDENTIFIER, true);
	}
	
	@Test
	public void addChildNodeTest() {
		INode node = zkService.getNode(TEST_IDENTIFIER);
		if (!node.exists()) {
			zkService.createNode(TEST_IDENTIFIER, TEST_NODE_VALUE);
		}
		
		node.addChildNode("child", "childValue");
		INode child = zkService.getNode(String.format("%s/%s", TEST_IDENTIFIER, "child"));
		Assert.assertEquals("childValue", child.getNodeValue());
		
		node.deleteChildNode("child", true);
		INode[] children = node.getChildren();
		Assert.assertEquals(0, children.length);
		
		zkService.deleteNode(TEST_IDENTIFIER, true);
	}

}
