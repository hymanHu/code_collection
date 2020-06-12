package com.thornBird.exception;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.thornBird.config.web.URLConfigResources;

/**
 * 
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyExceptionHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

	/**
	 * 集中处理 controller 层 AccessDeniedException 异常
	 * 用户未登录情况下访问受保护资源 ---- login
	 * 用户登录情况下访问被保护资源 ---- 403错误码
	 */
	@ExceptionHandler(value=AccessDeniedException.class)
	public String handlerAccessDeniedException(HttpServletRequest reuqest, AccessDeniedException exception) {
		LOGGER.debug("Handler AccessDeniedException: " + exception.getMessage());
		return "redirect:" + URLConfigResources.FORBIDDEN.getUrl();
	}
}
