var tree;
$(function () {
	//init the UI layout
	var pageLayout = $("#pageWrapper").layout({
		//applyDefaultStyles: true,
		north__size: 65,
		west__size:280,
		resizable: true,
		north__closable: false,
		west__closable: true,
		north__spacing_open: 5,
		onresize: function(){
		}
	});
	
	//init tree(callback方法调用不需写括号)
	var treeUri = "getChildrenNodesJson";
	tree = new Tree("tree", treeUri);
	tree.LoadDynamicTree(displayNodeInfo);
	
	// get zookeeper host info
	$.ajax({
        type : "POST",
        url : 'getZkHost',
        dataType : "text",
        success : function(data, textStatus, jqXHR) {
        	$("#ZkHost").val(data);
        },
        error : function(jqXHR, textStatus, errorThrown) {
        }
    });
	
	/*
	 * add click listener
	 */
	$("#openAllTree").click(function() {
		tree.openAll();
	});
	
	$("#closeAllTree").click(function() {
		tree.closeAll();
	});
	
	$("#refreshTree").click(function() {
		tree.refreshNode(0);
	});
	
	$("#createBtn").click(function() {
		createNode();
	});
	
	$("#deleteNode").click(function() {
		deleteNode();
	});
	
	$("#updateBtn").click(function() {
		updateNode();
	});
	
	$("#copyNodeBtn").click(function() {
		copyNode();
	});
	
	$("#exportNode").click(function() {
		exportNode();
	});
	
	$("#importFileBtn").click(function() {
		importFile();
	});
});

//展示选中节点信息
function displayNodeInfo(selectedNode) {
	$("#parentIdentifier").html(selectedNode.parentIdentifier);
	$("#currentIdentifier").html(selectedNode.identifier);
	$("#currentNodeKeyValue").html(selectedNode.nodeKey + " & " + selectedNode.nodeValue);
	$("#updateValue").val(selectedNode.nodeValue);
}

//添加节点
function createNode() {
	var nodeKey = $("#nodeKey").val().trim();
	var nodeValue = $("#nodeValue").val().trim();
	if (!tree.selectNode) {
		msgDialog.openMsg("Please select node.");
		return;
	} else if (!nodeKey || !nodeValue) {
		msgDialog.openMsg("Please input new node key and value.");
		return;
	}
	
	$.ajax({
        beforeSend: function(){
        	loadDialog.open('Please wait...');
        },
        type : "POST",
        url : 'createNode',
        dataType : "json",
        data : {
        	identifier : tree.selectNode.identifier,
        	nodeKey : nodeKey,
        	nodeValue : nodeValue,
			time : (new Date()).getTime()
        },
        success : function(data, textStatus, jqXHR) {
        	setTimeout(function(){loadDialog.close();}, 1000);
        	if (data["success"]) {
        		tree.refreshNode(0);
        	} else {
        		msgDialog.open(data["msg"], 450, 100);
        	}
        },
        error : function(jqXHR, textStatus, errorThrown) {
        	loadDialog.close();
        	msgDialog.openMsg("Create node error.");
        }
    });
}

//删除节点
var identifiers = "";
function deleteNode() {
	identifiers = "";
	var selecteNodes = $(".jstree-clicked");
	if (selecteNodes.size() < 1) {
		msgDialog.openMsg("Please select node.");
		return;
	} else {
		var flag = false;
		$.each(selecteNodes, function(index, data) {
			var identifier = data.parentNode.attributes["identifier"].nodeValue;
			identifiers += identifier + ";";
			if (identifier == "/") {
				flag = true;
			}
		});
		if (flag) {
			msgDialog.openMsg("The root node can not be deleted.");
			return;
		}
	}
	
	confirmDialog.initConfirm(ajaxDelete, undefined);
	confirmDialog.open("Confirm Delete?", 380, 140);
}

function ajaxDelete() {
	$.ajax({
		beforeSend: function(){
			loadDialog.openMsg('Please wait...');
		},
		type : "POST",
		url : 'deleteNode',
		dataType : "json",
		data : {
			identifiers : identifiers,
			time : (new Date()).getTime()
		},
		success : function(data, textStatus, jqXHR) {
			setTimeout(function(){loadDialog.close();}, 1000);
			if (data["success"]) {
				tree.refreshNode(2);
			} else {
				msgDialog.open(data["msg"], 450, 100);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			loadDialog.close();
			msgDialog.openMsg("Delete node error.");
		}
	});
}

//修改节点
function updateNode() {
	var nodeValue = $("#updateValue").val().trim();
	if (!tree.selectNode) {
		msgDialog.openMsg("Please select node.");
		return;
	} else if (!nodeValue) {
		msgDialog.openMsg("Please input node value.");
		return;
	}
	
	$.ajax({
        beforeSend: function(){
        	loadDialog.open('Please wait...');
        },
        type : "POST",
        url : 'updateNode',
        dataType : "json",
        data : {
        	identifier : tree.selectNode.identifier,
        	nodeValue : nodeValue,
			time : (new Date()).getTime()
        },
        success : function(data, textStatus, jqXHR) {
        	setTimeout(function(){loadDialog.close();}, 1000);
        	if (data["success"]) {
        		tree.refreshNode(1);
        	} else {
        		msgDialog.open(data["msg"], 450, 100);
        	}
        },
        error : function(jqXHR, textStatus, errorThrown) {
        	loadDialog.close();
        	msgDialog.openMsg("Update node error.");
        }
    });
}

//copy node
function copyNode() {
	var toIdentifier = $("#copyTo").val().trim();
	if (!tree.selectNode) {
		msgDialog.openMsg("Please select node.");
		return;
	} else if (!toIdentifier) {
		msgDialog.openMsg("Please input copy to identifier.");
		return;
	}
	
	$.ajax({
        beforeSend: function(){
        	loadDialog.open('Please wait...');
        },
        type : "POST",
        url : 'copyNode',
        dataType : "json",
        data : {
        	fromIdentifier : tree.selectNode.identifier,
        	toIdentifier: toIdentifier,
			time : (new Date()).getTime()
        },
        success : function(data, textStatus, jqXHR) {
        	setTimeout(function(){loadDialog.close();}, 1000);
        	if (data["success"]) {
        		tree.refreshNode(2);
        	} else {
        		msgDialog.open(data["msg"], 450, 100);
        	}
        },
        error : function(jqXHR, textStatus, errorThrown) {
        	loadDialog.close();
        	msgDialog.openMsg("Copy node error.");
        }
    });
}

/*
 * 导出节点，以文件方式保存，在此不能用ajax方式
 */
function exportNode() {
	identifiers = "";
	var selecteNodes = $(".jstree-clicked");
	if (selecteNodes.size() < 1) {
		msgDialog.openMsg("Please select node.");
		return;
	} else {
		$.each(selecteNodes, function(index, data) {
			var identifier = data.parentNode.attributes["identifier"].nodeValue;
			identifiers += identifier + ";";
		});
	}
	
	window.open("exportNode?identifiers=" + identifiers);
}

//导入节点文件
function importFile() {
	if (!tree.selectNode) {
		msgDialog.openMsg("Please select node.");
		return;
	} else if (!$("#importFile").val()) {
		msgDialog.openMsg("Please select file.");
		return;
	}
	
	loadDialog.open('Please wait...');
	$.ajaxFileUpload({
		url : 'importNode',
		secureuri : false,
		fileElementId : 'importFile',
		dataType : 'json',
		data : {
			identifier : tree.selectNode.identifier,
			time : (new Date()).getTime()
		},
		success : function(data, textStatus, jqXHR) {
			setTimeout(function(){loadDialog.close();}, 1000);
        	if (data["success"]) {
        		tree.refreshNode(0);
        	} else {
        		msgDialog.open(data["msg"], 450, 100);
        	}
		},
		error: function(jqXHR, textStatus, errorThrown){
			loadDialog.close();
        	msgDialog.openMsg("Import file error.");
		}
	});
}