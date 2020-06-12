package com.thornBird.personalArchives.modules.test.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: Application Config Bean
 * @author: HymanHu
 * @date: 2019-09-13 21:05:14
 */
@Component
@ConfigurationProperties(prefix = "com.thorn.bird")
public class ApplicationConfigBean {
	private String name;
	private String author;
	private String description;

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
}
