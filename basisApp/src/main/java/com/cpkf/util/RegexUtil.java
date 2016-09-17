package com.cpkf.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	// 端口0-65535
	public static String PORT_REGEX = "^([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";
	// ip
	public static String IP_REGEX = "^(?:(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.){3}(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\b";
	// 整形，支持001
	public static String NUM_REGEX = "[0-9]+";
	
	public static String PHONE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	
	public static String EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	
	public static String URL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	public static boolean regex(String regexString, String testString) {
		// testString.matches(regexString);

		Pattern pattern = Pattern.compile(regexString);
		return pattern.matcher(testString).matches();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(RegexUtil.regex(IP_REGEX, "s213608058461"));
		System.out.println(RegexUtil.regex(URL, "C:\\Users\\Administrator\\Desktop\\20140617092022.jpg"));
		System.out.println(RegexUtil.regex(URL, "http://ww4.sinaimg.cn/bmiddle/74c247f7tw1ehgv05klsoj20hs0hwq50.jpg"));
		
		// 替换'\'
		String input = "cdsacdas\\cdsacas\\cdsaca\\cds";
        Pattern p = Pattern.compile("\\\\");
        Matcher m = p.matcher(input);
        String r = m.replaceAll("_");
        System.out.println(r);
 
        Pattern p2 = Pattern.compile("\\", Pattern.LITERAL);
        Matcher m2 = p2.matcher(input);
        String r2 = m2.replaceAll("_");
        System.out.println(r2);
	}
	
}
