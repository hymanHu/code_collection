package com.thornBird.commons.serviceCall.http;

import java.util.List;
import java.util.Map;

/**
 * @Description: Client Response
 * @author: HymanHu
 * @date: 2019-01-26 21:47:56
 */
public interface ClientResponse<T> {
	
	Map<String, List<String>> getHeaders();
	
	int getStatus();
	
	T getBody();
	
	String getTextBody();
	
	default boolean is1xxInformational() {
		return HttpStatus.getHttpStatus(getStatus()).is1xxInformational();
	}
	
	default boolean is2xxSuccessful() {
		return HttpStatus.getHttpStatus(getStatus()).is2xxSuccessful();
	}
	
	default boolean is3xxRedirection() {
		return HttpStatus.getHttpStatus(getStatus()).is3xxRedirection();
	}
	
	default boolean is4xxClientError() {
		return HttpStatus.getHttpStatus(getStatus()).is4xxClientError();
	}
	
	default boolean is5xxServerError() {
		return HttpStatus.getHttpStatus(getStatus()).is5xxServerError();
	}
	
	default boolean succeeded() {
		return is2xxSuccessful();
	}
}
