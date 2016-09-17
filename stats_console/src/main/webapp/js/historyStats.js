$(function() {
	//init PageWrapper height
	var $pageWrapper = $('#pageWrapper');
	var height =  $(window).height() - $pageWrapper.offset().top -20;
	height = height > 550 ? height :550;
	$pageWrapper.height(height);
	
	//init the UI layout
	$("#pageWrapper").layout({
		//applyDefaultStyles: true,
		north__size: 65,
		west__size:220,
		resizable: true,
		north__closable: false,
		west__closable: true,
		north__spacing_open: 5,
	});
	
	// init tree
	var treeUri = "history/items";
	var myTree = new Tree("tree", treeUri);
	myTree.loadData(function(selectNode){
		$("#startTime").datepicker().val(getSpaceTime(3600 * 240,0).substring(0, 10));
		$("#endTime").datepicker().val(getSpaceTime(0,1).substring(0, 10));
		$("#unit").empty();
		$("#unit").append(
    			'<option id="dayOption" value="3">Day</option>' + 
    			'<option id="hourOption" value="4">Hour</option>');
		var staticGraph = new StaticGraph(selectNode.nodeKey,
				getUrl(selectNode.nodeValue,"",""),"graph");
		staticGraph.fetch();
	});
	
	/*
	 * Add jQuery UI datepicker to 'startTime' & 'endTime'
	 */
	$( "#startTime" ).datepicker({
		defaultDate: null,
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true,
		selectOtherMonths: true,
		dateFormat: 'yy-mm-dd',
		onSelect: function(dateText, inst){
			initUnit();
		}
	});
	$("#startTime").datepicker().val(getSpaceTime(3600 * 240,0).substring(0, 10));
	$( "#endTime" ).datepicker({
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true,
		selectOtherMonths: true,
		dateFormat: 'yy-mm-dd',
		onSelect: function(dateText, inst){
			initUnit();
		}
	});
	$("#endTime").datepicker().val(getSpaceTime(0,1).substring(0, 10));
	
	/*
	 * add click listener 
	 */
	$("#openAllTree").click(function() {
		myTree.openAll();
	});
	
	$("#closeAllTree").click(function() {
		myTree.closeAll();
	});
	
	$("#refreshTree").click(function() {
		myTree.refreshNode(0);
	});
	
	$("#reset").click(function(){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == ""){
			myDialog.open("Please select the start time.");
			return;
		}
		if(endTime == ""){
			myDialog.open("Please select the end time.");
			return;
		}
		if(new Date(startTime) > new Date(endTime)){
			myDialog.open("End time must greater than start time.");
			return;
		}
		if(myTree.selectNode == ""){
			myDialog.open("Please select node.");
			return;
		}
		startTime = startTime + " 00:00:00";
		endTime = endTime + " 23:59:59";
		
		staticGraph = new StaticGraph(myTree.selectNode.nodeKey,
				getUrl(myTree.selectNode.nodeValue,startTime,endTime),"graph");
		staticGraph.fetch();
	});
	
	/*
	 * Add tree & graph if its exist when reload the page
	 */
	if(localStorage["reportZkData"] && localStorage["reportZkData"].length > 0){
		myTree.loadTree(JSON.parse(localStorage["reportZkData"]));
		if(localStorage["StaticGraphData"] && localStorage["StaticGraphData"].length > 0 &&
				localStorage["unit"] && localStorage["unit"].length > 0) {
			$("#unit").val(localStorage["unit"]);
			var staticGraphTemp = new StaticGraph("","","graph");
			staticGraphTemp.cycleGraph(JSON.parse(localStorage["StaticGraphData"]));
		}
	} else {
		//init unit
		initUnit();
		myTree.loadData();
	}
	
});

/*
 * get url String
 * select value:
 * 	1.year
 * 	2.month
 * 	3.day
 * 	4.hour
 * 	5.30minutes
 */
function getUrl(nodeValue,startTime,endTime){
	if(startTime == ""){
		startTime = getSpaceTime(3600 * 240,0);
	}
	if(endTime == ""){
		endTime = getSpaceTime(0,1);
	}
	var unit = $("#unit").val();
	
	var prefixUrl = "http://" + location.hostname + ":" + location.port
		+ "/stats_console/";
	return prefixUrl + nodeValue + "&startTime=" + startTime + "&endTime=" + endTime + 
		"&timeUnit=" + unit + "&callback=?";
}

/*
 * get space time
 * spaceTime:before now time space, unit s.
 * flag: 0-start,1-end
 * */
function getSpaceTime(spaceTime,flag){
	var dateTime = new Date();
	var milliseconds = dateTime.getMilliseconds() - spaceTime * 1000;
	dateTime.setMilliseconds(milliseconds);
	var yy = dateTime.getFullYear();
	var MM = dateTime.getMonth()+1;
	MM = MM < 10 ? "0" + MM : MM;
	var dd = dateTime.getDate();
	dd = dd < 10 ? "0" + dd : dd;
	var hh = (flag == 0) ? "00" : "23";
	var mm = (flag == 0) ? "00" : "59";
	var ss = (flag == 0) ? "00" : "59";
	return yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss;
}

//init unit
function initUnit() {
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(startTime == ""){
		return;
	}
	if(endTime == ""){
		return;
	}
	var startDate = new Date(startTime);
	var endDate = new Date(endTime);
	var intervalSeconds = (
		(new Date(endDate.getFullYear(),endDate.getMonth() + 1,endDate.getDate() + 1,"00","00","00").getTime()) - 
		(new Date(startDate.getFullYear(),startDate.getMonth() + 1,startDate.getDate(),"00","00","00").getTime())) / 1000;
	if ((intervalSeconds / (3600 * 24 * 365)) < 1 && (intervalSeconds / (3600 * 24 * 30)) >= 1) {
		$("#unit").empty();
		$("#unit").append(
    			'<option id="monthOption" value="2">Month</option>' + 
    			'<option id="dayOption" value="3">Day</option>');
	} else if ((intervalSeconds / (3600 * 24 * 30)) < 1 && (intervalSeconds / (3600 * 24)) > 1) {
		$("#unit").empty();
		$("#unit").append(
    			'<option id="dayOption" value="3">Day</option>' + 
    			'<option id="hourOption" value="4">Hour</option>');
	} else if ((intervalSeconds / (3600 * 24)) <= 1) {
		$("#unit").empty();
		$("#unit").append(
    			'<option id="hourOption" value="4">Hour</option>' + 
    			'<option id="minuteOption" value="5">30Minutes</option>');
	} else {
		$("#unit").empty();
		$("#unit").append(
				'<option id="yearOption" value="1" selected="selected">Year</option>' + 
    			'<option id="monthOption" value="2">Month</option>');
	}
}