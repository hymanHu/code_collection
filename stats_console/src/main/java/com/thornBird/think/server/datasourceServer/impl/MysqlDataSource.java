package com.thornBird.think.server.datasourceServer.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thornBird.think.server.datasourceServer.StatsDataSource;
import com.thornBird.think.server.datasourceServer.mysql.MysqlStats;

/**
 * mysql 数据源
 * @author hyman
 */
public class MysqlDataSource extends StatsDataSource {

	private static final Logger logger = LoggerFactory.getLogger(MysqlDataSource.class);
	
	private String host;
	private int port;
	private static final List<String> KEYS = new ArrayList<String>();
	static {
		// MySQL Binary logs
		KEYS.add("Binlog_cache_disk_use");
		KEYS.add("Binlog_cache_use");

        // MySQL connections
		KEYS.add("Aborted_clients");
		KEYS.add("Aborted_connects");
		KEYS.add("Connections");
		KEYS.add("Max_used_connections");
		KEYS.add("Threads_connected");

        // MySQL Files and Tables
		KEYS.add("Open_files");
		KEYS.add("Open_streams");
		KEYS.add("Open_table_definitions");
		KEYS.add("Open_tables");
		KEYS.add("Opened_files");
		KEYS.add("Opened_table_definitions");
		KEYS.add("Opened_tables");

        // MySQL Network Traffic
		KEYS.add("Bytes_received");
		KEYS.add("Bytes_sent");

        // MySQL Transaction Handler
		KEYS.add("Handler_commit");
		KEYS.add("Handler_rollback");
		KEYS.add("Handler_savepoint");
		KEYS.add("Handler_savepoint_rollback");
	}
	
	public MysqlDataSource(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void loadStats() {
		MysqlStats mysqlStats = new MysqlStats(host, port);
		Map<String, String> dataMap = null;
		try {
			dataMap = mysqlStats.getStatsMap();
		} catch (Exception e) {
			logger.error(String.format("%s%s%s%s%s", "Get mysql(", host, "-", port, ") status error."), e);
			e.printStackTrace();
		}
		if (dataMap == null || dataMap.isEmpty()) {
			return;
		}
		
		for (String key : KEYS) {
			stats.addGauge(key, Double.valueOf(dataMap.get(key)));
		}
	}
	
	public static void main(String[] args) {
		MysqlDataSource mysqlDataSource = new MysqlDataSource("172.17.20.73", 3306);
		mysqlDataSource.loadStats();
		logger.info(mysqlDataSource.toJson());
	}

}
