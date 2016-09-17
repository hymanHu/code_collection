package com.thornbird.framework.stats.server.impl;

import java.io.IOException;

import org.jboss.com.sun.net.httpserver.HttpExchange;

import com.thornbird.framework.stats.server.AbstractHttpHandler;

/**
 * 文件加载控制器,传入文件的classpath
 * @author hyman
 */
public class FileResourceHandler extends AbstractHttpHandler {
	private String body;
	
	public FileResourceHandler(String filePath) {
		body = loadResource(filePath);
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		render(body, arg0);
	}

	public static void main(String[] args) {
	}

}
