package com.thornbird.framework.zookeeper.exception;


public class ZookeeperException extends RuntimeException {
    
    private static final long serialVersionUID = 2778316312871998377L;

    public ZookeeperException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZookeeperException(String message) {
        super(message);
    }
    
}
