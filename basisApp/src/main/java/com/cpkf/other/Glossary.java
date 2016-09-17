package com.cpkf.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Glossary {
	public void readAndWriteByIOUtil(String readFilePath, String writeFilePath) {
		try {
			InputStream is = new FileInputStream(readFilePath);
			@SuppressWarnings("unchecked")
			List<String> lines = IOUtils.readLines(is, "UTF-8");
			IOUtils.closeQuietly(is);

			List<String> temp = makeGlossary(lines);
			
			File writeFile = new File(writeFilePath);
			OutputStream os = new FileOutputStream(writeFile, false);
			IOUtils.writeLines(temp, IOUtils.LINE_SEPARATOR_WINDOWS, os, "UTF-8");
			IOUtils.closeQuietly(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<String> makeGlossary(List<String> lines) {
		List<String> temp = new ArrayList<String>();
		
		for (String line : lines) {
			String first = line.substring(0, 1).toUpperCase();
			if (!temp.contains(first) && !first.endsWith(line.trim())) {
				temp.add(first);
			}
			temp.add(line);
		}
		
		Collections.sort(temp, new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		return temp;
	}
	
	public static void main(String[] args) {
		Glossary aa = new Glossary();
		aa.readAndWriteByIOUtil("C:\\Users\\hyman\\Desktop\\new.txt", "C:\\Users\\hyman\\Desktop\\1.txt");
	}
}
