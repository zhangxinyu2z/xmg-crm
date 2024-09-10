/**
role.js
*/
$(function() {
	// 1、抽取通用变量
	var roleDatagrid, roleDialog, roleForm, allPermissions, selfPermissions;
	roleDatagrid = $("#role_datagrid");
	roleDatagridEditAndDel = $("#role_datagrid_edit,#role_datagrid_del");
	roleDialog = $("#role_dialog");
	roleForm = $("#role_form");
	allPermissions = $("#allPermissions");
	selfPermissions = $("#selfPermissions");

	// 页面加载完毕，填充表格数据
	roleDatagrid.datagrid({
		url: '/role_list',
		rownumbers: true,/*显示一个行号列 */
		fit: true,
		fitColumns: true, /*列表自适应大小*/
		pagination: true,/*显示分页*/
		pageList: [1, 10, 20, 30, 40, 50],/*分页记录选择项*/
		singleSelect: true,/*只允许选择一行*/
		toolbar: '#role_datagrid_tb',/*顶部显示数据面板*/
		columns: [[
			{ field: 'sn', title: '角色编号', width: 1, align: 'center' },
			{ field: 'name', title: '角色名称', width: 1, align: 'center' }
		]]
	});

	// 页面加载完毕，对话框弹窗就完成，只不过被closeed隐藏
	roleDialog.dialog({
		width: 600,
		height: 450,
		closed: true,/*对话框关闭*/
		buttons: '#role_dialog_tb'/*底部按钮 */
	});

	// 所有权限列表
	allPermissions.datagrid({
		title: "所有权限",
		url: "/permission_list",
		rownumbers: true,
		fitColumns: true,
		width: 250,
		height: 300,
		pagination: true,
		singleSelect: true,
		// 用户双击一行触发
		onDblClickRow: function(rowIndex, rowData) {
			// 获取已有权限列表的所有数据
			var selfData = selfPermissions.datagrid("getRows");
			console.log(selfData);
			var flag = false;
			var index = -1;
			// 判断已有权限列表中是否有选中的这条记录
			for (var i = 0; i < selfData.length; i++) {
				// 如果已有权限中含有该权限	
				if (rowData.id == selfData[i].id) {
					flag = true;
					index = i;
					break;
				}
			}
			if (flag) {
				// 已经存在的权限，则高亮再次选中
				selfPermissions.datagrid("selectRow", index);
			} else {
				// 如果没有添加到已有权限列表
				selfPermissions.datagrid("appendRow", rowData);
			}
		},
		columns: [[
			{ field: 'name', title: '权限名称', width: 1, align: 'center' }
		]]
	});
	var pager1 = allPermissions.datagrid("getPager");
	pager1.pagination({
		showPageList: false,
		showRefresh: false,
		displayMsg: ''
	});

	// 已有权限列表
	selfPermissions.datagrid({
		title: "已有权限",
		rownumbers: true,
		fitColumns: true,
		width: 250,
		height: 300,
		singleSelect: true,
		// 已有权限双击移除权限
		onDblClickRow: function(rowIndex, rowData) {
			selfPermissions.datagrid("deleteRow", rowIndex);
		},
		columns: [[
			{ field: 'name', title: '权限名称', width: 1, align: 'center' }
		]]
	});



	// 2、将方法定义到对象中统一管理
	var cmdObj = {
		add: function() {
			roleDialog.dialog("open");/*打开窗口*/
			roleDialog.dialog("setTitle", "新增");
			// 清空默认打开的对话框数据，除了分页的数据外
			$("#role_form [name='id'],[name='sn'],[name='name']").val("");
			// 新增的角色没有右侧的已分配权限
			// 得到右侧所有的数据，循环遍历删除
			$("#selfPermissions").datagrid("loadData", { rows: [] });

		},

		edit: function() {
			// 获取选中行的数据
			var rowData = $("#role_datagrid").datagrid("getSelected");
			if (rowData) {
				$("#role_dialog").dialog("open");/*打开窗口*/
				$("#role_dialog").dialog("setTitle", "编辑");
				//$("#role_form").form("clear"); // 这种方式清空表单数据的同时会清空分页数据
				$("#role_form [name='id'],[name='sn'],[name='name']").val("");

				// 将选中的角色信息填充到编辑列表中，这里角色的信息和角色的权限做了分开处理
				$("#role_form").form("load", rowData);
				console.log(rowData);

				// 查询角色已有权限，显示到已有权限列中
				var options = selfPermissions.datagrid("options");
				options.url = "/permission_queryById";
				selfPermissions.datagrid("load", { rid: rowData.id });

				console.log(rowData);
			} else {
				$.messager.alert("温馨提示", "请选择需要编辑的数据", "info");
			}
		},

		find: function() {
			var keyword = $("[name='keyword']").val();
			// 传递参数，重新从服务器加载数据
			roleDatagrid.datagrid("load", { keyword: keyword });
			console.log(keyword);
		},

		del: function() {
			// 获取选中行数据
			var rowData = roleDatagrid.datagrid("getSelected");
			if (rowData) {
				$.messager.confirm("温馨提示", "您确认要删除该角色吗？", function(yes) {
					if (yes) {
						$.get("/role_del?id=" + rowData.id, function(data) {
							if (data.success) {
								// 刷新表格数据
								$.messager.alert("温馨提示", data.msg, "info", function() {
									roleDatagrid.datagrid('reload');
								});
							} else {
								$.messager.alert("温馨提示", data.msg, "info");
							}
						}, "json");
					}
				});
			} else {
				$.messager.alert("温馨提示", "请选中要删除的角色", "info");
			}
		},

		save: function() {
			var idVal = $("#role_form [name='id']").val();
			var url;
			// id从编辑对话框中获取，edit中取到表单数据填充，add没有
			if (idVal) {
				url = "/role_update";
			} else { // 如果没有值，说明是新增
				url = "/role_save"
			}

			roleForm.form('submit', {
				url: url,
				// 提交表单数据之前，将分配的权限id添加到提交参数中
				onSubmit: function(param) {
					var rowData = selfPermissions.datagrid("getRows");
					for (var i = 0; i < rowData.length; i++) {
						param["permissions[" + i + "].id"] = rowData[i].id;
					}
					console.log(param);
				},
				success: function(data) {
					data = $.parseJSON(data);/*json格式的字符串转成json对象*/
					if (data.success) {
						$.messager.alert("温馨提示", data.msg, "info", function() {
							// 关闭对话框
							roleDialog.dialog("close");
							// 刷新页面
							roleDatagrid.datagrid('load');
						});
					} else {
						$.messager.alert("温馨提示", data.msg, "info");
					}
				}
			});
		},

		reload: function() {
			roleDatagrid.datagrid("reload");
		},

		cancel: function() {
			roleDialog.dialog("close");
		}
	};

	// 3、对按钮进行统一监听
	$("a[data-cmd]").on("click", function() {
		var cmd = $(this).data("cmd");
		console.log(cmd);
		if (cmd) {
			cmdObj[cmd]();
		}
	});

});

// 显示dept的id对象的value
function deptFormatter(value, row, index) {
	return value ? value.name : value;
}

// 状态为1在职，状态为0离职
function stateFormatter(value, row, index) {
	if (value) {
		return "<font color='green'>在职</font>";
	} else {
		return "<font color='red'>离职</font>";
	}
}

// 1管理员，0普通
function adminFormatter(value, row, index) {
	return value ? "是" : "否";
}







