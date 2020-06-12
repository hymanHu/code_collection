package com.thornBird.commons.serviceCall.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.thornBird.commons.environment.Environment;

/**
 * @Description: Url Components
 * @author: HymanHu
 * @date: 2019-01-18 16:56:40  
 */
public class UrlComponents {
	private static final Pattern pathPattern = Pattern.compile("\\{\\w*}");

	private String scheme;
	private boolean ssl;
	private String host;
	private String path;
	private String[] pathParameters;
	private Map<String, String> queryParameters;
	private int siteId;

	public UrlComponents() {
	}

	public UrlComponents(String scheme, boolean ssl, String host, String path, String[] pathParameters,
			Map<String, String> queryParameters, int siteId) {
		super();
		this.scheme = scheme;
		this.ssl = ssl;
		this.host = host;
		this.path = path;
		this.pathParameters = pathParameters;
		this.queryParameters = queryParameters;
		this.siteId = siteId;
	}
	
	/**
	 * build Url
	 * @return url
	 */
	public String buildUrl() {
		StringBuilder sb = new StringBuilder();
		
		if (StringUtils.isNotBlank(scheme)) {
			sb.append(scheme).append("://");
		} else {
			sb.append(ssl ? "https" : "http").append("://");
		}
		sb.append(buildServiceDomain()).append(buildPath(path, pathParameters)).append(buildQueryParameters());
		
		return sb.toString();
	}
	
	/**
	 * build Service Domain
	 * @return
	 */
	public String buildServiceDomain() {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(host)) {
			sb.append(host);
		} else {
			if (path.toLowerCase().contains("soa")) {
				sb.append(Environment.initialize().mainHost);
			} else {
				sb.append(checkPathFirstLetter(path).toLowerCase().indexOf("micro") == 1 ? 
						Environment.initialize().service.getMicroHost() : 
						Environment.initialize().service.getHost());
			}
		}
		return sb.toString();
	}
	
	/**
	 * build Path
	 * @param path					path
	 * @param pathParameters		path Parameters
	 * @return string
	 */
	public String buildPath(String path, String[] pathParameters) {
		if (pathParameters == null || pathParameters.length <= 0) {
			return checkPathFirstLetter(path);
		}
		
		Matcher matcher = pathPattern.matcher(path);
        int index = 0;
        
        while (matcher.find()) {
        	if (pathParameters.length > index) {
        		String replacement = pathParameters[index];
        		if (StringUtils.isBlank(replacement)) {
                    throw new IllegalArgumentException("path parameter replacement is null");
                }
        		path = matcher.replaceFirst(replacement);
        		matcher = pathPattern.matcher(path);
        	}
        	index ++;
        }
        
		return checkPathFirstLetter(path);
	}
	
	/**
	 * check Path First Letter
	 * @param path		path
	 * @return string
	 */
	public String checkPathFirstLetter(String path) {
		if (path.charAt(0) == '/') {
			return path;
		}
		return "/" + path;
	}
	
	/**
	 * build Query Parameters
	 * @return string
	 */
	public String buildQueryParameters() {
		StringBuilder queryParam = new StringBuilder();
		if (queryParameters != null && !queryParameters.isEmpty()) {
			for (String key : queryParameters.keySet()) {
				String value = queryParameters.get(key);
				if (queryParam.indexOf("?") > -1) {
					queryParam.append("&");
				} else {
					queryParam.append("?");
				}
				queryParam.append(key).append("=").append(value);
			}
			
			if (!queryParam.toString().contains("siteId")) {
				queryParam.append("&siteId=").append(siteId);
			}
		} else {
			queryParam.append("?siteId=").append(siteId);
		}
		
		return queryParam.toString();
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String[] getPathParameters() {
		return pathParameters;
	}

	public void setPathParameters(String[] pathParameters) {
		this.pathParameters = pathParameters;
	}

	public Map<String, String> getQueryParameters() {
		return queryParameters;
	}

	public void setQueryParameters(Map<String, String> queryParameters) {
		this.queryParameters = queryParameters;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public static Pattern getPathpattern() {
		return pathPattern;
	}
	
	public static void main(String[] args) {
		Map<String, String> queryParameters = new HashMap<>();
		queryParameters.put("name", "hyman");
		queryParameters.put("act", "4");
		UrlComponents urlComponents = new UrlComponents("http", false, null, 
				"store/Volume/{}", new String[]{"111"}, queryParameters, 66);
		System.out.println(urlComponents.buildUrl());
		urlComponents.setPath("micro/Volume/{}");
		System.out.println(urlComponents.buildUrl());
		urlComponents.setPath("store/Volume/{}");
		System.out.println(urlComponents.buildUrl());
		urlComponents.setPath("micro/Volume/{}/soa");
		System.out.println(urlComponents.buildUrl());
		urlComponents.setHost("dev-cn.thornBird.com");
		System.out.println(urlComponents.buildUrl());
	}
}
