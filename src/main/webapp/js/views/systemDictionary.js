$(function() {
	var systemDictionary_datagrid, systemDictionaryItem_datagrid, systemDictionaryItem_dialog, systemDictionaryItem_form;
	systemDictionary_datagrid = $("#systemDictionary_datagrid");
	systemDictionaryItem_datagrid = $("#systemDictionaryItem_datagrid");
	systemDictionaryItem_dialog = $("#systemDictionaryItem_dialog");
	systemDictionaryItem_form = $("#systemDictionaryItem_form");


	systemDictionary_datagrid.datagrid({
		url: '/systemDictionaryList',
		rownumbers: true,/*显示一个行号列 */
		fit: true,
		fitColumns: true, /*列表自适应大小*/
		pagination: true,/*显示分页*/
		pageList: [1, 10, 20, 30, 40, 50],/*分页记录选择项*/
		singleSelect: true,/*只允许选择一行*/
		onClickRow: function(rowIndex, rowData) {
			/*systemDictionaryItem_datagrid.datagrid({
				toolbar: "#systemDictionaryItem_tb",
			});*/
//			systemDictionaryItem_datagrid.datagrid("loadData", { total: 0, rows: [] });
		/*
		想要实现的效果是：只有点击事件发生，item的toolbar才会出现
			如果使用datagrid添加，会请求两次，第一次请求携带的是上一次的参数
		*/
		//	systemDictionaryItem_datagrid.datagrid("options")["toolbar"] = "#systemDictionaryItem_tb";
			systemDictionaryItem_datagrid.datagrid("load", { "id": rowData.id });
		},
		columns: [[
			{ field: 'sn', title: '字典编号', width: 1, align: 'center' },
			{ field: 'name', title: '字典名称', width: 1, align: 'center' },
			{ field: 'intro', title: '字典简介', width: 1, align: 'center' }
		]]
	});

	systemDictionaryItem_datagrid.datagrid({
		url: "/querySystemDictionaryItemById",
		rownumbers: true,/*显示一个行号列 */
		fit: true,
		fitColumns: true, /*列表自适应大小*/
		pagination: true,/*显示分页*/
		pageList: [1, 10, 20, 30, 40, 50],/*分页记录选择项*/
		singleSelect: true,/*只允许选择一行*/
		toolbar: "#systemDictionaryItem_tb",
		columns: [[
			{ field: 'id', title: '字典明细编号', width: 1, align: 'center' },
			{ field: 'name', title: '字典明细名称', width: 1, align: 'center' },
			{ field: 'intro', title: '字典明细简介', width: 1, align: 'center' },
			{ field: 'state', title: '字典明细状态', width: 1, align: 'center', formatter: stateFormatter },
			{ field: 'parent', title: '字典名称', width: 1, align: 'center', formatter: dictionaryFormatter }
		]]
	});

	systemDictionaryItem_dialog.dialog({
		width: 350,
		height: 360,
		closed: true,/*对话框关闭*/
		buttons: '#dictionaryItem_b'/*底部按钮 */
	});


	var cmdObj = {
		add: function() {
			systemDictionaryItem_dialog.dialog("setTitle", "新增");
			systemDictionaryItem_dialog.dialog("open");
			systemDictionaryItem_form.form("clear");
			// 对字典项添加新明细时，默认回显当前字典项
			var rowData = systemDictionary_datagrid.datagrid("getSelected");
			systemDictionaryItem_form.form("load", { "parent.id": rowData.id });

		},
		edit: function() {
			var rowData = systemDictionaryItem_datagrid.datagrid("getSelected");
			if (rowData) {
				systemDictionaryItem_dialog.dialog("setTitle", "编辑");
				systemDictionaryItem_dialog.dialog("open");
				if (rowData.parent) {
					rowData["parent.id"] = rowData.parent.id;
				}
				// 获取当前行数据，填充到表格中
				systemDictionaryItem_form.form("load", rowData);
			} else {
				$.messager.alert("温馨提示", "请选择需要编辑的数据", "info");
			}

		},
		save: function() {
			// 判断id是否有值，新增的操作没有回显，所以不会有id
			var idVal = $("#systemDictionaryItem_form [name='id']").val();
			var url;
			if (idVal) {
				url = "/sysDic_update"
			} else {
				url = "/sysDic_add";
			}
			systemDictionaryItem_form.form("submit", {
				url: url,
				success: function(data) {
					data = $.parseJSON(data);

					if (data.success) {
						$.messager.alert("温馨提示", data.msg, "info");
						systemDictionaryItem_dialog.dialog("close");
						// 刷新页面
						systemDictionaryItem_datagrid.datagrid('reload');
					} else {
						$.messager.alert("温馨提示", data.msg, "info");
					}
				}
			});
		},

		del: function() {
			var rowData = systemDictionaryItem_datagrid.datagrid("getSelected");
			if (rowData) {
				if (!rowData.state) { // false 代表正常
					// 禁用
					$.messager.confirm("温馨提示", "您确认要禁用该字典明细吗？", function(yes) {
						if (yes) {
							$.get("/sysDic_forbidden?id=" + rowData.id, function(data) {
								if (data.success) {
									$.messager.alert("温馨提示", data.msg, "info");
									systemDictionaryItem_dialog.dialog("close");
									// 刷新页面
									systemDictionaryItem_datagrid.datagrid('reload');
								} else {
									$.messager.alert("温馨提示", data.msg, "info");
								}
							});
						}
					});
				} else {
					// 启用
				}
			} else {
				$.messager.alert("温馨提示", "请选中要禁用的条目", "info");
			}
		},
		reload: function() {
			systemDictionaryItem_datagrid.datagrid("reload");
		},
		cancel: function() {
			systemDictionaryItem_dialog.dialog("close");
		}
	}


	// 3、对按钮进行统一监听
	$("a[data-cmd]").on("click", function() {
		var cmd = $(this).data("cmd");
		if (cmd) {
			cmdObj[cmd]();
		}
	});
});

function stateFormatter(value, row, index) {
	// 根据情况判断，我处理的默认0是正常
	if (!value) {
		return "<font color='green'>正常</font>";
	} else {
		return "<font color='red'>禁用</font>";
	}
}

function dictionaryFormatter(value, row, index) {
	if (value) {
		return value.name;
	} else {
		return "";
	}
}