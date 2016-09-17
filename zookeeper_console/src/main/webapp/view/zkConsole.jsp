<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	<link type="text/css" href="css/ui-lightness/jquery-ui.css" rel="stylesheet" />
	<link type="text/css" href="css/layout-default-latest.css" rel="stylesheet" />
	<link type="text/css" href="css/bootstrap.css" rel="stylesheet" />
	<link type="text/css" href="css/basis.css" rel="stylesheet" />
	
	<script type="text/javascript" src="js/jquery/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.layout-latest.js"></script>
	<script type="text/javascript" src="js/jquery/ajaxfileupload.js"></script>
	<script type="text/javascript" src="js/jquery/jstree/jquery.cookie.js"></script>
	<script type="text/javascript" src="js/jquery/jstree/jquery.hotkeys.js"></script>
	<script type="text/javascript" src="js/jquery/jstree/jquery.jstree.js"></script>
	
	<script type="text/javascript" src="js/common/tools.js"></script>
	<script type="text/javascript" src="js/common/dialog.js"></script>
	<script type="text/javascript" src="js/common/tree.js"></script>
	<script type="text/javascript" src="js/zkConsole.js"></script>
	
	<title>Zookeeper_Console</title>
</head>
<body>
	<div id="dialog-message" title="message"></div>
	<div id="dialog-load" title="load"></div>
	<div id="dialog-confirm" title="confirm"></div>
	<div id="pageWrapper">
		<div class="pane ui-layout-north">
			<div id="header">
				<a href="#" id="logo"></a>
				<div id="version">
					<jsp:include page="version.jsp"></jsp:include>
				</div>
		        <ul class="user pull-right">
					<li>Welcome!</li>
				</ul>
			</div>
		</div>
		<div class="pane ui-layout-west">
			<div id="sideLeft">
		    	<div class="btnTwo">
		    		<a id="openAllTree" class="btn btn-mini" href="#"><i class="icon-folder-open"></i>OpenAll</a>
		    		<a id="closeAllTree" class="btn btn-mini" href="#"><i class="icon-folder-close"></i>CloseAll</a>
		    		<a id="refreshTree" class="btn btn-mini" href="#"><i class="icon-refresh"></i>Refresh</a>
		        </div>
		        <div id="tree" class="treeDiv"></div>
		    </div>
		</div>
		<div class="pane ui-layout-center">
			<div id="pageMain" class="form-horizontal">
				<fieldset>
					<legend>Show Node Info</legend>
					<div class="control-group">
						<label class="control-label" for="cZkHost">Current Zk Host:</label>
						<div class="controls">  
			            	<p id="cZkHost" class="help-block">${zkHost}</p>  
			        	</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="parentIdentifier">Parent Node:</label>
						<div class="controls">  
			            	<p id="parentIdentifier" class="help-block"></p>  
			        	</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="currentIdentifier">Current Node:</label>
						<div class="controls">  
			            	<p id="currentIdentifier" class="help-block"></p>  
			        	</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="currentNodeKeyValue">Node Key & Value:</label>
						<div class="controls">  
			            	<p id="currentNodeKeyValue" class="help-block"></p>  
			        	</div>
					</div>
					<legend>Zk Operation</legend>
					<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
					<div class="control-group">
						<label class="control-label">Export Node:</label>
						<div class="controls">
							<button id="exportNode" class="btn">Export</button>
			        	</div>
					</div>
					<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
					<div class="control-group">
						<label class="control-label" for="upLoadFile">Import Node:</label>
						<div class="controls">
							<!-- <form action="importNode" method="post" enctype="multipart/form-data">
							</form> -->
							<input id="importFile" name="importFile" class="input-file" type="file" required="required" />
							<button id="importFileBtn" class="btn">Import</button>
			        	</div>
					</div>
					<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
					<div class="control-group">
						<label class="control-label">Delete Node:</label>
						<div class="controls">
							<button id="deleteNode" class="btn">Delete</button>
			        	</div>
					</div>
					<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
					<div class="control-group">
						<label class="control-label" for="copyTo">Copy Node:</label>
						<div class="controls">
							<input id="copyTo" type="text" class="input-xlarge" />
							<button id="copyNodeBtn" class="btn">Copy</button>
							<p class="help-block"><strong>Please begin with "/"</strong></p>
			        	</div>
					</div>
					<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
					<div class="control-group">
						<label class="control-label" for="updateValue">Update Node:</label>
						<div class="controls">
							<textarea class="input-xlarge" id="updateValue" rows="3"></textarea>
							<button id="updateBtn" class="btn">Update</button>
			        	</div>
					</div>
					<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
					<div class="control-group">
						<label class="control-label" for="nodeKey">Create Node:</label>
						<div class="controls">
							<input id="nodeKey" type="text" class="input-xlarge" />
			        		<button id="createBtn" class="btn">Create</button>
			        	</div>
					</div>
					<div class="control-group">
			        	<div class="controls">
			        		<textarea class="input-xlarge" id="nodeValue" rows="3"></textarea>
			        	</div>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
</body>
</html>