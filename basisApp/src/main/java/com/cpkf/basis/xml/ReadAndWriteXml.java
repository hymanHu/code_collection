package com.cpkf.basis.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 利用dom4j读写xml
 * @author hyman
 */
public class ReadAndWriteXml {

	public static Document getDocument(String xmlPath) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(xmlPath);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	public static Element getRootElement(Document document) {
		return document == null ? null : document.getRootElement();
	}

	// 根据nodeName查询，从parentElement开始，返回第一个element
	public static Element getElementByNodeName(Element parentElement, String nodeName) {
		Element element = null;
		if (parentElement == null) {
			return element;
		}

		@SuppressWarnings("unchecked")
		Iterator<Element> iterator = parentElement.elementIterator();
		while (iterator.hasNext()) {
			Element childElement = iterator.next();
			if (childElement.getName().equals(nodeName)) {
				element = childElement;
				break;
			}
			element = getElementByNodeName(childElement, nodeName);
			if (element != null) {
				break;
			}
		}

		return element;
	}
	
	// 根据nodePath查询，从parentElement开始，返回第一个element
	public static Element getElementByNodePath(Element parentElement, String nodePath) {
		Element element = null;
		if (parentElement == null) {
			return element;
		}

		@SuppressWarnings("unchecked")
		Iterator<Element> iterator = parentElement.elementIterator();
		while (iterator.hasNext()) {
			Element childElement = iterator.next();
			if (childElement.getPath().equals(nodePath)) {
				element = childElement;
				break;
			}
			element = getElementByNodePath(childElement, nodePath);
			if (element != null) {
				break;
			}
		}

		return element;
	}
	
	public List<Element> getElements(Element parentElement, List<Element> elementList) {
		if (parentElement == null) {
			return elementList;
		}
		
		Iterator<Element> iterator = parentElement.elementIterator();
		while (iterator.hasNext()) {
			Element childElement = iterator.next();
			if (childElement != null) {
				String path = childElement.getPath();
				String[] pieces = path.split("/");
				childElement.addAttribute("depth", pieces.length - 1 + "");
				childElement.addAttribute("rootElementName", pieces[1]);
				elementList.add(childElement);
			} else {
				break;
			}
			getElements(childElement, elementList);
		}
		return elementList;
	}
	
	// 写xml
	public static void createXml(String writePath) {
		OutputStream os = null;
		XMLWriter writer = null;
		try {
			Document document = DocumentHelper.createDocument();
			Element rootElement = document.addElement("item");
			rootElement.addAttribute("id", "1");
			rootElement.addAttribute("name", "Hyman");
			Element child1 = rootElement.addElement("relations");
			child1.addAttribute("code", "079");
			Element child2 = child1.addElement("address");
			child2.addText("chengdu sichuan");
			Element element3 = child1.addElement("QQ");
			element3.addText("123456");
			
			os = new FileOutputStream(new File(writePath), false);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			writer = new XMLWriter(os, format);
			
			writer.write(document);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer == null) {
					writer.close();
				}
				if (os == null) {
					os.close();
				}
			} catch (Exception e2) {
			}
		}
	}

	public static void main(String[] args) {
//		String xmlPath = ReadAndWriteXml.class.getClassLoader()
//				.getResource("testFile/task.xml").getPath();
//		Document document = ReadAndWriteXml.getDocument(xmlPath);
//		Element rootElement = ReadAndWriteXml.getRootElement(document);
//		Element element = ReadAndWriteXml.getElementByNodeName(rootElement, "property");
//		System.out.println(String.format("%s-%s-%s-%s-%s", element.getName(),
//				element.getTextTrim(), element.getPath(),
//				element.attribute("key").getValue(), element.attribute("value").getValue()));
//		element = ReadAndWriteXml.getElementByNodePath(rootElement, "/taskConfiguration/sqlCluster/property");
//		System.out.println(String.format("%s-%s-%s-%s-%s", element.getName(),
//				element.getTextTrim(), element.getPath(),
//				element.attribute("key").getValue(), element.attribute("value").getValue()));
//		element = ReadAndWriteXml.getElementByNodeName(rootElement, "rootConfigNode");
//		System.out.println(String.format("%s-%s-%s", element.getName(),
//				element.getTextTrim(), element.getPath()));
		
//		ReadAndWriteXml.createXml("D:/1.xml");
	}
}
