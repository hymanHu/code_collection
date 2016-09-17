package com.cpkf.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String byte2HexSt(byte b) {
		String hexStr = null;
		int n = b;
		if (n < 0) {
			n = b & 0x7F + 128;
		}
		hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
		return hexStr.toUpperCase();
	}

	public static String byteArray2HexStr(byte[] bs) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bs) {
			sb.append(byte2HexSt(b));
		}
		return sb.toString();
	}

	public static String getMD5ofStr(String origStr) {
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

	public static String getMD5ofStr(String origStr, int times) {
		String md5 = getMD5ofStr(origStr);
		for (int i = 0; i < times - 1; i++) {
			md5 = getMD5ofStr(md5);
		}
		return getMD5ofStr(md5);
	}

	public static boolean verifyPassword(String inputStr, String MD5Code) {
		return getMD5ofStr(inputStr).equals(MD5Code);
	}

	public static boolean verifyPassword(String inputStr, String MD5Code,
			int times) {
		return getMD5ofStr(inputStr, times).equals(MD5Code);
	}

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */ 
	public static String convertMD5(String inStr){

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++){
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}
	
	public static void main(String[] args) {
		String pwd = "111@qqq";
		System.out.println(getMD5ofStr(pwd));
		System.out.println(string2MD5(pwd));
		System.out.println(convertMD5(string2MD5(pwd)));
		System.out.println(convertMD5(convertMD5(string2MD5(pwd))));
	}

}
