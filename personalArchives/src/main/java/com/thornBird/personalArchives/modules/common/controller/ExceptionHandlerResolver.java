package com.thornBird.personalArchives.modules.common.controller;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.thornBird.personalArchives.common.URLConfigResources;

/**
 * @Description: Exception Handler Resolver ---- exception 全局处理
 * @author: HymanHu
 * @date: 2019-09-17 19:56:53
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerResolver {

	@ExceptionHandler(value = AccessDeniedException.class)
	public String handleAccessDeniedException(HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("httpStatus", HttpStatus.FORBIDDEN.value());
		return "redirect:" + URLConfigResources.ERROR_PATTH.getUrl();
	}
}
