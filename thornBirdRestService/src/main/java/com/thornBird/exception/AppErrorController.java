package com.thornBird.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thornBird.config.web.URLConfigResources;

/**
 * @Description: Error Controller
 * ${server.error.path:${error.path:/error}}
 * 类似三目运算符，读取server.error.path，没有就读取error.path，再没有就取默认值/error
 * @author: HymanHu
 * @date: 2019-09-14 11:39:40
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class AppErrorController extends AbstractErrorController {
	
	private ErrorProperties errorProperties;
	
	/**
	 * 查看ErrorMvcAutoConfiguration.basicErrorController可知：
	 * ErrorProperties是通过ServerProperties获取的，所以我们在构造器里面注入了ServerProperties；
	 * basicErrorController注解@ConditionalOnMissingBean，意思是在没有其它实现类的时候才生效；
	 */
	@Autowired
	public AppErrorController(ErrorAttributes errorAttributes,
			List<ErrorViewResolver> errorViewResolvers, ServerProperties serverProperties) {
		super(errorAttributes, errorViewResolvers);
		this.errorProperties = serverProperties.getError();
	}

	/**
	 * 浏览器访问https://127.0.0.1/error
	 */
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		HttpStatus status = getStatus(request);
		Map<String, Object> model = Collections
				.unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
		response.setStatus(status.value());
		
		/*
		 * 首先尝试根据状态码解析是否有开发人员提供的相应的错误展示页面
		 * <templates>/error/404.<ext>, /<static>/static/404.html 等等
		 * 没有找到相应的页面，缺省情况跳转到Whitelabel Error Page
		 */
		ModelAndView modelAndView = resolveErrorView(request, response, status, model);
		if (null == modelAndView) {
			return new ModelAndView("error", model);
		}
		
		int statusCode = modelMap.get("httpStatus") != null ? 
				(Integer)modelMap.get("httpStatus") : status.value();
		switch (statusCode) {
			case 403:
				modelAndView = new ModelAndView(URLConfigResources.FORBIDDEN.getUrl());
				break;
			case 404:
				modelAndView = new ModelAndView(URLConfigResources.NOT_FOUND.getUrl());
				break;
			default:
				modelAndView = new ModelAndView(URLConfigResources.SERVICE_ERROR.getUrl());
				break;
		}
		
		if (null != model) {
			modelAndView.addObject("errorInfo", model.get("message"));
		}
		
		return modelAndView;
	}

	/**
	 * postman访问https://127.0.0.1/error
	 */
	@RequestMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus status = getStatus(request);
		return new ResponseEntity<>(body, status);
	}
	
	/**
	 * 是否包含exception Stack Trace
	 */
	protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
		IncludeStacktrace include = this.errorProperties.getIncludeStacktrace();
		if (include == IncludeStacktrace.ALWAYS) {
			return true;
		}
		if (include == IncludeStacktrace.ON_TRACE_PARAM) {
			return getTraceParameter(request);
		}
		return false;
	}
	
	@Override
	public String getErrorPath() {
		return this.errorProperties.getPath();
	}
}
