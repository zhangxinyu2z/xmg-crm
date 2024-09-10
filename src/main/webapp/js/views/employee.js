$(function() {
	// 1、抽取通用变量
	var empDatagrid, empDatagridEditAndDel, empDialog, empForm;
	empDatagrid = $("#employee_datagrid");
	empDatagridEditAndDel = $("#employee_datagrid_edit,#employee_datagrid_del");
	empDialog = $("#employee_dialog");
	empForm = $("#employee_form");

	// 页面加载完毕，填充表格数据
	empDatagrid.datagrid({
		url: '/employee_list',
		rownumbers: true,/*显示一个行号列 */
		fit: true,
		fitColumns: true, /*列表自适应大小*/
		pagination: true,/*显示分页*/
		pageList: [1, 10, 20, 30, 40, 50],/*分页记录选择项*/
		singleSelect: true,/*只允许选择一行*/
		onClickRow: function(rowIndex, rowData) {
			console.log(rowData);
			if (rowData.state) {
				empDatagridEditAndDel.linkbutton('enable');
			} else {  // 用户状态离职，不可操作编辑和离职按钮
				empDatagridEditAndDel.linkbutton('disable');
			}
		},
		onLoadError: function(param) {
			// 这里有问题，如果用户权限不够就会无法加载数据，可以访问员工主页，但是却没有加载数据的权限
			console.log(param);
			alert("dada");
		},
		toolbar: '#employee_datagrid_tb',/*顶部显示数据面板*/
		columns: [[
			{ field: 'username', title: '员工账号', width: 1, align: 'center' },
			{ field: 'realname', title: '真实姓名', width: 1, align: 'center' },
			{ field: 'tel', title: '电话', width: 1, align: 'center' },
			{ field: 'email', title: '邮箱', width: 1, align: 'center' },
			{ field: 'dept', title: '部门', width: 1, align: 'center', formatter: deptFormatter },
			{ field: 'inputtime', title: '入职时间', width: 1, align: 'center' },
			{ field: 'state', title: '状态', width: 1, align: 'center', formatter: stateFormatter },
			{ field: 'admin', title: '是否超级管理员', width: 1, align: 'center', formatter: adminFormatter }
		]]
	});

	// 页面加载完毕，对话框弹窗就完成，只不过被closeed隐藏
	empDialog.show().dialog({
		width: 280,
		height: 300,
		closed: true,/*对话框关闭*/
		buttons: '#employee_dialog_tb'/*底部按钮 */
	});

	// 2、将方法定义到对象中统一管理
	var cmdObj = {
		add: function() {
			empDialog.dialog("open");/*打开窗口*/
			empDialog.dialog("setTitle", "新增");
			empForm.form("clear");/*清空表单数据*/
		},

		edit: function() {
			// 获取选中行的数据
			var rowData = empDatagrid.datagrid("getSelected");
			if (rowData) {
				empDialog.dialog("open");/*打开窗口*/
				empDialog.dialog("setTitle", "编辑");
				empForm.form("clear");/*清空表单数据*/
				// 特殊属性的处理
				if (rowData.dept) {
					rowData["dept.id"] = rowData.dept.id;

				}
				// 进行员工角色信息的回显：发送一个同步请求到后台获取员工对应的角色信息，如果异步请求就会直接执行下一步js代码，此时还没有响应数undefined
				var html = $.ajax({
					url: "/employee_queryRoleById?eid=" + rowData.id,
					async: false
				}).responseText;
				html = $.parseJSON(html);
				console.log(html);
				// 角色信息回显
				$("#emp_roles").combobox("setValues", html);

				// 员工信息回显
				empForm.form("load", rowData);
			} else {
				$.messager.alert("温馨提示", "请选择需要编辑的数据", "info");
			}
		},

		find: function() {
			var keyword = $("[name='keyword']").val();
			// 传递参数，重新从服务器加载数据
			empDatagrid.datagrid("load", { keyword: keyword });
			console.log(keyword);
		},

		del: function() {
			// 获取选中行数据
			var rowData = empDatagrid.datagrid("getSelected");
			console.log(rowData);
			if (rowData) {
				$.messager.confirm("温馨提示", "您确认要离职该员工吗？", function(yes) {
					if (yes) {
						$.get("/employee_delete?id=" + rowData.id, function(data) {
							if (data.success) {
								// 刷新表格数据
								$.messager.alert("温馨提示", data.msg, "info", function() {
									empDatagrid.datagrid('reload');
								});
							} else {
								$.message.alert("温馨提示", data.msg, "info");
							}
						}, "json");
					}
				});
			} else {
				$.messager.alert("温馨提示", "请选中要离职的员工", "info");
			}
		},

		save: function() {
			var idVal = $("#employee_form [name='id']").val();
			var url;
			if (idVal) {
				url = "/employee_update";
			} else { // 如果没有值，说明是新增
				url = "/employee_save"
			}
			console.log(idVal);
			empForm.form('submit', {
				url: url,
				onSubmit: function(param) {
					var roleIds = $("#emp_roles").combobox("getValues");
					for (var i = 0; i < roleIds.length; i++) {
						param["roles[" + i + "].id"] = roleIds[i];
					}
				},
				success: function(data) {
					data = $.parseJSON(data);/*json格式的字符串转成json对象*/
					if (data.success) {
						$.messager.alert("温馨提示", data.msg, "info", function() {
							// 关闭对话框
							empDialog.dialog("close");
							// 刷新页面
							empDatagrid.datagrid('load');
						});
					} else {
						$.messager.alert("温馨提示", data.msg, "info");
					}
				}
			});
		},
		fail:function() {
		},

		export: function() {
			$.messager.alert("温馨提示", "您确定要导出数据吗?", "info", function() {
				window.location.href = "/employee_export";
			})
		},
		reload: function() {
			empDatagrid.datagrid("reload");
		},

		cancel: function() {
			empDialog.dialog("close");
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







