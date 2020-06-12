package com.thornBird.personalArchives.modules.test.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Description: Application Config Test Bean
 * @author: HymanHu
 * @date: 2019-09-13 21:05:14
 */
@Configuration
//@PropertySource("classpath:applicationTest.properties")
@PropertySource(value={"classpath:config/applicationTest.properties"},ignoreResourceNotFound=true)
@ConfigurationProperties(prefix = "com.hqyj")
public class ApplicationConfigTestBean {
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
