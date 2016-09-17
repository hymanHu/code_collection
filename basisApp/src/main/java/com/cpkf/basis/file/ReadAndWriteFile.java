package com.cpkf.basis.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 读写文件
 */
public class ReadAndWriteFile {
	private InputStream is;
	private OutputStream os;
	private RandomAccessFile randomFile = null;
	private StringBuffer sb = new StringBuffer();

	public String readFile(String filePath) {
		try {
			is = new FileInputStream(filePath);
			int buffer = 0;
			String lineString = null;

			// FileInputStream读取，出现编码问题
			// while ((buffer = is.read()) != -1) {
			// sb.append((char)buffer);
			// }

			// 通过FileReader读取文件
			// FileReader fileReader = new FileReader(filePath);
			// while ((buffer = fileReader.read()) != -1) {
			// sb.append((char)buffer);
			// }

			// 通过InputStreamReader读取文件
			// InputStreamReader isr = new InputStreamReader(is, "utf-8");
			// while ((buffer = isr.read()) != -1) {
			// sb.append((char) buffer);
			// }

			// 通过BufferedReader读取文件
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			while ((lineString = reader.readLine()) != null) {
				// sb.append(String.format("%s%s", lineString, "\r\n"));
				sb.append(lineString.trim());
			}

			// 随机读取，出现乱码
			// randomFile = new RandomAccessFile(filePath, "r");
			// long fillength = randomFile.length();
			// randomFile.seek(0);
			// while ((buffer = randomFile.read()) != -1) {
			// sb.append((char) buffer);
			// }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (randomFile != null) {
					randomFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	public void writeFile(String content, String filePath) {
		try {
			// 用fileoutputstream写文件
			os = new FileOutputStream(filePath);
			os.write(content.getBytes("utf-8"));
			os.flush();

			// 用FileWriter写文件
			// FileWriter fileWriter = new FileWriter(filePath);
			// fileWriter.write(content, 0, content.length());
			// fileWriter.flush();
			// fileWriter.close();

			// 用OutputStreamWriter写文件
			// OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			// osw.write(content, 0, content.length());
			// osw.flush();
			// osw.close();

			// 用RandomAccessFile随机写文件
			// randomFile = new RandomAccessFile(filePath, "rw");
			// randomFile.seek(randomFile.length());
			// randomFile.write(content.getBytes("utf-8"));
		} catch (Exception e) {
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (randomFile != null) {
					randomFile.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * OutputStream os = new FileOutputStream(writeFile, false);
	 * false--覆盖原文件
	 * true--不覆盖原文件
	 * IOUtils.writeLines(lines2, IOUtils.LINE_SEPARATOR_WINDOWS, os, "UTF-8");
	 * 第二个参数，每行追加的字符串，null和换行效果一致
	 */
	public void readAndWriteByIOUtil(String readFilePath, String writeFilePath) {
		try {
			File readFile = new File(readFilePath);
			InputStream is = new FileInputStream(readFilePath);

			List<String> lines = FileUtils.readLines(readFile, "UTF-8");
//			List<String> lines2 = IOUtils.readLines(is, "UTF-8");
			
			IOUtils.closeQuietly(is);
			
			Collections.sort(lines, new Comparator<String>() {

				public int compare(String o1, String o2) {
					String tmp1 = o1.substring(o1.indexOf("\">") + 2, o1.lastIndexOf("</a"));
					String tmp2 = o2.substring(o2.indexOf("\">") + 2, o2.lastIndexOf("</a"));
					return tmp1.compareToIgnoreCase(tmp2);
				}
			});
			
//			List<String> lines3 = new ArrayList<String>();
//			for (String line : lines) {
//				if (line.indexOf(": ") > 0) {
//					line = line.substring(line.indexOf(": ") + 2, line.length());
//				}
//				if (!line.startsWith("\t") && !lines3.contains(line)) {
//					lines3.add(line);
//				}
//			}
			
			File writeFile = new File(writeFilePath);
			OutputStream os = new FileOutputStream(writeFile, false);
			
//			FileUtils.writeLines(writeFile, "UTF-8", lines);
			IOUtils.writeLines(lines, IOUtils.LINE_SEPARATOR_WINDOWS, os, "UTF-8");
			
			IOUtils.closeQuietly(os);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void  readAndWriteBigFile(String readFilePath, String writeFilePath) {
		BufferedReader br = null;
		OutputStream os = null;
		try {
			br = new BufferedReader(new FileReader(readFilePath));
			os = new FileOutputStream(writeFilePath);
			String line = null;
			
//			os.write("<root>\r\n".getBytes("utf-8"));
//			os.flush();
			System.out.println("start");
			while((line = br.readLine()) != null){
				if (line.indexOf("<categoryPath>") != -1 || line.indexOf("<categoryNode>") != -1) {
					os.write(line.trim().getBytes("utf-8"));
					os.flush();
				} else if (line.indexOf("</item>") != -1) {
					os.write("\r\n".getBytes("utf-8"));
					os.flush();
				}
			}
			System.out.println("end");
//			os.write("\r\n</root>".getBytes("utf-8"));
//			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void  readAndWriteBigFile2(String readFilePath, String writeFilePath) {
		BufferedReader br = null;
		OutputStream os = null;
		try {
			br = new BufferedReader(new FileReader(readFilePath));
			os = new FileOutputStream(writeFilePath);
			String line = null;
			
			os.write("<root>\r\n\t".getBytes("utf-8"));
			os.flush();
			System.out.println("start");
			while((line = br.readLine()) != null){
				if (line.indexOf("<name>") != -1 || line.indexOf("<categoryPath>") != -1 || line.indexOf("<categoryNode>") != -1) {
					os.write(line.trim().getBytes("utf-8"));
					os.flush();
				} else if (line.indexOf("</item>") != -1) {
					os.write("\r\n\t".getBytes("utf-8"));
					os.flush();
				}
			}
			System.out.println("end");
			os.write("\r\n</root>".getBytes("utf-8"));
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<String> readKey (String readFilePath) {
		List<String> list = new ArrayList<String>();
		try {
			File readFile = new File(readFilePath);
			List<String> lines = FileUtils.readLines(readFile, "UTF-8");
			
			for (String line : lines) {
				list.add(line.trim());
				line = line.trim();
			}
			System.out.println(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void  readAndWriteBigFile3(String readFilePath, String writeFilePath) {
		List<String> keys = readKey("C:\\Users\\Administrator\\Desktop\\inserted.txt");
		
		BufferedReader br = null;
		OutputStream os = null;
		try {
			br = new BufferedReader(new FileReader(readFilePath));
			os = new FileOutputStream(writeFilePath);
			String line = null;
			
			System.out.println("start");
			
			StringBuffer sb = new StringBuffer();
			boolean flag = false;
			boolean flag1 = false;
			while((line = br.readLine()) != null){
				if (line.indexOf("<root>") != -1 || line.indexOf("<items>") != -1 
						|| line.indexOf("</root>") != -1 || line.indexOf("</items>") != -1) {
					os.write((line + "\r\n").getBytes("utf-8"));
					os.flush();
					sb = new StringBuffer();
				} else {
					sb.append(line + "\r\n");
				}
				
				if (line.indexOf("<marketplace>") != -1) {
					String marketplace = line.substring(line.indexOf(">") + 1, line.indexOf("</"));
					if ("false".equals(marketplace)) {
						flag1 = true;
					} else {
						flag1 = false;
					}
				}
				
				if (line.indexOf("<categoryNode>") != -1) {
					String key = line.substring(line.indexOf(">") + 1, line.indexOf("</"));
					if (keys.contains(key)) {
						flag = true;
					} else {
						flag = false;
					}
				}
				
				if (line.indexOf("</item>") != -1) {
					if (flag && flag1) {
						os.write(sb.toString().getBytes("utf-8"));
						os.flush();
					}
					sb = new StringBuffer();
					flag = false;
					flag1 = false;
				}
				
			}
			System.out.println("end");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeRepeat(String readFilePath, String writeFilePath) {
		BufferedReader br = null;
		OutputStream os = null;
		try {
			br = new BufferedReader(new FileReader(readFilePath));
			os = new FileOutputStream(writeFilePath);
			String line = null;
			List<String> list = new ArrayList<String>();
			
			os.write("<root>\r\n".getBytes("utf-8"));
			os.flush();
			
			while((line = br.readLine()) != null){
				System.out.println("--" + line);
				if (!list.contains(line.trim()) && StringUtils.isNotEmpty(line)) {
					list.add(line);
					os.write(("\t" +line.trim() + "\r\n").getBytes("utf-8"));
					os.flush();
				}
			}
			
			os.write("</root>".getBytes("utf-8"));
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ReadAndWriteFile readAndWriteFile = new ReadAndWriteFile();
		// String filePath =
		// ReadAndWriteFile.class.getResource("/document/js&jsp/summary.txt").getPath();
		// String filePath = "D:/1.txt";
		// System.out.println(readAndWriteFile.readFile(filePath));

		// String content = readAndWriteFile.readFile(filePath);
		// String writeFilePath = "d:/test.txt";
		// readAndWriteFile.writeFile(content, writeFilePath);

//		readAndWriteFile.readAndWriteByIOUtil(
//				ReadAndWriteFile.class.getClassLoader().getResource("testFile/test.log").getPath(), "D:/1.log");
//		readAndWriteFile.readAndWriteByIOUtil("D:\\2.txt", "D:\\3.txt");
//		readAndWriteFile.readAndWriteBigFile("C:\\Users\\Administrator\\Desktop\\items.xml", "C:\\Users\\Administrator\\Desktop\\11.xml");
//		readAndWriteFile.readAndWriteBigFile("C:\\projects\\etl\\workspace\\data\\walmart\\download\\items_source.xml", "C:\\Users\\Administrator\\Desktop\\11.xml");
//		readAndWriteFile.removeRepeat("C:\\Users\\Administrator\\Desktop\\11.xml", "C:\\Users\\Administrator\\Desktop\\22.xml");
//		readAndWriteFile.readAndWriteBigFile2("C:\\projects\\etl\\workspace\\data\\walmart\\download\\items_source.xml", "C:\\Users\\Administrator\\Desktop\\11.xml");
//		readAndWriteFile.readKey("C:\\Users\\Administrator\\Desktop\\inserted.txt");
//		readAndWriteFile.readAndWriteBigFile3("C:\\projects\\etl\\workspace\\data\\walmart\\download\\items_source.xml", "D:\\projectCodes\\google\\basisApp\\src\\main\\resources\\nyc.xml");
		readAndWriteFile.readAndWriteBigFile3("C:\\projects\\etl\\workspace\\data\\walmart\\download\\items_source.xml", "C:\\Users\\Administrator\\Desktop\\11.xml");
//		readAndWriteFile.readAndWriteBigFile3("C:\\Users\\Administrator\\Desktop\\items.xml", "C:\\Users\\Administrator\\Desktop\\11.xml");
		
	}

}
