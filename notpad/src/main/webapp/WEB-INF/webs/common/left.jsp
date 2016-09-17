<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link type="text/css" rel="stylesheet" href="<c:url value='/styles/left.css'/>"/>
<script type="text/javascript" src="<c:url value='/scripts/jquery-1.5.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/scripts/menu.js'/>"></script>
<div id="LeftMenu" class="leftMenuWrap">
	<c:forEach items="${menuVoList }" var="menuVo" varStatus="menuVoIndex">
		<c:if test="${menuVoIndex.index ne fn:length(menuVoList) - 1 }">
			<dl class="leftMenu noBottomBorder">
		</c:if>
		<c:if test="${menuVoIndex.index eq fn:length(menuVoList) - 1 }">
			<dl class="leftMenu">
		</c:if>
			<dt><spring:message code="${menuVo.group }"/></dt>
			<c:forEach items="${menuVo.menuList}" var="menu">
				<dd>
					<a href="<c:url value='${menu.url }'/>">
						<spring:message code="${menu.menuName }"/>
					</a> 
					<img width="7" height="14" src="<c:url value='/images/bgArrow.gif'/>" />
				</dd>
			</c:forEach>
		</dl>
	</c:forEach>
</div>