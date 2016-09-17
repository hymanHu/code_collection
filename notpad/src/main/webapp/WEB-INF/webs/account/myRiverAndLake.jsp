<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link type="text/css" rel="stylesheet" href="<c:url value='/styles/all.css'/>"/>
		<link type="text/css" rel="stylesheet" href="<c:url value='/styles/right.css'/>"/>
		<title>home</title>
	</head>
	<body>
		<div class="allWrap">
			<%@include file="../common/head.jsp" %>
			<div class="bodyWrap">
				<%@include file="../common/left.jsp" %>
				<c:if test="${fn:length(menuVoList) eq 0 }">
					<div class="rightContentWrapCenter">
				</c:if>
				<c:if test="${fn:length(menuVoList) ne 0 }">
					<div class="rightContentWrap">
				</c:if>
					<img src="<c:url value='/images/welcomeBg.jpg' />" />
				</div>
			</div>
			<%@include file="../common/foot.jsp" %>
		</div>
	</body>
</html>