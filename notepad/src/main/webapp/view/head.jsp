<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/bootstrap/bootstrap-dropdown.js"></script>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<ul class="nav">
				<li class="active">
					<a class="brand" href="#">Notepad</a>
					<li><a href="#">Home</a></li>
				</li>
			</ul>
			<ul class="nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">User<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${contextPath }/">Sign In</a></li>
						<li><a href="${contextPath }/toSignUp">Sign Up</a></li>
					</ul>
				</li>
			</ul>
			<ul class="nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">About<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"><jsp:include page="version.jsp"></jsp:include></a></li>
					</ul>
				</li>
			</ul>
			<ul class="nav pull-right">
				<li><a href="#">Welcome!</a></li>
			</ul>
		</div>
	</div>
</div>
