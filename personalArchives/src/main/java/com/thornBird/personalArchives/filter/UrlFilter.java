package com.thornBird.personalArchives.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName="urlFilter", urlPatterns="/**")
public class UrlFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Init the url filter.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) request;
		String requestUri = req.getRequestURI();
		if (requestUri.contains("/info")) {
			request.getRequestDispatcher("/error").forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}
	
	@Override
	public void destroy() {
		System.out.println("Destroy the url filter.");
	}

}
