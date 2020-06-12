package com.thornBird.commons.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.thornBird.commons.environment.Environment;

/**
 * @Description: 
 * 		Cookie Util
 * 		Cookie: 是客户端技术，程序把每个用户的数据以cookie的形式写给用户各自的浏览器,
 * 		当用户使用浏览器再去访问服务器中的web资源时，就会带着各自的数据去,这样，web资源处理的就是用户各自的数据了.
 * 		Session: 是服务器端技术，利用这个技术，服务器在运行时可以为每一个用户的浏览器创建一个其独享的session对象，
 * 		由于session为用户浏览器独享，所以用户在访问服务器的web资源时，可以把各自的数据放在各自的session中，
 * 		当用户再去访问服务器中的其它web资源时，其它web资源再从用户各自的session中取出数据为用户服务.
 * @author: HymanHu
 * @date: 2019-04-14 20:57:29
 */
public class CookieUtil {
	
	public static final String COOKIE_SPLITTER = "=";
	public static final String DEFAULT_PATH = "/";
	public static final String DEFAULT_DOMAIN = ".thornBird.com";
	public static final String THORNBIRD_USERSESSION_KEY = "THORNBIRD_USERSESSION_ID";
	
	/**
	 * create cookie
	 * @param name			name
	 * @param value			value
	 * @param domain		domain
	 * @param path			path
	 * @param expiry		max age
	 * @return cookie
	 */
	public static Cookie createCookie(String name, String value, String domain, String path, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	/**
	 * create cookie
	 * @param name		name
	 * @param value		value
	 * @param domain	domain
	 * @param path		path
	 * @return cookie
	 */
	public static Cookie createCookie(String name, String value, String domain, String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		return cookie;
	}
	
	/**
	 * find cookie
	 * @param cookies			cookies
	 * @param cookieName		cookie name
	 * @return cookie
	 */
	public static Cookie findCookie(Cookie[] cookies, String cookieName) {
		if (cookies == null || cookies.length == 0) {
			return null;
		}
		
		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				return cookie;
			}
		}
		
		return null;
	}
	
	/**
	 * build cookie by cookie string, for example: MBC_LOCALEID=1; Domain=dev-mbc.shop.com; Path=/
	 * @param cookieName		cookie name
	 * @param cookieString		cookie string
	 * @return cookie
	 */
	public static Cookie buildCookie(String cookieName, String cookieString) {
		if (StringUtils.isBlank(cookieString)) {
			return null;
		}
		
		String[] cookieParts = cookieString.split(";");
        String cookeValue = "";
        String path = "/";
        String domain = DEFAULT_DOMAIN;
        
        for (String cookiePart : cookieParts) {
            if (cookiePart.contains(cookieName + "=")) {
                cookeValue = cookiePart.split(COOKIE_SPLITTER)[1];
            } else if (cookiePart.contains("Path=")) {
                path = cookiePart.split(COOKIE_SPLITTER)[1];
            } else if (cookiePart.contains("Domain=")) {
                domain = cookiePart.split(COOKIE_SPLITTER)[1];
            }
        }
        
        Cookie cookie = new Cookie(cookieName, cookeValue);
        cookie.setDomain(domain);
        cookie.setPath(path);
        
        return cookie;
	}
	
	/**
	 * delete cookie for response
	 * @param cookieName		cookie name
	 * @param response			response
	 */
	public static void deleteCookie(String cookieName, HttpServletResponse response) {
		Cookie deleteCookie = createCookie(
				cookieName, 
				"", 
				DEFAULT_DOMAIN, 
				DEFAULT_PATH, 
				0);
		response.addCookie(deleteCookie);
	}
	
	/**
	 * get User Session Cookie Key
	 * @return key string
	 */
	public static String getUserSessionCookieKey() {
		return String.format("CATALOGCITY_CID_%s_%d", 
				Environment.initialize().location.name().toUpperCase(), 
				SiteContext.getSiteId());
	}
	
	/**
	 * get User Session Cookie Value
	 * @param request		request
	 * @return string
	 */
	public static String getUserSessionCookieValue(HttpServletRequest request) {
		String userSessionId = "";
		String userSessionCookieKey = getUserSessionCookieKey();
		Cookie userSessionCookie = findCookie(request.getCookies(), userSessionCookieKey);
		
		if (request.getSession(false) != null && 
				StringUtils.isNotBlank((String)request.getSession().getAttribute(THORNBIRD_USERSESSION_KEY))) {
			userSessionId = (String)request.getSession().getAttribute(THORNBIRD_USERSESSION_KEY);
		} else if (userSessionCookie != null) {
			userSessionId = userSessionCookie.getValue();
		}
		
		return userSessionId;
	}
	
	/**
	 * build user session cookie string
	 * @param request	request
	 * @return string
	 */
	public static String buildUserSessionCookieString(HttpServletRequest request) {
		return getUserSessionCookieKey() + "=" + getUserSessionCookieValue(request);
	}
	
	/**
	 * write thornbird cookie
	 * @param response
	 * @param userSessionId
	 */
	public static void writeThornbirdCookie(HttpServletResponse response, String userSessionId) {
		Cookie userSessionCookie = createCookie(getUserSessionCookieKey(), userSessionId, DEFAULT_DOMAIN, DEFAULT_PATH);
		response.addCookie(userSessionCookie);
	}
	
	public static void main(String[] args) {
		System.out.println(CookieUtil.getUserSessionCookieKey());
	}
}
