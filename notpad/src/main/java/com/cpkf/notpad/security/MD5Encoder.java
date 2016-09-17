package com.cpkf.notpad.security;

/**  
 * Filename:    MD5Encoder.java
 * Description: 自定义加密器接口
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 24, 2011 4:33:08 PM
 * modified:    
 */
public interface MD5Encoder {
	/* 
	 * method name   : encodePassword
	 * description   : 获得加密密码
	 * @author       : 
	 * @param        : @param origStr
	 * @param        : @param salt
	 * @return       : String
	 * Create at     : May 24, 2011 4:33:42 PM
	 * modified      : 
	 */      
	public String encodePassword(String origStr,Object salt);
	/* 
	 * method name   : isPasswordValid
	 * description   : 验证密码
	 * @author       : 
	 * @param        : @param encPwd
	 * @param        : @param origStr
	 * @param        : @param salt
	 * @return       : boolean
	 * Create at     : May 24, 2011 4:34:01 PM
	 * modified      : 
	 */      
	public boolean isPasswordValid(String encPwd,String origStr,Object salt);
}
