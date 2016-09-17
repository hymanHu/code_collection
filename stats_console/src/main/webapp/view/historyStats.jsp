<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="css/layout-default-latest.css" />
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="css/basis.css" />
	
	<script type="text/javascript" src="js/jquery/jquery.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.flot.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.flot.crosshair.js"></script>
	<script type="text/javascript" src="js/jquery/jstree/jquery.jstree.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui-sliderAccess.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.layout-latest.js"></script>
	
	<script type="text/javascript" src="js/common/tools.js"></script>
	<script type="text/javascript" src="js/common/dialog.js"></script>
	<script type="text/javascript" src="js/common/tree.js"></script>
	<script type="text/javascript" src="js/common/staticGraph.js"></script>
	<script type="text/javascript" src="js/historyStats.js"></script>
	
	<title>History_Stats</title>
</head>
<body>
	<div id="dialog-message" title="message"></div>
	<div id="dialog-load" title="loading"></div>
	<div id="dialog-confirm" title="confirm"></div>
	<div id="pageWrapper">
		<div class="ui-layout-center">
			<form class="well form-inline">
				<span class="help-inline">Start Time:</span>
				<input type="text" name="example1" id="startTime" value="" class="span2" />
				<span class="help-inline">End Time:</span>
				<input type="text" name="example1" id="endTime" value="" class="span2" />
				<span class="help-inline">Unit:</span>
				<select name="unit" id="unit" class="span2">
	    			<option id="yearOption" value="1" selected="selected">Year</option>
	    			<option id="monthOption" value="2">Month</option>
	    			<option id="dayOption" value="3">Day</option>
	    			<option id="hourOption" value="4">Hour</option>
	    			<option id="minuteOption" value="5">30Minutes</option>
	    		</select>
	    		<input class="btn" type="button" id="reset" value="refresh"/>
			</form>
			<div id="graph"></div>
		</div>
		<div class="ui-layout-north">
			<div id="header">
				<a href="#" id="logo"></a>
				<div id="version">
					<jsp:include page="version.jsp"></jsp:include>
				</div>
				<ul class="user pull-right">
					<li>Welcome!</li>
		        	<li><a href="realtimeStats">Go to Realtime Stats</a></li>
				</ul>
		    </div>
		</div>
		<div class="ui-layout-west">
			<div id="sideLeft">
		    	<div class="btnTwo">
		    		<a id="openAllTree" class="btn btn-mini" href="#"><i class="icon-folder-open"></i>OpenAll</a>
		    		<a id="closeAllTree" class="btn btn-mini" href="#"><i class="icon-folder-close"></i>CloseAll</a>
		    		<a id="refreshTree" class="btn btn-mini" href="#"><i class="icon-refresh"></i>Refresh</a>
		        </div>
		        <div id="tree" class="treeDiv"></div>
		    </div>
		</div>
	</div>
</body>
</html>