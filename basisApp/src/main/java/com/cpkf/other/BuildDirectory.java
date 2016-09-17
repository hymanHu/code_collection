package com.cpkf.other;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BuildDirectory {

	public static void main(String[] args) {
		InputStream is = null;
		String lineString = null;
		StringBuffer sb = new StringBuffer();
		try {
			is = new FileInputStream(BuildDirectory.class.getResource(
					"/document/js&jsp&CSS/summary.txt").getPath());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"));
			sb.append("/*\r\n");
			while ((lineString = reader.readLine()) != null) {
				int index = lineString.indexOf(".") == -1 ? 1 : lineString
						.indexOf(".");
				if (lineString.substring(0, index).matches("[0-9]+")) {
					sb.append(String.format("%s%s%s", " * ", lineString, "\r\n"));
				}
			}
			sb.append(" * -------------------------------------------------- */");
			reader.close();
			
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
