package com.thornBird.think.notepad.util;

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

	public static void main(String[] args) {
		System.out.println("1111:" + getMD5ofStr("livejournal"));
		System.out.println("admin:" + getMD5ofStr("admin"));
	}

}
