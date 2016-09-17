function TimedValue(value) {  this.time = new Date();  this.value = value;}function newFilledArray(len, val) {  var rv = new Array(len);  while (--len >= 0) {      rv[len] = val;  }  return rv;}function Indicator(id, name, initialValue, type, server) {  var initial = new TimedValue(initialValue);  this.id = id;  this.type = type;  this.name = name;  this.values = newFilledArray(350, initial);  this.opts = {};  this.server = server;  this.addValue = function(value) {    this.values = this.values.slice(1);    this.values.push(value);  };    this.setValue = function(value) {    this.values = newFilledArray(1, value);  };  this.rowId = function() {    return 'row_'+this.id;  };  this.render = function(color, count, index) {    if(isFiltered(this.rowId())) {      return ;    }    //add row if it not exist    if(!$("#" + this.rowId()).length && !isFiltered(this.rowId())){    	$('#graphs > table > tbody').append(    		'<tr id="'+this.rowId()+'" class="'+this.type+'">' +    		'<td width="2%" id="servers_'+id+'" class="tdOne"><span style="display:none;">'+type+'</span><span style="color: '+this.server.color+';">●&nbsp;</span></td>' +     		'<td width="35%" class="tdTwo"><a href="#" onclick="closeItem(\''+ this.rowId() +'\')">×</a>&nbsp;'+wordWrap(50,name)+'&nbsp</td>' +     		'<td width="18%" class="tdThree" id="value_'+id+'"></td>' +     		'<td width="10%" class="tdFour" id="delta_'+id+'"></td>' +     		'<td width="35%" class="spark tdFive" n="-1" id="'+id+'"><div id="'+id+'d" style="width:350px;float:right;">...</div></td>' +     		'</tr>'    	);    }      var oldCount = parseInt($('#'+this.id).attr("n"));    var composite = true;    if (oldCount < count) { //first redraw in this fetch pack      $('#'+this.id).attr('n', count);      $('#servers_'+this.id).empty();      composite = false;      }     var data = this.getData();        var value = this.values[this.values.length-1].value;        if(this.getType() == "piegram") {        var sign  = "";        var delta = "";                var dataObjs = eval("("+JSON.stringify(data)+")");        var dataTemp = "[";        for(var i = 0;i < dataObjs.length;i ++){        	if(dataObjs[i].data){        		dataTemp += dataObjs[i].data + ",";        	}        }        dataTemp = dataTemp.substring(0, dataTemp.length - 1);        dataTemp += "]";        if(dataTemp.length < 3){        	dataTemp = JSON.stringify(data);        }                $('#value_'+this.id).text(dataTemp);              	        $('#'+this.id+"d").css("width","200px");        $('#'+this.id+"d").css("height","150px");                $.plot($('#'+this.id+"d"), data,       	{      		series: {      			pie: {       				show: true,      				radius: 1,      				label: {      					show: true,      					radius: 3/4,      					formatter: function(label, series){      						var percentValue = Math.round(series.percent);      						if(label == 'free' && percentValue <=20) {			                	return '<div style="font-size:8pt;text-align:center;padding:2px;color:red;font-weight:bold">'+label+'<br/>'+percentValue+'%</div>';			                } else {			                    return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+label+'<br/>'+percentValue+'%</div>';			                }			      		},      					background: {       						opacity: 0.5,      						color: '#000'      					}      				}      			}      		},      		legend: {      			show: false      		}      	});      	        $('#delta_'+this.id).text('N/A');    } else {        var sign  = delta >= 0 ? "+" : "";        var delta = this.values[this.values.length-1].value - this.values[this.values.length-2].value;        $('#'+this.id+" > div").sparkline(data, {type: "line", width: "350px", height: "30px", lineColor: color, composite: composite, fillColor: "#cdf"});        $('#value_'+this.id).text(value);        $('#delta_'+this.id).text('('+sign+delta+')');    }        $('#servers_'+this.id).children().remove();    $('#servers_'+this.id).append('<span title="'+this.server.name+'" style="color: '+this.server.color+';">●&nbsp;</span>');        var serversAttr = $('#'+this.rowId()).attr('servers');    if (!serversAttr) {      serversAttr = '';    }    if (serversAttr.indexOf(this.server.id+',') < 0) {      $('#'+this.rowId()).attr('servers', serversAttr+this.server.id+',');    }  };}//remove indicatorvar itemFilters = [];function closeItem(id){  $("#"+id).remove();  itemFilters.push(id);}function isFiltered(id) {  var result = false;  $.map(itemFilters, function(n){    if(n == id) {      result = true;    }  });  return result;}function Counter(name, initialValue, server) {  var id = "c_"+name.replace(/[^a-zA-Z0-9]/g, "_") + server.id;  var me = new Indicator(id, name, initialValue, 'counter', server);  this.type = "c";  me.getData = function() {    var deltas = [];    for (var i=1; i < me.values.length; i++) { //(puke)      deltas.push(me.values[i].value - me.values[i-1].value);    };    return deltas;  };    me.getType = function() {    return "counter";  };  return me;}function Gauge(name, initialValue, server) {  var id = "g_"+name.replace(/[^a-zA-Z0-9]/g, "_") + server.id;  var me = new Indicator(id, name, initialValue, 'gauge', server);  me.getData = function() {    return $.map(me.values, function(timevalue){      return timevalue.value;    });  };    me.getType = function() {    return "gauge";  };    return me;  }function Piegram(name, initialValue, server) {  var id = "p_"+name.replace(/[^a-zA-Z0-9]/g, "_") + server.id;  var me = new Indicator(id, name, initialValue, 'piegram', server);  me.getData = function() {      //alert(this.values[0]);            return this.values[0];  };    me.getType = function() {    return "piegram";  };  return me;  }function wordWrap(textlength, strText){	var tem = "";	while(strText.length > textlength){		tem += strText.substr(0,textlength) + "<br/>";		strText=strText.substr(textlength, strText.length);	}	tem += strText;	return tem;}