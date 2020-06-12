package com.thornBird.commons.serviceCall.http.responseImpl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thornBird.commons.serviceCall.http.ClientResponse;

/**
 * @Description: Rest Template Client Response
 * @author: HymanHu
 * @date: 2019-02-15 15:36:17
 */
public class RestTemplateClientResponse<T> implements ClientResponse<T> {
	
	private final ResponseEntity<T> responseEntity;

	public RestTemplateClientResponse(ResponseEntity<T> responseEntity) {
		this.responseEntity = responseEntity;
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		HttpHeaders httpHeaders = responseEntity.getHeaders();
		Map<String, List<String>> headers = new LinkedHashMap<>(httpHeaders.size());
		for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            headers.put(entry.getKey(), entry.getValue());
        }
        return headers;
	}

	@Override
	public int getStatus() {
		return responseEntity.getStatusCode().value();
	}

	@Override
	public T getBody() {
		return responseEntity.getBody();
	}

	@Override
	public String getTextBody() {
		ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(responseEntity.getBody());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
	}

}
