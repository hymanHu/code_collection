package com.thornBird.modules.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thornBird.commons.environment.Environment;
import com.thornBird.modules.test.beans.ApplicationConfigBean;
import com.thornBird.modules.test.beans.ApplicationTestConfigBean;

/**
 * @Description: Config Test Controller
 * @author: HymanHu
 * @date: 2019-06-09 15:39:55
 */
@RestController
@RequestMapping("/configTest")
public class ConfigTestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigTestController.class);

	/**
	 * @Value(1000) ---- 直接赋值
	 * @Value("${com.thornBird.name}") ---- 从环境变量以及配置文件中获取值
	 * @Value("#{10*3}") ---- Spring 表达式
	 */
	@Value("${com.thornBird.name}")
	private String projectName;
	@Value("${com.thornBird.author}")
	private String projectAuthor;
	@Value("${com.test.description}")
	private String description;
	
	@Value("${com.test.randomTest}")
	private String randomTest;
	
	@Value("${logging.config}")
	private String loggingConfig;
	
	@Autowired
	private ApplicationConfigBean applicationConfigBean;
	@Autowired
	private ApplicationTestConfigBean applicationTestConfigBean;
	
	/**
	 * http://dev-services.thornBird.com/configTest/environment
	 * https://dev-services.thornbird.com:8085/configTest/environment
	 * @return string
	 * @throws JsonProcessingException ...
	 */
	@RequestMapping("/environment")
	public String environmentTest() throws JsonProcessingException {
		Environment environment = Environment.initialize();
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(environment);
	}
	
	/**
	 * http://dev-services.thornBird.com/configTest/config
	 * https://dev-services.thornbird.com:8085/configTest/config
	 * @return string
	 */
	@RequestMapping("/config")
	public String configTest() {
		LOGGER.trace("Config Test. trace");
		LOGGER.debug("Config Test. debug");
		LOGGER.info("Config Test. info");
		LOGGER.warn("Config Test. warn");
		LOGGER.error("Config Test. error");
		
		StringBuilder sb = new StringBuilder();
		sb.append("application.properties 配置文件绑定属性 or 类================================").append("<br/>");
		sb.append("Project name: ").append(projectName).append("<br/>");
		sb.append("Project author: ").append(projectAuthor).append("<br/>");
		sb.append("Project name: ").append(applicationConfigBean.getName()).append("<br/>");
		sb.append("Project author: ").append(applicationConfigBean.getAuthor()).append("<br/>");
		sb.append("Project description: ").append(applicationConfigBean.getDescription()).append("<br/>");
		sb.append("applicationTest.properties 配置文件绑定属性 or 类================================").append("<br/>");
		sb.append("Project description: ").append(description).append("<br/>");
		sb.append("Project name: ").append(applicationTestConfigBean.getName()).append("<br/>");
		sb.append("Project author: ").append(applicationTestConfigBean.getAuthor()).append("<br/>");
		sb.append("Random Test: ").append(applicationTestConfigBean.getRandomTest()).append("<br/>");
		sb.append("Random Test: ").append(randomTest).append("<br/>");
		sb.append("Profile 多环境配置================================").append("<br/>");
		sb.append("Logging Config: ").append(loggingConfig).append("<br/>");
		return sb.toString();
	}
}
