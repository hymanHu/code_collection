package com.thornBird.commons.serviceCall.http.clientImpl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.thornBird.commons.serviceCall.http.Client;
import com.thornBird.commons.serviceCall.http.ClientProvider;
import com.thornBird.commons.serviceCall.http.ClientResponse;
import com.thornBird.commons.serviceCall.http.ClientType;
import com.thornBird.commons.serviceCall.http.interceptor.CustomRestRequestHeaderInterceptor;
import com.thornBird.commons.serviceCall.http.responseImpl.RestTemplateClientListResponse;
import com.thornBird.commons.serviceCall.http.responseImpl.RestTemplateClientResponse;

/**
 * @Description: Spring Rest Template Implement
 * @author: HymanHu
 * @date: 2019-02-15 09:03:19
 */
@ClientProvider(ClientType.SPRING)
public class SpringRestTemplateImpl implements Client {
	
	private RestTemplate restTemplate;

	public SpringRestTemplateImpl() {
		this.restTemplate = new RestTemplate();
		this.restTemplate.setInterceptors(Collections.singletonList(new CustomRestRequestHeaderInterceptor()));
		this.restTemplate.setMessageConverters(getHttpMessageConverters());
	}
	
	@SuppressWarnings("rawtypes")
	private static List<HttpMessageConverter<?>> getHttpMessageConverters() {
		
		List<HttpMessageConverter<?>> list = new ArrayList<>();
		list.add(new ByteArrayHttpMessageConverter());
		list.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
		list.add(new ResourceHttpMessageConverter());
		list.add(new SourceHttpMessageConverter());
		list.add(new AllEncompassingFormHttpMessageConverter());
		list.add(new MappingJackson2HttpMessageConverter());
		list.add(new FormHttpMessageConverter());
		
		return list;
	}
	
	/**
	 * build HttpHeaders by header map
	 * @param headerMap		header map
	 * @param isGet			is get request
	 * @return HttpHeaders
	 */
	private HttpHeaders buildHttpHeaders(Map<String, String> headerMap, boolean isGet) {
		HttpHeaders httpHeaders = new HttpHeaders();
		if (headerMap != null) {
			for (String key : headerMap.keySet()) {
				httpHeaders.add(key, headerMap.get(key));
			}
		}
		
		if (!isGet) {
			httpHeaders.setContentType(headerMap != null && headerMap.get("Content-Type") != null ? 
					MediaType.valueOf(headerMap.get("Content-Type")) : 
					MediaType.APPLICATION_FORM_URLENCODED);
		}
		
		return httpHeaders;
	}

	@Override
	public <T> ClientResponse<T> getEntity(String url, Map<String, String> headerMap, Class<T> classType) {
		return new RestTemplateClientResponse<>(restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(buildHttpHeaders(headerMap, true)), classType));
	}

	@Override
	public <T> ClientResponse<List<T>> getAll(String url, Map<String, String> headerMap, Class<T[]> classType) {
		return new RestTemplateClientListResponse<>(restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(buildHttpHeaders(headerMap, true)), classType));
	}

	@Override
	public <T> ClientResponse<T> postEntity(String url, Object body, Map<String, String> headerMap,
			Class<T> classType) {
		HttpEntity<Object> httpEntity = new HttpEntity<>(body, buildHttpHeaders(headerMap, false));
		return new RestTemplateClientResponse<>(restTemplate.postForEntity(url, httpEntity, classType));
	}

	@Override
	public <T> ClientResponse<T> putEntity(String url, Object body, Map<String, String> headerMap, Class<T> classType) {
		HttpEntity<Object> httpEntity = new HttpEntity<>(body, buildHttpHeaders(headerMap, false));
		return new RestTemplateClientResponse<>(restTemplate.exchange(url, HttpMethod.PUT, httpEntity, classType));
	}

	@Override
	public void delete(String url, Map<String, String> headerMap) {
		HttpEntity<Object> httpEntity = new HttpEntity<>(null, buildHttpHeaders(headerMap, false));
		restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
	}

}
