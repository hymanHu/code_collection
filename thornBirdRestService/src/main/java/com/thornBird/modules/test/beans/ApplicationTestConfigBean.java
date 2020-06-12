package com.thornBird.modules.test.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Description: Application Test Config Bean，对应applicationTest.properties配置文件
 * @author: HymanHu
 * @date: 2019-06-15 20:44:44
 */
@Configuration
@PropertySource("classpath:applicationTest.properties")
@ConfigurationProperties(prefix = "com.test")
public class ApplicationTestConfigBean {
	private String name;
	private String author;
	private String description;
	private String randomTest;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRandomTest() {
		return randomTest;
	}

	public void setRandomTest(String randomTest) {
		this.randomTest = randomTest;
	}
}
