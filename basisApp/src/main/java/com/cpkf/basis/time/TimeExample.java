package com.cpkf.basis.time;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

public class TimeExample {

	public static void main(String[] args) throws ParseException {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		Date date1 = new Date();
		System.out.println(String.format("%s%d-%d-%d %d:%d:%d(%s)", "date1: ",
				date1.getYear() + 1900, date1.getMonth() + 1, date1.getDate(),
				date1.getHours(), date1.getMinutes(), date1.getSeconds(),
				date1.getTime()));
		System.out.println(String.format("%s%s", "date1: ", sdf.format(date1)));
		System.out.println("=====================================");

		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.HOUR_OF_DAY, 14);
		calendar1.set(Calendar.MINUTE, 35);
		calendar1.add(Calendar.DATE, 1);//增加1天
		calendar1.add(Calendar.DATE, -3);//三天前
		Calendar calendar2 = Calendar.getInstance();
		if (calendar1.before(calendar2)) {
			System.out.println("time1 brfore time2.");
			calendar1.add(Calendar.DAY_OF_MONTH, 1);
		} else {
			System.out.println("time1 after time2.");
		}
		Date calendar3 = calendar1.getTime();
		System.out.println(String.format("%s%d-%d-%d %d:%d:%d", "canlendar1: ",
						calendar1.get(Calendar.YEAR),
						calendar1.get(Calendar.MONTH) + 1,
						calendar1.get(Calendar.DAY_OF_MONTH),
						calendar1.get(Calendar.HOUR_OF_DAY),
						calendar1.get(Calendar.MINUTE),
						calendar1.get(Calendar.SECOND)));
		System.out.println(String.format("%s%s", "canlendar3: ",
				sdf.format(calendar3)));
		System.out.println("=====================================");

		long initialDelay = TimeUnit.MINUTES.convert(
				calendar1.getTimeInMillis() - System.currentTimeMillis(),
				TimeUnit.MILLISECONDS);
		System.out.println(String.format("%s%s%s",
				"delay for calendar1 & now: ", initialDelay, " MINUTES"));
		initialDelay = TimeUnit.MINUTES.convert(calendar1.getTimeInMillis()
				- calendar2.getTimeInMillis(), TimeUnit.MILLISECONDS);
		System.out.println(String.format("%s%s%s",
				"delay for calendar1 & calendar2: ", initialDelay, " MINUTES"));

		System.out.println("=====================================");
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(
					"MM/dd/yyyy hh:mm:ss a", Locale.US);
			System.out.println(sdf1.parse("01/17/2013 09:57:16 AM"));

			SimpleDateFormat sdf2 = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss z yyyy", Locale.US);
			System.out.println(sdf2.parse("Thu Jan 17 09:57:15 CST 2013"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		/*
		 * CST\DST\UTC\GMT
		 */
//		System.out.println("=====================================");
//		System.out.println(date1);
//		sdf.setTimeZone(TimeZone.getTimeZone("CST"));
//		System.out.println(sdf.parse(sdf.format(date1)));
		
//		System.out.println("=====================================");
//		System.out.println(sdf.parse("6/8/2013"));
//		System.out.println(convertToCalendar(sdf.parse("6/8/2013")));
//		sdf.setTimeZone(TimeZone.getTimeZone("CST"));
//		System.out.println(sdf.parse("6/8/2013"));
		
		System.out.println("=====================================");
		System.out.println(TimeExample.convTimeZone("6/8/2013", "CST +8", "CST"));
		System.out.println(TimeExample.convertToCalendar(sdf.parse("6/8/2013")).getTime());
		
		try {
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			Date date = sdf.parse("6/8/2013");
			System.out.println(date.toString() + "-----------");
			GregorianCalendar calendar = convertToCalendar(date);
			System.out.println(datatypeFactory.newXMLGregorianCalendar(calendar));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static String convTimeZone(String time, String sourceTZ, String destTZ) {
		final String DATE_TIME_FORMAT = "MM/dd/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		Date specifiedTime;
		try {
			if (sourceTZ != null)
				sdf.setTimeZone(TimeZone.getTimeZone(sourceTZ));
			else
				sdf.setTimeZone(TimeZone.getDefault()); 
			specifiedTime = sdf.parse(time);
		} catch (Exception e1) {
			return time;
		}

		if (destTZ != null)
			sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdf.setTimeZone(TimeZone.getDefault()); 

		return sdf.format(specifiedTime);
	}
	
	public static GregorianCalendar convertToCalendar(Date date) throws ParseException {
		GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
//		calendar.setTimeZone(TimeZone.getTimeZone("CST + 8"));
//		GregorianCalendar calendar = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());
		return calendar;
	}

	public static Date convertToDate(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(date);
	}

}
