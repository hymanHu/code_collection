package com.cpkf.commons;

/**
 * 转义符号
 * @author Administrator
 */
public class EscapedSymbols {
	public static final char DIR_SEPARATOR_UNIX = '/';
	public static final char DIR_SEPARATOR_WINDOWS = '\\';
	public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
	public static final String LINE_SEPARATOR_UNIX = "\n";
	public static final String BACKSPACE_SEPARATOR = "\b";
	public static final String TURNPAGE_SEPARATOR = "\f";
	public static final String TAB_SEPARATOR = "\t";
	
	public static void main(String[] args) {
		System.out.println(String.format("%s%s%s%s", "UNIX 目录：", "start", DIR_SEPARATOR_UNIX, "end"));
		System.out.println(String.format("%s%s%s%s", "WINDOWS 目录：", "start", DIR_SEPARATOR_WINDOWS, "end"));
		System.out.println(String.format("%s%s%s%s", "WINDOWS换行：", "start", LINE_SEPARATOR_WINDOWS, "end"));
		System.out.println(String.format("%s%s%s%s", "UNIX 换行：", "start", LINE_SEPARATOR_UNIX, "end"));
		System.out.println(String.format("%s%s%s%s", "退格：", "start", BACKSPACE_SEPARATOR, "end"));
		System.out.println(String.format("%s%s%s%s", "换页：", "start", TURNPAGE_SEPARATOR, "end"));
		System.out.println(String.format("%s%s%s%s", "水平制表符：", "start", TAB_SEPARATOR, "end"));
	}
}
