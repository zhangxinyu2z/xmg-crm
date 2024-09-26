/**
 * 
 */
$(function() {
	var customerTransferDatagrid, customerTransferDialog, transferForm, customerCombogrid, newsellerCombogrid;
	customerTransferDatagrid = $("#customerTransfer_datagrid");
	customerTransferDialog = $("#transfer_dialog");
	transferForm = $("#transfer_form");
	customerCombogrid = $("#customerCombogrid");
	newsellerCombogrid = $("#newsellerCombogrid");

	customerTransferDatagrid.datagrid({
		url: "/customerTransfer_list",
		fit: true, // 自适应容器，比如分页栏自动到bottom
		fitColumns: true,
		rownumbers: true,
		singleSelect: true,
		pagination: true,
		toolbar: "#customerTransfer_toolbar",
		columns: [[
			{ field: 'customer', title: '客户名称', width: 1, align: 'center', formatter: customerFormatter },
			{ field: 'transtime', title: '移交时间', width: 1, align: 'center' },
			{ field: 'oldseller', title: '原市场专员', width: 1, align: 'center', formatter: userFormatter },
			{ field: 'newseller', title: '新市场专员', width: 1, align: 'center', formatter: userFormatter },
			{ field: 'transreason', title: '移交原因', width: 1, align: 'center' },
			{ field: 'transuser', title: '操作人', width: 1, align: 'center', formatter: userFormatter }
		]]
	});


	customerTransferDialog.dialog({
		width: 400,
		height: 400,
		closed: true,/*对话框关闭*/
		buttons: "#transfer_tb"
	});

	// 查询正式客户信息
	customerCombogrid.combogrid({
		panelWidth: 400,
		editable: false, // 禁止输入
		idField: "id",
		rownumbers: true,/*显示一个行号列 */
		fitColumns: true, /*列表自适应大小*/
		pagination: true,/*显示分页*/
		textField: "name",
		url: '/official_customer_list',
		columns: [[
			{ field: "name", title: "客户姓名", width: 1, align: 'center' },
			{ field: "inchargeruser", title: "负责人", width: 1, align: 'center', formatter: userFormatter },
		]]
	});

	// 市场专员
	newsellerCombogrid.combogrid({
		panelWidth: 400,
		editable: false, // 禁止输入
		idField: "id",
		rownumbers: true,/*显示一个行号列 */
		fitColumns: true, /*列表自适应大小*/
		pagination: true,/*显示分页*/
		textField: "username",
		url: '/listCommissionerEmployees',
		columns: [[
			{ field: "username", title: "专员姓名", width: 1, align: 'center' }
		]]
	});

	var cmdObj = {
		add: function() {
			customerTransferDialog.dialog("open");/*打开窗口*/
			customerTransferDialog.dialog("setTitle", "创建客户移交");
			transferForm.form("clear"); // 清空表单数据
		},

		transfersave: function() {
			transferForm.form('submit', {
				url: "/customer_transfer", // 和潜在客户模块中的移交功能一样
				onSubmit: function(param) {
					var g = customerCombogrid.combogrid('grid');
					var r = g.datagrid('getSelected');
					param["oldseller.id"] = r.inchargeruser.id;
				},
				success: function(data) {
					data = $.parseJSON(data);/*json格式的字符串转成json对象*/
					if (data.success) {
						$.messager.alert("温馨提示", data.msg, "info", function() {
							// 关闭对话框
							customerTransferDialog.dialog("close");
							// 刷新页面
							customerTransferDatagrid.datagrid('load');
						});
					} else {
						$.messager.alert("温馨提示", data.msg, "info");
					}
				}
			});
		},
		transfercancel: function() {
			customerTransferDialog.dialog("close");
		},
		search: function() {
			var customername = $("[name='customername']").val();
			console.log(customername);
			var begintime = $("input[name='begintime']").val();
			var endtime = $("input[name='endtime']").val();
			console.log(begintime);
			// 传递参数，重新从服务器加载数据
			customerTransferDatagrid.datagrid("load", { customername: customername, begintime: begintime, endtime: endtime });
		},
		cancel: function() {
			customerTransferDialog.dialog("close");
		},
		refresh: function() {
			customerTransferDatagrid.datagrid("reload");
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

function customerFormatter(value, index, row) {
	return value ? value.name : "";
}

function userFormatter(value, index, row) {
	return value ? value.username : "";

}
