<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<link rel="stylesheet" type="text/css" href="css/basis.css" />
		
		<script type="text/javascript" src="js/jquery/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="js/notePadSignIn.js"></script>
		
		<title>NotePad</title>
	</head>
	<body>
		<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
		<jsp:include page="head.jsp"></jsp:include>
		<div class="container">
			<form class="form-horizontal">
				<fieldset>
					<legend>Sign in to Notepad:</legend>
					<div class="control-group">
						<label class="control-label" for="emailForSignIn">Email Address:</label>
						<div class="controls">
							<input type="text" class="input-large" id="emailForSignIn" placeholder="Email">
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="pwdForSignIn">Password:</label>
						<div class="controls">
							<input type="password" class="input-large" id="pwdForSignIn" placeholder="Password">
							<a href="#">Forgot password?</a>
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-primary">Sign in</button>
							<a class="btn" href="${contextPath }/toSignUp">Join Notepad</a>
						</div>
					</div>
				</fieldset>
			</form>
			
			<hr>
			<footer>  
				<p>© ThornBird 2013</p>  
			</footer>
		</div>
	</body>
</html>