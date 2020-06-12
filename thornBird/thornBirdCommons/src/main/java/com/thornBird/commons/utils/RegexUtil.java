package com.thornBird.commons.utils;

import java.util.regex.Pattern;

/**
 * @Description: Regex Util
 * @author: HymanHu
 * @date: 2019-03-20 08:59:17
 */
public class RegexUtil {
	public final static String PORT_REGEX = "^([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";
	public final static String IP_REGEX = "^(?:(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.){3}(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\b";
	public final static String NUM_REGEX = "[0-9]+";
	public final static String PHONE_REGEX = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	public final static String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	public final static String URL_REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	/**
	 * regex
	 * @param regex
	 * @param input
	 * @return
	 */
	public static boolean regex(String regex, String input) {
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(input).matches();
	}

}
