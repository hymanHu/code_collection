package com.cpkf.util.iTextPDF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cpkf.basis.xml.ReadAndWriteXml;

public class XmlUtil {
	public Document getDocument(String xmlPath) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(xmlPath);
		return document;
	}

	public Element getRootElement(Document document) {
		return document == null ? null : document.getRootElement();
	}

	public List<Element> getElements(Element parentElement, List<Element> elementList) {
		if (parentElement == null) {
			return elementList;
		}

		@SuppressWarnings("unchecked")
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

	public static String getElementinfo(Element element) {
		StringBuffer sb = new StringBuffer();
		if (element.attributeCount() > 2) {
			sb.append("[");
		}
		for (int i = 0; i < element.attributeCount(); i++) {
			if (!"depth".equals(element.attribute(i).getName())
					&& !"rootElementName".equals(element.attribute(i).getName())) {
				sb.append(String.format("%s%s%s%s", element.attribute(i).getName(), "=", element.attribute(i)
						.getValue(), "; "));
			}
		}
		if (element.attributeCount() > 2) {
			sb.append("] ");
		}
		sb.append(element.getTextTrim());

		return sb.toString();
	}

	public static void main(String[] args) throws DocumentException {
		XmlUtil xmlUtil = new XmlUtil();
		List<Element> elementList = new ArrayList<Element>();
		String xmlPath = ReadAndWriteXml.class.getClassLoader().getResource("testFile/test.xml").getPath();
		Document document = xmlUtil.getDocument(xmlPath);
		Element rootElement = xmlUtil.getRootElement(document);
		xmlUtil.getElements(rootElement, elementList);
		for (Element element : elementList) {
			System.out.println(element.getPath() + "---" + getElementinfo(element));
			// System.out.println(element.getPath() + "--" +
			// element.attributeValue("depth") + "--"
			// + element.attributeValue("rootElementName"));
		}
	}
}
