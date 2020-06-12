package com.thornBird.commons.serviceCall.http;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thornBird.commons.serviceCall.exception.RestServiceException;

/**
 * @Description: Client Response Converter
 * @author: HymanHu
 * @date: 2019-02-15 16:11:50
 */
public final class ClientResponseConverter {
	public final static ObjectMapper mapper = new ObjectMapper();
	
	static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
	
	/**
	 * convert String to T
	 * @param classType			classType
	 * @param clientResponse	clientResponse
	 * @return T
	 */
	public static <T> T convert(Class<T> classType, ClientResponse<String> clientResponse) {
		if (clientResponse == null) {
			throw new RestServiceException(HttpStatus.NOT_FOUND.getCode(), "Response is null!");
		}
		
		if (HttpStatus.NO_CONTENT.getCode() == clientResponse.getStatus()) {
			return null;
		}
		
		String body = clientResponse.getBody();
		if (body == null) {
			return null;
		}
		
		try {
			return mapper.readValue(body, classType);
		} catch (IOException e) {
			throw new RestServiceException(clientResponse.getStatus(), e.getMessage());
		}
	}
	
	/**
	 * convert String to List<T>
	 * @param classType			classType
	 * @param clientResponse	clientResponse
	 * @return List<T>
	 */
	public static <T> List<T> convertToList(Class<T> classType, ClientResponse<String> clientResponse) {
		if (clientResponse == null) {
			throw new RestServiceException(HttpStatus.NOT_FOUND.getCode(), "Response is null!");
		}
		
		if (HttpStatus.NO_CONTENT.getCode() == clientResponse.getStatus()) {
			return null;
		}
		
		String body = clientResponse.getBody();
		if (body == null) {
			return null;
		}
		
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, classType);
		
		try {
			return mapper.readValue(body, type);
		} catch (IOException e) {
			throw new RestServiceException(clientResponse.getStatus(), e.getMessage());
		}
	}

}
