$(function() {
	$("#knowledge_tree").tree({
		url : '/knowledgeMenu',
		onClick : function(node) {
			if (node.text == '客户来源') {
				$("#know_dialog").dialog("open");

			} else if (node.text == '怎么处理投诉') {
				$("#complaint_dialog").dialog("open");

			} else if (node.text == '关键时刻怎么应急') {
				$("#meet_dialog").dialog("open");
			}
			// 获取子子节点
			// console.debug($("#knowledge_tree").tree("getChildren",node.target));

		}
	});

	// 知识首页数据表格
	$("#knowledge_dadagrid").datagrid({
		url : '/know_list',
		toolbar : '#dadagrid_linknutton',
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			title : '编号'
		}, {
			field : 'context',
			title : '内容'
		}, ] ],
		onClickRow : function(rowIndex, rowData) {

			$.ajax({
				type : "POST",
				url : "/konw_listAll",
				data : "id=" + rowData.id,
				success : function(date) {
					$("#know2_dialog").dialog({
						title:'具体内容',
						width : 400,
						height : 300,
						content : date.context
					})
					
					$("#know2_dialog").dialog("open")
				}
			});
		},

	});
    
	$("#know_dialog").dialog({
		width : 500,
		height : 300,
		title : '知识分类',
		closed : true,
		buttons : '#know_linknutton'
	});

	$("#complaint_dialog").dialog({
		width : 500,
		height : 300,
		title : '知识分类',
		closed : true,
		buttons : '#complain_linknutton'
	});

	$("#meet_dialog").dialog({
		width : 500,
		height : 300,
		title : '知识分类',
		closed : true,
		buttons : '#knowledge_linknutton'
	});

	var objCmd = {

		cancelCoun : function() {
			$("#know_dialog").dialog("close")
		},
		cancelCou : function() {
			$("#complaint_dialog").dialog("close")
		},
		cancelCount : function() {
			$("#meet_dialog").dialog("close")
		},

		// 全文检索按钮
		searchCount : function() {
			// 创建一个json空对象:存取要查询的条件
			var param = {};

			// 获取表单中所有的数据的数组 [Object { name="keyword", value=""}, Object
			// { name="beginDate", value=""}, Object { name="endDate",
			// value=""}]
			var paramArr = $("#know_from").serializeArray();

			// 设置格式为 Object { keyword="", beginDate="",
			// endDate=""}的json对象
			for ( var i = 0; i < paramArr.length; i++) {
				param[paramArr[i].name] = paramArr[i].value;
				// (paramArr[i].name+"==="+paramArr[i].value)
			}
			// 根据查询的条件去重新查询后台,加载到前台中
			$("#knowledge_dadagrid").datagrid("load", param);
			console.debug(param);
		},
	};

	$("a[data-cmd]").on("click", function() {
		var cmd = $(this).data("cmd");
		objCmd[cmd]();
	})
})