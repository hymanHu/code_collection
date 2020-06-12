package com.thornBird.config;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

/**
 * @Description: Redis Config
 * @author: HymanHu
 * @date: 2019-08-14 10:48:18
 */
@Configuration
@EnableCaching // 启用缓存，使用Lettuce客户端，自动注入配置的方式
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport{
	
	/**
	 * get RedisTemplate<String, Object>
	 * @param factory		redis connection factory
	 * @return RedisTemplate<String, Object>
	 */
	@Bean
	@SuppressWarnings("all")
	public RedisTemplate<String, Object> redisTemplate (RedisConnectionFactory factory){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(factory);
		
		// String 序列化方式
		StringRedisSerializer stringSerializer = new StringRedisSerializer();
		// Jackson 序列化方式
		Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(DefaultTyping.NON_FINAL);
		jacksonSerializer.setObjectMapper(objectMapper);
		
		// key 采用 stringSerializer，value 采用 jacksonSerializer
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(jacksonSerializer);
		redisTemplate.setHashValueSerializer(jacksonSerializer);
		
		return redisTemplate;
	}
	
	// 自定义缓存key的生成策略，默认的生成策略是乱码
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				Arrays.asList(params).stream().forEach(item -> {
					sb.append(item.toString());
				});
				return sb.toString();
			}
		};
	}
	
	/**
	 * RedisCacheConfiguration 默认序列化方式：
	 * key ---- StringRedisSerializer，value ---- JdkSerializationRedisSerializer
	 * (我们使用Jackson2JsonRedisSerializer作为value的序列化方式，cacheManager需要重新指定)
	 */
	@Bean
	@SuppressWarnings("all")
	public CacheManager cacheManager(LettuceConnectionFactory factory) {
		RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory);
		RedisSerializationContext.SerializationPair pair = 
				RedisSerializationContext.SerializationPair.fromSerializer(
						new Jackson2JsonRedisSerializer(Object.class));
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
		// default set
//		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		RedisCacheManager cacheManager = new RedisCacheManager(writer, config);
		return cacheManager;
	}
}
