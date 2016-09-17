<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>login</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link type="text/css" rel="stylesheet" href="<c:url value='/styles/all.css'/>"/>
		<!--
			<link rel="stylesheet" type="text/css" href="styles.css">
		-->
	</head>

	<body>
		<!--  
			/j_spring_security_check:提交到spring security验证中心
			${sessionScope['SPRING_SECURITY_LAST_USERNAME']}:使用最后一次登陆的用户名
			_spring_security_remember_me:记住我
		-->
		<div>
			<font color="red">
				login error:<br/>
				${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
			</font><br/>
			<c:if test="${not empty errorMsg }">
				<font color="red">
					<spring:message code="security.error.login"/>
				</font>
			</c:if>
		</div>
		<form id="form2" action="<c:url value='/j_spring_security_check' />" method="post">
			<spring:message code="message.login.email"/>
			<input type="text" id="j_username" name="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"/><br/>
			<spring:message code="message.login.password"/>:</label>
			<input type="password" id="j_password" name="j_password"/><br/>
			<input type="checkbox" name="_spring_security_remember_me" />
			<spring:message code="message.login.keepLogin"/><br/>
			<input type="submit" value="<spring:message code='message.login.login'/>" />
			<a href="redirectRegist.do"><spring:message code='message.user.regist'/></a>
		</form>
		<br/>
		<form id="form1" action="<c:url value="/login.do" />" method="post">
			<label><spring:message code="message.login.email"/>:</label><input type="text" id="email" name="email" /><br/>
			<label><spring:message code="message.login.password"/>:</label><input type="password" id="passWord" name="passWord"/><br/>
			<input type="submit" value="<spring:message code='message.login.login'/>">
		</form>
		<br/>
	</body>
</html>
