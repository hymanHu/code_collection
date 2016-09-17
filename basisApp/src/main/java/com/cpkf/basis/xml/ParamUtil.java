package com.cpkf.basis.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.thoughtworks.xstream.XStream;

public class ParamUtil {

	public static void serializeToObjectXml(Object object, String pathFile) {
		XStream xstream = new XStream();
		try {
			FileOutputStream fos = new FileOutputStream(pathFile);
			xstream.toXML(object, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object deSerializeObjectFromXml(String pathFile) {
		XStream xs = new XStream();
		Object object = null;
		try {
			FileInputStream fis = new FileInputStream(pathFile);
			object = xs.fromXML(fis);
			fis.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	public static void saveObjectToFile(Object object, String pathFile) {
		try {
			FileOutputStream fos = new FileOutputStream(pathFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object restoreObjectFromFile(String pathFile) {
		Object object = null;
		try {
			FileInputStream fis = new FileInputStream(pathFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			object = ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	public static void main(String[] args) {
		String xmlPath = ReadAndWriteXml.class.getClassLoader()
				.getResource("testFile/task.xml").getPath();
		System.out.println(ParamUtil.deSerializeObjectFromXml(xmlPath));
	}
}
