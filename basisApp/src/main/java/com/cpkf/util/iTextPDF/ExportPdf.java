package com.cpkf.util.iTextPDF;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.text.html.parser.DocumentParser;

import org.w3c.tidy.Parser;

import com.cpkf.basis.xml.ReadAndWriteXml;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.WritableDirectElement;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.WritableElement;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

public class ExportPdf {

	public OutputStream createOutputStream(String pdfTitle, String filePath) throws FileNotFoundException {
		OutputStream os = null;
		if (!filePath.isEmpty()) {
			String directory = filePath;
			if (filePath.endsWith(".pdf") || filePath.endsWith("\\")) {
				directory = filePath.substring(0, filePath.lastIndexOf("\\"));
			}
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			os = new FileOutputStream(String.format("%s%s%s%s", directory, "\\", pdfTitle.replaceAll(" ", "_"), ".pdf"));
		}
		return os;
	}

	public void exportDatabase2Pdf(DatabaseUtil dbc, String querySql, String pdfTitle, String filePath)
			throws SQLException, DocumentException, IOException {
		ResultSet rs = dbc.query(querySql);
		List<Map<String, Object>> result = dbc.convertToList(rs);

		PDFCreator pdfCreator = new PDFCreator();
		Document document = pdfCreator.initDocument(pdfTitle, createOutputStream(pdfTitle, filePath));

		pdfCreator.addPdfTitle(document, pdfTitle);
		pdfCreator.createSeparatorLine(document);
		pdfCreator.addContent(document, pdfCreator.createTable(result));

		pdfCreator.closeDocument(document);

	}

	public void exportPdf(String pdfTitle, String filePath) throws FileNotFoundException, DocumentException {
		PDFCreator pdfCreator = new PDFCreator();
		Document document = pdfCreator.initDocument(pdfTitle, createOutputStream(pdfTitle, filePath));

		Section section = null;
		Section subsection = null;

		Chapter chapter1 = pdfCreator.createChapter(document, "Summary");
		section = pdfCreator.createSection(chapter1, "Introduction");
		pdfCreator
				.addContent(
						section,
						"If you look at PDF creation, you'll find that graphical designers use desktop "
								+ "applications such as Adobe Acrobat or Adobe InDesign to create a document in a manual or semi-manual "
								+ "process. iText is not such an end-user tool. iText is used to create PDF documents programmatically. "
								+ "Developers write Java or .NET code to produce PDFs directly from software applications, without—or "
								+ "with minimal—human intervention. Sometimes the document is created in an intermediary format first, "
								+ "and then converted to PDF.");
		section = pdfCreator.createSection(chapter1, "Version");
		subsection = pdfCreator.createSubsection(section, "Release Version");
		pdfCreator.addContent(subsection, new Paragraph("2.1.4", PDFCreator.BASE_FONT));
		subsection = pdfCreator.createSubsection(section, "History Version");
		pdfCreator.addContent(subsection, new Paragraph("1.0.0 ~ 3.1.3", PDFCreator.BASE_FONT));
		pdfCreator.addContent(document, chapter1);

		Chapter chapter2 = pdfCreator.createChapter(document, "Basic Function");
		section = pdfCreator.createSection(chapter2, "Font");
		subsection = pdfCreator.createSubsection(section, "Chian Support Font");
		pdfCreator.addContent(subsection, "baseFont = BaseFont.createFont(\"c://windows//fonts//simsun.ttc,1\", "
				+ "BaseFont.IDENTITY_H, BaseFont.EMBEDDED);");
		subsection = pdfCreator.createSubsection(section, "English Support Font");
		pdfCreator.addContent(subsection, "baseFont = BaseFont.createFont();");
		subsection = pdfCreator.createSubsection(section, "Init Base Font");
		pdfCreator.addContent(subsection, "font = new Font(baseFont, fontSize, Font.BOLD, color);\r\n" + "static {\r\n"
				+ "FONT[0] = new Font(FontFamily.HELVETICA, 24);\r\n"
				+ "FONT[1] = new Font(FontFamily.HELVETICA, 18);\r\n"
				+ "FONT[2] = new Font(FontFamily.HELVETICA, 14);\r\n"
				+ "FONT[3] = new Font(FontFamily.HELVETICA, 12, Font.BOLD);\r\n"
				+ "FONT[4] = new Font(FontFamily.HELVETICA, 10);\r\n" + "}\r\n");
		section = pdfCreator.createSection(chapter2, "Content");
		pdfCreator.addContent(section, "document.add(new Paragraph(content, BASE_FONT));");
		section = pdfCreator.createSection(chapter2, "Anchor");
		subsection = pdfCreator.createSubsection(section, "Out URL Anchor");
		pdfCreator.addContent(subsection, "Anchor anchor = new Anchor(anchorName, ANCHOR_FONT);");
		pdfCreator.addContent(subsection, "anchor.setReference(anchorUrl);");
		pdfCreator.addContent(subsection, "document.add(anchor);");
		subsection = pdfCreator.createSubsection(section, "Internal URL Anchor");
		pdfCreator.addContent(subsection, "Anchor anchor = new Anchor(anchorName, ANCHOR_FONT);");
		pdfCreator.addContent(subsection, "anchor.setReference(\"#OtherAnchorName\");");
		subsection = pdfCreator.createSubsection(section, "Internal Go To");
		pdfCreator.addContent(subsection,
				"new Chunk(subsectionTitle, SUBSECTION_FONT).setLocalDestination(destinationString)");
		pdfCreator.addContent(subsection,
				"new Chunk(title.getContent(), PDFCreator.SUBSECTION_FONT).setLocalGoto(destination)");
		subsection = pdfCreator.createSubsection(section, "Example");
		pdfCreator.addContent(subsection,
				new Chunk("Google", PDFCreator.ANCHOR_FONT).setAnchor("https://www.google.com.hk/"));
		pdfCreator.addContent(subsection, "Test end.");
		pdfCreator.addContent(document, chapter2);

		pdfCreator.addDirectory(document, pdfTitle);

		pdfCreator.closeDocument(document);

	}

	public void exportHtml2Pdf(String pdfTitle, String filePath, InputStream htmlFileStream) throws IOException,
			DocumentException {

		PDFCreator pdfCreator = new PDFCreator();
		Document document = pdfCreator.initDocument(pdfTitle, createOutputStream(pdfTitle, filePath));

		InputStreamReader isr = new InputStreamReader(htmlFileStream, "UTF-8");
		XMLWorkerHelper.getInstance().parseXHtml(pdfCreator.getPdfWriter(), document, isr);

		pdfCreator.closeDocument(document);
	}

	public void exportHtml2Pdf(String pdfTitle, String filePath, String requestUrl) throws IOException,
			DocumentException {
		InputStream htmlFileStream = null;
		String URLRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern pattern = Pattern.compile(URLRegex);
		if (pattern.matcher(requestUrl).matches()) {
			HttpClientUtil httpClientUtil = new HttpClientUtil();
			htmlFileStream = httpClientUtil.doURLPost(requestUrl);
		} else {
			htmlFileStream = new FileInputStream(requestUrl);
		}

		exportHtml2Pdf(pdfTitle, filePath, htmlFileStream);
	}

	public void exportHTML2PDFElement(String pdfTitle, String filePath, InputStream htmlFileStream)
			throws DocumentException, IOException {
		PDFCreator pdfCreator = new PDFCreator();
		Document document = pdfCreator.initDocument(pdfTitle, createOutputStream(pdfTitle, filePath));

		Chapter chapter = pdfCreator.createChapter(document, "Html Content");
		Section section = pdfCreator.createSection(chapter, "");

		final List<Element> pdfeleList = new ArrayList<Element>();
		ElementHandler elemH = new ElementHandler() {
			public void add(final Writable w) {
				if (w instanceof WritableElement) {
					pdfeleList.addAll(((WritableElement) w).elements());
				}

			}
		};
		InputStreamReader isr = new InputStreamReader(htmlFileStream, "UTF-8");
		XMLWorkerHelper.getInstance().parseXHtml(elemH, isr);

		List<Element> list = new ArrayList<Element>();
		for (Element ele : pdfeleList) {
			if (ele instanceof LineSeparator || ele instanceof WritableDirectElement) {
				continue;
			}
			System.out.println(ele.type());
			list.add(ele);
		}
		section.addAll(list);

		pdfCreator.addContent(document, chapter);
		pdfCreator.addDirectory(document, pdfTitle);
		pdfCreator.closeDocument(document);
	}

	public void exportXml2PDF(String pdfTitle, String pdfFilePath, String xmlPath) throws FileNotFoundException,
			DocumentException, org.dom4j.DocumentException {
		PDFCreator pdfCreator = new PDFCreator();
		Document document = pdfCreator.initDocument(pdfTitle, createOutputStream(pdfTitle, pdfFilePath));

		XmlUtil xmlUtil = new XmlUtil();
		List<org.dom4j.Element> elementList = new ArrayList<org.dom4j.Element>();
		org.dom4j.Document dom4jDocument = xmlUtil.getDocument(xmlPath);
		org.dom4j.Element rootElement = xmlUtil.getRootElement(dom4jDocument);
		xmlUtil.getElements(rootElement, elementList);
		
		if (!elementList.isEmpty()) {
			String chapterRootTitle = elementList.get(0).attributeValue("rootElementName");
			Chapter chapter = null;
			Section section = null;
			Section subsection = null;
			com.itextpdf.text.List list = new com.itextpdf.text.List(true, 20);;
			List<Chapter> chapters = new ArrayList<Chapter>();
			for (org.dom4j.Element element : elementList) {
				String depth = element.attributeValue("depth");
				if ("2".equals(depth)) {
					chapter = pdfCreator.createChapter(document, element.getName());
					pdfCreator.addContent(chapter, XmlUtil.getElementinfo(element));
					chapters.add(chapter);
				} else if ("3".equals(depth)) {
					section = pdfCreator.createSection(chapter, element.getName());
					pdfCreator.addContent(section, XmlUtil.getElementinfo(element));
				} else if ("4".equals(depth)) {
					subsection = pdfCreator.createSubsection(section, element.getName());
					pdfCreator.addContent(subsection, XmlUtil.getElementinfo(element));
				} else if (Integer.parseInt(depth) > 4) {
					if (element != null) {
						list.add(new ListItem(XmlUtil.getElementinfo(element)));
						pdfCreator.addContent(subsection, list);
					}
				}
			}
			for (Chapter chapterTemp : chapters) {
				pdfCreator.addContent(document, chapterTemp);
			}
			pdfCreator.addDirectory(document, chapterRootTitle);
		}
		pdfCreator.closeDocument(document);
	}

	public static void main(String[] args) {
		try {
			ExportPdf exportPdf = new ExportPdf();

//			exportPdf.exportPdf("PDF Report", "\\testFolder");
//			DatabaseUtil dbc = new DatabaseUtil("com.mysql.jdbc.Driver",
//					"jdbc:mysql://localhost:3306/notepad?characterEncoding=utf-8", "root", "");
//			exportPdf.exportDatabase2Pdf(dbc, "SELECT * FROM account WHERE available = TRUE;", "Export From Database",
//					"\\testFolder");
//			exportPdf.exportHtml2Pdf("Convert HTML to PDF", "\\testFolder", "\\testFolder\\test.html");
//			exportPdf.exportHTML2PDFElement("Convert HTML to PDF Element", "\\testFolder", new FileInputStream(
//					"\\testFolder\\test.html"));
//			exportPdf.exportXml2PDF("Convert XML to PDF", "\\testFolder", "D:\\testFolder\\test.xml");
			exportPdf.exportXml2PDF("Convert XML to PDF", "\\testFolder", "D:\\apache-tomcat-7.0.53\\conf\\server.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
