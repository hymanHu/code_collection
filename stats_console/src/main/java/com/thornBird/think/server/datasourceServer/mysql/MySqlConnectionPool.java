package com.thornBird.think.server.datasourceServer.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 构建mysql连接池，获取连接
 * @author hyman
 */
public class MySqlConnectionPool {
	
	private static final Logger logger = LoggerFactory.getLogger(MySqlConnectionPool.class);
	
	private static MySqlConnectionPool mysqlConnectionPool = null;
	// key: connectionUrl
	private ConcurrentHashMap<String, ObjectPool> connectionPoolMap = new ConcurrentHashMap<String, ObjectPool>();
	private static String DRIVER = "com.mysql.jdbc.Driver";

	private MySqlConnectionPool() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			logger.error("Init mysql driver error.", e);
		}
	}

	public static MySqlConnectionPool getInstance() {
		if (mysqlConnectionPool == null) {
			synchronized (MySqlConnectionPool.class) {
				if (mysqlConnectionPool == null) {
					mysqlConnectionPool = new MySqlConnectionPool();
				}
			}
		}
		return mysqlConnectionPool;
	}

	public synchronized Connection getConnection(String connectionUrl) {
		if (!connectionPoolMap.containsKey(connectionUrl)) {
			// 对象池
			GenericObjectPool connectionPool = new GenericObjectPool(null);
			connectionPool.setMaxActive(500);
			connectionPool.setMaxIdle(10);
			connectionPool.setMaxWait(2000);
			connectionPool.setMinIdle(5);

			/*
			 * 对象池工厂，管理对象池(常用： createPool,destroyPool)
			 */
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectionUrl, null);

			// 池化对象工厂，封装了真正的connections
			@SuppressWarnings("unused")
			PoolableObjectFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,
					connectionPool, null, null, false, true);

			connectionPoolMap.putIfAbsent(connectionUrl, connectionPool);
		}

		ObjectPool connectionPool = connectionPoolMap.get(connectionUrl);
		PoolingDataSource dataSource = new PoolingDataSource(connectionPool);
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("Get Mysql connection error.", e);
		}
		return conn;
	}

	public synchronized void clear() throws Exception {
		logger.debug("Close mysql connection pool.");
		for (ObjectPool objectPool : connectionPoolMap.values()) {
			objectPool.close();
		}
		connectionPoolMap.clear();
	}

	public static void main(String[] args) {

		Runnable run = new Runnable() {
			public void run() {
				Connection conn = null;
				Statement state = null;
				try {
					conn = MySqlConnectionPool.getInstance().getConnection("jdbc:mysql://localhost:3306/?user=lj&password=livejournal");
					state = conn.createStatement();
					ResultSet result = state.executeQuery("select 1;");
					while (result.next()) {
						System.out.println(String.format("%s%s%s", Thread.currentThread().getName(), " result: ",
								result.getInt(1)));
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
			}
		};

		try {
			ExecutorService threadPool = Executors.newFixedThreadPool(10);
			for (int i = 0; i < 10; i++) {
				threadPool.submit(run);
			}
			threadPool.shutdown();

			Thread.sleep(2000);
			MySqlConnectionPool.getInstance().clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
