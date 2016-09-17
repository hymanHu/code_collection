package com.thornbird.framework.zookeeper.service;

import java.util.List;

import com.thornbird.framework.zookeeper.listener.IChildListener;
import com.thornbird.framework.zookeeper.listener.INodeListener;


public interface IZkService {

    /**
     * 根节点标识
     */
    static final String ROOT_PATH = "/";
    
    public void start();
    
    public void stop();
    
    /**
     * 获得根节点
     * @return
     */
    public INode getRoot();
    
    /**
     * 根据节点标识获取节点
     * @param identifier
     * @return
     */
    public INode getNode(String identifier);
    
    /**
     * 根据节点标识和值创建节点
     * @param identifier
     * @param value
     * @return 新创建的节点
     */
    public INode createNode(String identifier, Object value);
    
    /**
     * 根据节点标识删除节点
     * @param identifier
     * @return boolean
     */
    public boolean deleteNode(String identifier);
    
    /**
     * 递归删除节点
     * @param identifier
     * @param recursive
     * @return boolean
     */
    public boolean deleteNode(String identifier, boolean recursive);
    
    /**
     * 拷贝已知节点到新节点
     * @param originalNode
     * @param newIdentifier
     * @return 新节点
     */
    public INode copyNode(INode originalNode, String newIdentifier);
    
    /**
     * 根据源节点标识拷贝到新节点
     * @param originalIdentifier
     * @param newIdentifier
     * @return 新节点
     */
    public INode copyNode(String originalIdentifier, String newIdentifier);
    
    /**
     * 子节点变化通知（订阅）
     * @param identifier
     * @param childListener
     * @return 子节点标识集合
     */
    public List<String> subscribeChildChanges(String identifier, IChildListener childListener);
    
    /**
     * 取消子节点变化通知（订阅）
     * @param identifier
     * @param childListener
     */
    public void UnSubscribeChildChanges(String identifier, IChildListener childListener);
    
    /**
     * 节点变化通知（订阅）
     * @param identifier
     * @param nodeListener
     */
    public void subscribeNodeValueChange(String identifier, INodeListener nodeListener);
    
    /**
     * 取消节点变化通知（订阅）
     * @param identifier
     * @param nodeListener
     */
    public void unSubscribeNodeValueChange(String identifier, INodeListener nodeListener);
    
    /**
     * 取消所有订阅
     */
    public void unSubscribeAll();
}
