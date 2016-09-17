package com.thornbird.framework.zookeeper.adapter;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;

import com.thornbird.framework.zookeeper.listener.IChildListener;


/**
 * 适配器：将一个类的接口转化为客户希望的另外一个接口
 * 将zookeeper提供的IZkChildListener接口转化为自己设计的IChildListener接口，提供给外部
 * @author Hyman
 */
public class ZkChildListenerAdapter implements IZkChildListener {
    
    private IChildListener childListener;
    
    public ZkChildListenerAdapter(IChildListener childListener) {
        this.childListener = childListener;
    }

    @Override
    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
        childListener.handleChildChange(parentPath, currentChilds);
    }

}
