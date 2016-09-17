package com.cpkf.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * mysql连接池运用 
 * step1：单列该类 
 * step2：从连接池中获取连接(对象池的运用) 
 * step3：清除连接池
 * @author hyman
 */
public class MySqlConnectionPool {

	private static MySqlConnectionPool mysqlConnectionPool = null;
	// key: connectionUrl
	private ConcurrentHashMap<String, ObjectPool> connectionPoolMap = new ConcurrentHashMap<String, ObjectPool>();
	private static String DRIVER = "com.mysql.jdbc.Driver";

	// private static String USER_NAME = "lj";
	// private static String USER_PASSWORD = "livejournal";

	private MySqlConnectionPool() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
			 * 不设置用户名密码则需要在connectionUrl中指定
			 */
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectionUrl, null);
			// ConnectionFactory connectionFactory = new
			// DriverManagerConnectionFactory(connectionUrl, USER_NAME,
			// USER_PASSWORD);

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
			e.printStackTrace();
		}

		return conn;
	}

	public synchronized void clear() {
		System.out.println("Close mysql connection pool.");
		for (ObjectPool objectPool : connectionPoolMap.values()) {
			try {
				objectPool.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		connectionPoolMap.clear();
	}

	public static void main(String[] args) {
		// MySqlConnectionPool mysqlPool = MySqlConnectionPool.getInstance();
		// mysqlPool.getConnection("jdbc:mysql://172.17.20.112:3306/?user=lj&password=livejournal");

		Runnable run = new Runnable() {
			public void run() {
				Connection conn = MySqlConnectionPool.getInstance().getConnection("jdbc:mysql://172.17.20.100:3306/?user=lj&password=livejournal");
				Statement state = null;
				try {
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
			while (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
				System.out.println("Thread pool not close.");
			}

			Thread.sleep(2000);
			MySqlConnectionPool.getInstance().clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
