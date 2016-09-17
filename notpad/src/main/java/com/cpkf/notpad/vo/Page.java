package com.cpkf.notpad.vo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cpkf.notpad.commons.constants.PageConstants;

/**  
 * Filename:    Page.java
 * Description: 分页对象
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   2011-6-6 下午12:47:38
 * modified:    
 */
public class Page {
	//每页显示记录数
	private int pageSize;
	//显示分页数
	private int displayPageCount;
	//当前页
	private int currentPage;
	//总记录数
	private int count;
	//关键字
	private String keyWord;
	//当前页记录集合
	private List list = new ArrayList();
	//请求路径
	private String actionString;
	
	public String getActionString() {
		return actionString;
	}
	public void setActionString(String actionString) {
		this.actionString = actionString;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getDisplayPageCount() {
		return displayPageCount;
	}
	public void setDisplayPageCount(int displayPageCount) {
		this.displayPageCount = displayPageCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	/* 
	 * method name   : initPage
	 * description   : 初始化page对象
	 * @author       : Jiang.Hu
	 * @param        : @param request
	 * @return       : Page
	 * Create at     : 2011-6-6 下午12:56:40
	 * modified      : 
	 */      
	public static Page initPage(HttpServletRequest request){
		int currentPage = request.getParameter("currentPage") == null 
			? 1 : Integer.parseInt(request.getParameter("currentPage"));
		String keyWord = request.getParameter("keyWord") == null 
			? "" : request.getParameter("keyWord");
//		try {
//			keyWord = URLEncoder.encode(keyWord,"utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		String actionString = request.getScheme() + "://"
//        	+ request.getServerName() + ":" + request.getServerPort() 
//        	+ request.getContextPath() + request.getServletPath();
		String actionString = request.getContextPath() + request.getServletPath();
		Page page = new Page();
		page.setPageSize(PageConstants.PAGE_SIZE);
		page.setDisplayPageCount(PageConstants.DISPLAY_PAGE_COUNT);
		page.setCurrentPage(currentPage);
		page.setKeyWord(keyWord);
		page.setActionString(actionString);
		return page;
	}
	/* 
	 * method name   : setPage
	 * description   : 设置page对象
	 * @author       : Jiang.Hu
	 * @param        : @param request
	 * @param        : @param page
	 * @return       : void
	 * Create at     : 2011-6-6 下午12:56:57
	 * modified      : 
	 */      
	public static void setPage(HttpServletRequest request,Page page){
		request.setAttribute("pageSize", page.getPageSize());
		request.setAttribute("displayPageCount", page.getDisplayPageCount());
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("count", page.getCount());
		request.setAttribute("keyWord", page.getKeyWord());
		request.setAttribute("actionString", page.getActionString());
	}
}
