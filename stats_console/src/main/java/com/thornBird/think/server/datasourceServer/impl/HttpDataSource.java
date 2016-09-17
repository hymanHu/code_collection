package com.thornBird.think.server.datasourceServer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.thornBird.think.server.datasourceServer.IDataSource;


/**
 * http数据源
 * @author hyman
 */
public class HttpDataSource implements IDataSource {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpDataSource.class);

	private String uri;

	public HttpDataSource(String uri) {
		this.uri = uri;
	}

	public String toJson() {
		RestTemplate client = new RestTemplate();
		return client.getForObject(uri, String.class);
	}
	
	public static void main(String[] args) {
		HttpDataSource httpDataSource = new HttpDataSource("http://172.17.20.21:9999/stats");
		logger.debug(httpDataSource.toJson());
	}

}
