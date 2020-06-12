package com.thornBird.commons.serviceCall;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.thornBird.commons.environment.Environment;
import com.thornBird.commons.environment.bean.Location;
import com.thornBird.commons.serviceCall.builder.UrlComponents;
import com.thornBird.commons.serviceCall.exception.RestServiceException;
import com.thornBird.commons.serviceCall.http.Client;
import com.thornBird.commons.serviceCall.http.ClientBuilder;
import com.thornBird.commons.serviceCall.http.ClientResponse;
import com.thornBird.commons.serviceCall.http.ClientType;

/**
 * @Description: Service Call
 * @author: HymanHu
 * @date: 2019-02-08 17:29:52
 */
public final class ServiceCall {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCall.class);
	
	private final static String CONTENT_TYPE = "Content-Type";
	private final static String ACCEPT = "Accept";
	
	private String scheme;
	private boolean ssl;
	private String host;
	private String path;
	private String[] pathParameters;
	private Map<String, String> queryParameters;
	private int siteId;
	private String url;
	
	private Map<String, String> headers;
	private Object body;
	private String contentType;
	private String accept;
	
	private Client client;
	private ClientType clientType;
	
	private ServiceCall() {
	}
	
	public static ServiceCall newInstance() {
		synchronized (ServiceCall.class) {
			return new ServiceCall();
		}
	}
	
	public static void main(String[] args) {
		Environment.initEnvironment(null, Location.dev);
		ClientBuilder.initClient();
		
//		Map<String, String> queryParameters = new HashMap<>();
//		queryParameters.put("siteId", "260");
//		ServiceCall serviceCall = ServiceCall.newInstance()
//				.host("dev-services.shop.com:8085")
//				.path("/Store/Venue/{venueId}")
//				.pathParameters(new String[]{String.valueOf(2860)})
//				.queryParameters(queryParameters)
//				.contentType("application/json;version=2.0")
//                .accept("application/json");
//		JsonNode jsonNode = serviceCall.getEntity(JsonNode.class);
//		System.out.println(jsonNode.toString());
//		
//		ServiceCall serviceCall1 = ServiceCall.newInstance()
//				.url("http://dev-services.shop.com:8998/CommissionRate/Custom/269045")
//				.contentType("application/json")
//                .accept("application/json");
//		List<JsonNode> result = serviceCall1.getAllEntity(JsonNode[].class);
//		System.out.println(result.size());
		
		ServiceCall serviceCall2 = ServiceCall.newInstance()
				.path("/test")
				.contentType("application/json")
				.accept("application/json");
		List<JsonNode> result2 = serviceCall2.getAllEntity(JsonNode[].class);
		System.out.println(result2.size());
	}
	
	public void delete() {
		try {
			getClient().delete(getServiceUrl(), getHeaders());
		} catch (Exception e) {
			throw new RestServiceException(e.getMessage(), e);
		}
	}
	
	public <T> T putEntity(Class<T> classType) {
		ClientResponse<T> responseEntity = null;
		try {
			responseEntity = getClient().putEntity(getServiceUrl(), getBody(), getHeaders(), classType);
		} catch (Exception e) {
			throw new RestServiceException(e.getMessage(), e);
		}
		
		if (responseEntity.succeeded()) {
			return responseEntity.getBody();
		}
		
		throw new RestServiceException(responseEntity.getStatus(), "Not found any service.");
	}
	
	public <T> ClientResponse<T> put(Class<T> classType) {
		ClientResponse<T> responseEntity = null;
		try {
			responseEntity = getClient().putEntity(getServiceUrl(), getBody(), getHeaders(), classType);
		} catch (Exception e) {
			throw new RestServiceException(e.getMessage(), e);
		}
		
		if (responseEntity.succeeded()) {
			return responseEntity;
		}
		
		throw new RestServiceException(responseEntity.getStatus(), "Not found any service.");
	}
	
	public <T> T postEntity(Class<T> classType) {
		ClientResponse<T> responseEntity = null;
		try {
			responseEntity = getClient().postEntity(getServiceUrl(), getBody(), getHeaders(), classType);
		} catch (Exception e) {
			throw new RestServiceException(e.getMessage(), e);
		}
		
		if (responseEntity.succeeded()) {
			return responseEntity.getBody();
		}
		
		throw new RestServiceException(responseEntity.getStatus(), "Not found any service.");
	}
	
	public <T> ClientResponse<T> post(Class<T> classType) {
		ClientResponse<T> responseEntity = null;
		try {
			responseEntity = getClient().postEntity(getServiceUrl(), getBody(), getHeaders(), classType);
		} catch (Exception e) {
			throw new RestServiceException(e.getMessage(), e);
		}
		
		if (responseEntity.succeeded()) {
			return responseEntity;
		}
		
		throw new RestServiceException(responseEntity.getStatus(), "Not found any service.");
	}
	
	public <T> List<T> getAllEntity(Class<T[]> classType) {
		ClientResponse<List<T>> responseEntity = null;
		try {
			responseEntity = getClient().getAll(getServiceUrl(), getHeaders(), classType);
		} catch (Exception e) {
			throw new RestServiceException(e.getMessage(), e);
		}
		
		if (responseEntity.succeeded()) {
			return responseEntity.getBody();
		}
		
		throw new RestServiceException(responseEntity.getStatus(), "Not found any service.");
	}
	
	public <T> ClientResponse<List<T>> getAll(Class<T[]> classType) {
		ClientResponse<List<T>> responseEntity = null;
		try {
			responseEntity = getClient().getAll(getServiceUrl(), getHeaders(), classType);
		} catch (Exception e) {
			throw new RestServiceException(e.getMessage(), e);
		}
		
		if (responseEntity.succeeded()) {
			return responseEntity;
		}
		
		throw new RestServiceException(responseEntity.getStatus(), "Not found any service.");
	}
	
	public <T> T getEntity(Class<T> classType) {
		ClientResponse<T> responseEntity = null;
		try {
			responseEntity = getClient().getEntity(getServiceUrl(), getHeaders(), classType);
		} catch (Exception e) {
			throw new RestServiceException(e.getMessage(), e);
		}
		if (responseEntity.succeeded()) {
			return responseEntity.getBody();
		}
		
		throw new RestServiceException(responseEntity.getStatus(), "Not found any service.");
	}
	
	public <T> ClientResponse<T> get(Class<T> classType) {
		ClientResponse<T> responseEntity = null;
		try {
			responseEntity = getClient().getEntity(getServiceUrl(), getHeaders(), classType);
		} catch (Exception e) {
			throw new RestServiceException(e.getMessage(), e);
		}
		if (responseEntity.succeeded()) {
			return responseEntity;
		}
		
		throw new RestServiceException(responseEntity.getStatus(), "Not found any service.");
	}
	
	private String getServiceUrl() {
		if (StringUtils.isBlank(url) && StringUtils.isBlank(path)) {
			LOGGER.error("No service url found!");
			throw new RestServiceException("No service url found!");
		}
		
		if (StringUtils.isNotBlank(url)) {
			LOGGER.debug("Service url: " + url);
			return url;
        }
		
		String serviceUrl = new UrlComponents(scheme, ssl, host, path, 
				pathParameters, queryParameters, siteId).buildUrl();
		LOGGER.debug("Service url: " + serviceUrl);
		
		return serviceUrl;
	}
	
	public void putQueryParam(String key, String value) {
		if (this.queryParameters == null) {
			this.queryParameters = new HashMap<>();
		}
		this.queryParameters.putIfAbsent(key, value);
	}

	public String getScheme() {
		return scheme;
	}

	public ServiceCall scheme(String scheme) {
		this.scheme = scheme;
		return this;
	}

	public boolean isSsl() {
		return ssl;
	}

	public ServiceCall ssl(boolean ssl) {
		this.ssl = ssl;
		return this;
	}

	public String getHost() {
		return host;
	}

	public ServiceCall host(String host) {
		this.host = host;
		return this;
	}

	public String getPath() {
		return path;
	}

	public ServiceCall path(String path) {
		this.path = path;
		return this;
	}

	public String[] getPathParameters() {
		return pathParameters;
	}

	public ServiceCall pathParameters(String[] pathParameters) {
		this.pathParameters = Arrays.copyOf(pathParameters, pathParameters.length);
		return this;
	}

	public Map<String, String> getQueryParameters() {
		return queryParameters;
	}

	public ServiceCall queryParameters(Map<String, String> queryParameters) {
		this.queryParameters = queryParameters;
		return this;
	}

	public int getSiteId() {
		return siteId;
	}

	public ServiceCall siteId(int siteId) {
		this.siteId = siteId;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public ServiceCall url(String url) {
		this.url = url;
		return this;
	}

	public Map<String, String> getHeaders() {
		return headers == null ? new HashMap<String, String>() : headers;
	}

	public ServiceCall headers(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}

	public Object getBody() {
		return body;
	}

	public ServiceCall body(Object body) {
		this.body = body;
		return this;
	}

	public String getContentType() {
		return contentType;
	}

	public ServiceCall contentType(String contentType) {
		this.contentType = contentType;
		if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.put(CONTENT_TYPE, contentType);
        return this;
	}

	public String getAccept() {
		return accept;
	}

	public ServiceCall accept(String accept) {
		this.accept = accept;
		if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.put(ACCEPT, accept);
        
        return this;
	}

	public Client getClient() {
		if (client != null) {
			return client;
		}
		if (clientType != null) {
			return ClientBuilder.getClient(clientType);
		}
		return ClientBuilder.getClient();
	}

	public ServiceCall client(Client client) {
		this.client = client;
		return this;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public ServiceCall clientType(ClientType clientType) {
		this.clientType = clientType;
		return this;
	}
	
}
