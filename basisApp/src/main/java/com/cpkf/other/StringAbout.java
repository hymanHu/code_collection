package com.cpkf.other;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;


public class StringAbout {

	public static void main(String[] args) {
		System.out.println(String.format("jdbc:mysql://%s:%d/?user=%s&password=%s", "172.17.20.112", 3306, "lj",
				"livejournal"));
		System.out.println(String.format("%03d", 4));
		
		System.out.println(StringUtils.isEmpty("") + "|" + StringUtils.isBlank(""));
		System.out.println(StringUtils.isEmpty(null) + "|" + StringUtils.isBlank(null));
		System.out.println(StringUtils.isNumeric("") + "|" + StringUtils.isNumeric(null) 
				+ "|" + StringUtils.isNumeric("aa") + "|" + StringUtils.isNumeric("0123") 
				+ "|" + StringUtils.isNumericSpace("as"));
		System.out.println(Integer.parseInt("00123"));
		
		System.out.println(Integer.parseInt("33"));
		System.out.println(String.valueOf(7));
		
		NumberFormat nf = NumberFormat.getInstance();
		NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US);
		System.out.println(nf.format(22.3));
		System.out.println(df.format(22.3));
		
		System.out.println("Credit Card Purchase: ".length());
		System.out.println("USAD".substring(0, 2));
		
		String a = "Abc";
		System.out.println(a.compareTo("abc"));
	}
	
	public static String toUpperCaseFirstOne(String s) {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
	
	public static String toLowerCaseFirstOne(String s) {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

}
