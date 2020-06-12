package com.thornBird.commons.serviceCall.http;

import java.util.List;
import java.util.Map;

/**
 * @Description: Client
 * @author: HymanHu
 * @date: 2019-01-27 16:11:00
 */
public interface Client {

	<T> ClientResponse<T> getEntity(String url, Map<String, String> headerMap, Class<T> classType);
	
	<T> ClientResponse<List<T>> getAll(String url, Map<String, String> headerMap, Class<T[]> classType);

	<T> ClientResponse<T> postEntity(String url, Object body, Map<String, String> headerMap, Class<T> classType);

	<T> ClientResponse<T> putEntity(String url, Object body, Map<String, String> headerMap, Class<T> classType);

	void delete(String url, Map<String, String> headerMap);
}
