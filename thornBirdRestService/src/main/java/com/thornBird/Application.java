package com.thornBird;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.thornBird.commons.environment.Environment;
import com.thornBird.commons.environment.bean.Location;

/**
 * VM arguments: -javaagent:D:/repository/org/aspectj/aspectjweaver/1.9.2/aspectjweaver-1.9.2.jar -DENV=dev
 * @Description: Application
 * @author: HymanHu
 * @date: 2019-07-30 10:21:15
 */
@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class Application {
	private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		LOGGER.info("Start thorn bird rest service.");
		
		LOGGER.debug("Program Arguments: ");
		Arrays.asList(args).stream().forEach(item -> {LOGGER.debug(item);});
		LOGGER.debug("VM Program: " + System.getProperty(Location.VM_ENV));
		
		Environment.initialize();
		
		SpringApplication.run(Application.class, args);
	}
}
