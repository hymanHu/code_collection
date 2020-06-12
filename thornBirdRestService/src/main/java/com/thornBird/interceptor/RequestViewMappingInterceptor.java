package com.thornBird.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: Request View Mapping Interceptor
 * URL匹配模版拦截器：如果ModelAndView中设置有template，则按照template设置的模版显示，
 * a如果没有设置，则按照URL路径匹配模版，自动添加index.html，需要每个模版包下创建index.html文件
 * @author: HymanHu
 * @date: 2019-09-01 19:40:03
 */
@Component
public class RequestViewMappingInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String path = request.getServletPath();
		
		if (null == modelAndView) {
			return;
		}
		
		if (null == modelAndView.getModelMap().get("template")) {
			if (path.startsWith("/")) {
                path = path.substring(1);
            }
            modelAndView.addObject("template", path.toLowerCase() + "/index");
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
