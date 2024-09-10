$(function () {
    var attendanceDialog, attendanceDatagrid, attendanceDialogForm, attendanceDatagridBtn, attendanceForm_combogrid;
    attendanceDialog = $("#attendance_dialog");
    attendanceDatagrid = $("#attendance_datagrid");
    attendanceDialogForm = $("#attendance_dialog_form");
    attendanceDatagridBtn = $("#attendance_datagrid_btn a");
    attendanceForm_combogrid = $("#attendanceForm_combogrid");


    attendanceDatagrid.datagrid({
        url: "attendance_list",
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        rownumbers: true,
        pageList: [5, 10, 20],
        toolbar: "#attendance_datagrid_btn"
    });

    attendanceDialog.dialog({
        width: 300,
        height: 300,
        buttons: "#attendance_dialog_bb",
        closed: true

    });

    //名字下拉框
    attendanceForm_combogrid.combogrid({
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


    // 获取按钮的点击事件
    $("a").on("click", function () {

        var cmd = $(this).data("cmd");
        console.log(cmd);
        console.log(cmdObj[cmd])
        if (cmd) {
            cmdObj[cmd]();
        }
    });


    var cmdObj = {
        //导出到excel
        exportAttendance: function () {
            /*  $.messager.confirm("温馨提示", "导出考勤为Excel,确定执行操作?", function (yes) {
                  if (yes) {
                      // 发送请求去后插入签到时间
                      $.get("/attendance_export", function (data) {
                          if (data.success) {
                              var win = $.messager.progress({
                                  title:'请等待...',
                                  msg:"正在导出中..."
                              });
                              setTimeout(function(){
                                  $.messager.progress('close');
                              },3300)
                          //	$.messager.alert("温馨提示", data.msg, "info");
                          } else {
                              $.messager.alert("温馨提示", data.msg, "warning");
                          }
                      });
                  }
              });*/

            window.location.href = "/attendance_export";

        },


        //重置查询条件
        resetSearchForm: function () {
            $("#attendance_searchForm").form("clear");
        },

        // 导入考勤表
        loadSignIn: function () {
            $.messager.confirm("温馨提示", "将会覆盖当前记录,确定执行操作?", function (yes) {
                if (yes) {
                    // 发送请求去后插入签到时间
                    $.get("/attendance_updateAttendance", function (data) {
                        if (data.success) {
                            var win = $.messager.progress({
                                title: '请等待...',
                                msg: "正在导入中..."
                            });
                            setTimeout(function () {
                                $.messager.progress('close');
                            }, 3300)
                            //	$.messager.alert("温馨提示", data.msg, "info");
                            attendanceDatagrid.datagrid("reload");
                        } else {
                            $.messager.alert("温馨提示", data.msg, "warning");
                        }
                    });
                }
            });

        },


        add: function () {
            // 打开新增对话框
            attendanceDialog.dialog("open");
            // 设置标题为新增
            attendanceDialog.dialog("setTitle", "新增");
            // 清除表单的缓存数据
            $("#attendance_dialog_form input").val("");
        },

        edit: function () {
            // 返回选中的行对象
            var rowData = attendanceDatagrid.datagrid("getSelected");
            console.log(rowData);
            if (rowData) {
                // 打开编辑对话框
                attendanceDialog.dialog("open");
                // 打开编辑对话框
                attendanceDialog.dialog("setTitle", "编辑");
                // 清除表单的缓存数据
                attendanceDialogForm.form("clear");
                if (rowData.employee) {
                    rowData["employee.id"] = rowData.employee.id;
                }
                // 回显表单的数据
                attendanceDialogForm.form("load", rowData);

            } else {
                $.messager.alert("温馨提示", "请选中要编辑数据", "info");
            }
        },

        del: function () {
            // 返回选中的行对象
            var row = attendanceDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定要删除这个员工的考勤吗", function (yes) {
                    if (yes) {
                        // 发送请求去后台删除数据
                        $.get("/attendance_delete?id=" + row.id, function (data) {
                            if (data.success) {
                                // 删除成功后,重新加载数据
                                attendanceDatagrid.datagrid("reload");
                            } else {
                                $.messager.alert("温馨提示", data.msg, "warning");
                            }
                        });
                    }
                });

            } else {
                $.messager.alert("温馨提示", "请选中要删除的行!", "warning");
            }
        },


        /**
         * 刷新列表
         */
        refresh: function () {
            attendanceDatagrid.datagrid("reload");
        },


        /**
         * 高级查询:根据关键字,录入时间,员工状态查询
         */
        searchContent: function () {
            // 创建一个json空对象:存取要查询的条件
            var param = {};
            var paramArr = $("#attendance_searchForm").serializeArray();

            // 设置格式为 Object { keyword="",  beginDate="",  endDate=""}的json对象
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            // 根据查询的条件去重新查询后台,加载到前台中
            attendanceDatagrid.datagrid("load", param);
        },


        save: function () {
            var url = null;
            // 根据id,设置发送的是保存还是更新请求的地址
            var id = $("input[name=id]").val();
            console.log("id" + id);
            if (id) {
                url = "/attendance_update"
            } else {
                url = "/attendance_save"
            }

            // 表单的提交操作
            attendanceDialogForm.form("submit", {
                url: url,
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {
                        $.messager.alert("温馨提示", data.msg, "info");
                        attendanceDialog.dialog("close");
                        attendanceDatagrid.datagrid("reload");
                    } else {
                        $.messager.alert("温馨提示", data.msg, "warning");
                    }
                }
            });

        },
        cancel: function () {
            attendanceDialog.dialog("close");
        }


    };

});

// 显示员工的名称
function employeeFormatter(value, record, index) {
    //console.log(value); employee
//	console.log(record);  Attendance
    console.log("--------");
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



