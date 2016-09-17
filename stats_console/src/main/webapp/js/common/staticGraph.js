/*
 * static graph object
 */
var StaticGraph = function(id,address,containerId){
	this.originalAddress = address;
	this.address = encodeURI(address);
	this.id = id;
	this.name = address;
	this.containerId = containerId;
	
	var self = this;
	
	//start test
//	this.address = "http://localhost:8080/stats_console/test/GraphData.jsp?callback=?";
//	this.originalAddress = "http://localhost:8080/stats_console/test/GraphData.jsp?callback=?";
	//end test
	
	/*
	 * get static graph data
	 */
	this.fetch = function(){
		msgDialog.open("Loading...");
		$.ajax({
			url: self.address,
			dataType: "jsonp",
			timeout: 15000,
			success: function(data){
				localStorage["StaticGraphData"] = JSON.stringify(data);
				self.cycleGraph(data);
				msgDialog.close();
			},
			error : function() {
				localStorage["StaticGraphData"] = "";
				$("#" + self.containerId).children().remove();
				msgDialog.open("No graph result return.");
			}
		});
	};
	
	//ticks generator
	function tickGenerator(data) {
		var ticksArray = [];
		
		//divide 12 copies
		var ticksLength = data.length;
		var interval = (parseInt(data[ticksLength - 1][0]) - parseInt(data[0][0])) / 12;
		for(var i = 0;i <= 12; i ++) {
			ticksArray.push(parseInt(data[0][0]) + i * interval);
		}
		
		//display the point in data
		/*var ticksLength = data.length;
		if(ticksLength < 12){
			$.each(data,function(n,v){
				ticksArray.push(v[0]);
			});
		} else {
			var interval = parseInt((data.length) / 12);
			for(var i = 0;i < data.length; i ++){
				if(i % interval == 0){
					ticksArray.push(data[i][0]);
				}
			}
			ticksArray.push(data[0][0]);
			var interval = parseInt((data.length - 2) / 10);
			var remainder = (data.length - 2) % 10;
			var incrementTime  = remainder == 0 ? 0 : 1;
			var pushIndex = interval + incrementTime;
			for(var i = 1;i < data.length - 1; i ++){
				if(i == pushIndex){
					ticksArray.push(data[i][0]);
					if (remainder == 0) {
						pushIndex = i + interval;
					} else {
						incrementTime += 1;
						pushIndex = incrementTime > remainder ? i + interval : i + interval + 1;
					}
				}
			}
			ticksArray.push(data[ticksLength - 1][0]);
		}*/
		return ticksArray;
	};
	
	//draw graph in cycle
	this.cycleGraph = function (data) {
		localStorage["unit"] = $("#unit").val();
		$("#" + self.containerId).children().remove();
		$.each(data,function(name,value){
			self.graph(value, tickGenerator(value), name.replace(/[^a-zA-Z0-9]/g, "_"));
		});
	};
	
	/*
	 * draw graph
	 */
	this.graph = function (data, ticksArray, id){
		var options = {
				series: {
					color: '#90EE90',
					lines: { 
						show: true, 
						/*fill: true, 
						fillColor: "rgba(255, 255, 255, 0.7)"*/
					},
				    points: { show: true, fill: false }
				},
				crosshair: { mode: "x" },
				xaxis: { 
					show: true,
					/*mode: "time",
					timeformat: "%y-%0m-%0d %H:%M:%S",//%y-%m-%d %H:%M:%S*/
					ticks: ticksArray,
					color : "#5181B4",
					tickColor: "grey",
					tickFormatter: function(val,axis) {
						var dateTime = new Date(val);
						var hours = formatNumber(dateTime.getHours());
						var minutes = formatNumber(dateTime.getMinutes());
						var seconds = formatNumber(dateTime.getSeconds());
						var tickLabel = dateTime.getFullYear() + "-" + formatNumber(dateTime.getMonth() + 1) + "-" + formatNumber(dateTime.getDate()) + 
						"<br/>" + hours + ":" + minutes + ":" + seconds;
						/*if($("#unit").val()){
							var unit = $("#unit").val();
							if(unit == 1){
								tickLabel = dateTime.getFullYear();
							} else if(unit == 2){
								tickLabel = dateTime.getFullYear() + "/" + (dateTime.getMonth() + 1);
							} else if(unit == 3) {
								tickLabel = (dateTime.getMonth() + 1) + "/" + dateTime.getDate();
							} else if(unit == 4) {
								tickLabel = (dateTime.getMonth() + 1) + "/" 
									+ dateTime.getDate() + "<br/>" + dateTime.getHours() + ":" + dateTime.getMinutes();
							} else if(unit == 5) {
								tickLabel = (dateTime.getMonth() + 1) + "/" + dateTime.getDate() + "<br/>" 
									+ dateTime.getHours() + ":" + dateTime.getMinutes();
							}
						}*/
						return tickLabel;
					}
				},
				yaxis: { 
					min: 0,
					color: "#5181B4",
					tickColor: "grey",
					position: "left",
				},
				grid: {
					show: true,
					backgroundColor: { colors: ["#fff", "#eee"] },
					autoHighlight: false,
					hoverable: true,
					clickable: true
				},
				bars: {
					show: true,
					lineWidth: 0,
					fill: true,
					fillColor: { colors: [ { opacity: 0.8 }, { opacity: 0.1 } ] }
				},
				legend: { 
					position: 'ne',//label position "ne" or "nw" or "se" or "sw"
					backgroundOpacity: 0
					/*backgroundColor: "#eee",
					backgroundOpacity: 0.85,
					labelFormatter: function(label, series) {
						return '<a href="#' + label + '">' + label + '</a>';
					},*/
				}
		};
		$("#" + self.containerId).append(
				'<div id="title_' + id + '" class="well"><h4>' + id + '</h4></div>' + 
				'<div id="placeholder_' + id + 
				'" style="width:90%;height:200px;" class="staticGraph"></div>'
		);
		
		var plot = $.plot($("#placeholder_" + id), [ {data: data, label: "x = 0,y = 0"} ], options);
		
		//bind plot click event
		/*$("#placeholder_" + id).bind("plotclick",function(event, pos, item){
		    	alert("You clicked at " + pos.x + ", " + pos.y);
		    	if (item) {
		    		highlight(item.series, item.datapoint);
		    		alert("You clicked a point!");
		    	}
		    });*/
		
		//set legends label CSS
		var legends = $("#placeholder_" + id + " .legendLabel");
		legends.each(function () {
			$(this).css('backgroundColor', "#ccc");
			$(this).css('color', "green");
			$(this).css('font-size', "14px");
		});
		
		var updateLegendTimeout = null;
		var latestPosition = null;
		
		function updateLegend() {
			updateLegendTimeout = null;
			var pos = latestPosition;
			var axes = plot.getAxes();
			if (pos.x < axes.xaxis.min || pos.x > axes.xaxis.max ||
					pos.y < axes.yaxis.min || pos.y > axes.yaxis.max) {
				return;
			}
			var i, j, dataset = plot.getData();
			for (i = 0; i < dataset.length; ++i) {
				var series = dataset[i];
				// find the nearest points, x-wise
				for (j = 0; j < series.data.length; ++j) {
					if (series.data[j][0] > pos.x) {
						break;
					}
				}
				// now interpolate
				var x, y, p1 = series.data[j - 1], p2 = series.data[j];
	            if (p1 == null)
	                y = parseFloat(p2[1]);
	            else if (p2 == null)
	                y = parseFloat(p1[1]);
	            else
	                y = parseFloat(p1[1]) + (parseFloat(p2[1]) - 
	                		parseFloat(p1[1])) * (pos.x - parseFloat(p1[0])) / 
	                		(parseFloat(p2[0]) - parseFloat(p1[0]));
				var dateTime = new Date(Math.round(pos.x));
				var hours = formatNumber(dateTime.getHours());
				var minutes = formatNumber(dateTime.getMinutes());
				var seconds = formatNumber(dateTime.getSeconds());
				x = dateTime.getFullYear() + "-" + formatNumber(dateTime.getMonth() + 1) + "-" + formatNumber(dateTime.getDate()) + 
					" " + hours + ":" + minutes + ":" + seconds;
				y = y.toFixed(0);
				legends.eq(i).text("x = " + x + ",y = " + y);
			}
		}
		
		//bind plot hover event
		$("#placeholder_" + id).bind("plothover",function(event, pos, item){
			latestPosition = pos;
			if (!updateLegendTimeout) {
				updateLegendTimeout = setTimeout(updateLegend, 50);
			}
		});
	};
	
	function formatNumber (key) {
		key = key == 0 ? "00" : (key < 10 ? "0" + String(key) : key);
		return key;
	}
};