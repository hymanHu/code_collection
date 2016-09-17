package com.cpkf.notpad.commons.constants;

/**  
 * Filename:    PublicResourceEnum.java
 * Description: 公共资源
 * 				home-根目录下所有链接(/*不包含二级目录下请求，/**包含二级目录请求)
 * 				owner-owner下所有请求，用户相关资源eg修改个人资料等
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 30, 2011 10:53:27 AM
 * modified:    
 */
public enum PublicResourceEnum {
	OWNER("account","/account/**"),HOME("home","/*");
	private String publicResourceName;
	private String publicResourcePath;
	private PublicResourceEnum(String publicResourceName, String publicResourcePath) {
		this.publicResourceName = publicResourceName;
		this.publicResourcePath = publicResourcePath;
	}
	public String getPublicResourceName() {
		return publicResourceName;
	}
	public String getPublicResourcePath() {
		return publicResourcePath;
	}
	
}
