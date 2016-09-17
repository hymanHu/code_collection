package com.thornbird.framework.zookeeper.listener;


/**
 * 为外部设计的监听器接口，监听节点改变或删除
 * @author Hyman
 */
public interface INodeListener {

    public void handleValueChange (String identifier, Object value) throws Exception;
    
    public void handleNodeDelete (String identifier) throws Exception;
}
