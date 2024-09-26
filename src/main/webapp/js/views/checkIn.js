$(function () {
    var checkInDialog, checkInDatagrid, checkInDialogForm, checkInDatagridBtn, checkInForm_combogrid;
    checkInDialog = $("#checkIn_dialog");
    checkInDatagrid = $("#checkIn_datagrid");
    checkInDialogForm = $("#checkIn_dialog_form");
    checkInDatagridBtn = $("#checkIn_datagrid_btn a");
    checkInForm_combogrid = $("#checkInForm_combogrid");


    checkInDatagrid.datagrid({
        url: "/checkIn_list",
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        rownumbers: true,
        pageList: [5, 10, 20],
        toolbar: "#checkIn_datagrid_btn",
        /* columns: [[
             {field: "employee", title: "用户姓名", width: 1, align: "center",formatter:employeeFormatter},
             {field: "userip", title: "签到IP", width: 1, align: "center"},
             {field: "signintime", title: "签到时间", width: 1, align: "center", formatter: signInFormatter},
             {field: "signouttime", title: "签退时间", width: 1, align: "center", formatter: signOutFormatter},
             {field: "state", title: "状态", width: 1, align: "center", formatter: stateFormatter},
             {field: "checkman", title: "补签人", width: 1, align: "center",formatter:employeeFormatter},
             {field: "checktime", title: "补签时间", width: 1, align: "center"},
         ]],*/

    });

    //名字下拉框
    checkInForm_combogrid.combogrid({
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

    checkInDialog.dialog({
        width: 300,
        height: 230,
        buttons: "#checkIn_dialog_bb",
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
        //    补签
        check: function () {
            // 打开对话框
            var rowData = checkInDatagrid.datagrid("getSelected");
            // console.log(rowData);
            if (rowData) {
                checkInDialog.dialog("open");
                checkInDialog.dialog("setTitle", "补签窗口");
                checkInDialogForm.form("clear");
                // 回显
                if (rowData.employee) {
                    rowData["employee.id"] = rowData.employee.id;
                    // console.log(rowData);
                }

                // 数据回显
                checkInDialogForm.form("load", rowData);

            } else {
                //没有选中数据   新增一条
                checkInDialog.dialog("open");
                checkInDialog.dialog("setTitle", "新增补签窗口");
                checkInDialogForm.form("clear");

            }
        },


        //    签退
        signOut: function () {
            $.messager.confirm("温馨提示", "亲,确定要签退吗?", function (yes) {
                if (yes) {
                    // 发送请求去后插入签退时间
                    $.get("/checkIn_signOut", function (data) {
                        if (data.success) {
                            $.messager.alert("温馨提示", data.msg, "info");
                            checkInDatagrid.datagrid("reload");
                        } else {
                            $.messager.alert("温馨提示", data.msg, "warning");
                        }
                    });
                }
            });
        },

        //      签到
        signIn: function () {
            $.messager.confirm("温馨提示", "亲,一天只能签到一次! 确定 ?", function (yes) {
                if (yes) {
                    // 发送请求去后插入签到时间
                    $.get("/checkIn_signIn", function (data) {
                        if (data.success) {
                            $.messager.alert("温馨提示", data.msg, "info");
                            // 删除成功后,重新加载数据
                            checkInDatagrid.datagrid("reload");
                        } else {
                            $.messager.alert("温馨提示", data.msg, "warning");
                        }
                    });
                }
            });
        },

        //    刷新列表
        refresh: function () {
            checkInDatagrid.datagrid("reload");
        },
        /**
         * 高级查询:根据关键字,签到/签退和状态查询
         */
        searchContent: function () {
            // 创建一个json空对象:存取要查询的条件
            var param = {};
            // 获取表单中所有的数据的数组 [Object { name="keyword",  value=""}, 
            //Object {name="beginDate",  value=""}, Object { name="endDate",  value=""}]
            var paramArr = $("#checkIn_searchForm").serializeArray();

            // 设置格式为 Object { keyword="",  beginDate="",  endDate=""}的json对象
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            // 根据查询的条件去重新查询后台,加载到前台中
            checkInDatagrid.datagrid("load", param);

        },

        resetSearchForm: function () {
            $("#checkIn_searchForm").form("clear");
        },

        //   补签保存
        save: function () {
            //var url = null;
            // 根据id,设置发送的是保存还是更新请求的地址
            var id = $("input[name=id]").val();
            /* if (id) {
                  url = "checkIn_update"
              } else {
                  url = "checkIn_save"
              }*/
            // 表单的提交操作
            checkInDialogForm.form("submit", {
                url: "/checkIn_checkIn",
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {
                        $.messager.alert("温馨提示", data.msg, "info");
                        checkInDialog.dialog("close");
                        checkInDatagrid.datagrid("reload");
                    } else {
                        $.messager.alert("温馨提示", data.msg, "warning");
                    }
                }
            });
        },

        //  取消按钮
        cancel: function () {
            checkInDialog.dialog("close");
        }


    };

});

// 显示员工的状态:
function stateFormatter(value, record, index) {
    if (value == 1) {
        return "<font color='green'>正常</font>";
    } else if (value == 2) {
        return "<font color='red'>迟到</font>";
    } else if (value == 3) {
        return "<font color='red'>早退</font>";
    } else if (value == 4) {
        return "<font color='red'>迟到+早退</font>";
    } else {
        return "<font color='red'>请假</font>";
    }
}

// 显示签到栏的颜色:
function signInFormatter(value, record, index) {
    if (value) {

        var date = new Date(value);
        var hour = date.getHours();
        var minutes = date.getMinutes();
        // console.log(hour)
        //console.log(minutes)
        if (hour < 8) {
            return value;
        } else if (hour == 8 && minutes <= 30) {
            return value;
        } else {
            return "<font color='red'>" + value + "</font>";
        }

    }
}

// 显示签退栏的颜色:
function signOutFormatter(value, record, index) {
    if (value) {

        var date = new Date(value);
        var hour = date.getHours();
        //console.log(hour)
        var minutes = date.getMinutes();
        if (hour > 17) {
            return value;
        } else if (hour == 17 && minutes >= 30) {
            return value;
        } else {
            return "<font color='red'>" + value + "</font>";
        }
    }
}


// 显示员工的名称
function employeeFormatter(value, record, index) {
    //console.log("222");
    //console.log(arguments);
    if (value) {
        return value.username;

    } else {
        return null;
    }
}

// 补签人的名称
function checkmanFormatter(value, record, index) {
    //console.log(arguments);
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


