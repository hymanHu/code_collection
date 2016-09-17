<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
				<div class="rightContentWrap">
					<div class="rightConentTitleWrap">
						<h3 class="user"><spring:message code="massage.account.accountList" /></h3>
						<div class="tableWrap">
							<div class="bar">
								<div class="queryDiv fr mR11 cursor mT8">
									<form name="queryForm" action="<c:url value='/account/queryAccount.do'/>" method="post">
										<input class="queryText" name="keyWord" type="text" value="${keyWord }" />
										<input class="queryBtn" type="submit" value="<spring:message code='message.tool.search'/>"/>
									</form>
								</div>
							</div>
							<table class="commonContentTable">
								<tr>
									<th><spring:message code="message.user.userName"/></th>
									<th><spring:message code="message.login.email"/></th>
									<th><spring:message code="message.user.role"/></th>
									<th><spring:message code="message.user.registTime"/></th>
									<th><spring:message code="message.user.status"/></th>
								</tr>
								<c:if test="${not empty accountList }">
								<c:forEach var="accountVo" items="${accountList }" >
									<tr>
										<td>
											<c:if test="${not empty accountVo.account.user.userName }">
												<a href="<c:url value='/account/accountInfo.do?accountId=${accountVo.account.accountId}'/>">
													<c:out value="${accountVo.account.user.userName}" />
												</a>
											</c:if>
											<c:if test="${empty accountVo.account.user.userName }">
												<a href="<c:url value='/account/accountInfo.do?accountId=${accountVo.account.accountId}'/>">
													<c:out value="${accountVo.account.email}" />
												</a>
											</c:if>
										</td>
										<td>
											<a href="<c:url value='/account/accountInfo.do?accountId=${accountVo.account.accountId}'/>">
												<c:out value="${accountVo.account.email}" />
											</a>
										</td>
										<td><c:out value="${accountVo.roleName}" /></td>
										<td><fmt:formatDate value="${accountVo.account.user.registTime}" pattern="yyyy-MM-dd"/></td>
										<td><c:out value="${accountVo.status}" /></td>
									</tr>
								</c:forEach>
								</c:if>
							</table>
							<div id="page_div" class="page">
								<form name="pageForm">
									<input type="hidden" name="currentPage" value="${currentPage }">
									<input type="hidden" name="keyWord" value="${keyWord }"/>
								</form>
								<%@include file="../common/page.jsp" %>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%@include file="../common/foot.jsp" %>
		</div>
	</body>
</html>