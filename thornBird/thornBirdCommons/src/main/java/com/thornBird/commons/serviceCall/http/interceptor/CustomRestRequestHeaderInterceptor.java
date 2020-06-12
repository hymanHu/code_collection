package com.thornBird.commons.serviceCall.http.interceptor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.thornBird.commons.utils.CookieUtil;

/**
 * @Description: 
 * 		Custom Rest Request Header Interceptor For Spring Rest Template
 * 		ClientHttpRequestInterceptor: 对请求进行拦截，并在其被发送至服务端之前修改请求或是增强相应的信息;
 * @author: HymanHu
 * @date: 2019-02-15 09:16:36
 */
public class CustomRestRequestHeaderInterceptor implements ClientHttpRequestInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomRestRequestHeaderInterceptor.class);
	
	private static final String FORCE_PARAM = "force=1";
	private static final String FRONT_END_HTTPS_HEADER_NAME = "Front-End-Https";

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		long startTime = System.currentTimeMillis();
		
		HttpHeaders headers = request.getHeaders();
		String uri = request.getURI().toString();
		
		// 如果非web调用（main 方法调用），currentRequestAttributes ，会抛出异常，NonWebRequestAttributes 继承 RequestAttributes，实现的方法为空
		RequestAttributes requestAttributes = null;
		try {
			requestAttributes = RequestContextHolder
					.currentRequestAttributes();
		} catch (IllegalStateException e) {
			requestAttributes = new NonWebRequestAttributes();
		}
		HttpServletRequest originalRequest = (HttpServletRequest) requestAttributes
				.resolveReference(RequestAttributes.REFERENCE_REQUEST);;
		
		if (originalRequest != null) {
			// add force=1
			if (originalRequest.getQueryString().toLowerCase().contains(FORCE_PARAM) && 
					!uri.toLowerCase().contains(FORCE_PARAM)) {
				try {
					Field uriField = request.getClass().getDeclaredField("uri");
					uriField.setAccessible(true);
					uriField.set(request, new URI(appendForce(uri)));
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | 
						IllegalAccessException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
			
			// build headers
			if (originalRequest.isSecure() && !headers.containsKey(FRONT_END_HTTPS_HEADER_NAME)) {
				headers.add(FRONT_END_HTTPS_HEADER_NAME, "true");
			}
			headers.add("Origin", originalRequest.getServerName());
			headers.add("User-Agent", originalRequest.getHeader("User-Agent") + " ThornBird");
			if (uri.contains("/soa")) {
				headers.add("HTTP_ACCEPT_ENCODING", "true");
			}
			headers.set("Cookie", CookieUtil.buildUserSessionCookieString(originalRequest));
		}
		
		ClientHttpResponse response = execution.execute(request, body);
		
		long endTime = System.currentTimeMillis();
		
		// debug info
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("%s request for service endpoint %s, cost %d milliseconds", 
					request.getMethod().name(), request.getURI(), endTime - startTime));
			request.getHeaders().forEach((key, value) -> {LOGGER.debug(String.format("%s: %s", key, value));});
		}
		
		return response;
	}
	
	/**
	 * append force
	 * @param uri	uri
	 * @return string
	 */
	private String appendForce(String uri) {
        if (uri.contains("?")) {
            uri += "&";
        } else {
            uri += "?";
        }

        return uri + FORCE_PARAM;
    }

}
