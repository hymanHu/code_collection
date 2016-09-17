package com.cpkf.basis.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.jboss.com.sun.net.httpserver.HttpHandler;
import org.jboss.com.sun.net.httpserver.HttpServer;

public class HttpService {

	private int port = 9999;
	// 最大连接数
	private int backlog = 30;
	private HttpServer httpServer;

	public HttpService(int port, int backlog) throws IOException {
		this.port = port;
		this.backlog = backlog;
		this.httpServer = HttpServer.create(new InetSocketAddress(this.port), this.backlog);
	}
	
	/**
	 * 为 httpserver 添加请求路劲以及相应的控制器
	 * @param uriPath
	 * @param httphandler
	 */
	public void addContext(String uriPath, HttpHandler httphandler) {
		httpServer.createContext(uriPath, httphandler);
	}
	
	public void start() {
		httpServer.start();
		
		addContext("/file", new FileResourceHandler("/testFile/test.html"));
		addContext("/folder/test.js", new FolderResourceHandler("/folder"));
		addContext("/missfile", new MissingFileHandler());
	}
	
	public void stop(int delay) {
		if (httpServer != null) {
			httpServer.stop(delay);
		}
	}

	public static void main(String[] args) throws IOException {
		HttpService httpService = new HttpService(9999, 30);
		httpService.start();
	}

}
