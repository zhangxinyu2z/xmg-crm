$(function () {
    var vacateDialog, vacateDatagrid, vacateDialogForm, vacateDatagridBtn, vacateForm_combogrid;
    vacateDialog = $("#vacate_dialog");
    vacateDatagrid = $("#vacate_datagrid");
    vacateDialogForm = $("#vacate_dialog_form");
    vacateDatagridBtn = $("#vacate_datagrid_btn a");
    vacateForm_combogrid = $("#vacateForm_combogrid");

    vacateDatagrid.datagrid({
        url: "/vacate_list",
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        rownumbers: true,
        pageList: [5, 10, 20],
        toolbar: "#vacate_datagrid_btn",

    });

    //名字下拉框
    vacateForm_combogrid.combogrid({
        panelWidth: 400,
        idField: 'id',
        textField: 'username',
        url: '/manager_list',
        method: 'get',
        columns: [[
            {field: "username", title: "用户名", width: 80},
            {field: "realname", title: "真实姓名", width: 80},
            {field: "email", title: "邮箱", width: 120},
            {field: "dept", title: "部门", width: 80, formatter: departmentFormatter},
        ]]
    })

    vacateDialog.dialog({
        width: 300,
        height: 230,
        buttons: "#vacate_dialog_bb",
        closed: true

    });

    // 获取按钮的点击事件
    $("a").on("click", function () {

        var cmd = $(this).data("cmd");

        if (cmd) {
            cmdObj[cmd]();
        }
    });

    var cmdObj = {
        add: function () {
            // 打开新增对话框
            vacateDialog.dialog("open");
            // 设置标题为新增
            vacateDialog.dialog("setTitle", "新增");
            // 清除表单的缓存数据
            $("#vacate_dialog_form").form("clear");
        },
        edit: function () {
            // 打开对话框
            var rowData = vacateDatagrid.datagrid("getSelected");
            if (rowData) {
                vacateDialog.dialog("open");
                vacateDialog.dialog("setTitle", "编辑窗口");
                vacateDialogForm.form("clear");
                // 回显
                if (rowData.employee) {
                    rowData["employee.id"] = rowData.employee.id;
                }
                // 数据回显
                vacateDialogForm.form("load", rowData);

            } else {
                $.messager.alert("温馨提示", "请选中要编辑请假单", "info");
            }
        },

        //删除
        del: function () {
            // 返回选中的行对象
            var row = vacateDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定要删除该请假单吗", function (yes) {
                    if (yes) {
                        // 发送请求去后台删除数据
                        $.get("/vacate_delete?id=" + row.id, function (data) {
                            if (data.success) {
                                $.messager.alert("温馨提示", data.msg, "info");
                                vacateDatagrid.datagrid("reload");
                            } else {
                                $.messager.alert("温馨提示", data.msg, "warning");
                            }
                        });
                    }
                });
            } else {
                $.messager.alert("温馨提示", "请选中要删除的请假单", "info");
            }
        },

        //审核
        audit: function () {
            var row = vacateDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "确定审核该请假单?", function (yes) {
                    if (yes) {
                        // 发送请求去审核
                        $.get("/vacate_audit?id=" + row.id, function (data) {
                            if (data.success) {
                                $.messager.alert("温馨提示", data.msg, "info");
                                vacateDatagrid.datagrid("reload");
                            } else {
                                $.messager.alert("温馨提示", data.msg, "warning");
                            }
                        });
                    }
                });
            } else {
                $.messager.alert("温馨提示", "请选中要审核的请假单", "info");
            }
        },

        save: function () {
            var url = null;
            // 根据id,设置发送的是保存还是更新请求的地址
            var id = $("input[name=id]").val();
            console.log("id:" + id);
            if (id) {
                url = "/vacate_update"
            } else {
                url = "/vacate_save"
            }

            // 表单的提交操作
            vacateDialogForm.form("submit", {
                url: url,
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {
                        $.messager.alert("温馨提示", data.msg, "info");
                        vacateDialog.dialog("close");
                        vacateDatagrid.datagrid("reload");
                    } else {
                        $.messager.alert("温馨提示", data.msg, "warning");
                    }
                }
            });

        },

        //    刷新列表
        refresh: function () {
            vacateDatagrid.datagrid("reload");
        },

        searchContent: function () {
            var param = {};
            var paramArr = $("#vacate_searchForm").serializeArray();
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            vacateDatagrid.datagrid("load", param);
        },

        resetSearchForm: function () {
            $("#vacate_searchForm").form("clear");

        },

        //  取消按钮
        cancel: function () {
            vacateDialog.dialog("close");
        }
    };

});

// 显示员工的状态:
function stateFormatter(value, record, index) {
    if (value == 0) {
        return "<font color='green'>待审核</font>";
    } else if (value == 1) {
        return "<font color='red'>已审核</font>";
    }
}

// 显示员工的名称
function employeeFormatter(value, record, index) {
    if (value) {
        return value.username;

    } else {
        return null;
    }
}

// 审核人的名称
function auditFormatter(value, record, index) {
    if (value) {
        return value.username;
    } else {
        return null;
    }
}

function departmentFormatter(value, record, index) {
    if (value) {
        return value.name;
    } else {
        return value;
    }
}