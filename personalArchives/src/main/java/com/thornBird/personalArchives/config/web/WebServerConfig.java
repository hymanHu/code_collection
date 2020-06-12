package com.thornBird.personalArchives.config.web;

import java.time.Duration;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.thornBird.personalArchives.common.URLConfigResources;

/**
 * @Description: Web Server Config
 * @author: HymanHu
 * @date: 2019-09-14 10:09:28
 */
@Configuration
public class WebServerConfig {

	@Value("${server.port}")
	private int serverPort;
	@Value("${server.http.port}")
	private int serverHttpPort;

	/**
	 * http Connector
	 */
	@Bean
	public Connector httpConnector() {
		Connector connector = new Connector(
				"org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(serverHttpPort);
		connector.setSecure(false);
		connector.setRedirectPort(serverPort);
		return connector;
	}

	/**
	 * config ServletWebServerFactory, support https
	 */
	@Bean
	public ServletWebServerFactory tomcatServletWebServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern(URLConfigResources.ANY_PATTERN.getUrl());
				
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				securityConstraint.addCollection(collection);
				
				context.addConstraint(securityConstraint);
			}
		};
		// if you need http support, we need add http Connector
		factory.addAdditionalTomcatConnectors(httpConnector());
		return factory;
	}

	/**
	 * -自定义内嵌式容器参数，ConfigurableServletWebServerFactory 有三个子类：
	 * TomcatServletWebServerFactory, JettyServletWebServerFactory and UndertowServletWebServerFactory
	 */
//	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> getWebServerFactoryCustomizer() {
		return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {
			@Override
			public void customize(ConfigurableServletWebServerFactory factory) {
				Session session = new Session();
				session.setTimeout(Duration.ofMillis(60));
				factory.setPort(8086);
				factory.setContextPath("/newContextPath");
				factory.setSession(session);
				factory.addErrorPages(
						new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
			}
		};
	}
}
