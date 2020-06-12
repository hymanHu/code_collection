package com.thornBird.personalArchives.config.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	@Bean
	public MyRealm getMyRealm() {
		return new MyRealm();
	}
	
	@Bean
	public DefaultWebSecurityManager getSecurityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(getMyRealm());
		return securityManager;
//		DefaultSecurityManager manager = new DefaultSecurityManager();
//		manager.setRealm(getMyRealm());
//		return manager;
	}
	
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
		filterFactory.setSecurityManager(getSecurityManager());
		filterFactory.setLoginUrl("/login");
		filterFactory.setSuccessUrl("/index");
		filterFactory.setUnauthorizedUrl("/error");
		Map<String, String> map = new HashMap<String, String>();
		map.put("/index", "authc");
		filterFactory.setFilterChainDefinitionMap(map);
		return filterFactory;
	}
}
