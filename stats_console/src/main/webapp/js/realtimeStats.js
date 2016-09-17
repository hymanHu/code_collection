$(function() {
	var prefixUrl = "http://" + location.hostname + ":" + location.port + "/stats_console/";
	
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
	var treeUri = "rt/items?callback=?";
	var myTree = new Tree("tree", treeUri);
	myTree.loadData(function(selectNode){
		$("#ostrich-address").val(prefixUrl + selectNode.nodeValue + "||" + selectNode.nodeKey);
		$("#reset").click();
	});
	
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
	
});