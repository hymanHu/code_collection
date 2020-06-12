package com.thornBird.personalArchives.config.web;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thornBird.personalArchives.filter.UrlFilter;

/**
 * @Description: Web Mvc Config
 * @author: HymanHu
 * @date: 2019-09-20 09:25:15
 */
@Configuration
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
public class WebMvcConfig {

	/**
	 * Filter Registration Bean
	 */
	@Bean
	public FilterRegistrationBean<UrlFilter> urlFilterRegistration() {
		FilterRegistrationBean<UrlFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new UrlFilter());
		registration.addUrlPatterns("/*");
        registration.setName("urlFilter");
        registration.setOrder(1);
		return registration;
	}
}
