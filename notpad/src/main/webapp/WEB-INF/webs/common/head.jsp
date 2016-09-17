<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() 
		+ ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>"/>
<div class="headWrap">
	<div class="headBlackLine"></div>
	<a href="securityLogin.do"><div class="logo"></div></a>
    <div class="loginInfo">
		<div>
			<c:if test="${not empty accountVo.account.user.userName }">
				<label><spring:message code="message.account.wellcome"/>,${accountVo.account.user.userName }</label>&nbsp;
			</c:if>
			<c:if test="${empty accountVo.account.user.userName }">
				<label><spring:message code="message.account.wellcome"/>,${accountVo.account.email }</label>&nbsp;
			</c:if>
			<a href="<c:url value='/redirectRegist.do'/>"><spring:message code='message.user.regist'/></a>&nbsp;
			<a href="<c:url value='/logout.do'/>"><spring:message code="message.user.logout"/></a>
		</div>
	</div>
</div>