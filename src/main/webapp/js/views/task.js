$(function () {
    var mytable, myfromdialog, myform, handle_dialog, handle_form;
    mytable = $("#task_tb");
    myfromdialog = $("#task_dialog");
    myform = $("#taskform");
    changeButton = $("#task_tabletools  a ");
    handle_dialog = $("#handle_dialog");
    handle_form = $("#handle_form");

    mytable.datagrid({
        url: "/task_list",
        fitColumns: true,
        fit: true,
        striped: true,
        rownumbers: true,
        selectOnCheck: true,
        checkOnSelect: true,
        pagination: true,
        singleSelect: false,
        pageSize: 50,
        toolbar: "#task_tabletools",
        columns: [[
            {field: 'ck', checkbox: true, width: 1, align: 'center'},
            {field: 'start', title: '任务开始日期', width: 1, align: 'center'},
            {field: 'end', title: '任务结束日期', width: 1, align: 'center'},
            {field: 'handleuser', title: '员工姓名', width: 1, align: 'center', formatter: empFormatter},
            {field: 'title', title: '任务描述', width: 1, align: 'center', formatter: desFormatterLength},
            {field: 'remark', title: '处理描述', width: 1, align: 'center', formatter: desFormatterLength},
            {
                field: 'status', title: '状态', width: 1, align: 'center', formatter: function (value, row, index) {
                    if (value == 0) {
                        return "<font color='blue'>初始状态</font>"
                    } else if (value == 1) {
                        return "<font color='orange'>成功</font>"
                    } else if (value == 2) {
                        return "<font color='red'>失败</font>"
                    }
                }
            }
        ]],
        view: detailview,
        detailFormatter: function (rowIndex, rowData) {
            return '<table><tr>' +
                '<td colspan="3" style="border:0">详细任务描述:</td>' +
                '<td style="border:0">' +
                '<p> <font  color="blue">' + rowData.title + '</font></p>' +
                '</td>' +
                '</tr> <tr><td colspan="3" style="border:0">详细处理描述:</td> <td style="border:0" ><p><font color="green">' + rowData.title + '</font></p></td ></tr></table>';
        },
        onDblClickRow: function (rowIndex, rowData) {
            mytable.datagrid("expandRow", rowIndex);
        },
        onClickRow: function (rowIndex, rowData) {
            //判断.
            console.log(rowData);
            console.log($("#task_tabletools a").eq(0));
            if (rowData.status == 1 || rowData.status == 2) {
                //标记的变灰,
                $("#task_tabletools a").eq(1).linkbutton("disable");
                $("#task_tabletools a").eq(2).linkbutton("disable");
                $("#task_tabletools a").eq(3).linkbutton("disable");
                $("#task_tabletools a").eq(4).linkbutton("disable");
                $("#task_tabletools a").eq(5).linkbutton("disable");
                $("#task_tabletools a").eq(6).linkbutton("disable");
            } else if (rowData.status == 0) {
                $("#task_tabletools a").eq(1).linkbutton("enable");
                $("#task_tabletools a").eq(2).linkbutton("enable");
                $("#task_tabletools a").eq(3).linkbutton("enable");
                $("#task_tabletools a").eq(4).linkbutton("enable");
                $("#task_tabletools a").eq(5).linkbutton("enable");
                $("#task_tabletools a").eq(6).linkbutton("enable");
            }

        },
    });

    //对话框的初始化
    myfromdialog.dialog({
        resizable: false,
        width: 350,
        height: 360,
        top: 100,
        modal: true,
        buttons: "#task_dialogbut",
        closed: true
    });
    //对话框的初始化
    handle_dialog.dialog({
        resizable: false,
        width: 280,
        height: 300,
        top: 100,
        modal: true,
        buttons: "#handle_dialogbut",
        closed: true
    });

    //表格下拉框
    $("#employeesele").combogrid({
        panelWidth: 450,
        idField: 'id',
        textField: 'realname',
        rownumbers: true,
        url: '/employee_list',
        columns: [[
            {field: 'realname', title: '员工姓名', width: 60},
            {field: 'tel', title: '电话', width: 100},
            {field: 'email', title: '邮箱', width: 120},
            {field: 'dept', title: '部门姓名', width: 100, align: 'center', formatter: deptFormatter}
        ]]
    });

    //a标签的方法
    $("a").on("click", function () {
        var cmd = $(this).data("cmd");
        if (cmd) {
            ObjCmd[cmd]();
        }
    });

    //方法
    ObjCmd = {
        add: function () {
            myfromdialog.dialog("open");
            //先清空2个inpu,然后清空自身的数据
            myform.form("clear");
            myfromdialog.dialog("setTitle", "新分配任务");
            //当前用户的显示的操作.

        },
        //查询的方法.
        searchPage: function () {
            var param = {};
            var paramArr = $("#searchbut").serializeArray();
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            mytable.datagrid("load", param);
        },
        eidte: function () {
            var seled = mytable.datagrid("getSelected");
            var todaydate = new Date();
            today = todaydate.toLocaleDateString().split("/")[2];
            if (today.length == 1) {
                today = "0" + today + " ";
            }
            if (seled) {
                if (seled.handleuser) {
                    seled["handleuser.id"] = seled.handleuser.id;
                }
                if ((seled.start.toString().split("-")[2]) != today) {
                    $.messager.alert("提示消息", "只能修改当天的数据哦!", "info");
                    return;
                }
                myfromdialog.dialog("open");
                myform.form("clear");
                myfromdialog.dialog("setTitle", "计划编辑");
                myform.form("load", seled);

            } else {
                $.messager.alert("提示消息", "请先选择要操作的数据", "info");
            }
        },
        del: function () {
            var seled = mytable.datagrid("getSelections");
            if (seled) {
                //要判断这里的数据的多少.同时判断类型.
                if (seled.length == 1) {
                    if (seled[0].status != 0) {
                        $.messager.alert("警告消息", "这个记录已经被标注过了!不能删除!", "info");
                        return;
                    }
                    $.messager.confirm("确认", "你真的要删除记录吗?", function (r) {
                        if (r) {
                            $.get(
                                "/task_del?id=" + seled[0].id,
                                function (data) {
                                    if (data.success) {
                                        mytable.datagrid("reload");
                                        $.messager.alert("提示消息", data.message, "info");
                                    } else {
                                        $.messager.alert("提示消息", data.message, "info");
                                    }
                                },
                                "json"
                            );
                        }
                    });
                } else {
                    //同时设置多个数据的处理.
                    var cids = [];
                    for (var int = 0; int < seled.length; int++) {
                        var row = seled[int];
                        cids.push(row.id);
                        if (row.status != 0) {
                            $.messager.alert("警告消息", "员工姓名为" + row['handleuser.realName'] + "的信息已经被标注过,不能进行删除!", "info");
                            return;
                        }
                    }
                    $.messager.confirm("确认", "你真的要执行这个操作吗?", function (r) {
                        if (r) {
                            //执行批量的操作.
                            $.post(
                                "/task_delAll?ids=" + cids,
                                function (data) {
                                    if (data.success) {
                                        $.messager.alert("提示消息", data.message, "info");
                                        mytable.datagrid("reload");
                                    } else {
                                        $.messager.alert("提示消息", data.message, "info");
                                    }
                                },
                                "json"
                            );
                        }
                    })
                }
            } else {
                $.messager.alert("提示消息", "请先选择要操作的数据", "info");
            }

        },
        reflush: function () {
            mytable.datagrid("reload");
        },

        tosave: function () {
            //先查看有没有id,是编辑还是保存.
            var url = "";
            var idvalue = $("input[name=id]").val();
            if (idvalue) {
                //是编辑
                url = "/task_update"
            } else {
                //是保存
                url = "/task_save"
            }
            myform.form("submit", {
                url: url,
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //保存成功
                        mytable.datagrid("reload");
                        myfromdialog.dialog("close");

                        $.messager.alert("提示消息", data.message, "info");
                        //页面刷新,关闭窗口
                    } else {
                        //不成功
                        mytable.datagrid("reload");
                        myfromdialog.dialog("close");
                        $.messager.alert("提示消息", data.message, "info");
                        //关闭窗口
                    }
                }
            })
        },
        tocancel: function () {
            myfromdialog.dialog("close");
        },
        adddes: function () {
            var seled = mytable.datagrid("getSelected");
            if (seled) {
                //添加处理描述
                if (seled.title) {
                    $.messager.alert("提示消息", "这条数据已经添加过描述了,您可以选择修改?", "info");
                    return;
                }
                handle_dialog.dialog("open");
                handle_dialog.dialog("setTitle", "添加处理描述");
                handle_form.form("clear");
                handle_form.form("load", seled);
                //单独设置aid的值.

                handle_form.find("input").eq(0).val(seled.id);

            } else {
                $.messager.alert("提示消息", "请先选择要操作的数据", "info");
            }
        },
        handlecancel: function () {
            handle_dialog.dialog("close");
        },
        newhandle: function () {

            var url = "/task_addDescribe";
            handle_form.form("submit", {
                url: url,
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //保存成功
                        mytable.datagrid("reload");
                        handle_dialog.dialog("close");

                        $.messager.alert("提示消息", data.message, "info");
                        //页面刷新,关闭窗口
                    } else {
                        //不成功
                        mytable.datagrid("reload");
                        handle_dialog.dialog("close");
                        $.messager.alert("提示消息", data.message, "info");
                        //关闭窗口
                    }
                }
            });
        },

        //编辑完成的描述
        editdes: function () {
            var seled = mytable.datagrid("getSelected");
            if (seled) {
                //添加处理描述
                handle_dialog.dialog("open");
                handle_dialog.dialog("setTitle", "修改处理描述");
                handle_form.form("clear");
                handle_form.form("load", seled);
                //单独设置aid的值.
                handle_form.find("input").eq(0).val(seled.id);
            } else {
                $.messager.alert("提示消息", "请先选择要操作的数据", "info");
            }
        },
        complete: function () {
            var seled = mytable.datagrid("getSelections");
            if (seled) {
                //要判断这里的数据的多少.同时判断类型.
                if (seled.length == 1) {
                    if (seled[0].status != 0) {
                        $.messager.alert("警告消息", "这个记录已经被标注过了!不能重复标注!", "info");
                        return;
                    }
                    $.messager.confirm("确认", "你真的要修改这个状态吗?", function (r) {
                        if (r) {
                            $.get(
                                "/task_complate?cId=" + seled[0].id,
                                function (data) {
                                    if (data.success) {
                                        mytable.datagrid("reload");
                                        $.messager.alert("提示消息", data.message, "info");
                                    } else {
                                        $.messager.alert("提示消息", data.message, "info");
                                    }
                                },
                                "json"
                            );
                        }
                    });
                } else {
                    //同时设置多个数据的处理.
                    var cids = [];
                    for (var int = 0; int < seled.length; int++) {
                        var row = seled[int];
                        cids.push(row.id);
                        if (row.status != 0) {
                            $.messager.alert("警告消息", "员工姓名为" + row['handleuser.realName'] + "的信息已经被标注过,不能重复标注!", "info");
                            return;
                        }
                    }
                    $.messager.confirm("确认", "你真的要执行这个操作吗?", function (r) {
                        if (r) {
                            //执行批量的操作.
                            $.get(
                                "/task_complateAll?cIds=" + cids,
                                function (data) {
                                    if (data.success) {
                                        mytable.datagrid("reload");
                                        $.messager.alert("提示消息", data.message, "info");
                                    } else {
                                        $.messager.alert("提示消息", data.message, "info");
                                    }
                                },
                                "json"
                            );
                        }
                    })
                }
            } else {
                $.messager.alert("提示消息", "请先选择要操作的数据", "info");
            }
        },
        lose: function () {
            var seled = mytable.datagrid("getSelections");
            if (seled) {
                //要判断这里的数据的多少.同时判断类型.
                if (seled.length == 1) {
                    if (seled[0].status != 0) {
                        $.messager.alert("警告消息", "这个记录已经被标注过了!不能重复标注!", "info");
                        return;
                    }
                    $.messager.confirm("确认", "你真的要修改这个状态吗?", function (r) {
                        if (r) {
                            $.get(
                                "/task_lose?lId=" + seled[0].id,
                                function (data) {
                                    if (data.success) {
                                        mytable.datagrid("reload");
                                        $.messager.alert("提示消息", data.message, "info");
                                    } else {
                                        $.messager.alert("提示消息", data.message, "info");
                                    }
                                },
                                "json"
                            );
                        }
                    });
                } else {
                    //同时设置多个数据的处理.
                    var cids = [];
                    for (var int = 0; int < seled.length; int++) {
                        var row = seled[int];
                        cids.push(row.id);
                        if (row.status != 0) {
                            $.messager.alert("警告消息", "员工姓名为" + row['handleuser.realName'] + "的信息已经被标注过,不能重复标注!", "info");
                            return;
                        }
                    }
                    $.messager.confirm("确认", "你真的要执行这个操作吗?", function (r) {
                        if (r) {
                            //执行批量的操作.
                            $.get(
                                "/task_loseAll?lIds=" + cids,
                                function (data) {
                                    if (data.success) {
                                        mytable.datagrid("reload");
                                        $.messager.alert("提示消息", data.message, "info");
                                    } else {
                                        $.messager.alert("提示消息", data.message, "info");
                                    }
                                },
                                "json"
                            );
                        }
                    })
                }
            } else {
                $.messager.alert("提示消息", "请先选择要操作的数据", "info");
            }
        }
    }

});


function deptFormatter(value, row, index) {
    if (value) {
        return value.name;
    } else {
        return value;
    }
}

function empFormatter(value, row, index) {
    if (value) {
        return value.username;
    } else {
        return value;
    }
}

function desFormatterLength(value, row, index) {
    if (value) {
        return value.substring(0, 5) + "******";
    } else {
        //return "<span>"+vlaue.substring(0,vlaue.length)+"'...'+</span>";
        return value;
    }
}
