<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>login</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<link type="text/css" rel="stylesheet" href="<c:url value='/styles/all.css'/>"/>
		<link type="text/css" rel="stylesheet" href="<c:url value='/styles/login.css'/>"/>
		<script type="text/javascript" src="<c:url value='/scripts/jquery-1.5.min.js' />"></script>
		<script type="text/javascript" src="<c:url value='/scripts/login.js' />"></script>
	</head>

	<body>
		<div class="allWrap">
			<div class="headWrap">
				<div class="headBlackLine"></div>
				<div class="logo"></div>
				<div class="loginInfo">
					<a href="#"><spring:message code="message.login.wellcome"/></a>
				</div>
			</div>
			<div class="bodyWrap loginBg">
		        <form id="loginForm" action="<c:url value='/j_spring_security_check' />" method="post">
					<div class="loginWrap">
						<div id="msg" class="errorMsgDiv redText">
							<c:if test="${not empty errorMsg }">
								<spring:message code="security.error.login"/>
							</c:if>
						</div>
						<ul>
							<li class="loginTitle"><spring:message code="message.login.email"/></li>
				            <li><input name="j_username" id="username" type="text" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" class="textBox1" /></li>
				            <li class="loginTitle"><spring:message code="message.login.password"/></li>
				            <li><input name="j_password" id="password" type="password" class="textBox1" /></li>
				            <li><input type="checkbox" name="_spring_security_remember_me" class="mT8" /><spring:message code="message.login.keepLogin"/></li>
				            <li>
				            	<a id="login_button" href="javascript:login();" class="btSubmit mT8"><spring:message code='message.login.login'/></a>
				            	<a id="regist_button" href="redirectRegist.do" class="btRegist mT8"><spring:message code='message.user.regist'/></a>
				            </li>
						</ul>
					</div>
				</form>
			</div>
			<div class="bottomWrap">
				<label class="leftSideText">Legal terms and copyrights</label>
			</div>
		</div>
	</body>
</html>
