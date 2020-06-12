package com.thornBird.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * @Description: MongoDB Config
 * @author: HymanHu
 * @date: 2019-08-19 14:57:46
 */
@Configuration
@ConfigurationProperties(prefix = "mongodb.custom")
public class MongoDBConfig {
	private final static Logger LOGGER = LoggerFactory.getLogger(MongoDBConfig.class);

	@Value("${mongodb.custom.host}")
	private String host;
	@Value("${mongodb.custom.port}")
	private int port;
	@Value("${mongodb.custom.replica-set}")
	private String replicaSet;
	@Value("${mongodb.custom.username}")
	private String username;
	@Value("${mongodb.custom.password}")
	private String password;
	@Value("${mongodb.custom.database}")
	private String database;
	@Value("${mongodb.custom.authentication-database}")
	private String authenticationDatabase;
	@Value("${mongodb.custom.connections-per-host}")
	private int connectionsPerHost;
	@Value("${mongodb.custom.min-connections-per-host}")
	private int minConnectionsPerHost;
	
	@SuppressWarnings("all")
	@Bean(name="mongoDbFactory")
	public MongoDbFactory getMongoDbFactory() {
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.connectionsPerHost(connectionsPerHost);
		builder.minConnectionsPerHost(minConnectionsPerHost);
		if (StringUtils.isNoneBlank(replicaSet)) {
			builder.requiredReplicaSetName(replicaSet);
		}
		MongoClientOptions mongoClientOptions = builder.build();
		
		List<ServerAddress> serverAddresses = new ArrayList<>();
		ServerAddress serverAddress = new ServerAddress(host, port);
		serverAddresses.add(serverAddress);
		LOGGER.debug("MongoDb Services: " + serverAddresses.toString());
		
		List<MongoCredential> mongoCredentialList = new ArrayList<>();
		if (StringUtils.isNoneBlank(username)) {
            mongoCredentialList.add(MongoCredential.createScramSha1Credential(
                    username,
                    authenticationDatabase != null ? authenticationDatabase : database,
                    password.toCharArray()));
        }
		LOGGER.debug("MongoDb Services Credential: " + mongoCredentialList.toString());
		
		MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredentialList, mongoClientOptions);
		return new SimpleMongoDbFactory(mongoClient, database);
	}
}
