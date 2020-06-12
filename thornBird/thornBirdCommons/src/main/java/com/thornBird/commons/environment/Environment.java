package com.thornBird.commons.environment;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.thornBird.commons.environment.bean.DbConfigProperties;
import com.thornBird.commons.environment.bean.Location;
import com.thornBird.commons.environment.bean.ServiceConfigProperties;
import com.thornBird.commons.environment.exception.EnvironmentFailedException;

/**
 * @Description: Environment
 * @author: HymanHu
 * @date: 2019-01-11 16:05:58  
 */
public class Environment {
	private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);
	
	private static final String DEFAULT_ENV_YAML_PATH = "/environment.yml";
	private static final String DEFAULT_ENV_JSON_PATH = "/environment.json";
	
	public String mainHost;
	public String state;
	public Map<String, Map<String, String>> expandHosts;
	public ServiceConfigProperties service;
	public Map<String, DbConfigProperties> jdbc;
	public Location location;
	
	private static Environment environment = null;
	
	private Environment() {
	}
	
	/**
	 * initialize Environment, Singleton
	 * @return Environment
	 */
	public static Environment initialize() {
		return initEnvironment(null, null);
	}
	
	/**
	 * initialization Environment
	 * @param inputStream		inputStream
	 * @param location			location
	 * @return Environment
	 */
	public static Environment initEnvironment(InputStream inputStream, Location location) {
		if (environment == null) {
			synchronized (Environment.class) {
				if (environment == null) {
					environment = initialization(inputStream, location);
				}
			}
		}
		return environment;
	}
	
	/**
	 * initialization environment
	 * @param inputStream		inputStream
	 * @param location			location
	 * @return environment
	 */
	private static Environment initialization(InputStream inputStream, Location location) {
		LOGGER.info("Initialize Environment...");
		if (location == null) {
			location = Location.getLocation();
		}
		LOGGER.info("Location: " + location.toString());
		LOGGER.debug("Machine: " + getHostName());
		LOGGER.debug("Ip: " + getIpAddress());
		
		Environment environment = null;
		try {
			Map<Location, Environment> environments = getEnvironmentsByYaml(inputStream);
//			Map<Location, Environment> environments = getEnvironmentsByJson(inputStream);
			environment = environments.get(location);
			if (environment == null) {
				throw new EnvironmentFailedException();
			}
			environment.location = location;
			
		} catch (IOException e) {
			throw new EnvironmentFailedException(e);
		}
		
		return environment;
	}
	
	/**
	 * get Environments By Json
	 * @param inputStream			inputStream
	 * @return map
	 * @throws IOException ...
	 */
	@SuppressWarnings("unused")
	private static Map<Location, Environment> getEnvironmentsByJson(InputStream inputStream) throws IOException {
		Map<Location, Environment> environments = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        if(inputStream == null) {
        	inputStream = Environment.class.getResourceAsStream(DEFAULT_ENV_JSON_PATH);
        }
        environments = objectMapper.readValue(inputStream, new TypeReference<Map<Location, Environment>>() {});
        LOGGER.debug("Json config: " + objectMapper.writeValueAsString(environments));
        
		return environments;
	}
	
	/**
	 * get Environments By Yaml
	 * @param inputStream			inputStream
	 * @return map
	 * @throws IOException ...
	 */
	private static Map<Location, Environment> getEnvironmentsByYaml(InputStream inputStream) throws IOException {
		Map<Location, Environment> environments;
		ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		if (inputStream == null) {
			inputStream = Environment.class.getResourceAsStream(DEFAULT_ENV_YAML_PATH);
		}
		environments = objectMapper.readValue(inputStream, new TypeReference<Map<Location, Environment>>() {
		});
		environments.forEach((k, v) -> {v.location = k;});
		
		// convert to json string
		ObjectMapper objectJsonMapper = new ObjectMapper();
		LOGGER.debug("Yaml config: " + objectJsonMapper.writeValueAsString(environments));
		
		return environments;
	}
	
	/**
	 * get machine name
	 * @return string
	 */
	private static String getHostName() {
		String hostName = "";
		
		try {
			hostName = System.getenv("COMPUTERNAME");
			if (StringUtils.isNotBlank(hostName)) {
				return hostName;
			}
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			hostName = inetAddress.getHostName();
		} catch (UnknownHostException e) {
			throw new IllegalArgumentException("Unable to acquire machine name。");
		}
		
		return hostName;
	}
	
	/**
	 * get ip address
	 * @return string
	 */
	private static String getIpAddress() {
		String ipAddress = "";
		
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new IllegalArgumentException("Unable to acquire machine ip address。");
		}
		
		return ipAddress;
		
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(initialize().mainHost);
		System.out.println(initialize().service.getHost());
		System.out.println(initialize().service.getMicroHost());
		System.out.println(initialize().location.name().toUpperCase());
		System.out.println(initEnvironment(null, null).mainHost);
		System.out.println(initEnvironment(null, Location.staging).mainHost);
	}

}
