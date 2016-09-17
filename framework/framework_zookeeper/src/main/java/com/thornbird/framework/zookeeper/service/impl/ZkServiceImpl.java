package com.thornbird.framework.zookeeper.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thornbird.framework.zookeeper.adapter.ZkChildListenerAdapter;
import com.thornbird.framework.zookeeper.adapter.ZkNodeValueListenerAdapter;
import com.thornbird.framework.zookeeper.listener.IChildListener;
import com.thornbird.framework.zookeeper.listener.INodeListener;
import com.thornbird.framework.zookeeper.service.INode;
import com.thornbird.framework.zookeeper.service.IZkService;
import com.thornbird.framework.zookeeper.util.ZkStringSerializer;


public class ZkServiceImpl implements IZkService {
	
	private static Logger logger = LoggerFactory.getLogger(ZkServiceImpl.class);
    
    private ZkClient zkClient;
    
    private ZkSerializer zkSerializer;
    
    /**
     * e.g. 192.168.1.1:2181
     */
    private String zkServerString;
    
    private int sessionTimeout;
    
    private int connectionTimeout;
    
    /**
     * A线程调用lock()方法，B线程调用lock()方法时被禁止，处于休眠状态，直到A线程释放
     */
    private ReentrantLock lock = new ReentrantLock();

    public ZkServiceImpl(
        ZkSerializer zkSerializer,
        String zkServerString,
        int sessionTimeout,
        int connectionTimeout) {
        this.zkSerializer = zkSerializer;
        this.zkServerString = zkServerString;
        this.sessionTimeout = sessionTimeout;
        this.connectionTimeout = connectionTimeout;
    }

    public ZkServiceImpl(String zkServerString, int sessionTimeout, int connectionTimeout) {
        this(new ZkStringSerializer(), zkServerString, sessionTimeout, connectionTimeout);
    }

    public ZkServiceImpl(ZkClient zkClient) {
        this.zkClient = zkClient;
        this.zkSerializer = new ZkStringSerializer();
        this.zkClient.setZkSerializer(zkSerializer);
    }

    @Override
    public void start() {
        lock.lock();
        try {
            if (zkClient == null) {
            	logger.info("Start zkClient.");
                zkClient = new ZkClient(zkServerString, sessionTimeout, connectionTimeout, zkSerializer);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() {
        zkClient.close();
    }

    @Override
    public INode getRoot() {
        return getNode(ROOT_PATH);
    }

    @Override
    public INode getNode(String identifier) {
        if (identifier != null) {
            identifier = identifier.trim();
        }
        return new NodeImpl(zkClient, identifier);
    }

    @Override
    public INode createNode(String identifier, Object value) {
        if (identifier != null) {
            identifier = identifier.trim();
        }
        String path = zkClient.create(identifier, value, CreateMode.PERSISTENT);
        return new NodeImpl(zkClient, path);
    }

    @Override
    public boolean deleteNode(String identifier) {
        if (identifier != null) {
            identifier = identifier.trim();
        }
        return zkClient.delete(identifier);
    }

    @Override
    public boolean deleteNode(String identifier, boolean recursive) {
        if (identifier != null) {
            identifier = identifier.trim();
        }
        return zkClient.deleteRecursive(identifier);
    }

    /**
     * 拷贝节点时，我们需要确定newIdentifier，第二步操作目的是用toIdentifier替换源节点及其子节点中的fromIdentifier
     * @param node:源节点或者其子节点
     * @param fromIdentifier
     * @param toIdentifier
     */
    private void copy (INode node, String fromIdentifier, String toIdentifier) {
        String oldIdentifier = node.getIdentifier();
        String newIdentifier = toIdentifier + oldIdentifier.substring(fromIdentifier.length(), oldIdentifier.length());
        zkClient.createPersistent(newIdentifier, node.getNodeValue());
    }
    
    @Override
    public INode copyNode(INode originalNode, String newIdentifier) {
        if (!originalNode.exists()) {
            return null;
        }
        INode newNode = new NodeImpl(zkClient, newIdentifier);
        if (newNode.exists()) {
            return null;
        }
        
        List<INode> nodes = new ArrayList<INode>();
        nodes.add(originalNode);
        String fromIdentifier = originalNode.getIdentifier();
        while (nodes.size() > 0) {
            INode node = nodes.remove(0);
            for (INode child : node.getChildren()) {
                nodes.add(child);
            }
            copy(node, fromIdentifier, newIdentifier);
        }
        return newNode;
    }

    @Override
    public INode copyNode(String originalIdentifier, String newIdentifier) {
        return copyNode(getNode(originalIdentifier), newIdentifier);
    }

    @Override
    public List<String> subscribeChildChanges(String identifier, IChildListener childListener) {
    	ZkChildListenerAdapter zkChildListenerAdapter = new ZkChildListenerAdapter(childListener);
        return zkClient.subscribeChildChanges(identifier, zkChildListenerAdapter);
    }

    @Override
    public void UnSubscribeChildChanges(String identifier, IChildListener childListener) {
    	ZkChildListenerAdapter zkChildListenerAdapter = new ZkChildListenerAdapter(childListener);
    	zkClient.unsubscribeChildChanges(identifier, zkChildListenerAdapter);
    }

    @Override
    public void subscribeNodeValueChange(String identifier, INodeListener nodeListener) {
    	ZkNodeValueListenerAdapter zkNodeValueListenerAdapter = new ZkNodeValueListenerAdapter(nodeListener);
    	zkClient.subscribeDataChanges(identifier, zkNodeValueListenerAdapter);
    }

    @Override
    public void unSubscribeNodeValueChange(String identifier, INodeListener nodeListener) {
    	ZkNodeValueListenerAdapter zkNodeValueListenerAdapter = new ZkNodeValueListenerAdapter(nodeListener);
    	zkClient.unsubscribeDataChanges(identifier, zkNodeValueListenerAdapter);
    }

    @Override
    public void unSubscribeAll() {
    	zkClient.unsubscribeAll();
    }

}
