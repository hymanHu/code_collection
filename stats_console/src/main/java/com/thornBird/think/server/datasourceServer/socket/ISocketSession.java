package com.thornBird.think.server.datasourceServer.socket;

import java.io.IOException;

/**
 * 打开socket，运行命令，获取状态值，在此用于监控redis & memcache
 * @author hyman
 */
public interface ISocketSession {
	
	void open() throws IOException;

	String execute(String command) throws IOException;

	void close() throws IOException;
}
