package com.cpkf.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MysqlStats {
	private static String USER_NAME = "lj";
	private static String USER_PASSWORD = "livejournal";
	private String connectionUrl;

	public MysqlStats(String host, int port) {
		this.connectionUrl = String.format("jdbc:mysql://%s:%d/?user=%s&password=%s", host, port, USER_NAME,
				USER_PASSWORD);
	}
	
	private Map<String, String> getStats() {
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = MySqlConnectionPool.getInstance().getConnection(connectionUrl);
		if (conn == null) {
			return null;
		}
		Statement state = null;
		try {
			state = conn.createStatement();
			ResultSet result = state.executeQuery("show global status;");
			while (result.next()) {
				map.put(result.getString(1), result.getString(2));
			}
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (state != null) {
					state.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return map;
	}
	
	public static void main(String[] args) {
		MysqlStats mysqlStats = new MysqlStats("172.17.20.100", 3306);
		System.out.println(mysqlStats.getStats());
	}

}
