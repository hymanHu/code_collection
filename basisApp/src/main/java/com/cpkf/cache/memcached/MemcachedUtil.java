package com.cpkf.cache.memcached;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemcachedUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(MemcachedUtil.class);
	
	// 创建全局唯一实例
	private static MemCachedClient cachedClient = new MemCachedClient();

	static {
		// 设置服务器列表以及权重
		String[] servers = {"localhost:11211"};
		Integer[] weights = {3};
		
		// 获取socket连接池的实例对象
		SockIOPool pool = SockIOPool.getInstance();
		
		// 设置服务器信息
		pool.setServers(servers);
		pool.setWeights(weights);
		
		// 设置初始连接数、最大最小连接数以及最大处理时间
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaxIdle(1000*60*60*6);
		
		// 设置主线程睡眠时间
		pool.setMaintSleep(30);
		
		// 设置TCP参数，连接超时等
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);
		
		// 初始化连接池
		pool.initialize();
		
		// 压缩设置，超过指定大小的都被压缩
//		cachedClient.setCompressEnable(true);
//		cachedClient.setCompressThreshold(1024*1024);
	}
	
	public MemcachedUtil() {
	}

	public static boolean add(String key, Object value) {
		return cachedClient.add(key, value);
	}
	
	public static boolean add(String key, Object value, Integer expire) {
		return cachedClient.add(key, value, expire);
	}
	
	public static boolean put(String key, Object value) {
		return cachedClient.set(key, value);
	}
	
	public static boolean put(String key, Object value, Integer expire) {
		return cachedClient.set(key, value, expire);
	}
	
	public static boolean replace(String key, Object value) {
		return cachedClient.replace(key, value);
	}
	
	public static boolean replace(String key, Object value, Integer expire) {
		return cachedClient.replace(key, value, expire);
	}
	
	public static Object get(String key) {
		return cachedClient.get(key);
	}
	
	public static boolean delete(String key) {
		return cachedClient.delete(key);
	}

	public static void main(String[] args) {
		MemcachedUtil.put("hello", "hj");
		String a = (String) MemcachedUtil.get("hello");
		logger.debug(a);
		
		for (int i = 0 ;i < 100; i++) {
			boolean marray = false;
			if (i % 2 == 0) {
				marray = true;
			} else {
				marray = false;
			}
			UserBean userBean = new UserBean("hj" + i, i, marray);
			MemcachedUtil.put("user" + i, userBean);
		}
		
//		for (int i = 0 ;i < 100; i++) {
//			MemcachedUtil.delete("user" + i);
//		}
		
		for (int i = 0 ;i < 100; i++) {
			UserBean userBean = (UserBean) MemcachedUtil.get("user" + i);
			if (userBean == null) {
				logger.debug("no result.");
			} else {
				logger.debug(userBean.toString());
			}
		}
	}

}
