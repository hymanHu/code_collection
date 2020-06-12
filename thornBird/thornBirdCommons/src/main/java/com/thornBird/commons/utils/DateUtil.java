package com.thornBird.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Description: Date Util
 * @author: HymanHu
 * @date: 2019-03-19 11:10:08
 */
public class DateUtil {
	public static final String DATE_FORMAT_SIMPLE = "yyyy-MM-dd";
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String TW_TIME_ZONE = "GMT+8";
	public static final String STANDARD_TIME_ZONE = "GMT";
	public static final String EST_TIME_ZONE = "GMT-5";
	public static final String PST_TIME_ZONE = "GMT-8";
	
	/**
	 * format date
	 * @param date			date
	 * @param pattern		pattern
	 * @return string
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * format To Time Zone Date
	 * @param date					date
	 * @param timeZoneString		time Zone String
	 * @return date
	 */
	public static Date formatToTimeZoneDate(Date date, String timeZoneString) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long time = date.getTime() - differentTimeZonesOffset(calendar.getTimeZone(), TimeZone.getTimeZone(timeZoneString));
		calendar.setTimeInMillis(time);
		return calendar.getTime();
	}
	
	/**
	 * different Time Zones Offset
	 * @param fromTimeZone		from Time Zone
	 * @param toTimeZone		to Time Zone
	 * @return long
	 */
	private static long differentTimeZonesOffset(TimeZone fromTimeZone, TimeZone toTimeZone) {
		return fromTimeZone.getRawOffset() - toTimeZone.getRawOffset();
	}
	
}
