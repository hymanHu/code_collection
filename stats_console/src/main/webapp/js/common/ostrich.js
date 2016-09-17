var servers = [];
var intervalTime = 2;
var filterPool = [];
var totalFilterPool = [];
var isInitIntervalTime = true;
var fetchCounter = 0;
$(function(){
	localStorage["ostrich-addresses"] = "";
	var colors = ["blue", "green", "red", "black", "magenta"];
	
	//init tabs
	var statsTabs = new StatsTabs("servers");
	statsTabs.init();
	statsTabs.paging();
		
	
	//fetch servers in cycle
	var fetch = function() {
		var targetCount = servers.length;
		var updatedSet = [];
		$.each(servers,function(index,server){
			server.fetch(fetchCounter,updatedSet,targetCount);
		});
		fetchCounter ++;
//		window.setTimeout(fetch, intervalTime * 1000);
		window.setTimeout(fetch, 2 * 1000);
	};
	
	//track server
	var trackServer = function(){
		$('#graphs > table > tbody > tr').each(function(k, tr){
			$(tr).hide();
		});
		var addressAndTabName = $("#ostrich-address").val().replace(/\/$/g, "");
		addServer(addressAndTabName);
		if(localStorage) {
			localStorage["ostrich-addresses"] = 
				$.map(servers, function(server){return server.address + "||" + server.tabName;}).join(';;;');
		}
	};
	
	// add server in servers
	var addServer = function(addressAndTabName){
		var temp = addressAndTabName.split("||");
		var address = temp[0];
		var tabName = temp[1];
		var id = address.replace(/[^a-zA-Z0-9]/g, "_");
		
		/*
		 * judge server exist or not
		 * add server if it not exist
		 */
		var serverExist = false;
		if(localStorage && localStorage["ostrich-addresses"] && localStorage["ostrich-addresses"].length > 0) {
			var serverStrings = localStorage["ostrich-addresses"].split(';;;');
			$.each(serverStrings,function(index,serverString) {
				if(addressAndTabName == serverString){
					serverExist = true;
				}
			});
		}
		if(!serverExist){
			//add tab
			$("#graphTabs").show();
			statsTabs.addTab(id, tabName, "", address);
			
			//add server
			var server = new Server(id,address,tabName,colors[servers.length]);
			servers.push(server);
			
			//add click event
			$('#tab_' + id + ' span.ui-icon-close').click(function(){
				removeServer(id);
				return true;
			});
			$('#tab_' + id + " > a").click();
		} else {
			//turn to select tab id
			if($("#tab_" + id).css("display") == "none"){
				while($("#tab_" + id).css("display") == "none"){
					$(".ui-tabs-paging-prev > a").click();
				}
				$("#tab_" + id + " > a").click();
			} else {
				$("#tab_" + id + " > a").click();
			}
		}
	};
	
	//remove server
	var removeServer = function(id){
		if(servers.length ==  1) {
	    	$('#graphs > table > tbody > tr').remove();
	    	$("#serverVersion").html("");
	    } else {
	    	$('#graphs > table > tbody > tr').filter(function(i){return this.getAttribute('servers') == (id+',');}).remove();
	    	if($('#pa_p_memoryd' + id)) {
	    		$('#pa_p_memoryd' + id).remove();
	    	}
	    	if($('#pa_p_jvm_nonheapd' + id)) {
	    		$('#pa_p_jvm_nonheapd' + id).parent().parent().remove();
	    	}
	    	if($('#pa_p_jvm_heapd' + id)) {
	    		$('#pa_p_jvm_heapd' + id).parent().parent().remove();
	    	}
	    }
	    servers = servers.filter(function(server){return server.id != id;});
	    if (localStorage) {
	      localStorage["ostrich-addresses"] = 
	    	  $.map(servers, function(server){return server.address + "||" + server.tabName;}).join(';;;');
	    }
	};
	
	/*
	 * add listener for 'reset', 'ostrich-address'
	 */
	$('#reset').click(trackServer);
	
	$('#ostrich-address').keyup(function(event){
		if (event.keyCode == 13) {
			trackServer();
		}
	});
	
	$("#graphsFilter").keyup(function() {
		initFilterPool();
		showTable();
	});
	
	$("#serverReset").click(function() {
		if($("#serverAddress").val() == ""){
			msgDialog.open("Please input server address like 'http://{ip}:{port}/stats'.");
			return;
		}
		$('#graphs > table > tbody > tr').each(function(k, tr){
			$(tr).hide();
		});
		var address = $("#serverAddress").val().trim();
		if(address.substring(0,7) != "http://"){
			address = "http://" + address;
	    }
	    if (address.endswith("/globalstats")) {
	    	address = address + ".json";
	    }
	    address = address.replace(/\/$/g, "");
		var tabName = address.substring(address.lastIndexOf("//") + 2, address.length);
		addServer(address + "||" + tabName);
		if(localStorage) {
			localStorage["ostrich-addresses"] = 
				$.map(servers, function(server){return server.address + "||" + server.tabName;}).join(';;;');
		}
	});
	
	$('#serverAddress').keyup(function(event){
		if (event.keyCode == 13) {
			$("#serverReset").click();
		}
	});
	
	//fetch server when load page
	fetch();
});

//init interval time
function initIntervalTime (serverAddress) {
	var addressParameters = serverAddress.split("&");
	$.each(addressParameters,function(index,value) {
		var parameter = value.split("=");
		if(parameter.length == 2){
			var parameterKey = parameter[0];
			if(parameterKey == "interval"){
				intervalTime = parseInt(parameter[1]);
			}
		}
	});
};

//judge the string contain the filterPool
function judgeQueryWords (key,filterPool) {
	var contain = false;
	if(filterPool.length == 0){
		return true;
	}
	if(key == null || key.trim().length == 0){
		return contain;
	}
	$.each(filterPool,function(index,filter){
		if(key.toLowerCase().indexOf(filter.toLowerCase()) != -1){
			contain = true;
			return contain;
		}
	});
	return contain;
};

//init filter div
function initFilterDiv(tabName){
	if(localStorage[tabName + "_filter"] && localStorage[tabName + "_filter"].length > 0){
		$("#graphsFilter").val(localStorage[tabName + "_filter"]);
	} else {
		$("#graphsFilter").val("");
	}
}

//init filter pool
function initFilterPool() {
	filterPool = [];
	var filterArray = $("#graphsFilter").val().split(",");
	$.each(filterArray,function(index,filter){
		if(filter.trim() != "" && !filterPool.contains(filter.trim())){
			filterPool.push(filter.trim());
		}
	});
	var serverName = $("#graphTabs > .ui-state-active > a").text().trim();
	localStorage[serverName + "_filter"] = filterPool.join(",");
};

/*
 * show table
 */
function showTable() {
	var currentTabId = $("#graphTabs > .ui-state-active").attr("id");
	var currentServerId = currentTabId.substring(currentTabId.indexOf("_") + 1,currentTabId.length);
	$.each(servers,function(index,server) {
		if(server.id == currentServerId){
			server.showGraphTable();
		}
	});
}