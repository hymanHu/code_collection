package com.thornBird.personalArchives.common;

/**
 * @Description: URL Config Resources
 * @author: HymanHu
 * @date: 2019-09-01 15:48:10
 */
public enum URLConfigResources {
	// 不会拦截页面，只会拦截路径
	DEFAULT_PATTERN("/"),
	// 拦截所有的文件夹，不包含子文件夹
	ANY_SINGLE_PATTERN("/*"),
	// 拦截所有的文件夹及里面的子文件夹
	ANY_PATTERN("/**"),
	
	WELCOME_PATTERN("/welcome"),
	LOGIN_URL_PATTERN("/login"),
	LOGOUT_URL_PATTERN("/logout"),
	
	// js && css
	STATIC_PATTERN("/static/**"),
	JS_PATTERN("/js/**"),
	CSS_PATTERN("/css/**"),
	MINI_PATTERN("/min/**"),
	
	// error page
	ERROR_PATTERN("/error/**"),
	ERROR_PATTH("/error"),
	NOT_FOUND("/error/404"),
    FORBIDDEN("/error/403"),
	SERVICE_ERROR("/error/500");
	
	String url;

	private URLConfigResources(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * get js && css path
	 * @return
	 */
	public static String[] getStaticAssetsList() {
        return new String[]{
        	MINI_PATTERN.url, CSS_PATTERN.url, JS_PATTERN.url
        };
    }
	
	// 本地资源路径
	public static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/"
    };
}
