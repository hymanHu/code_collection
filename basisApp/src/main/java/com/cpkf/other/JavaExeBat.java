package com.cpkf.other;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * java执行bat文件
 * @author hyman
 */
public class JavaExeBat {

	/**
	 * 执行单个bat
	 */
	public void exeBat(String command) {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command);
			loadStream(process.getInputStream());
			loadStream(process.getErrorStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			process.destroy();
		}
	}

	/**
	 * 执行多个bat
	 */
	public void exeBats(String[] cmdarray) {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmdarray);
			loadStream(process.getInputStream());
			loadStream(process.getErrorStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			process.destroy();
		}
	}

	/**
	 * 输出流
	 */
	public void loadStream(InputStream in) {
		if (in == null) {
			return;
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		String createCmd = "cmd.exe /c D:\\livejournal\\client\\java-client\\target\\java_client-1.7.2-SNAPSHOT\\bin\\create.bat";
		String updateCmd = "cmd.exe /c D:\\livejournal\\client\\java-client\\target\\java_client-1.7.2-SNAPSHOT\\bin\\update.bat";
		String removeCmd = "cmd.exe /c D:\\livejournal\\client\\java-client\\target\\java_client-1.7.2-SNAPSHOT\\bin\\remove.bat";
		String queryCmd = "cmd.exe /c D:\\livejournal\\client\\java-client\\target\\java_client-1.7.2-SNAPSHOT\\bin\\query.bat";

		String[] cmdarray = new String[] { createCmd, updateCmd, removeCmd,
				queryCmd };

		JavaExeBat javaExeBat = new JavaExeBat();
		javaExeBat.exeBat(createCmd);

	}

}
