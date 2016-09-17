package com.cpkf.basis.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * 文件操作相关
 */
public class FileOperation {

	public void getFileInfo(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			System.out.println("  " + file.getAbsolutePath());
		} else if (file.isDirectory()) {
			System.out.println(file.getAbsolutePath() + ":");
			File[] childrenFiles = file.listFiles();
			for (File child : childrenFiles) {
				getFileInfo(child.getAbsolutePath());
			}
		}
	}

	public void getJmxFiles(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			System.out.println(filePath + " not exist.");
			return;
		}
		if (file.isFile() && file.getName().endsWith(".jmx")) {
			System.out.println(file.getAbsolutePath());
			modifyConfig(file);
		} else if (file.isDirectory()) {
			File[] children = file.listFiles();
			for (File child : children) {
				getJmxFiles(child.getAbsolutePath());
			}
		}
	}

	public void modifyConfig(File file) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(file);

			@SuppressWarnings("unchecked")
			List<String> lines = IOUtils.readLines(is, "UTF-8");

			int hostIndex = -1;
			int portIndex = -1;
			List<String> lines2 = new ArrayList<String>();
			for (String line : lines) {
				hostIndex = hostIndex == -1 ? hostIndex : ++hostIndex;
				portIndex = portIndex == -1 ? portIndex : ++portIndex;

				if (line.indexOf("name=\"LoopController.loops\"") != -1) {
					line = line.substring(0, line.indexOf("<"))
							+ "<stringProp name=\"LoopController.loops\">22222222</stringProp>";
				}
				if (line.indexOf("name=\"ThreadGroup.num_threads\"") != -1) {
					line = line.substring(0, line.indexOf("<"))
							+ "<stringProp name=\"ThreadGroup.num_threads\">100</stringProp>";
				}
				if (line.indexOf("name=\"rs.host\"") != -1) {
					hostIndex = 0;
				}
				if (line.indexOf("name=\"rs.port\"") != -1) {
					portIndex = 0;
				}
				if (hostIndex == 2) {
					line = line.substring(0, line.indexOf("<"))
							+ "<stringProp name=\"Argument.value\">172.17.20.100</stringProp>";
				}
				if (portIndex == 2) {
					line = line.substring(0, line.indexOf("<"))
							+ "<stringProp name=\"Argument.value\">9999</stringProp>";
				}

				lines2.add(line);
			}

			System.out.println(lines2);

			os = new FileOutputStream(file, false);
			IOUtils.writeLines(lines2, IOUtils.LINE_SEPARATOR_WINDOWS, os, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
	}

	public static void main(String[] args) {
		FileOperation mFile = new FileOperation();
		String folderPath = FileOperation.class.getResource("/testFile/1").getPath();
		mFile.getFileInfo(folderPath);
		// mFile.modifyConfig(new File(folderPath));

		mFile.getJmxFiles("D:/test");
	}

}
