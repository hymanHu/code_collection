package com.thornbird.framework.zookeeper.service;

import java.util.Map;


public interface INode {
    
    /**
     * 判断节点是否存在
     * @return boolean
     */
    public boolean exists();
    
    /**
     * 获取节点标识（节点全路径）
     * @return identifier
     */
    public String getIdentifier();
    
    /**
     * 获取节点name值
     * @return
     */
    public String getNodeName();
    
    /**
     * 获取节点值
     * @return object
     */
    public Object getNodeValue();
    
    public String getStringValue();
    
    public Integer getIntValue();
    
    /**
     * 获取父节点
     * @return parent node
     */
    public INode getParent();
    
    /**
     * 获取子节点集合
     * @return children nodes
     */
    public INode[] getChildren();
    
    /**
     * 获取指定子节点
     * @param nodeName
     * @return child node which name match nodeName
     */
    public INode getChild(String nodeName);
    
    /**
     * 保存节点
     * @param value
     */
    public void saveNode(Object value);
    
    /**
     * 创建节点
     * @param value
     */
    public void createNode(Object value);
    
    /**
     * 修改节点
     * @param value
     */
    public void updateNode(Object value);
    
    /**
     * 创建子节点
     * @param name
     * @param value
     * @return identifier
     */
    public String addChildNode(String name, Object value);
    
    /**
     * 删除子节点
     * @param name
     * @param recursive 是否递归删除
     * @return boolean
     */
    public boolean deleteChildNode(String name, boolean recursive);
    
    /**
     * 将子节点转换为map
     * @return map
     */
    public Map<String, String> dumpChildren();
    
}
