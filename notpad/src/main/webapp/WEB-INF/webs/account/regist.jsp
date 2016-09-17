<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link type="text/css" rel="stylesheet" href="<c:url value='/styles/all.css'/>"/>
		<link type="text/css" rel="stylesheet" href="<c:url value='/styles/regist.css'/>"/>
		<script type="text/javascript" src="<c:url value='/scripts/jquery-1.5.min.js' />"></script>
		<script type="text/javascript" src="<c:url value='/scripts/regist.js' />"></script>
		<title>regist</title>
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
			<div class="bodyWrap registBg">
			<div class="bodyWrap registBg2">
				<form:form id="registForm" modelAttribute="registAccountModel" action="addAccount.do" method="post">
					<table>
						<tr>
							<td class="registTitle"><spring:message code="message.login.email"/></td>
							<td class="registText"><input class="textBox1" id="email" name="email" type="text" value="${registAccountModel.email }"/></td>
							<td class="registError redText"><form:errors path="email" /></td>
						</tr>
						<tr>
							<td class="registTitle"><spring:message code="message.login.password"/></td>
							<td class="registText"><input class="textBox1" id="password" type="password" name="password"/></td>
							<td class="registError redText"><form:errors path="password"/></td>
						</tr>
						<tr>
							<td class="registTitle"><spring:message code="message.login.rePassWord"/></td>
							<td class="registText"><input class="textBox1" id="rePassword" type="password" name="rePassword"/></td>
							<td class="registError redText"><form:errors path="rePassword"/></td>
						</tr>
						<tr>
							<td class="registTitle"><spring:message code="message.account.visualCode"/></td>
							<td class="registText">
								<img id="visualCode" alt="visualCode" src="<c:url value='/visualCode.do'/>">
								<a class="mL11" href="javascript:reloadVerifyCode();"><spring:message code="message.account.visualCode.notClear"/></a>
							</td>
							<td class="registError redText">
							</td>
						</tr>
						<tr>
							<td class="registTitle"><spring:message code="message.account.inputIisualCode"/></td>
							<td class="registText"><input class="textBox1" id="visualCodeStr" type="text" name="visualCodeStr"/></td>
							<td class="registError redText"><form:errors path="visualCodeStr"/></td>
						</tr>
						<tr>
							<td colspan="3">
								<a id="regist_button" href="javascript:regist();" class="btSubmit mT8"><spring:message code='message.user.regist'/></a>
								<a id="login_button" href="<c:url value='/login.jsp'/>" class="btRegist mT8"><spring:message code="message.login.login"/></a>
							</td>
						</tr>
					</table>
				</form:form>
			</div>	
			</div>
			<div class="bottomWrap">
				<label class="leftSideText">Legal terms and copyrights</label>
			</div>
		</div>
	</body>
</html>