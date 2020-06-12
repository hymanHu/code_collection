package com.thornBird.personalArchives;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Description: Application
 * @author: HymanHu
 * @date: 2019-09-13 19:27:38
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) {
		LOGGER.debug("Start personal archives application.");
		
		SpringApplication.run(Application.class, args);
//		SpringApplication application = new SpringApplication(Application.class);
//		application.setBannerMode(Banner.Mode.OFF);
//		application.run(args);
	}
}
