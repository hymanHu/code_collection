<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
		<title>addUser</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="<c:url value='/scripts/addUser.js'/>" ></script>
		<script type="text/javascript" src="<c:url value='/scripts/area_database.js'/>" ></script>
		<script type="text/javascript" src="<c:url value='/scripts/area_config.js'/>" ></script>
		<script type="text/javascript" src="<c:url value='/scripts/My97DatePicker/WdatePicker.js'/>" ></script>
		<!--
			<link rel="stylesheet" type="text/css" href="styles.css">
		-->
	</head>

	<body>
		addUser page.
		<form:form modelAttribute="addUserModel" action="addUser.do" method="post">
			<table>
				<tr>
					<td><spring:message code="message.user.userName"/></td>
					<td><input id="userName" name="userName" type="text" value="${addUserModel.userName }"/></td>
					<td>
						<div><form:errors path="userName"/></div>
					</td>
				</tr>
				<tr>
					<td><spring:message code="message.user.nickname"/></td>
					<td><input id="nickname" name="nickname" type="text" value="${addUserModel.nickname }"/></td>
					<td>
						<div><form:errors path="nickname"/></div>
					</td>
				</tr>
				<tr>
					<td><spring:message code="message.login.email"/></td>
					<td><input id="email" name="email" type="text" value="${addUserModel.email }"/></td>
					<td>
						<div><form:errors path="email"/></div>
					</td>
				</tr>
				<tr>
					<td><spring:message code="message.login.password"/></td>
					<td><input id="passWord" name="passWord" type="password"/></td>
					<td>
						<div><form:errors path="passWord"/></div>
					</td>
				</tr>
				<tr>
					<td><spring:message code="message.login.rePassWord"/></td>
					<td><input id="rePassWord" name="rePassWord" type="password"/></td>
					<td>
						<div><form:errors path="rePassWord"/></div>
						<div><form:errors path="passWordValid"/></div>
					</td>
				</tr>
				<tr>
					<td><spring:message code="message.user.gender"/></td>
					<td>
						<select id="gender" name="gender">
							<c:choose>
								<c:when test="${addUserModel.gender eq 'male'}">
									<option value="male" selected="selected"><spring:message code="message.user.male"/></option>
									<option value="female"><spring:message code="message.user.female"/></option>
								</c:when>
								<c:when test="${addUserModel.gender eq 'female'}">
									<option value="male"><spring:message code="message.user.male"/></option>
									<option value="female"  selected="selected"><spring:message code="message.user.female"/></option>
								</c:when>
								<c:otherwise>
									<option value="male" selected="selected"><spring:message code="message.user.male"/></option>
									<option value="female"><spring:message code="message.user.female"/></option>
								</c:otherwise>
							</c:choose>
						</select>
					</td>
					<td>
						<div><form:errors path="gender"/></div>
					</td>
				</tr>
				<tr>
					<td><spring:message code="message.user.location"/></td>
					<td>
						<select id="userProvince" name='userProvince' id='userProvince' 
							onchange="TwoSelectInit(area1,this.value);document.getElementById('reside_province').value=this.value;">
						</select>
						<input type='hidden' name='reside_province' 
							id='reside_province' value='' />
						<select id="userCity" name='userCity' id='userCity' 
							onchange='document.getElementById("reside_city").value=this.value'></select>
						<input type='hidden' name='reside_city' id='reside_city' value='' />
						<script type="text/javascript">
							var area1 = new HwTwoSelect(document.getElementById("userProvince"),document.getElementById("userCity"),selecttext,"－省份－@","－城市－@",0);
							TwoSelectInit(area1);
						</script>
					</td>
					<td>
						<div><form:errors path="userProvince"/></div>
					</td>
				</tr>
				<tr>
					<td><spring:message code="message.user.birthday"/></td>
					<td>
						<input name="birthday" id="birthday" type="text" value="${addUserModel.birthday }"/>
						<img onclick="WdatePicker({el:'birthday',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="<c:url value='/scripts/My97DatePicker/skin/datePicker.gif'/>" width="16" height="22" align="absmiddle" />
					</td>
					<td>
						<div><form:errors path="birthday"/></div>
					</td>
				</tr>
				<tr>
					<td><spring:message code="message.user.phone"/></td>
					<td><input id="phone" name="phone" type="text" value="${addUserModel.phone }"/></td>
					<td>
						<div><form:errors path="phone"/></div>
					</td>
				</tr>
				<tr>
					<td><spring:message code="message.user.registTime"/></td>
					<td>
						<input name="registTime" id="registTime" type="text" value="${addUserModel.registTime }"/>
						<img onclick="WdatePicker({el:'registTime',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="<c:url value='/scripts/My97DatePicker/skin/datePicker.gif'/>" width="16" height="22" align="absmiddle" />
					</td>
					<td>
						<div><form:errors path="registTime"/></div>
					</td>
				</tr>
				<tr >
					<td colspan="3">
						<input type="submit" value="<spring:message code='message.user.regist'/>"/>
						<input type="button" value="<spring:message code='message.login.reset'/>"/>
					</td>
				</tr>
			</table>
		</form:form>
	</body>
</html>
