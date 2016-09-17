package com.cpkf.notpad.commons.constants;

/**  
 * Filename:    RegexConstants.java
 * Description: 正则表达式常量
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 17, 2011 9:01:48 AM
 * modified:    
 */
public class RegexConstants {
	//电话号码
	public static final String PHONE_REGEX = "(^(\\d{3,4}-)?\\d{7,8})$|(13[0-9]{9})";
	public static final String EMAIL_REGEX = "^([a-z0-ArrayA-Z]+[-|\\.]?)+[a-z0-ArrayA-Z]@([a-z0-ArrayA-Z]+(-[a-z0-ArrayA-Z]+)?\\.)+[a-zA-Z]{2,}$";
	
	public static void main(String[] args) {
		String email = "test1@test.test";
		System.out.println(email.matches(EMAIL_REGEX));
	}
}
