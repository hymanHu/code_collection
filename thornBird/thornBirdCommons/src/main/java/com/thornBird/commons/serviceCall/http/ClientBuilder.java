package com.thornBird.commons.serviceCall.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: Client Builder
 * @author: HymanHu
 * @date: 2019-01-29 20:10:44
 */
public class ClientBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientBuilder.class);
	
	private final static Map<ClientType, Client> CLIENT_MAP = new HashMap<>();
	private final static String SETTINGS = "/servicecall-settings.properties";
	public static ClientType DEFAULT_CLIENT_TYPE;
	
	public static void initClient() {
		Properties properties = loadSettings();
		DEFAULT_CLIENT_TYPE = ClientType.valueOf(properties.getProperty("servicecall.client.default-type"));
		initClients(properties.getProperty("servicecall.client.package"));
		LOGGER.info("Initialize ServiceCall Component Completed.");
	}
	
	public static void initClient(String packageName, ClientType defaultClientType) {
		DEFAULT_CLIENT_TYPE = defaultClientType;
		initClients(packageName);
		LOGGER.info("Initialize ServiceCall Component Completed.");
	}
	
	public static Client getClient() {
		return CLIENT_MAP.get(DEFAULT_CLIENT_TYPE);
	}
	
	public static Client getClient(ClientType clientType) {
		if (CLIENT_MAP.isEmpty()) {
			LOGGER.error("Not found any client implementation!");
			throw new IllegalStateException("Not found any client implementation!");
		}
		return CLIENT_MAP.get(clientType);
	}
	
	/**
	 * The return map is read only, it will throw exception when call put method.
	 * @return client map
	 */
	public static Map<ClientType, Client> getClientMap() {
		return Collections.unmodifiableMap(CLIENT_MAP);
	}
	
	public static void add(ClientType clientType, Client client) {
		if (clientType == null || client == null) {
			throw new IllegalArgumentException("Client Type or Client can not be null.");
		}
		CLIENT_MAP.putIfAbsent(clientType, client);
	}
	
	public static void replace(ClientType clientType, Client client) {
		if (clientType == null || client == null) {
			throw new IllegalArgumentException("Client Type or Client can not be null.");
		}
		if (CLIENT_MAP.containsKey(clientType)) {
			CLIENT_MAP.replace(clientType, client);
		}
	}
	
	/**
	 * Init clients
	 * @param packageName	client implements packageName
	 */
	private static void initClients(String packageName) {
		if (packageName == null || packageName.length() <= 0) {
			LOGGER.error("PackageName parameter not defined.");
			throw new IllegalStateException("PackageName parameter not defined.");
		}
		
		Reflections reflections = new Reflections(packageName);
		Set<Class<? extends Client>> allClasses = reflections.getSubTypesOf(Client.class);
		
		for (Class<? extends Client> client : allClasses) {
			ClientProvider clientProvider = client.getAnnotation(ClientProvider.class);
			if (clientProvider != null) {
				try {
					CLIENT_MAP.putIfAbsent(clientProvider.value(), client.newInstance());
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					throw new IllegalStateException(e);
				}
			}
		}
	}
	
	/**
	 * Load settings file
	 * @return Properties
	 */
	private static Properties loadSettings() {
        try(InputStream inputStream = ClientBuilder.class.getResourceAsStream(SETTINGS)) {

            Properties properties = new Properties();
            properties.load(inputStream);

            return properties;
        } catch (IOException exception) {
        	LOGGER.error("settings properties file not found.");
            throw new IllegalStateException(exception);
        }
    }
}
