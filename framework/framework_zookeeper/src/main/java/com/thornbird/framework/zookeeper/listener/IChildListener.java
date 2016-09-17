package com.thornbird.framework.zookeeper.listener;

import java.util.List;


/**
 * 为外部设计的监听器接口，监听节点子节点变化情况
 * @author Hyman
 */
public interface IChildListener {
    
    public void handleChildChange(String parentIdentifier, List<String> childrenIdentifier) throws Exception;
    
}
