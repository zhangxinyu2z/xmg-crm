/**
department.js
*/
$(function() {
	// 1、抽取通用变量
	var departmentDatagrid, departmentDialog, departmentForm, managerList;
	departmentDatagrid = $("#department_datagrid");
	departmentDatagridEditAndDel = $("#department_datagrid_edit,#department_datagrid_del");
	departmentDialog = $("#department_dialog");
	departmentForm = $("#department_form");
	managerList = $("#managerList");


	// 页面加载完毕，填充表格数据
	departmentDatagrid.datagrid({
		url: '/department_list',
		rownumbers: true,/*显示一个行号列 */
		fit: true,
		fitColumns: true, /*列表自适应大小*/
		pagination: true,/*显示分页*/
		pageList: [1, 10, 20, 30, 40, 50],/*分页记录选择项*/
		singleSelect: true,/*只允许选择一行*/
		toolbar: '#department_datagrid_tb',/*顶部显示数据面板*/
		onClickRow: function(rowIndex, rowData) {
			// 部门停用后不允许编辑
			if(rowData.state) {
				departmentDatagridEditAndDel.linkbutton('enable');
			} else {
				departmentDatagridEditAndDel.linkbutton('disable');
				
			}
		},
		columns: [[
			{ field: 'sn', title: '部门编号', width: 1, align: 'center' },
			{ field: 'name', title: '部门名称', width: 1, align: 'center' },
			{ field: 'manager', title: '部门经理', width: 1, align: 'center', formatter: managerFormatter },
			{ field: 'parent', title: '上级部门', width: 1, align: 'center', formatter: departmentFormatter },
			{ field: 'state', title: '状态', width: 1, align: 'center', formatter: stateFormatter }
		]]
	});

	// 页面加载完毕，对话框弹窗就完成，只不过被closeed隐藏
	departmentDialog.dialog({
		width: 280,
		height: 300,
		closed: true,/*对话框关闭*/
		buttons: '#department_dialog_tb'/*底部按钮 */,
		onBeforeOpen: function() {
			$("#parentDept").combobox({
				valueField: 'id',
				textField: 'name',
				url: '/dep_queryDept'
			});
		}
	});

	// 在对话框栏显示经理栏员工信息
	managerList.combogrid({
		panelWidth: 450,
		idField: "id",
		rownumbers: true,/*显示一个行号列 */
		fitColumns: true, /*列表自适应大小*/
		pagination: true,/*显示分页*/
		textField: "username",
		url: '/employee_list',
		columns: [[
			{ field: "username", title: "用户名", width: 1,  align: 'center' },
			{ field: "realname", title: "真实姓名", width: 1, align: 'center' },
			{ field: "dept", title: "部门", width: 1, align: 'center', formatter: departmentFormatter },
			{ field: "state", title: "状态", width: 1, align: 'center', formatter: managerStateFormatter }
		]]

	})
		;
	// 2、将方法定义到对象中统一管理
	var cmdObj = {
		add: function() {
			departmentDialog.dialog("open");/*打开窗口*/
			departmentDialog.dialog("setTitle", "新增");
			departmentDialog.form("clear"); // 清空表单数据
		},

		edit: function() {
			// 获取选中行的数据
			var rowData = $("#department_datagrid").datagrid("getSelected");
			if (rowData) {
				$("#department_dialog").dialog("open");/*打开窗口*/
				$("#department_dialog").dialog("setTitle", "编辑");
				//$("#department_form").form("clear"); // 这种方式清空表单数据的同时会清空分页数据
				$("#department_form [name='id'],[name='sn'],[name='name']").val("");
				console.log(rowData);
				if (rowData.manager) {
					rowData["manager.id"] = rowData.manager.id;
				}
				if (rowData.parent) {
					rowData["parent.id"] = rowData.parent.id;
				}
				// 将选中的行数据填充到编辑列表中
				$("#department_form").form("load", rowData);
				console.log(rowData);
			} else {
				$.messager.alert("温馨提示", "请选择需要编辑的数据", "info");
			}
		},

		find: function() {;
			var searchdata = $("#depart_find").serialize
			console.log(searchdata);
			var keyword = $("[name='keyword']").val();
//			var state = $("select option:selected").val();
			var state=$("select[name='state']").val();

			// 传递参数，重新从服务器加载数据
			departmentDatagrid.datagrid("load", { keyword: keyword, state: state });
		},

		del: function() {
			// 获取选中行数据
			var rowData = departmentDatagrid.datagrid("getSelected");
			if (rowData) {
				$.messager.confirm("温馨提示", "您确认要停用该部门吗？", function(yes) {
					if (yes) {
						// 由于我这里只是改变部门的状态，而不是真的删除部门信息，所以还是update的操作
						$.get("/department_del?id=" + rowData.id, function(data) {
							if (data.success) {
								// 刷新表格数据
								$.messager.alert("温馨提示", data.msg, "info", function() {
									departmentDatagrid.datagrid('reload');
								});
							} else {
								$.messager.alert("温馨提示", data.msg, "info");
							}
						}, "json");
					}
				});
			} else {
				$.messager.alert("温馨提示", "请选中要停用的部门", "info");
			}
		},

		save: function() {
			var idVal = $("#department_form [name='id']").val();
			var url;
			// id从编辑对话框中获取，edit中取到表单数据填充，add没有
			if (idVal) {
				url = "/department_update";
			} else { // 如果没有值，说明是新增
				url = "/department_save"
			}

			departmentForm.form('submit', {
				url: url,
				success: function(data) {
					data = $.parseJSON(data);/*json格式的字符串转成json对象*/
					if (data.success) {
						$.messager.alert("温馨提示", data.msg, "info", function() {
							// 关闭对话框
							departmentDialog.dialog("close");
							// 刷新页面
							departmentDatagrid.datagrid('load');
						});
					} else {
						$.messager.alert("温馨提示", data.msg, "info");
					}
				}
			});
		},

		reload: function() {
			departmentDatagrid.datagrid("reload");
		},

		cancel: function() {
			departmentDialog.dialog("close");
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


// 返回经理名称
function managerFormatter(value, row, index) {
	if (value) {
		return value.username;
	} else {
		return "";
	}
}

// 返回部门名称
function departmentFormatter(value, row, index) {
	return value ? value.name : "";
}

// 返回部门状态
function stateFormatter(value, row, index) {
	if (value) {
		return "<font color='green'>正常</font>";
	} else {
		return "<font color='red'>停用</font>";
	}
}

// 返回员工状态
function managerStateFormatter(value, row, index) {
	if (value) {
		return "<font color='green'>在职</font>";
	} else {
		return "<font color='red'>离职</font>";
	}
}







