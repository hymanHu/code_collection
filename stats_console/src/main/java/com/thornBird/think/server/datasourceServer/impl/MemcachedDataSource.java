package com.thornBird.think.server.datasourceServer.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thornBird.think.server.datasourceServer.StatsDataSource;
import com.thornBird.think.server.datasourceServer.socket.impl.SocketSessionImpl;

/**
 * memcached 数据源
 * @author hyman
 */
public class MemcachedDataSource extends StatsDataSource {
	
	private static final Logger logger = LoggerFactory.getLogger(MemcachedDataSource.class);
	
	private String host;
	private int port;
	private Map<String, Long> dataMap = new TreeMap<String, Long>();
	private static final List<String> KEYS = new ArrayList<String>();
	static {
		KEYS.add("curr_items");
		KEYS.add("total_items");
	}

	public MemcachedDataSource(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void loadStats() {
		SocketSessionImpl memcachedSocket = new SocketSessionImpl(host, port);
		
		String memcachedInfo = null;
		try {
			memcachedSocket.open();
			memcachedInfo = memcachedSocket.execute("stats");
		} catch (IOException e) {
			logger.error(String.format("%s%s%s%s%s", "Get memcached(", host, "-", port, ") info error."), e);
			e.printStackTrace();
		} finally {
			try {
				memcachedSocket.close();
			} catch (IOException e) {
				logger.error("Close socket error.", e);
				e.printStackTrace();
			}
		}
		
		convertToMap(memcachedInfo);
		
		if (dataMap != null && dataMap.size() > 0) {
			stats.addGauge("curr_items", Double.valueOf(dataMap.get("curr_items")));
			stats.addGauge("curr_items", Double.valueOf(dataMap.get("curr_items")));
		}
	}
	
	private void convertToMap(String memcachedInfo) {
		if (memcachedInfo == null) {
			return;
		}
		
		String[] lines = memcachedInfo.split("\n");
		for (String line : lines) {
			if (line.indexOf(" ") > 1) {
				String[] keyAndValue = line.split(" ");
				String key = keyAndValue[0].trim();
				String value = keyAndValue[1].trim();
				if (!KEYS.contains(key)) {
					continue;
				}
				dataMap.put(key, Long.parseLong(value));
			}
		}
	}

	public static void main(String[] args) {
		MemcachedDataSource memcachedDataSource = new MemcachedDataSource("localhost", 11211);
		memcachedDataSource.loadStats();
		logger.debug(memcachedDataSource.toJson());
	}
}
