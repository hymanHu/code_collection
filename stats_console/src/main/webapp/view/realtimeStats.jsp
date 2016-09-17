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
	<script type="text/javascript" src="js/jquery/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.layout-latest.js"></script>
	<script type="text/javascript" src="js/jquery/jstree/jquery.jstree.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.flot.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.flot.pie.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.sparkline.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.ui.tabs.js"></script>
	<script type="text/javascript" src="js/jquery/ui.tabs.paging.js"></script>
	
	<script type="text/javascript" src="js/common/tools.js"></script>
	<script type="text/javascript" src="js/common/dialog.js"></script>
	<script type="text/javascript" src="js/common/tree.js"></script>
	<script type="text/javascript" src="js/common/tabs.js"></script>
	<script type="text/javascript" src="js/common/indicators.js"></script>
	<script type="text/javascript" src="js/common/server.js"></script>
	<script type="text/javascript" src="js/common/ostrich.js"></script>
	<script type="text/javascript" src="js/realtimeStats.js"></script>
	
	<title>Realtime_Stats</title>
</head>
<body>
	<div id="dialog-message" title="message"></div>
	<div id="dialog-load" title="loading"></div>
	<div id="dialog-confirm" title="confirm"></div>
	<div style="display: none;">
		<input id="ostrich-address-key" type="text" value="">
		<input id="ostrich-address" type="text" value="" 
			placeholder="http://localhost:8080/stats_server/stats/memcached/172.17.20.21/11211" />
		<input type="submit" class="btn primary" value="Server Track" id="reset" />
	</div>
	<div id="pageWrapper">
		<div class="ui-layout-center">
			<form class="well form-inline">
				<input id="serverAddress" type="text" class="span3" placeholder="http://172.17.20.112:9999/stats">
				<button id="serverReset" type="button" class="btn">Server Track</button>
				<div class="pull-right">
					<span class="help-inline">Query Key Word:</span>
					<input id="graphsFilter" type="text" class="input-medium search-query" placeholder="request,bytes">
				</div>
			</form>
			<div id="servers">
				<ul class="tabs" id="graphTabs" style="display:none"></ul>
				<div id="graphs">
					<table class="table table-striped">
						<thead>
							<tr>
								<td></td>
								<td>Name</td>
								<td>Current</td>
								<td>Delta</td>
								<td>Graph</td>
							</tr>
							<tr>
								<td colspan="5" style="height:35px;">
									<a href="#" id="openInNewWindow"></a><div id="serverVersion"></div>
								</td>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="ui-layout-north">
			<div id="header">
				<a href="#" id="logo"></a>
				<div id="version">
					<jsp:include page="version.jsp"></jsp:include>
				</div>
				<ul class="user pull-right">
					<li>Welcome!</li>
		        	<li><a href="historyStats">Go to History Stats</a></li>
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