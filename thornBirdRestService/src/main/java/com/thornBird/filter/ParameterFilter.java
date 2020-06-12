package com.thornBird.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 参数过滤器，过滤参数中的敏感字符串
 */
@WebFilter(filterName="parameterFilter", urlPatterns="/**")
public class ParameterFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Filter init.");
		Filter.super.init(filterConfig);
	}

	/* 
	 * 修改request请求中的参数
	 * HttpServletRequest中的请求信息是locked状态，我们无法直接操作
	 * 我们使用HttpServletRequestWrapper对请求信息做处理
	 * 继续优化，自定义wrapper类，继承HttpServletRequestWrapper，重写实现方法……
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletRequestWrapper requestWrapper = null;
		
		requestWrapper = new HttpServletRequestWrapper(httpRequest) {
			@Override
			public String getParameter(String name) {
				String value = httpRequest.getParameter(name);
				if (null != value) {
					return value.replace("1", "2");
				}
				return super.getParameter(name);
			}
		};
		
		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void destroy() {
		System.out.println("Filter destroy.");
		Filter.super.destroy();
	}

}
