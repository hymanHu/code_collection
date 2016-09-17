package com.cpkf.notpad.security.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.cpkf.notpad.commons.utils.MD5;

/**  
 * Filename:    MD5EncoderImpl.java
 * Description: 自定义加密器，需实现PasswordEncoder接口
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 24, 2011 4:35:56 PM
 * modified:    
 */
public class MD5EncoderImpl implements  PasswordEncoder {
	/* 
	 * method name   : encodePassword
	 * description   : 获得加密密码
	 * @author       : Jiang.Hu
	 * modified      : leo ,  May 24, 2011
	 * @see          : @see org.springframework.security.authentication.encoding.PasswordEncoder#encodePassword(java.lang.String, java.lang.Object)
	 */    
	public String encodePassword(String rawPass, Object salt) throws DataAccessException {
		return MD5.getMD5ofStr(rawPass);
	}
	/* 
	 * method name   : isPasswordValid
	 * description   : 验证密码
	 * @author       : Jiang.Hu
	 * modified      : leo ,  May 24, 2011
	 * @see          : @see org.springframework.security.authentication.encoding.PasswordEncoder#isPasswordValid(java.lang.String, java.lang.String, java.lang.Object)
	 */    
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) throws DataAccessException {
		return encPass.equals(encodePassword(rawPass, salt));
	}
}
