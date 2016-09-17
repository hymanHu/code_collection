package com.thornbird.framework.zookeeper.adapter;

import org.I0Itec.zkclient.IZkDataListener;

import com.thornbird.framework.zookeeper.listener.INodeListener;


/**
 * 适配器，将zookeeper提供的IZkDataListener接口转化为自己设计的INodeValueListener接口，为外部调用
 * @author Hyman
 */
public class ZkNodeValueListenerAdapter implements IZkDataListener {

    private INodeListener nodeValueListener;
    
    public ZkNodeValueListenerAdapter(INodeListener nodeValueListener) {
        this.nodeValueListener = nodeValueListener;
    }

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        nodeValueListener.handleValueChange(dataPath, data);
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        nodeValueListener.handleNodeDelete(dataPath);
    }

}
