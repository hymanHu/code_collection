/*
 * server object
 * address:server Ajax url
 * tabName:server display name in tab
 */
var Server = function(id, address, tabName, color) {
	this.originalAddress = address;
	this.id = id;
	this.address = address.replace(/\/$/g, "");
	this.tabName = tabName;
	
	this.color = color;
	this.indicators = [];
	this.indicatorNames = [];
	this.lastCount = 0;
	
	this.version = "";
	this.initVersion = true;
	
	var self = this;
	
	//init interval time first time
	if(isInitIntervalTime) {
		initIntervalTime(self.address);
		isInitIntervalTime = false;
	}
	
	//get server data
	this.fetch = function(fetchCounter, updatedSet, targetCount) {
		//start test
//		var testurl = self.address;
//		if(self.address == "http://localhost:8080/stats_server/rt/stats?id=0&pollerType=JVM&pollerCmd=http%3A%2F%2Fserver-raid1%3A9999%2Fstats&interval=20"){
//			testurl = "http://localhost:8080/stats_console/test/serverData.jsp?";
//		} else if(self.address == "http://localhost:8080/stats_server/rt/stats?id=0&pollerType=JVM&pollerCmd=http%3A%2F%2Fserver-raid2%3A9999%2Fstats&interval=20") {
//			testurl = "http://localhost:8080/stats_console/test/serverData1.jsp?";
//		} else if(self.address == "http://localhost:8080/stats_server/rt/stats?id=0&pollerType=REDIS&pollerCmd=vip1-queue%3A5210&interval=60") {
//			testurl = "http://localhost:8080/stats_console/test/serverData2.jsp?";
//		}
		//end test
		
		$.ajax({
			url: self.address,
			//url: testurl,
			data: "callback=?",
			dataType: "jsonp",
			timeout: 15000,
			success: function(data) {
				if(self.initVersion){
					if(data.labels && self.version == ""){
						$.each(data.labels,function(k,v){
							self.version += v + " ";
						});
						if(self.version != ""){
							$("#serverVersion").html("(" + self.version + ")");
						}
					}
					self.initVersion = false;
				}
				if(!self.judgeIndicator(data)){
					$("#tab_" + self.id + " > a").removeClass("redText");
					$.each(data.counters,function(k,v) {
						if(judgeQueryWords(k,totalFilterPool)) {
							var indicator = self.indicators[k];
							if(!indicator){
								indicator = new Counter(k,v,self);
								self.indicators[k] = indicator;
								self.indicatorNames.push(k);
							}
							indicator.addValue(new TimedValue(v));
						}
					});
					$.each(data.gauges,function(k,v) {
						if(judgeQueryWords(k,totalFilterPool)) {
							var indicator = self.indicators[k];
							if(!indicator){
								indicator = new Gauge(k,v,self);
								self.indicators[k] = indicator;
								self.indicatorNames.push(k);
							}
							indicator.addValue(new TimedValue(v));
						}
					});
					$.each(data.piegrams,function(k,v) {
						if(judgeQueryWords(k,totalFilterPool)){
							var indicator = self.indicators[k];
							if (!indicator) {
								indicator = new Piegram(k, v, self);
								self.indicators[k] = indicator;
								self.indicatorNames.push(k);
							}
							indicator.setValue(v);
						}
					});
					updatedSet.push(self);
					if(updatedSet.length >= targetCount){
						$.each(updatedSet,function(k,server){
							server.render(fetchCounter,id);
						});
					}
				} else {
					$("#tab_" + self.id + " > a").addClass("redText");
				}
			},
			error: function(xOptions,status) {
				$("#tab_" + self.id + " > a").addClass("redText");
				$.each(self.indicators,function(indicator){
					indicator.addValue(new TimedValue(null));
				});
				//cause we don't want hanged server break our rendering
				updatedSet.push(self);
				if(updatedSet.length >= targetCount){
					$.each(updatedSet,function(k,server){
						server.render(fetchCounter,self.id);
					});
				}
			}
		});
	};
	
	//render the indicator
	this.render = function(fetchCounter,serverId) {
		this.lastCount = fetchCounter;
		var myTab  = $('#tab_' + self.id);
	    var allTab = $('#tab_all_servers');
	    if (myTab.hasClass('ui-state-active') || allTab.hasClass('ui-state-active')) {
	    	$.each(self.indicatorNames,function(k,indicatorName){
	    		if(judgeQueryWords(indicatorName,totalFilterPool)) {
	    			self.indicators[indicatorName].render(self.color,fetchCounter,serverId);
	    		}
	    	});
	    }
	};
	
	//judge indicator value
	this.judgeIndicator = function(data) {
		var indicatorIsNull = true;
		$.each(data,function(k,v){
			$.each(v,function(k1,v1){
				indicatorIsNull = false;
			});
		});
		return indicatorIsNull;
	};
	
	//show graph table
	this.showGraphTable = function() {
		$('#openInNewWindow').html('@See Full Stats of '+ self.tabName);
		if(self.version != ""){
			$("#serverVersion").html("(" + self.version + ")");
		} else {
			$("#serverVersion").html("");
		}
		$("#openInNewWindow").attr("href", self.address);
		$("#openInNewWindow").attr("target","_blank");
		$('#servers > .ui-state-active').removeClass('ui-state-active');
		$('#tab_' + id).addClass('ui-state-active');
		filterPool = [];
		if(localStorage[self.tabName + "_filter"] && localStorage[self.tabName + "_filter"].length > 0){
			var filterArray = localStorage[self.tabName + "_filter"].split(",");
			$.each(filterArray,function(index,filter){
				if(filter.trim() != "" && !filterPool.contains(filter.trim())){
					filterPool.push(filter.trim());
				}
			});
		}
		
		$('#graphs > table > tbody > tr').each(function(k, tr){
			var shown = false;
	        for (var i = 0; i < self.indicatorNames.length; i ++) {
	        	var indicatorNameId = self.indicators[self.indicatorNames[i]].rowId();
	        	var containFilterPool = judgeQueryWords(self.indicators[self.indicatorNames[i]].name, 
	        			filterPool);
	        	if ((tr.id == indicatorNameId)) {
	        		if(containFilterPool){
	        			shown = true;
	        			$(tr).show();
	        		}
	        		self.indicators[self.indicatorNames[i]].render(self.color, self.lastCount+0.5, id); 
	        	}
	        }
	        if (!shown) {
	        	$(tr).hide();
	        }
		});
	};
	
	/*
	 * add listener for tabs
	 */
	$("#tab_" + self.id + " > a").click(function(evt) {
		$('#graphs > table > tbody > tr').each(function(k, tr){
			$(tr).hide();
		});
		initFilterDiv(self.tabName);
		self.showGraphTable();
	});
	
};