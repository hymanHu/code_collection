package com.thornBird.config.web;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: Web Server Config
 * 	Set Http && Https
 * @author: HymanHu
 * @date: 2019-06-24 21:25:10
 */
@Configuration
public class WebServerConfig {
	
	@Value("${server.port}")
	private int serverPort;
	@Value("${http.port}")
	private int httpPort;

	/**
	 * set http connector, http.port redirect to server.port
	 * @return connector
	 */
	@Bean
	public Connector httpConnector(){
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(httpPort);
		connector.setSecure(false);
		connector.setRedirectPort(serverPort);
		return connector;
	}
	
	@Bean
	public TomcatServletWebServerFactory tomcatServletWebServerFactory(){
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory(){
			@Override
			protected void postProcessContext(Context context) {
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern(URLConfigResources.ANY_PATTERN.getUrl());
//				collection.addMethod(DEFAULT_PROTOCOL);
				
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				securityConstraint.addCollection(collection);
				
				context.addConstraint(securityConstraint);
			}
		};
		factory.addAdditionalTomcatConnectors(httpConnector());
		return factory;
	}
}
