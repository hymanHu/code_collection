package com.thornBird.modules.test.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: Application Config Bean
 * @author: HymanHu
 * @date: 2019-06-15 15:51:01
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
