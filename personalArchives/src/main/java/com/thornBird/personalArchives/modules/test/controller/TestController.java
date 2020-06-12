package com.thornBird.personalArchives.modules.test.controller;

import java.nio.file.AccessDeniedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thornBird.personalArchives.modules.test.service.SendMessage;
import com.thornBird.personalArchives.modules.test.vo.ApplicationConfigBean;
import com.thornBird.personalArchives.modules.test.vo.ApplicationConfigTestBean;

@RestController
@RequestMapping("/test")
public class TestController {
	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	@Value("${server.port}")
	private String port;
	@Value("${server.error.path:${error.path:/error}}")
	private String errorPath;
	
	@Autowired
	private ApplicationConfigBean applicationConfigBean;
	@Autowired
	private ApplicationConfigTestBean applicationConfigTestBean;
	@Autowired
	private SendMessage sendMessage;
	
	@RequestMapping(value="/addMessage", method=RequestMethod.POST)
	public String addMessage() {
		return "This is a post request.";
	}
	
	@RequestMapping(value="/sendMessage", method=RequestMethod.GET)
	public String sendMessage() {
		return sendMessage.send();
	}
	
	@RequestMapping(value="/info", method=RequestMethod.GET)
	public String getAppInfo() {
		return "This is personal archives app.";
	}
	
	@RequestMapping(value="/config", method=RequestMethod.GET)
	public String getConfigInfo() throws AccessDeniedException {
		LOGGER.trace("trace test.");
		LOGGER.debug("debug test.");
		LOGGER.info("info test.");
		LOGGER.warn("warn test.");
		LOGGER.error("error test.");
		
//		try {
//			int i = 1/0;
//		} catch (Exception e) {
//			throw new AccessDeniedException("ssss");
//		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("application.properties===================================11111").append("</br>");
		sb.append("port: " + port).append("</br>");
		sb.append("errorPath: " + errorPath).append("</br>");
		sb.append(applicationConfigBean.getName() + "--" + 
				applicationConfigBean.getAuthor() + "--" + applicationConfigBean.getDescription()).append("</br>");
		sb.append(applicationConfigTestBean.getName() + "--" + 
				applicationConfigTestBean.getAuthor() + "--" + applicationConfigTestBean.getDescription()).append("</br>");
		sb.append(applicationConfigTestBean.getRandomTest()).append("</br>");
		return sb.toString();
	}
}
