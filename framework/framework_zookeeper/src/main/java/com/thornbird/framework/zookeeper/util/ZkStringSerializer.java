package com.thornbird.framework.zookeeper.util;

import java.io.UnsupportedEncodingException;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;


/**
 * String序列化和反序列化
 * @author Hyman
 */
public class ZkStringSerializer implements ZkSerializer {
    
    /**
     * 序列化编码
     */
    private String encoding;
    
    public ZkStringSerializer () {
        this("UTF-8");
    }
    
    public ZkStringSerializer (String encoding) {
        this.encoding = encoding;
    }

    @Override
    public byte[] serialize(Object data) throws ZkMarshallingError {
        if (data instanceof String) {
            try {
                return ((String)data).getBytes(encoding);
            } catch (Exception e) {
                return ((String)data).getBytes();
            }
        }
        return null;
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        if (bytes != null) {
            try {
                return new String(bytes, encoding);
            } catch (UnsupportedEncodingException e) {
                return new String(bytes);
            }
        }
        return null;
    }

}
