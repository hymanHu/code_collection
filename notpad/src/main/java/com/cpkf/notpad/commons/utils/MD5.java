package com.cpkf.notpad.commons.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**  
 * Filename:    MD5.java
 * Description: 标准的MD5加密方法，使用security下messageDigest类处理
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 24, 2011 3:51:44 PM
 * modified:    
 */
public class MD5 {
	/* 
	 * method name   : byte2HexSt
	 * description   : 字节标准移位转十六进制方法
	 * @author       : 
	 * @param        : @param b
	 * @return       : String
	 * Create at     : May 24, 2011 4:06:08 PM
	 * modified      : 
	 */      
	public static String byte2HexSt(byte b){
		String hexStr = null;
		int n = b;
		if(n < 0){
			//若需要自定义加密，修改该移位算法
			n = b & 0x7F + 128;
		}
		hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
		return hexStr.toUpperCase();
	}
	/* 
	 * method name   : byteArray2HexStr
	 * description   : 处理字节数组得到MD5的方法
	 * @author       : 
	 * @param        : @param bs
	 * @return       : String
	 * Create at     : May 24, 2011 4:07:24 PM
	 * modified      : 
	 */      
	public static String byteArray2HexStr(byte[] bs){
		StringBuffer sb = new StringBuffer();
		for(byte b :bs){
			sb.append(byte2HexSt(b));
		}
		return sb.toString();
	}
	/* 
	 * method name   : getMD5ofStr
	 * description   : 获得MD5加密密码
	 * @author       : 
	 * @param        : @param origStr
	 * @return       : String
	 * Create at     : May 24, 2011 4:08:41 PM
	 * modified      : 
	 */      
	public static String getMD5ofStr(String origStr){
		String origMD5 = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] result = md5.digest(origStr.getBytes());
			origMD5 = byteArray2HexStr(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return origMD5;
	}
	/* 
	 * method name   : getMD5ofStr
	 * description   : 获得MD5多次加密密码
	 * @author       : 
	 * @param        : @param origStr
	 * @param        : @param times
	 * @return       : String
	 * Create at     : May 24, 2011 4:14:44 PM
	 * modified      : 
	 */      
	public static String getMD5ofStr(String origStr,int times){
		String md5 = getMD5ofStr(origStr);
		for(int i = 0;i < times - 1;i ++){
			md5 = getMD5ofStr(md5);
		}
		return getMD5ofStr(md5);
	}
	/* 
	 * method name   : verifyPassword
	 * description   : 验证密码
	 * @author       : 
	 * @param        : @param inputStr
	 * @param        : @param MD5Code
	 * @return       : boolean
	 * Create at     : May 24, 2011 4:11:16 PM
	 * modified      : 
	 */      
	public static boolean verifyPassword(String inputStr,String MD5Code){
		return getMD5ofStr(inputStr).equals(MD5Code);
	}
	/* 
	 * method name   : verifyPassword
	 * description   : 多次加密验证
	 * @author       : 
	 * @param        : @param inputStr
	 * @param        : @param MD5Code
	 * @param        : @return
	 * @return       : boolean
	 * Create at     : May 24, 2011 4:16:37 PM
	 * modified      : 
	 */      
	public static boolean verifyPassword(String inputStr,String MD5Code,int times){
		return getMD5ofStr(inputStr, times).equals(MD5Code);
	}
	public static void main(String[] args) {
		System.out.println("1111:" + getMD5ofStr("1111"));
		System.out.println("admin:" + getMD5ofStr("admin"));
	}
}
