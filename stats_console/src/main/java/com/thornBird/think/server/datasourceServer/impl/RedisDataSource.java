package com.thornBird.think.server.datasourceServer.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thornBird.think.server.datasourceServer.StatsDataSource;
import com.thornBird.think.server.datasourceServer.socket.impl.SocketSessionImpl;


/**
 * KEYS: 需要统计的项
 * 从 redisInfo 中按照 KEYS 转化为 dataMap，再转化为 stats
 * @author hyman
 */
public class RedisDataSource extends StatsDataSource {

	private static final Logger logger = LoggerFactory.getLogger(RedisDataSource.class);

	private String host;
	private int port;
	private TreeMap<String, Long> dataMap = new TreeMap<String, Long>();
	private static final List<String> KEYS = new ArrayList<String>();
	static {
		KEYS.add("used_memory");
		KEYS.add("max_memory");
	}

	public RedisDataSource(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void loadStats() {
		SocketSessionImpl redisSocket = new SocketSessionImpl(host, port);

		String redisInfo = null;
		try {
			redisSocket.open();
			redisInfo = redisSocket.execute("info");
			String redisMaxMemory = getMaxMemory(redisSocket.execute("config get maxmemory"));
			redisInfo = String.format("%s%s%s", redisInfo, "\n", redisMaxMemory);
		} catch (IOException e) {
			logger.error(String.format("%s%s%s%s%s", "Load redis(", host, "-", port, ") info error."), e);
			e.printStackTrace();
		} finally {
			try {
				redisSocket.close();
			} catch (Exception e2) {
				logger.error("Close socket error.", e2);
			}
		}
		
		convertToDataMap(redisInfo);
		
		if (dataMap != null && dataMap.size() > 0) {
			// pie gram
			stats.addPiegram("memory", "used", Double.valueOf(dataMap.get("used_memory")));
			stats.addPiegram("memory", "free",
					Double.valueOf(dataMap.get("max_memory") - dataMap.get("used_memory")));
			
			// gauge
			stats.addGauge("used_memory", Double.valueOf(dataMap.get("used_memory")));
			stats.addGauge("max_memory", Double.valueOf(dataMap.get("max_memory")));
		}
	}

	/**
	 * @param maxMemorySting
	 *            <pre>
	 * *2
	 * $9
	 * maxmemory
	 * $11
	 * 21474836480
	 * </pre>
	 * @return
	 */
	private String getMaxMemory(String maxMemorySting) {
		String[] lines = maxMemorySting.split("\n");
		return String.format("%s%s", "max_memory:", lines[4].trim());
	}

	private void convertToDataMap(String redisInfo) {
		if (redisInfo == null) {
			return;
		}

		String[] lines = redisInfo.split("\n");
		for (String line : lines) {
			if (line.indexOf(":") > 0) {
				String[] keyAndValue = line.split(":");
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
		RedisDataSource redisDataSource = new RedisDataSource("172.17.20.73", 6120);
		redisDataSource.loadStats();
		logger.debug(redisDataSource.toJson());
	}

}
