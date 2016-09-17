package com.thornBird.think.server.datasourceServer.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlStats {

	private static final Logger logger = LoggerFactory.getLogger(MysqlStats.class);

	private String connectionUrl;
	// 默认mysql的userName & password，在StatsNodeServer中会根据zookeeper上配置重置
	public static String USER_NAME = "lj";
	public static String PASSWORD = "livejournal";

	public MysqlStats(String host, int port) {
		this.connectionUrl = String.format("jdbc:mysql://%s:%d/?user=%s&password=%s&autoReconnect=true&failOverReadOnly=false", host, port, USER_NAME, PASSWORD);
	}

	public Map<String, String> getStatsMap() {
		Map<String, String> map = new HashMap<String, String>();
		
		Connection conn = MySqlConnectionPool.getInstance().getConnection(connectionUrl);
		if (conn == null) {
			return null;
		}
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery("show global status;");
			while (result.next()) {
				map.put(result.getString(1), result.getString(2));
			}
		} catch (Exception e) {
			logger.error("Execute mysql query error:", e);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		
		logger.debug("mysql[{}] stats: {}", connectionUrl, map.toString());
		return map;
	}

	public static void main(String[] args) {
		try {
			MysqlStats mysqlStats = new MysqlStats("172.17.20.73", 3306);
			
			while(true) {
				mysqlStats.getStatsMap();
				Thread.sleep(10000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
