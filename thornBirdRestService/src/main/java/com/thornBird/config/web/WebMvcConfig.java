package com.thornBird.config.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.thornBird.filter.ParameterFilter;
import com.thornBird.interceptor.RequestViewMappingInterceptor;

/**
 * @Description: Web Mvc Config
 * @author: HymanHu
 * @date: 2019-09-01 20:05:36
 */
@Configuration
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
public class WebMvcConfig implements WebMvcConfigurer {
	private final static Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);
	
	@Autowired
	private RequestViewMappingInterceptor requestViewMappingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestViewMappingInterceptor)
				.addPathPatterns(URLConfigResources.ANY_PATTERN.getUrl())
				.excludePathPatterns(URLConfigResources.getStaticAssetsList());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(URLConfigResources.STATIC_PATTERN.getUrl())
			.addResourceLocations(URLConfigResources.CLASSPATH_RESOURCE_LOCATIONS);
	}
	
	@Bean
	public FilterRegistrationBean<ParameterFilter> filterRegist() {
		FilterRegistrationBean<ParameterFilter> filterRegist = new FilterRegistrationBean<ParameterFilter>();
		filterRegist.setFilter(new ParameterFilter());
		return filterRegist;
	}
}
