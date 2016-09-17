package com.cpkf.cache.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {
	
	private final static Logger logger = LoggerFactory.getLogger(RedisTest.class);
	
	private JedisPool jedisPool;
	private Jedis jedis;
	
	public RedisTest() {
//		jedisPool = new JedisPool(new JedisPoolConfig(), "172.17.25.174");
//		jedis = jedisPool.getResource();
//		jedis.auth("");
		
		jedis = new Jedis("172.17.25.174", 6379);
	}
	
	public void basicOperation() {
		jedis.flushAll();
		
		jedis.set("name", "hyman");
		logger.debug(String.format("%s%s", "==添加数据==", jedis.get("name")));
		
		jedis.append("name", "hj");
		logger.debug(String.format("%s%s", "==追加数据==", jedis.get("name")));
		jedis.set("name", "hj");
		logger.debug(String.format("%s%s", "==修改数据==", jedis.get("name")));
		
		jedis.mset("age1", "19", "age2", "20");
		logger.debug(String.format("%s%s", "==批量插入数据==", jedis.mget("age1", "age2")));
		
		jedis.del("name");
		logger.debug(String.format("%s%s", "==删除数据==", jedis.get("name")));
		
		jedis.lpush("nameList", "1");
		jedis.lpush("nameList", "3");
		jedis.lpush("nameList", "2");
		jedis.lpush("nameList", "4");
		List<String> nameList = jedis.lrange("nameList", 0, -1);
		logger.debug(String.format("%s%s", "==list操作==", nameList.toString()));
		nameList = jedis.sort("nameList");
		logger.debug(String.format("%s%s", "==list操作(排序)==", nameList.toString()));
		
		jedis.sadd("nameSet", "1");
		jedis.sadd("nameSet", "2");
		jedis.sadd("nameSet", "3");
		jedis.sadd("nameSet", "4");
		Set<String> nameSet = jedis.smembers("nameSet");
		logger.debug(String.format("%s%s", "==set操作==", nameSet.toString()));
		logger.debug(String.format("%s%s", "==set操作(判断某元素是否在set中)==", jedis.sismember("nameSet", "2")));
		logger.debug(String.format("%s%s", "==set操作(返回set元素个数)==", jedis.scard("nameSet")));
		logger.debug(String.format("%s%s", "==set操作(随机返回set中某个元素)==", jedis.srandmember("nameSet")));
		
		jedis.zadd("sortSet", 1, "1");
		jedis.zadd("sortSet", 4, "2");
		jedis.zadd("sortSet", 3, "3");
		jedis.zadd("sortSet", 2, "4");
		Set<String> sortSet = jedis.zrange("sortSet", 0, -1);
		logger.debug(String.format("%s%s", "==set操作(排序)==", sortSet));
		
		Map<String, String> myHash = new HashMap<String, String>();
		myHash.put("hj", "33");
		myHash.put("ch", "34");
		myHash.put("joo", "30");
		myHash.put("ben", "34");
		jedis.hmset("ageHash", myHash);
		List<String> ageHash = jedis.hmget("ageHash", new String[]{"hj", "ch", "joo", "ben"});
		Set<String> ageHashSet = jedis.hkeys("ageHash");
		List<String> ageHashList = jedis.hvals("ageHash");
		Map<String, String> ageMap = jedis.hgetAll("ageHash");
		logger.debug(String.format("%s%s", "==hash操作(返回valueList)==", ageHash));
		logger.debug(String.format("%s%s", "==hash操作(返回keySet)==", ageHashSet));
		logger.debug(String.format("%s%s", "==hash操作(返回valueList)==", ageHashList));
		logger.debug(String.format("%s%s", "==hash操作(返回map)==", ageMap));
		
		logger.debug(String.format("%s%s", "==查询操作==", jedis.keys("*")));
		logger.debug(String.format("%s%s", "==查询操作==", jedis.keys("name*")));
	}

	public static void main(String[] args) {
		RedisTest test = new RedisTest();
		test.basicOperation();
	}

}
