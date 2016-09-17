package com.thornbird.framework.zookeeper.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import com.thornbird.framework.zookeeper.exception.ZookeeperException;
import com.thornbird.framework.zookeeper.service.INode;


public class NodeImpl implements INode {
    
    private ZkClient zkClient;
    
    private static final String EMPTY_STRING = "";
    
    private String identifier;
    
    private static final char SEPARATOR = '/';
    
    /**
     * 将identifier按照分隔符拆分成不同的段
     */
    private String[] segments;

    public NodeImpl(ZkClient zkClient, String identifier) {
        if (zkClient == null) {
            throw new ZookeeperException("Zookeeper can not be connected.");
        }
        
        this.zkClient = zkClient;
        
        if (identifier == null || identifier.charAt(0) != SEPARATOR) {
            throw new ZookeeperException("Invalid identifier, it should start with /.");
        }
        
        this.identifier = identifier;
        
        this.segments = calculateSegments(identifier);
    }

    @Override
    public boolean exists() {
        return zkClient.exists(identifier);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getNodeName() {
        int segmentsLength = segments.length;
        if (segmentsLength == 0) {
            return EMPTY_STRING;
        }
        return segments[segmentsLength - 1];
    }

    @Override
    public Object getNodeValue() {
        return zkClient.readData(identifier);
    }
    
    @Override
	public String getStringValue() {
		return (String)getNodeValue();
	}

	@Override
	public Integer getIntValue() {
		Integer val = null;
		if (!exists()) {
			return val;
		}
		
		try {
			val = Integer.parseInt(getStringValue().trim());
		} catch (NumberFormatException e) {
			val = null;
		}
		
		return val;
	}

	@Override
    public INode getParent() {
        if (segments.length == 0) {
            return null;
        } else if (segments.length == 1) {
            return new NodeImpl(zkClient, ZkServiceImpl.ROOT_PATH);
        }
        return new NodeImpl(zkClient, identifier.substring(0, identifier.lastIndexOf(SEPARATOR)));
    }

    @Override
    public INode[] getChildren() {
        List<String> children = zkClient.getChildren(identifier);
        int childrenSize = children.size();
        
        String prefix = "";
        if (segments.length == 0) {
            prefix = String.format("%s", SEPARATOR);
        } else {
            prefix = String.format("%s%s", identifier, SEPARATOR);
        }
        
        INode[] nodeChildren = new NodeImpl[childrenSize];
        for (int i = 0;i < childrenSize;i ++) {
            nodeChildren[i] = new NodeImpl(zkClient, String.format("%s%s", prefix, children.get(i)));
        }
        
        return nodeChildren;
    }

    @Override
    public INode getChild(String nodeName) {
        List<String> children = zkClient.getChildren(identifier);
        
        String prefix = "";
        if (segments.length == 0) {
            prefix = String.format("%s", SEPARATOR);
        } else {
            prefix = String.format("%s%s", identifier, SEPARATOR);
        }
        
        for (String childName : children) {
            if (nodeName.equals(childName)) {
                return new NodeImpl(zkClient, String.format("%s%s", prefix, childName));
            }
        }
        
        return null;
    }

    @Override
    public void saveNode(Object value) {
        if (exists()) {
            updateNode(value);
        } else {
            createNode(value);
        }
    }

    /* 
     * 实例化node的时候已经保证了identifier的有效性，该接口不会被调用
     */
    @Override
    public void createNode(Object value) {
        zkClient.create(getIdentifier(), value, CreateMode.PERSISTENT);
    }

    @Override
    public void updateNode(Object value) {
        zkClient.writeData(identifier, value);
    }

    @Override
    public String addChildNode(String name, Object value) {
        if (name.indexOf(SEPARATOR) >= 0) {
            throw new ZookeeperException("Child name can not contain '/'");
        }
        
        String childIdentifier = "";
        if (segments.length == 0) {
            childIdentifier = String.format("%s%s", SEPARATOR, name);
        } else {
            childIdentifier = String.format("%s%s%s", identifier, SEPARATOR, name);
        }
        
        return zkClient.create(childIdentifier, value, CreateMode.PERSISTENT);
    }

    @Override
    public boolean deleteChildNode(String name, boolean recursive) {
        if (name.indexOf(SEPARATOR) >= 0) {
            throw new ZookeeperException("Child name can not contain '/'");
        }
        
        String childIdentifier = "";
        if (segments.length == 0) {
            childIdentifier = String.format("%s%s", SEPARATOR, name);
        } else {
            childIdentifier = String.format("%s%s%s", identifier, SEPARATOR, name);
        }
        
        if (recursive) {
             return zkClient.deleteRecursive(childIdentifier);
        } else {
        	return zkClient.delete(childIdentifier);
        }
    }

    @Override
    public Map<String, String> dumpChildren() {
        Map<String, String> map = new HashMap<String, String>();
        
        INode[] children = getChildren();
        for (INode child : children) {
            map.put(child.getNodeName(), (String)child.getNodeValue());
        }
        
        return map;
    }

    /**
     * 根据identifier按照分隔符计算分段
     * 对字符串数组进行去空操作
     * @param identifier
     * @return String[]
     */
    private String[] calculateSegments(String identifier) {
        String[] segments = identifier.split(SEPARATOR + "");
        int segmentsLength = segments.length;
        
        List<String> segmentList = new ArrayList<String>(segmentsLength);
        for (String segment : segments) {
            if (!segment.isEmpty()) {
                segmentList.add(segment);
            }
        }
        
        return segmentList.toArray(new String[0]);
    }
    
    public static void main(String[] args) {
    }
}
