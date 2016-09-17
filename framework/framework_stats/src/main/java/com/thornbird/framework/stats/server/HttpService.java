package com.thornbird.framework.stats.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.jboss.com.sun.net.httpserver.HttpHandler;
import org.jboss.com.sun.net.httpserver.HttpServer;

import com.thornbird.framework.stats.server.impl.ClearStatsHandler;
import com.thornbird.framework.stats.server.impl.DefaultStatsJsonHandler;
import com.thornbird.framework.stats.server.impl.DefaultStatsTextHandler;
import com.thornbird.framework.stats.server.impl.FileResourceHandler;
import com.thornbird.framework.stats.server.impl.FolderResourceHandler;
import com.thornbird.framework.stats.server.impl.GlobalStatsJsonHandler;
import com.thornbird.framework.stats.server.impl.GlobalStatsTextHandler;
import com.thornbird.framework.stats.server.impl.MissingFileHandler;
import com.thornbird.framework.stats.server.impl.ResetStatsHandler;

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
		
		addContext("/file", new FileResourceHandler("/test.html"));
		addContext("/folder/test.js", new FolderResourceHandler("/folder"));
		addContext("/missfile", new MissingFileHandler());
		addContext("/stats", new DefaultStatsJsonHandler());
		addContext("/textstats", new DefaultStatsTextHandler());
		addContext("/globalstats", new GlobalStatsJsonHandler());
		addContext("/globaltextstats", new GlobalStatsTextHandler());
		addContext("/clearstats", new ClearStatsHandler());
		addContext("/resetstats", new ResetStatsHandler());
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
