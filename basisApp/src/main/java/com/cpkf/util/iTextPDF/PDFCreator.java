package com.cpkf.util.iTextPDF;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class PDFCreator {

	public PdfWriter pdfWriter;
	public HeaderFooterEvent headerFooterEvent;
	public ChapterSectionEvent chapterSectionEvent;

	public static int CHAPTER_NUMBER = 1;
	public static boolean IS_RECORD_PAGE = true;
	public static int ANCHOR_NUMBER = 0;
	public static Map<String, String> ANCHOR_MAP = new HashMap<String, String>();

	public static String SUBJECT_NAME = "FAMOS";
	public static String AUTHOR = "Administrator";
	public static String CREATOR = "Administrator";
	public static String KEY_WORD = "shop.com famos amos";

	public static Font BASE_FONT = initFont(12, false, false, BaseColor.BLACK);
	public static Font TITLE_FONT = initFont(30, true, false, BaseColor.BLACK);
	public static Font CHAPTER_FONT = initFont(18, true, false, new BaseColor(255, 0, 0));
	public static Font SECTION_FONT = initFont(16, false, false, new BaseColor(0, 0, 255));
	public static Font SUBSECTION_FONT = initFont(14, false, false, new BaseColor(0, 64, 64));
	public static Font ANCHOR_FONT = initFont(12, false, true, new BaseColor(0, 0, 255));
	public static Font TABLE_HEADER_FONT = initFont(12, true, false, new BaseColor(0, 0, 255));
	public static final Font[] FONT = new Font[5];
	static {
		FONT[0] = new Font(FontFamily.HELVETICA, 24);
		FONT[1] = new Font(FontFamily.HELVETICA, 18);
		FONT[2] = new Font(FontFamily.HELVETICA, 14);
		FONT[3] = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
		FONT[4] = new Font(FontFamily.HELVETICA, 10);
	}

	public PdfWriter getPdfWriter() {
		return pdfWriter;
	}

	public HeaderFooterEvent getHeaderFooterEvent() {
		return headerFooterEvent;
	}

	public ChapterSectionEvent getChapterSectionEvent() {
		return chapterSectionEvent;
	}

	public static Font initFont(int fontSize, boolean isBold, boolean isUnderLine, BaseColor color) {
		BaseFont baseFont;
		Font font = null;
		try {
//			baseFont = BaseFont.createFont("fonts/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//			baseFont = BaseFont.createFont("KozMinPro-Regular", "UniJIS-UCS2-H", BaseFont.EMBEDDED);
			baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			// baseFont = BaseFont.createFont();
			if (isBold) {
				font = new Font(baseFont, fontSize, Font.BOLD, color);
			} else {
				font = new Font(baseFont, fontSize, Font.NORMAL, color);
			}
			if (isUnderLine) {
				font.setStyle(Font.UNDERLINE);
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return font;
	}

	public Document initDocument(String title, OutputStream out) throws DocumentException {
		Rectangle pagesize = new Rectangle(PageSize.A4);
		pagesize.setBackgroundColor(new BaseColor(0xFF, 0xFF, 0xDE));
		Document document = new Document(pagesize, 50f, 50f, 50f, 50f);

		pdfWriter = PdfWriter.getInstance(document, out);
		// pdfWriter.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
		document.open();

		document.addTitle(title);
		document.addSubject(SUBJECT_NAME);
		document.addAuthor(AUTHOR);
		document.addCreator(CREATOR);
		document.addKeywords(KEY_WORD);

		headerFooterEvent = new HeaderFooterEvent(new Rectangle(36, 54, 559, 788), "Create by @Shop.Com");
		pdfWriter.setPageEvent(headerFooterEvent);

		pdfWriter.setLinearPageMode();
		chapterSectionEvent = new ChapterSectionEvent();
		pdfWriter.setPageEvent(chapterSectionEvent);

		return document;
	}

	public void closeDocument(Document document) {
		if (document != null) {
			document.close();
		}
	}

	public void createSeparatorLine(Document document) throws DocumentException {
		document.add(new Paragraph("\n\r"));
	}

	public Chapter createChapter(Document document, String chapterTitle) throws DocumentException {
		if (document == null || chapterTitle.isEmpty()) {
			return null;
		}

		String destinationString = String.format("%s%s", chapterTitle, ANCHOR_NUMBER++);
		Chapter chapter = new Chapter(new Paragraph(
				new Chunk(chapterTitle, CHAPTER_FONT).setLocalDestination(destinationString)), CHAPTER_NUMBER++);
		chapter.setBookmarkOpen(false);
		chapter.setBookmarkTitle(chapterTitle);
		chapter.setTriggerNewPage(false);

		ANCHOR_MAP.put(chapter.getTitle().getContent(), destinationString);

		return chapter;
	}

	public Section createSection(Chapter chapter, String sectionTitle) throws DocumentException {
		if (chapter == null) {
			return null;
		}

		String destinationString = String.format("%s%s", sectionTitle, ANCHOR_NUMBER++);
		Section section = chapter.addSection(new Paragraph(new Chunk(sectionTitle, SECTION_FONT)
				.setLocalDestination(destinationString)));
		section.setIndentation(10);
		section.setIndentationLeft(10);
		section.setBookmarkOpen(false);
		section.setBookmarkTitle(sectionTitle);
		section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);

		ANCHOR_MAP.put(section.getTitle().getContent(), destinationString);

		return section;
	}

	public Section createSubsection(Section section, String subsectionTitle) throws DocumentException {
		if (section == null) {
			return null;
		}

		String destinationString = String.format("%s%s", subsectionTitle, ANCHOR_NUMBER++);
		Section subsection = section.addSection(new Paragraph(new Chunk(subsectionTitle, SUBSECTION_FONT)
				.setLocalDestination(destinationString)));
		subsection.setIndentation(10);
		subsection.setIndentationLeft(10);
		subsection.setBookmarkOpen(false);
		subsection.setBookmarkTitle(subsectionTitle);
		subsection.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);

		ANCHOR_MAP.put(subsection.getTitle().getContent(), destinationString);

		return subsection;
	}

	public Anchor createAnchor(String anchorName, String anchorUrl) {
		if (anchorName.isEmpty()) {
			return null;
		}

		Anchor anchor = new Anchor(new Paragraph(anchorName, ANCHOR_FONT));
		anchor.setName(anchorName);
		if (!anchorUrl.isEmpty()) {
			anchor.setReference(anchorUrl);
		}

		return anchor;
	}

	public com.itextpdf.text.List createList(Document document, boolean isOrder, List<String> originList)
			throws DocumentException {
		com.itextpdf.text.List list = null;
		if (!originList.isEmpty()) {
			list = new com.itextpdf.text.List(isOrder, 20);
			for (int i = 0; i < originList.size(); i++) {
				list.add(new ListItem(originList.get(i)));
			}
		}
		return list;
	}

	public PdfPTable createTable(List<Map<String, Object>> result) {
		if (result == null) {
			return null;
		}

		List<String> headers = new ArrayList<String>();
		Iterator<String> iterator = result.get(0).keySet().iterator();
		while (iterator.hasNext()) {
			headers.add(iterator.next());
		}

		PdfPTable table = new PdfPTable(headers.size());
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		for (int i = 0; i < headers.size(); i++) {
			PdfPCell cell = new PdfPCell(new Paragraph(headers.get(i), TABLE_HEADER_FONT));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell.setBackgroundColor(BaseColor.CYAN);
			cell.setBorderColor(BaseColor.GREEN);
			table.addCell(cell);
		}
		try {
			for (Map<String, Object> map : result) {
				for (iterator = map.keySet().iterator(); iterator.hasNext();) {
					Object value = map.get(iterator.next());
					PdfPCell cell = null;
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

					cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
					cell.setBorderColor(BaseColor.GREEN);
					table.addCell(cell);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return table;
	}

	public <T> PdfPTable createTable(Collection<T> dataset) {
		if (dataset.isEmpty()) {
			return null;
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
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell.setBackgroundColor(BaseColor.CYAN);
			cell.setBorderColor(BaseColor.GREEN);
			table.addCell(cell);
		}

		try {
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

					cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
					cell.setBorderColor(BaseColor.GREEN);
					table.addCell(cell);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return table;
	}

	@SuppressWarnings("null")
	public Image createImage(String imgURL, byte[] imgBytes, int width, int height) {
		Image image = null;
		if (!imgURL.isEmpty()) {
			String URLRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			Pattern pattern = Pattern.compile(URLRegex);
			try {
				if (pattern.matcher(imgURL).matches()) {
					image = Image.getInstance(new URL(imgURL));
				} else {
					image = Image.getInstance(imgURL);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (imgBytes == null && imgBytes.length > 0) {
			try {
				image = Image.getInstance(imgBytes);
			} catch (BadElementException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (image != null) {
			image.setAlignment(Image.LEFT);
			image.scaleAbsoluteWidth(width);
			image.scaleAbsoluteHeight(height);
		}

		return image;
	}

	public void addContent(Document document, Element element) throws DocumentException {
		if (document != null && element != null) {
			document.add(element);
		}
	}

	public void addContent(Document document, String content) throws DocumentException {
		if (document != null && !content.isEmpty()) {
			document.add(new Paragraph(content, BASE_FONT));
		}
	}

	public void addContent(Chapter chapter, Element element) {
		if (chapter != null && element != null) {
			chapter.add(element);
		}
	}

	public void addContent(Chapter chapter, String content) {
		if (chapter != null && !content.isEmpty()) {
			chapter.add(new Paragraph(content, BASE_FONT));
		}
	}

	public void addContent(Section section, Element element) {
		if (section != null && element != null) {
			section.add(element);
		}
	}

	public void addContent(Section section, String content) {
		if (section != null && !content.isEmpty()) {
			section.add(new Paragraph(content, BASE_FONT));
		}
	}

	public void addPdfTitle(Document document, String pdfTitle) throws DocumentException {
		Paragraph titleParagraph = new Paragraph(pdfTitle, TITLE_FONT);
		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		document.add(titleParagraph);
	}

	public void addDirectory(Document document, String pdfTitle) throws DocumentException {
		if (document == null) {
			return;
		}

		document.newPage();
		IS_RECORD_PAGE = false;

		addPdfTitle(document, pdfTitle);

		int toc = pdfWriter.getPageNumber();
		for (Paragraph p : chapterSectionEvent.titles) {
			document.add(p);
		}
		document.newPage();

		int total = pdfWriter.reorderPages(null);
		int[] order = new int[total];
		for (int i = 0; i < total; i++) {
			order[i] = i + toc;
			if (order[i] > total)
				order[i] -= total;
		}
		pdfWriter.reorderPages(order);
	}

	class HeaderFooterEvent extends PdfPageEventHelper {
		int textNumber = 1;
		int directoryNumber = 1;
		Rectangle rect = new Rectangle(50, 50, 50, 50);
		String headerContent = "";

		public int getPageNumber() {
			return textNumber;
		}

		public void setPageNumber(int textNumber) {
			this.textNumber = textNumber;
		}

		public HeaderFooterEvent(Rectangle rect) {
			this.rect = rect;
		}

		public HeaderFooterEvent(Rectangle rect, String headerContent) {
			this.rect = rect;
			this.headerContent = headerContent;
		}

		public void onStartPage(PdfWriter writer, Document document) {
			if (PDFCreator.IS_RECORD_PAGE) {
				textNumber++;
			} else {
				directoryNumber++;
			}
		}

		public void onEndPage(PdfWriter writer, Document document) {
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase(headerContent),
					rect.getRight(), rect.getTop(), 0);
			if (PDFCreator.IS_RECORD_PAGE) {
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
						new Phrase(String.format("Text Page %d", textNumber)), (rect.getLeft() + rect.getRight()) / 2,
						rect.getBottom() - 18, 0);
			} else {
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
						new Phrase(String.format("Directory Page %d", directoryNumber)),
						(rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
			}
		}
	}

	class ChapterSectionEvent extends PdfPageEventHelper {
		List<Paragraph> titles = new ArrayList<Paragraph>();
		float indentationNumber = 20;
		int textNumber = 1;

		public void onStartPage(PdfWriter writer, Document document) {
			if (PDFCreator.IS_RECORD_PAGE) {
				textNumber++;
			}
		}

		public void onChapter(PdfWriter writer, Document document, float position, Paragraph title) {
			String destination = PDFCreator.ANCHOR_MAP.get(title.getContent());
			titles.add(new Paragraph(new Chunk(getDirectoryLine(title.getContent(), textNumber + ""),
					PDFCreator.CHAPTER_FONT).setLocalGoto(destination)));
		}

		public void onChapterEnd(PdfWriter writer, Document document, float position) {
//			drawLine(writer.getDirectContent(), document.left(), document.right(), position - 5);
		}

		public void onSection(PdfWriter writer, Document document, float position, int depth, Paragraph title) {
			boolean isSubsection = false;
			String titleContent = title.getContent();
			String destination = PDFCreator.ANCHOR_MAP.get(titleContent);
			String theNo = titleContent.substring(0, titleContent.indexOf(" "));
			if (theNo.indexOf(".") != theNo.lastIndexOf(".")) {
				isSubsection = true;
			}
			if (isSubsection) {
				title = new Paragraph(new Chunk(getDirectoryLine(title.getContent(), textNumber + ""),
						PDFCreator.SUBSECTION_FONT).setLocalGoto(destination));
			} else {
				title = new Paragraph(new Chunk(getDirectoryLine(title.getContent(), textNumber + ""),
						PDFCreator.SECTION_FONT).setLocalGoto(destination));
			}
			title.setIndentationLeft(indentationNumber * depth);
			titles.add(title);
		}

		public void onSectionEnd(PdfWriter writer, Document document, float position) {
//			drawLine(writer.getDirectContent(), document.left(), document.right(), position - 3);
		}

		public void drawLine(PdfContentByte cb, float x1, float x2, float y) {
			cb.moveTo(x1, y);
			cb.lineTo(x2, y);
			cb.stroke();
		}

		public String getDirectoryLine(String titleName, String pageNumber) {
			int total = titleName.length() + pageNumber.length();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 80 - total; i++) {
				sb.append(".");
			}
			return String.format("%s%s%s", titleName, sb.toString(), pageNumber);
		}
	}

	public static void main(String[] args) {
	}
}
