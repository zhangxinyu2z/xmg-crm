$(function () {
    var potentialDialog, potentialDatagrid, potentialDialogForm, potentialDatagridBtn, potentialShareOrturnDialog,
        potentialShareOrturnDialogBtn, potentialShareOrturnDialogForm;
    potentialDialog = $("#potentialCustomer_dialog");
    potentialDatagrid = $("#potentialCustomer_datagrid");
    potentialDialogForm = $("#potentialCustomer_dialog_form");
    potentialDatagridBtn = $("#potentialCustomer_datagrid_btn a");
    potentialShareOrturnDialog = $("#potential_shareOrturn_dialog");
    potentialShareOrturnDialogBtn = $("#potential_shareOrturn_dialog_bb")
    potentialShareOrturnDialogForm = $("#potential_shareOrturn_dialog_form");


    potentialDatagrid.datagrid({
        url: "potentialCustomer_list",
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        pageList: [1, 5, 10, 20],
        toolbar: "#potentialCustomer_datagrid_btn",

        columns: [[
            {field: "name", title: "姓名", width: 1, align: "center"},
            {field: "age", title: "年龄", width: 1, align: "center"},
            {field: "gender", title: "性别", width: 1, align: "center", formatter: genderFormatter},
            {field: "tel", title: "联系电话", width: 1, align: "center"},
            {field: "email", title: "邮箱", width: 1, align: "center"},
            {field: "qq", title: "QQ", width: 1, align: "center"},
            {field: "wechat", title: "微信", width: 1, align: "center"},
            {field: "job", title: "工作", width: 1, align: "center"},
            {field: "salarylevel", title: "收入水平", width: 1, align: "center"},
            {field: "customersource", title: "客户来源", width: 1, align: "center"},
            {field: "inputtime", title: "创建时间", width: 1, align: "center"},
            {field: "inputuser", title: "创建人", width: 1, align: "center", formatter: inputuserFormatter},
            {field: "inchargeuser", title: "负责人", width: 1, align: "center", formatter: inchargeuserFormatter}
        ]],

    });


    function genderFormatter(value, row, index) {
        if (value == '1') {
            return "男";
        } else if (value == '0') {
            return "女";
        }

    }

    function inputuserFormatter(value, row, index) {
        if (value) {
            return value.username;
        } else {
            return null;
        }

    }

    function inchargeuserFormatter(value, row, index) {
        if (value) {
            return value.username;
        } else {
            return null;
        }
    }


    potentialDialog.dialog({
        width: 600,
        height: 300,
        resizable: true,
        buttons: "#potentialCustomer_dialog_bb",
        closed: true

    });

    potentialShareOrturnDialog.dialog({
        width: 400,
        height: 250,
        resizable: true,
        buttons: "#potential_shareOrturn_dialog_bb",
        closed: true
    });


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

        add: function add() {
            // 打开新增对话框
            potentialDialog.dialog("open");
            // 设置标题为新增
            potentialDialog.dialog("setTitle", "新增");
            // 清除表单的缓存数据
            $("#potentialCustomer_dialog_form").form("clear");


        },
        edit: function () {
            // 返回选中的行对象
            var rowData = potentialDatagrid.datagrid("getSelected");
            if (rowData) {
                // 打开编辑对话框
                potentialDialog.dialog("open");
                // 打开编辑对话框
                potentialDialog.dialog("setTitle", "编辑");
                // 清除表单的缓存数据
                potentialDialogForm.form("clear");


                // 回显表单的数据
                potentialDialogForm.form("load", rowData);

            } else {
                $.messager.alert("温馨提示", "请选中要编辑数据", "info");
            }
        },

        del: function () {
            // 返回选中的行对象
            var row = potentialDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定要删除这个员工的数据吗", function (yes) {
                    if (yes) {
                        // 发送请求去后台删除数据
                        $.get("/potentialCustomer_delete?id=" + row.id, function (data) {
                            if (data.success) {
                                // 删除成功后,重新加载数据
                                potentialDatagrid.datagrid("reload");
                            } else {
                                $.messager.alert("温馨提示", data.message, "warning");
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
            potentialDatagrid.datagrid("reload");
        },
        /**
         * 高级查询:根据关键字,录入时间,员工状态查询
         */
        searchContent: function () {
            // 创建一个json空对象:存取要查询的条件
            var param = {};
            // 获取表单中所有的数据的数组 [Object { name="keyword",  value=""}, Object {                         name="beginDate",  value=""}, Object { name="endDate",  value=""}]
            var paramArr = $("#potentialCustomer_searchForm").serializeArray();

            // 设置格式为 Object { keyword="",  beginDate="",  endDate=""}的json对象
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            // 根据查询的条件去重新查询后台,加载到前台中
            potentialDatagrid.datagrid("load", param);

        },
        /**保存/更新操作:
         * id:null 保存
         * id:不为null 更新
         * */
        save: function () {
            var url = null;
            // 根据id,设置发送的是保存还是更新请求的地址
            var id = $("input[name=id]").val();
            console.log("id" + id);
            if (id) {
                url = "/potentialCustomer_update"
            } else {
                url = "/potentialCustomer_save"
            }

            // 表单的提交操作
            potentialDialogForm.form("submit", {
                url: url,
                onSubmit: function () {
                    if (!potentialDialogForm.form("validate")) {
                        return false;
                    }
                },

                // 传递选中角色的Id到后台中
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {

                        $.messager.alert("温馨提示", data.message, "info");
                        potentialDialog.dialog("close");
                        potentialDatagrid.datagrid("reload");
                    } else {
                        $.messager.alert("温馨提示", data.message, "warning");
                    }
                }
            });

        },
        cancel: function () {
            potentialDialog.dialog("close");
        },
        // 共享
        share: function () {
            // 返回选中的行对象
            // console.log(potentialShareOrturnDialog);
            var rowData = potentialDatagrid.datagrid("getSelected");
            if (rowData) {
                // 打开编辑对话框
                potentialShareOrturnDialog.dialog("setTitle", "共享");
                potentialShareOrturnDialog.dialog("open");

                // 打开编辑对话框
                // 清除表单的缓存数据
                potentialShareOrturnDialog.form("clear");
                // 回显负责人的名称
                if (rowData.inchargeuser) {
                    rowData['inchargeuser.name'] = rowData.inchargeuser.username;
                }
                // 回显负责人的Id,目的是为了控制自己共享给自己
                if (rowData.inchargeuser) {
                    rowData['inchargeuser.id'] = rowData.inchargeuser.id;
                }

                // 回显表单的数据
                potentialShareOrturnDialogForm.form("load", rowData);

            } else {
                $.messager.alert("温馨提示", "请选中要共享的客户", "info");
            }

        },
        /*移交*/
        turnOver: function () {
            // 返回选中的行对象
            var rowData = potentialDatagrid.datagrid("getSelected");
            console.log(rowData);
            if (rowData) {
                // 打开编辑对话框
                potentialShareOrturnDialog.dialog("open");
                // 打开编辑对话框
                potentialShareOrturnDialog.dialog("setTitle", "移交");
                // 清除表单的缓存数据
                potentialShareOrturnDialog.form("clear");
                // 回显负责人的名称
                if (rowData.inchargeuser) {
                    rowData['inchargeuser.name'] = rowData.inchargeuser.username;
                }
                // 回显负责人的Id,目的是为了控制自己移交给自己
                if (rowData.inchargeuser) {
                    rowData['inchargeuser.id'] = rowData.inchargeuser.id;
                }

                // 回显表单的数据
                potentialShareOrturnDialogForm.form("load", rowData);

            } else {
                $.messager.alert("温馨提示", "请选中要移交的客户", "info");
            }
        },
        // 共享和移交的保存方法
        shareSave: function () {
            potentialShareOrturnDialogForm.form("submit", {
                url: "/customer_updateInCharge",
                // 传递选中角色的Id到后台中
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {
                        $.messager.alert("温馨提示", data.message, "info");
                        // potentialShareOrturnDialog.dialog("close");
                        potentialDatagrid.datagrid("reload");
                    } else {
                        $.messager.alert("温馨提示", data.message, "warning");
                        // potentialShareOrturnDialog.dialog("close");
                    }
                }
            });

        },
        shareCancel: function () {
            potentialShareOrturnDialog.dialog("close");
        },
        /*开发失败*/
        developFalse: function () {
            var row = potentialDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定这个客户开发失败吗?", function (yes) {
                    if (yes) {
                        // 发送请求去后台删除数据
                        $.get("/potentialCustomer_developFalse?id=" + row.id, function (data) {
                            if (data.success) {
                                $.messager.alert("温馨提示", data.message, "info");
                                potentialShareOrturnDialog.dialog("close");
                                // 删除成功后,重新加载数据
                                potentialDatagrid.datagrid("reload");
                            } else {
                                $.messager.alert ("温馨提示", data.message, "warning");
                            }
                        });
                    }
                });

            } else {
                $.messager.alert("温馨提示", "请选中要开发失败的客户!", "warning");
            }


        },
        /*转正*/
        become: function () {
            var row = potentialDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定这个客户要转正吗?", function (yes) {
                    if (yes) {
                        // 发送请求去后台删除数据
                        $.get("/potentialCustomer_become?id=" + row.id, function (data) {
                            if (data.success) {
                                $.messager.alert("温馨提示", data.message, "info");
                                potentialShareOrturnDialog.dialog("close");
                                // 删除成功后,重新加载数据
                                potentialDatagrid.datagrid("reload");
                            } else {
                                $.messager.alert("温馨提示", data.message, "warning");
                            }
                        });
                    }
                });

            } else {
                $.messager.alert("温馨提示", "请选中要转正的客户!", "warning");
            }
        }

    };

});




