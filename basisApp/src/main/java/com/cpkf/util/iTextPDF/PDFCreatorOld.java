package com.cpkf.util.iTextPDF;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import com.cpkf.testBean.Student;
import com.lowagie.text.Anchor;
import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class PDFCreatorOld {
	private static String SUBJECT_NAME = "FAMOS";
	private static String AUTHOR = "Administrator";
	private static String CREATOR = "Administrator";
	private static String KEY_WORD = "shop.com famos amos";
	
	private static Font BASE_FONT = initFont(12, false, false, Color.black);
	private static Font TITLE_FONT = initFont(30, true, false, Color.black);
	private static Font FOOTER_FONT = initFont(10, false, false, Color.black);
	private static Font CHAPTER_FONT = initFont(18, true, false, Color.black);
	private static Font SECTION_FONT = initFont(16, false, false, Color.black);
	private static Font ANCHOR_FONT = initFont(12, false, true, new Color(0, 0, 255));
	private static Font TABLE_HEADER_FONT = initFont(12, true, false, new Color(0, 0, 255));
	
	/**
	 * BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED); from iTextAsian.jar
	 * BaseFont.createFont("c://windows//fonts//simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);  from window
	 * BaseFont.createFont("/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED); from classPath
	 * 
	 * @param fontSize
	 * @param isBold
	 * @param isUnderLine
	 * @param color
	 * @return
	 */
	protected static Font initFont(int fontSize, boolean isBold, boolean isUnderLine, Color color) {
		BaseFont bfChinese;
		Font fontChinese = null;
		try {
			bfChinese = BaseFont.createFont("c://windows//fonts//simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			if (isBold) {
				fontChinese = new Font(bfChinese, fontSize, Font.BOLD, color);
			} else {
				fontChinese = new Font(bfChinese, fontSize, Font.NORMAL, color);
			}
			if (isUnderLine) {
				fontChinese.setStyle(Font.UNDERLINE);
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return fontChinese;
	}

	public void initPdfTemplate(OutputStream out) throws Exception {
		Rectangle rectPageSize = new Rectangle(PageSize.A4);
		// rectPageSize = rectPageSize.rotate();
		rectPageSize.setBackgroundColor(new Color(0xFF, 0xFF, 0xDE));
		Document document = new Document(rectPageSize, 50, 50, 50, 50);

		PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
		PdfContentByte cb = pdfWriter.getDirectContent();
		PdfTemplate template = cb.createTemplate(500, 200);
	}

	public Document initDocument(String title, OutputStream out) throws Exception {

		Rectangle rectPageSize = new Rectangle(PageSize.A4);
		// rectPageSize = rectPageSize.rotate();
		rectPageSize.setBackgroundColor(new Color(0xFF, 0xFF, 0xDE));
		Document document = new Document(rectPageSize, 50, 50, 50, 50);

		PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
		// pdfWriter.setViewerPreferences(PdfWriter.PageModeUseOutlines |
		// PdfWriter.PageModeUseThumbs);
		// pdfWriter.setEncryption(PdfWriter.STRENGTH128BITS, "aaa", "bbb",
		// PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);

		document.addTitle(title);
		document.addSubject(SUBJECT_NAME);
		document.addAuthor(AUTHOR);
		document.addCreator(CREATOR);
		document.addKeywords(KEY_WORD);

		HeaderFooter header = new HeaderFooter(new Paragraph("©2014 SHOP.COM", FOOTER_FONT), false);
		header.setAlignment(Element.ALIGN_CENTER);
		HeaderFooter footer = new HeaderFooter(new Phrase("Page ", FOOTER_FONT),
				new Phrase("."));
		footer.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);
		document.setFooter(footer);

		document.open();
		Paragraph pdfTitle = new Paragraph(title, TITLE_FONT);
		pdfTitle.setAlignment(Element.ALIGN_CENTER | Element.ALIGN_MIDDLE);
		document.add(pdfTitle);
		Paragraph subjectInfo = new Paragraph(String.format("%s%s", "subject: ", SUBJECT_NAME), BASE_FONT);
		subjectInfo.setAlignment(Element.ALIGN_CENTER);
		document.add(subjectInfo);
		Paragraph authorInfo = new Paragraph(String.format("%s%s", "author: ", AUTHOR), BASE_FONT);
		authorInfo.setAlignment(Element.ALIGN_CENTER);
		document.add(authorInfo);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Paragraph dateInfo = new Paragraph(String.format("%s%s", "date: ", sdf.format(new Date())), BASE_FONT);
		dateInfo.setAlignment(Element.ALIGN_CENTER);
		document.add(dateInfo);

		document.newPage();

		return document;
	}

	public void closeDocument(Document document) {
		if (document != null) {
			document.close();
		}
	}

	public void addContent(Document document, String content) throws DocumentException {
		document.add(new Paragraph(content, BASE_FONT));
	}

	public void addList(Document document, boolean isOrder, List<String> originList) throws DocumentException {
		if (!originList.isEmpty()) {
			com.lowagie.text.List list = new com.lowagie.text.List(isOrder, 20);
			for (int i = 0; i < originList.size(); i++) {
				list.add(new ListItem(originList.get(i)));
			}
			// list.setIndentationLeft(20);
			document.add(list);
		}
	}

	public void addAnchor(Document document, String anchorName, String anchorUrl) throws Exception {
		if (anchorName.isEmpty()) {
			return;
		}

		Anchor anchor = new Anchor(anchorName, ANCHOR_FONT);
		anchor.setName(anchorName);
		if (!anchorUrl.isEmpty()) {
			anchor.setReference(anchorUrl);
		}

		document.add(anchor);
	}

	public void addChapter(Document document, Map<String, List<String>> chapterMap) throws DocumentException {
		if (!chapterMap.isEmpty()) {
			int chNum = 1;
			Iterator<String> iterator = chapterMap.keySet().iterator();
			Chapter chapter = null;
			while (iterator.hasNext()) {
				String key = iterator.next();
				chapter = new Chapter(new Paragraph(key, CHAPTER_FONT), chNum++);
				List<String> sections = chapterMap.get(key);
				if (!sections.isEmpty()) {
					for (String sectionString : sections) {
						Section section = chapter.addSection(new Paragraph(sectionString, SECTION_FONT));
						section.setIndentation(20);
						section.setIndentationLeft(20);
						section.setBookmarkOpen(false);
						section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
					}
				}
				document.add(chapter);
			}
		}
	}

	public <T> void addTable(Document document, Collection<T> dataset) throws Exception {
		if (dataset.isEmpty()) {
			return;
		}

		List<String> headers = new ArrayList<String>();
		Iterator<T> iterator = dataset.iterator();
		if (iterator.hasNext()) {
			T t = iterator.next();
			Field[] fields = t.getClass().getDeclaredFields();
			for (Field field : fields) {
				headers.add(field.getName());
			}
		}
		PdfPTable table = new PdfPTable(headers.size());
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setWidthPercentage(16 * headers.size());
		for (int i = 0; i < headers.size(); i++) {
			PdfPCell cell = new PdfPCell(new Paragraph(headers.get(i), TABLE_HEADER_FONT));
			cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
			cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
			cell.setBackgroundColor(Color.cyan);
			cell.setBorderColor(Color.green);
			table.addCell(cell);
		}

		Iterator<T> it = dataset.iterator();
		while (it.hasNext()) {
			T t = it.next();
			Field[] fields = t.getClass().getDeclaredFields();
			for (Field field : fields) {
				PdfPCell cell = null;
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Method getMethod = t.getClass().getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(t, new Object[] {});
				String textValue = null;
				if (value instanceof Boolean) {
					textValue = value.toString();
				} else if (value instanceof Date) {
					Date date = (Date) value;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					textValue = sdf.format(date);
				} else if (value instanceof byte[]) {
					byte[] bsValue = (byte[]) value;
					Image img = Image.getInstance(bsValue);
					cell = new PdfPCell(img);
				} else {
					textValue = value.toString();
				}

				if (textValue != null) {
					cell = new PdfPCell(new Paragraph(textValue, BASE_FONT));
				}

				cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
				cell.setBorderColor(Color.green);
				table.addCell(cell);
			}
		}

		document.add(table);
	}

	public void addImage(Document document, String imgURL, byte[] imgBytes, int width, int height) throws Exception {

		Image image = null;
		if (imgURL.isEmpty() && imgBytes == null) {
			return;
		} else if (!imgURL.isEmpty()) {
			String URLRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			Pattern pattern = Pattern.compile(URLRegex);
			if (pattern.matcher(imgURL).matches()) {
				image = Image.getInstance(new URL(imgURL));
			} else {
				image = Image.getInstance(imgURL);
			}
		} else if (imgBytes == null && imgBytes.length > 0) {
			image = Image.getInstance(imgBytes);
		} else {
			return;
		}

		image.setAlignment(Image.LEFT);
		image.scaleAbsoluteWidth(width);
		image.scaleAbsoluteHeight(height);

		document.add(image);
	}

	public void exportPdf(String title, String content, String filePath) throws Exception {
		OutputStream out = new FileOutputStream(filePath);
		Document document = initDocument(title, out);
		addContent(document, content);
		closeDocument(document);
	}

	public static void main(String[] args) {
		PDFCreatorOld pDFCreator = new PDFCreatorOld();
		try {
			pDFCreator.exportPdf("PDF Report generation framework", "Helloworld!", "\\test.pdf");

			Document document = pDFCreator.initDocument("PDF Report generation framework", new FileOutputStream(
					"\\test1.pdf"));
			List<String> testlList = new ArrayList<String>();
			testlList.add("AI-57274: MA UK Intergration :UK Affiliate Window - Reporting (take two)");
			testlList.add("AI-60252: Brands Pages- Top Health & Nutrition Brands not displaying all brands ");
			testlList.add("AI-60360：PDF Report generation framework");
			testlList.add("AI-59937, MBC: When removing a \"free shipping promo\" or"
					+ " \"flat rate shipping promo\" table is not being deleted. ");
			testlList.add("AI-59350: MBC - Add MA Refund Request Info similar to Amostools to MBC");
			testlList.add("AI-59532");
			pDFCreator.addList(document, true, testlList);
			pDFCreator.addContent(document, "\r\n");

			pDFCreator.addAnchor(document, "hao123", "http://www.hao123.com");
			pDFCreator.addContent(document, "\r\n");
			pDFCreator.addAnchor(document, "local", "");
			pDFCreator.addContent(document, "\r\n");

			List<Student> students = new ArrayList<Student>();
			Calendar calendar = Calendar.getInstance();
			calendar.set(1980, 10, 14);
			students.add(new Student(1, 123, "hyman", "male", 33, calendar.getTime()));
			calendar.add(Calendar.YEAR, -4);
			calendar.add(Calendar.MONTH, 2);
			calendar.add(Calendar.DATE, -6);
			students.add(new Student(2, 124, "hawkist", "male", 31, calendar.getTime()));
			calendar.add(Calendar.YEAR, 2);
			calendar.add(Calendar.MONTH, 3);
			calendar.add(Calendar.DATE, -4);
			students.add(new Student(3, 125, "hawkzom", "male", 32, calendar.getTime()));
			calendar.add(Calendar.YEAR, -4);
			calendar.add(Calendar.MONTH, -7);
			calendar.add(Calendar.DATE, 6);
			students.add(new Student(4, 126, "hawkoum", "male", 36, calendar.getTime()));
			pDFCreator.addTable(document, students);
			pDFCreator.addContent(document, "\r\n");

			pDFCreator.addImage(document, "http://ww4.sinaimg.cn/bmiddle/74c247f7tw1ehgv05klsoj20hs0hwq50.jpg", null,
					150, 150);
			pDFCreator.addImage(document, "C:\\Users\\Administrator\\Pictures\\20140617092022.jpg", null, 150, 150);
			pDFCreator.addContent(document, "\r\n");

			Map<String, List<String>> chapterMap = new TreeMap<String, List<String>>();
			for (int i = 0; i < 4; i++) {
				List<String> sectionList = new ArrayList<String>();
				for (int j = 0; j < 2; j++) {
					sectionList.add("This is section " + j);
				}
				chapterMap.put("This is chapter " + i, sectionList);
			}
			pDFCreator.addChapter(document, chapterMap);
			pDFCreator.addContent(document, "\r\n");

			pDFCreator.addAnchor(document, "local2", "#hao123");
			pDFCreator.addContent(document, "\r\n");

			pDFCreator.closeDocument(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
