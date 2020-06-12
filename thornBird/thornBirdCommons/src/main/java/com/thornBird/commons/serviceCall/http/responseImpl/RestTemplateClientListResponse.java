package com.thornBird.commons.serviceCall.http.responseImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thornBird.commons.serviceCall.exception.RestServiceException;
import com.thornBird.commons.serviceCall.http.ClientResponse;
import com.thornBird.commons.serviceCall.http.ClientResponseConverter;

/**
 * @Description: Rest Template Client List Response
 * @author: HymanHu
 * @date: 2019-02-15 16:15:14
 */
public class RestTemplateClientListResponse<T> implements ClientResponse<List<T>> {
	
	private final ResponseEntity<T[]> responseEntity;

	public RestTemplateClientListResponse(ResponseEntity<T[]> responseEntity) {
		this.responseEntity = responseEntity;
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		HttpHeaders httpHeaders = responseEntity.getHeaders();

        HashMap<String, List<String>> headers = new LinkedHashMap<>(httpHeaders.size());

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
	public List<T> getBody() {
		if (!responseEntity.hasBody()) {
			return new ArrayList<>();
		}
		
		return Arrays.asList(responseEntity.getBody());
	}

	@Override
	public String getTextBody() {
		if (!responseEntity.hasBody()) {
			return null;
		}
		
		try {
			return ClientResponseConverter.mapper.writeValueAsString(responseEntity.getBody());
		} catch (JsonProcessingException e) {
			throw new RestServiceException(e.getMessage());
		}
	}

}
