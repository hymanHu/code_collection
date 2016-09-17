package com.cpkf.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.IOUtils;


/**
 * 操作sysstats监控软件生成的cpu & memcached记录，求取某时间段的监控平均值
 * @author hyman
 *
 */
public class SysStatsLog {
	
	public List<String> cpuAverage = new ArrayList<String>();
	public List<String> memAverage = new ArrayList<String>();
	public DecimalFormat df = new DecimalFormat("#.###");
	public Date startDate;
	public Date endDate;

	public void init(String start, String end) {
		cpuAverage.add(0, "0");
		cpuAverage.add(1, "all");
		cpuAverage.add(2, df.format(0.00));
		cpuAverage.add(3, df.format(0.00));
		cpuAverage.add(4, df.format(0.00));
		cpuAverage.add(5, df.format(0.00));
		cpuAverage.add(6, df.format(0.00));
		cpuAverage.add(7, df.format(0.00));
		memAverage.add(0, "0");
		memAverage.add(1, df.format(0.00));
		memAverage.add(2, df.format(0.00));
		memAverage.add(3, df.format(0.00));
		memAverage.add(4, df.format(0.00));
		memAverage.add(5, df.format(0.00));
		memAverage.add(6, df.format(0.00));
		memAverage.add(7, df.format(0.00));
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
			startDate = sdf.parse(start);
			endDate = sdf.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void operateFolder(String folderPath) {
		File file = new File(folderPath);
		if (!file.exists()) {
			return;
		}
		
		if (file.isFile()) {
			String fileName = file.getName();
			if ((fileName.startsWith("cpu") || fileName.startsWith("mem")) && fileName.endsWith("log")) {
				operateFile(file.getAbsolutePath());
			}
		} else if (file.isDirectory()) {
			File[] children = file.listFiles();
			for (File child : children) {
				operateFolder(child.getAbsolutePath());
			}
		}
	}

	public void operateFile(String filePath) {
		List<String> lines = new ArrayList<String>();
		try {
			InputStream is = new FileInputStream(new File(filePath));
			lines = IOUtils.readLines(is, "utf-8");
			IOUtils.closeQuietly(is);
			
			if (lines == null || lines.isEmpty()) {
				return;
			}
			
			String date = lines.get(0).split(" ")[3].trim();
			for (int i = 3;i < lines.size() - 1; i ++) {
				operateLine(date, lines.get(i));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void operateLine(String dateString, String line){
		try {
			List<String> lineTemp = new ArrayList<String>();
			for (String item : line.split(" ")) {
				if (item != null && !item.trim().isEmpty()) {
					lineTemp.add(item);
				}
			}
			String time = String.format("%s%s%s%s%s", dateString, " ", lineTemp.get(0), " ", lineTemp.get(1));
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
			Date date = sdf.parse(time);
			
			if (date.after(startDate) && date.before(endDate)) {
				if (lineTemp.get(2).equals("all")) {
					List<String> cpuAverageTemp = new ArrayList<String>();
					int count = Integer.parseInt(cpuAverage.get(0));
					cpuAverageTemp.add(String.valueOf(count  +1));
					cpuAverageTemp.add("all");
					for(int i = 3; i < lineTemp.size(); i ++) {
						Double sum = count * Double.parseDouble(cpuAverage.get(i - 1)) + Double.parseDouble(lineTemp.get(i));
						Double average = sum / (count + 1);
						cpuAverageTemp.add(df.format(average));
					}
					cpuAverage = cpuAverageTemp;
				} else {
					List<String> memAverageTemp = new ArrayList<String>();
					int count = Integer.parseInt(memAverage.get(0));
					memAverageTemp.add(String.valueOf(count  +1));
					for (int i = 2; i < lineTemp.size(); i ++) {
						Double sum = count * Double.parseDouble(memAverage.get(i - 1)) + Double.parseDouble(lineTemp.get(i));
						Double average = sum / (count + 1);
						memAverageTemp.add(df.format(average));
					}
					memAverage = memAverageTemp;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void statsToString() {
		Map<String, String> cpuMap = new LinkedHashMap<String, String>();
		cpuMap.put("statsCount", cpuAverage.get(0));
		cpuMap.put("CPU", cpuAverage.get(1));
		cpuMap.put("%user", cpuAverage.get(2));
		cpuMap.put("%nice", cpuAverage.get(3));
		cpuMap.put("%system", cpuAverage.get(4));
		cpuMap.put("%iowait", cpuAverage.get(5));
		cpuMap.put("%steal", cpuAverage.get(6));
		cpuMap.put("%idle", cpuAverage.get(7));
		Map<String, String> memMap = new LinkedHashMap<String, String>();
		memMap.put("statsCount", memAverage.get(0));
		memMap.put("kbmemfree", memAverage.get(1));
		memMap.put("kbmemused", memAverage.get(2));
		memMap.put("%memused", memAverage.get(3));
		memMap.put("kbbuffers", memAverage.get(4));
		memMap.put("kbcached", memAverage.get(5));
		memMap.put("kbcommit", memAverage.get(6));
		memMap.put("%commit", memAverage.get(7));
		
		System.out.println(cpuMap.toString());
		System.out.println(memMap.toString());
	}
	
	public static void main(String[] args) {
		SysStatsLog ssl = new SysStatsLog();
		ssl.init("Thu Feb 28 09:27:45 CST 2013", "Thu Feb 28 11:28:27 CST 2013");
		ssl.operateFolder("C:\\Users\\hyman\\Desktop\\log\\73");
//		ssl.operateFile("C:\\Users\\hyman\\Desktop\\log\\21\\cpuInfo_2013-01-17_11_06_16.log");
//		ssl.operateline("01/17/2013", "12:32:21 PM     all      0.00      0.00      0.15      0.00      0.00     99.85");
		ssl.statsToString();
	}
}
