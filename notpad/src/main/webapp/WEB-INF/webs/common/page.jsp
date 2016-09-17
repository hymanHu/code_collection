<%@page import="java.net.URLEncoder"%>
<%@page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link type="text/css" rel="stylesheet" href="<c:url value='/styles/page.css'/>"/>
<%
	//每页显示记录数
	int pageSize = (Integer)request.getAttribute("pageSize");
	//最多显示分页页数
	int displayPageCount = (Integer)request.getAttribute("displayPageCount");
	//当前页
	int currentPage = (Integer)request.getAttribute("currentPage");
	//记录总数
	int count = (Integer)request.getAttribute("count");
	//总页数
	int pageCount = 0;
	if(count % pageSize == 0){
		pageCount = count / pageSize;
	} else {
		pageCount = count / pageSize + 1;
	}
	if(pageCount < currentPage){
		currentPage = pageCount;
	}
	//起始页
	int pageBegin = currentPage - (displayPageCount / 2);
	if(pageBegin < 1){
		pageBegin = 1;
	}
	//结束页
	int pageEnd = currentPage + (displayPageCount / 2);
	if(pageEnd > pageCount){
		pageEnd = pageCount;
	}
	//关键字
	String keyWord = request.getAttribute("keyWord") == null 
		? "" : (String)request.getAttribute("keyWord");
	keyWord = URLEncoder.encode(keyWord,"utf-8");
	String actionString = (String)request.getAttribute("actionString");
%>
<!-- 页面显示连接 -->
<a id="start_page" class="start"><spring:message code="message.page.start"/></a>
<a id="pre_page" class="pre"><spring:message code="message.page.prePage"/></a>
<span>
	<%
		for(int i = pageBegin;i <= pageEnd;i ++){
			if(i != currentPage){
	%>
				<a href="javascript:goPage(<%=i%>);"><%=i%></a>
	<%
			} else {
	%>
				<a class="selected"><%=i%></a>
	<%
			}
		}
	%>
</span>
<a id="next_page" class="next"><spring:message code="message.page.nextPage"/></a>
<a id="end_page" class="end"><spring:message code="message.page.endPage"/></a>
<script type="text/javascript">
	var pageForm = document.forms["pageForm"];
	pageForm.setAttribute("action","<%=actionString%>");
	function prePage(){
		pageForm.currentPage.value = pageForm.currentPage.value - 1;
		pageForm.submit();
	}
	function goPage(currentPage){
		pageForm.currentPage.value = currentPage;
		pageForm.submit();
	}
	function nextPage(){
		pageForm.currentPage.value = parseInt(pageForm.currentPage.value) + 1;
		pageForm.submit();
	}
	//控制链接和样式
	if(<%=pageCount%> <= 1){
		document.getElementById("page_div").style.display = "none";
	}
	if(<%=currentPage%> == 1 && <%=pageCount%> > 1){
		document.getElementById("next_page").href = "javascript:nextPage()";
		document.getElementById("end_page").href = "javascript:goPage(<%=pageCount%>)";
		document.getElementById("page_div").className = "page";
	}
	if(<%=currentPage%> > 1 && <%=currentPage%> != <%=pageCount%>){
		document.getElementById("pre_page").href = "javascript:prePage()";
		document.getElementById("start_page").href = "javascript:goPage(1)";
		document.getElementById("next_page").href = "javascript:nextPage()";
		document.getElementById("end_page").href = "javascript:goPage(<%=pageCount%>)";
		document.getElementById("page_div").className = "page";
	}
	if(<%=currentPage%> > 1 && <%=currentPage%> == <%=pageCount%>){
		document.getElementById("pre_page").href = "javascript:prePage()";
		document.getElementById("start_page").href = "javascript:goPage(1)";
		document.getElementById("page_div").className = "page";
	}
</script>
