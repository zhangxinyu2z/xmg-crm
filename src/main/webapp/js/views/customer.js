/**
 *
 */
$(function () {
    var customerDatagrid, customerDialog, customerForm, transferDialog, transferForm;
    customerDatagrid = $("#customer_datagrid");
    customerDialog = $("#customer_dialog");
    customerForm = $("#customer_form");
    transferDialog = $("#transfer_dialog");
    transferForm = $("#transfer_form");

    customerDatagrid.datagrid({
        url: "/customer_list",
        rownumbers: true,
        fit: true, // 自适应容器，比如分页栏自动到bottom
        fitColumns: true,
        rownumbers: true,
        singleSelect: true,
        pagination: true,
        toolbar: "#customer_toolbar",
        columns: [[
            {field: 'name', title: '姓名', width: 1, align: 'center'},
            {field: 'age', title: '年龄', width: 1, align: 'center'},
            {field: 'gender', title: ' 性别', width: 1, align: 'center', formatter: genderFormatter},
            {field: 'tel', title: '电话号码', width: 1, align: 'center'},
            {field: 'email', title: '邮箱', width: 1, align: 'center'},
            {field: 'qq', title: 'qq', width: 1, align: 'center'},
            {field: 'wechat', title: '微信', width: 1, align: 'center'},
            {field: 'job', title: '职业', width: 1, align: 'center'},
            {field: 'salarylevel', title: '收入水平', width: 1, align: 'center'},
            {field: 'customersource', title: '客户来源', width: 1, align: 'center'},
            {field: 'inchargeuser', title: '负责人', width: 1, align: 'center', formatter: userFormatter},
            {field: 'inputuser', title: '创建人', width: 1, align: 'center', formatter: userFormatter},
            {field: 'status', title: '状态', width: 1, align: 'center', formatter: stateFormatter},
            //	{ field: 'becometime', title: '转正时间', width: 1, align: 'center' },
            {field: 'inputtime', title: '创建时间', width: 1, align: 'center'}
        ]]
    });


    customerDialog.show().dialog({
        width: 380,
        height: 400,
        closed: true,/*对话框关闭*/
        buttons: '#customer_dialog_tb'/*底部按钮 */
    });

    transferDialog.dialog({
        width: 400,
        height: 280,
        resizable: true,
        closed: true,/*对话框关闭*/
        buttons: "#transfer_tb"
    });


    var cmdObj = {
        add: function () {
            customerDialog.dialog("open");/*打开窗口*/
            customerDialog.dialog("setTitle", "新增");
            customerForm.form("clear"); // 清空表单数据
            customerForm.form("load", {"gender": "1"});

        },
        edit: function () {
            // 获取选中行的数据
            var rowData = customerDatagrid.datagrid("getSelected");
            if (rowData) {
                customerDialog.dialog("open");/*打开窗口*/
                customerDialog.dialog("setTitle", "编辑");
                customerForm.form("clear"); // 这种方式清空表单数据的同时会清空分页数据
                //$("#department_form [name='id'],[name='sn'],[name='name']").val("");
                console.log(rowData);
                if (rowData.gender) {
                    rowData["gender"] = 1;
                } else {
                    rowData["gender"] = 0;
                }
                // 将选中的行数据填充到编辑列表中
                customerForm.form("load", rowData);
            } else {
                $.messager.alert("温馨提示", "请选择需要编辑的数据", "info");
            }
        },
        save: function () {
            var idVal = $("#customer_form [name='id']").val();
            var url;
            // id从编辑对话框中获取，edit中取到表单数据填充，add没有
            if (idVal) {
                url = "/customer_update";
            } else { // 如果没有值，说明是新增
                url = "/customer_save"
            }

            customerForm.form('submit', {
                url: url,
                success: function (data) {
                    data = $.parseJSON(data);/*json格式的字符串转成json对象*/
                    if (data.success) {
                        $.messager.alert("温馨提示", data.msg, "info", function () {
                            // 关闭对话框
                            customerDialog.dialog("close");
                            // 刷新页面
                            customerDatagrid.datagrid('load');
                        });
                    } else {
                        $.messager.alert("温馨提示", data.msg, "info");
                    }
                }
            });
        },
        /*移交*/
        transfer: function () {
            // 返回选中的行对象
            var rowData = customerDatagrid.datagrid("getSelected");
            console.log(rowData);
            if (rowData) {
                // 打开编辑对话框
                transferDialog.dialog("open");
                // 打开编辑对话框
                transferDialog.dialog("setTitle", "移交");
                // 清除表单的缓存数据
                transferDialog.form("clear");
                if (rowData.id) {
                    rowData["customerId"] = rowData.id;
                }
                // 回显负责人的名称
                if (rowData.inchargeuser) {
                    rowData['oldSellerName'] = rowData.inchargeuser.username;
                }
                // 回显负责人的Id,目的是为了控制自己移交给自己
                if (rowData.inchargeuser) {
                    rowData['oldSellerId'] = rowData.inchargeuser.id;
                }
                // 回显表单的数据
                transferForm.form("load", rowData);
            } else {
                $.messager.alert("温馨提示", "请选中要移交的客户", "info");
            }
        },
        share: function () {
            var rowData = customerDatagrid.datagrid("getSelected");
            if (rowData) {
                transferDialog.dialog("open");/*打开窗口*/
                transferDialog.dialog("setTitle", "共享");
                transferForm.form("clear"); // 这种方式清空表单数据的同时会清空分页数据
                console.log(rowData);
                if (rowData.inchargeuser) {
                    rowData["oldSellerName"] = rowData.inchargeuser.username;
                    rowData["oldSellerId"] = rowData.inchargeuser.id;
                }
                if (rowData.id) {
                    rowData["customerId"] = rowData.id;
                }
                // 将选中的行数据填充到编辑列表中
                transferForm.form("load", rowData);
            } else {
                $.messager.alert("温馨提示", "请选择需要编辑的数据", "info");
            }
        },
        moveToResource: function () {
            // 返回选中的行对象
            var rowData = customerDatagrid.datagrid("getSelected");
            if (rowData) {
                $.messager.confirm("温馨提示", "您确认要把客户移入资源池吗?", function (yes) {
                    if(yes) {
                        $.get("/customer_moveResourcePool?id=" + rowData.id, function (data) {
                            if (data.success) {
                                $.messager.alert("温馨提示", data.msg, "info");
                                customerDatagrid.datagrid("reload");
                            } else {
                                $.messager.alert("温馨提示", data.msg, "info");
                            }
                        });
                    }
                })
            } else {
                $.messager.alert("温馨提示", "请选中要移入资源池的客户", "info");
            }
        },
        transferSave: function () {
            transferForm.form('submit', {
                url: "/shareOrTransferCustomer",
                success: function (data) {
                    data = $.parseJSON(data);/*json格式的字符串转成json对象*/
                    if (data.success) {
                        $.messager.alert("温馨提示", data.msg, "info", function () {
                            // 关闭对话框
                            transferDialog.dialog("close");
                            // 刷新页面
                            customerDatagrid.datagrid('load');
                        });
                    } else {
                        $.messager.alert("温馨提示", data.msg, "info");
                    }
                }
            });
        },
        transferCancel: function () {
            transferDialog.dialog("close");
        },
        lost: function () {
            // 获取选中行的数据
            var rowData = customerDatagrid.datagrid("getSelected");
            if (rowData) {
                $.messager.confirm("温馨提示", "您确认要标记此用户开发失败吗?", function (yes) {
                    $.get("/customer_lost?id=" + rowData.id, function (data) {
                        if (data.success) {
                            $.messager.alert("温馨提示", data.msg, "info");
                            customerDatagrid.datagrid("reload");
                        } else {
                            $.messager.alert("温馨提示", data.msg, "info");
                        }
                    });
                })
            } else {
                $.messager.alert("温馨提示", "请选中要操作的数据", "info");
            }
        },
        export: function () {
            $.messager.confirm("温馨提示", "您确认要导出用户表吗?", function (yes) {
                window.location.href = "/customer_export";
            })
        },
        search: function () {
            var keyword = $("[name='keyword']").val();
            var state = $("select[name='state']").val();

            // 传递参数，重新从服务器加载数据
            customerDatagrid.datagrid("load", {keyword: keyword, status: state});
        },
        cancel: function () {
            customerDialog.dialog("close");
        },
        refresh: function () {
            customerDatagrid.datagrid("reload");
        }
    };

    // 3、对按钮进行统一监听
    $("a[data-cmd]").on("click", function () {
        var cmd = $(this).data("cmd");
        console.log(cmd);
        if (cmd) {
            cmdObj[cmd]();
        }
    });
});

function genderFormatter(value, row, index) {
    return value ? "男" : "女";
}

function userFormatter(value, row, index) {
    if (value) {
        return value.username;
    }
}

function stateFormatter(value, row, index) {
    if (value == 1) {
        return "<font color='green'>正式客户</font>";
    } else if (value == -2) {
        return "<font color='red'>流失</font>";
    } else {
        return "";
    }
}
