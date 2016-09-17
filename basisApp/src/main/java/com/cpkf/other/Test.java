package com.cpkf.other;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Test {
	public static void main(String[] args) throws ParseException {
		/*
		 * 2015-08-28 ---- yyyy-MM-dd
		 * 2015-08-28 18:28:30 ---- yyyy-MM-dd HH:mm:ss
		 * 2015-8-28 ---- yyyy-M-d
		 * 2015-8-28 18:8:30 ---- yyyy-M-d H:m:s
		 * Aug 28, 2015 6:8:30 PM ---- MMM d, yyyy h:m:s aa
		 * Fri Aug 28 18:08:30 CST 2015 ---- EEE MMM d HH:mm:ss ‘CST’ yyyy
		 * */
		DateFormat format = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
		String dateString = "May 10, 2021";
		System.out.println(format.parse(dateString));
	}
}
