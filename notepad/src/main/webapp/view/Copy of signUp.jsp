<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<link rel="stylesheet" type="text/css" href="css/basis.css" />
		
		<script type="text/javascript" src="js/jquery/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="js/notePadSignUp.js"></script>
		
		<title>NotePad</title>
	</head>
	<body>
		<jsp:include page="head.jsp"></jsp:include>
		<div class="container-fluid">
			<div class="row-fluid">
				<!-- left -->
				<div class="span3">
					<div class="well sidebar-nav">
						<ul class="nav nav-list">
							<li class="nav-header">Frontend</li>
							<li class="active"><a href="#">HTML 4.01</a></li>
							<li><a href="aaa">HTML5</a></li>
							<li><a href="#">CSS</a></li>
							<li class="nav-header">Backend</li>
							<li><a href="#">PHP</a></li>  
							<li><a href="#">SQL</a></li>  
							<li><a href="#">MySQL</a></li>  
							<li><a href="#">PostgreSQL</a></li>  
							<li><a href="#">MongoDB</a></li>
						</ul>
					</div>
				</div>
				
				<!-- right -->
				<div class="span9">
					<form class="form-horizontal">
						<fieldset>
							<legend>Sign up to Notepad:</legend>
							<div class="control-group">
								<label class="control-label" for="input01">Email Address:</label>
								<div class="controls">
									<input type="text" class="input-large" id="input01" placeholder="Email">
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="input02">Password:</label>
								<div class="controls">
									<input type="password" class="input-large" id="input02" placeholder="Password">
									<a href="#">Forgot password?</a>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<button type="submit" class="btn btn-primary">Sign in</button>
									<button class="btn">Join Notepad</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
			
			<!-- foot -->
			<hr>
			<footer>  
				<p>© ThornBird 2013</p>  
			</footer>
		</div>
	</body>
</html>