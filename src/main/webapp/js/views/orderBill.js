$(function () {
    var orderBillDialog, orderBillDatagrid, orderBillDialogForm, orderBillDatagridBtn, orderBill_seller_combogrid, orderBill_customer_combogrid, orderBill_contractDialog_form, orderBill_contractDialog;
    orderBillDialog = $("#orderBill_dialog");
    orderBillDatagrid = $("#orderBill_datagrid");
    orderBillDialogForm = $("#orderBill_dialog_form");
    orderBillDatagridBtn = $("#orderBill_datagrid_btn a");
    orderBill_seller_combogrid = $("#orderBill_seller_combogrid");
    orderBill_customer_combogrid = $("#orderBill_customer_combogrid");
    orderBill_contractDialog_form = $("#orderBill_contractDialog_form");
    orderBill_contractDialog = $("#orderBill_contractDialog");

    $(function () {
        $('.fancybox').fancybox();
    })


    // 显示营销人员名称
    function sellerFormatter(value, record, index) {
        if (value) {
            return value.username;
        } else {
            return null;

        }
    }

    function modifyuserFormatter(value, record, index) {
        if (value) {
            return value.username;
        } else {
            return null;
        }
    }

    // 显示定金客户名称
    function customerFormatter(value, record, index) {
        if (value) {
            return value.name;

        } else {
            return null;
        }
    }

    function stateFormatter(value, record, index) {
        if (value == 5) {
            return "<font color='purple'>已生成合同</font>"
        } else if (value == 4) {
            return "<font color='#ff1493'>已退款</font>"
        } else if (value == 3) {
            return "<font color='#ff00ff'>审核通过</font>"
        } else if (value == 2) {
            return "<font color='green'>财务未审核</font>"

        } else if (value == 1) {
            return "<font color='red'>部门未审核</font>";
        } else {
            return "<font color='blue'>初始录入</font>";
        }
    }


    orderBillDatagrid.datagrid({
        url: "/orderBill_list",
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        pageList: [1, 5, 10, 20],
        toolbar: "#orderBill_datagrid_btn",
        onClickRow: function (rowIndex, rowData) {
            if (rowData.status == 0) {
                orderBillDatagridBtn.eq(1).linkbutton("enable");
                orderBillDatagridBtn.eq(4).linkbutton("enable");
                orderBillDatagridBtn.eq(7).linkbutton("enable");
                orderBillDatagridBtn.eq(5).linkbutton("disable");
                orderBillDatagridBtn.eq(6).linkbutton("disable");
                orderBillDatagridBtn.eq(8).linkbutton("disable");
            } else if (rowData.status == 1) {
                orderBillDatagridBtn.eq(5).linkbutton("enable");
                orderBillDatagridBtn.eq(7).linkbutton("enable");
                orderBillDatagridBtn.eq(1).linkbutton("disable");
                orderBillDatagridBtn.eq(4).linkbutton("disable");
                orderBillDatagridBtn.eq(6).linkbutton("disable");
                orderBillDatagridBtn.eq(8).linkbutton("disable");
            } else if (rowData.status == 2) {
                orderBillDatagridBtn.eq(6).linkbutton("enable");
                orderBillDatagridBtn.eq(7).linkbutton("enable");
                orderBillDatagridBtn.eq(1).linkbutton("disable");
                orderBillDatagridBtn.eq(4).linkbutton("disable");
                orderBillDatagridBtn.eq(5).linkbutton("disable");
                orderBillDatagridBtn.eq(8).linkbutton("disable");
            } else if (rowData.status == 3) {
                orderBillDatagridBtn.eq(1).linkbutton("disable")
                orderBillDatagridBtn.eq(4).linkbutton("disable")
                orderBillDatagridBtn.eq(5).linkbutton("disable")
                orderBillDatagridBtn.eq(6).linkbutton("disable")
                orderBillDatagridBtn.eq(7).linkbutton("disable")
                orderBillDatagridBtn.eq(8).linkbutton("enable")
            } else if (rowData.status == 4) {
                orderBillDatagridBtn.eq(1).linkbutton("disable")
                orderBillDatagridBtn.eq(4).linkbutton("disable")
                orderBillDatagridBtn.eq(5).linkbutton("disable")
                orderBillDatagridBtn.eq(6).linkbutton("disable")
                orderBillDatagridBtn.eq(7).linkbutton("disable")
                orderBillDatagridBtn.eq(8).linkbutton("disable")
            } else if (rowData.status == 5) {
                orderBillDatagridBtn.eq(1).linkbutton("disable")
                orderBillDatagridBtn.eq(4).linkbutton("disable")
                orderBillDatagridBtn.eq(5).linkbutton("disable")
                orderBillDatagridBtn.eq(6).linkbutton("disable")
                orderBillDatagridBtn.eq(7).linkbutton("disable")
                orderBillDatagridBtn.eq(8).linkbutton("disable")
            }
        },
        columns: [[
            {field: "customer", title: "定金客户", width: 1, align: "center", formatter: customerFormatter},
            {field: "signtime", title: "签订时间", width: 1, align: "center"},
            {field: "seller", title: "营销人员", width: 1, align: "center", formatter: sellerFormatter},
            {field: "totalsum", title: "总金额", width: 1, align: "center"},
            {field: "bargainmoney", title: "定金金额", width: 1, align: "center"},
            {field: "intro", title: "摘要", width: 1, align: "center"},
            {field: "file", title: "附件", width: 1, align: "center"},
            {field: "createtime", title: "创建时间", width: 1, align: "center"},
            {field: "modifyuser", title: "最近修改人", width: 1, align: "center", formatter: modifyuserFormatter},
            {field: "modifytime", title: "最近修改时间", width: 1, align: "center"},
            {field: "status", title: "订单状态", width: 1, align: "center", formatter: stateFormatter}
        ]],
        view:detailview,
        detailFormatter: function (rowIndex, rowData) {
            console.log(rowData);
            console.log(rowIndex);
            return '<table><tr>' +
                '<td rowspan=2 style="border:0"><a class="fancybox" href="' + rowData.file + '"  data-fancybox-group="gallery"><img src="' + rowData.file + '" style="height:50px;"></a></td>' +
                '</tr></table>';
        }

    });


    orderBillDialog.dialog({
        width: 500,
        height: 350,
        closed: true,
        buttons: "#orderBill_dialog_b"
    });

    // 审核通过，合同信息dialog
    orderBill_contractDialog.dialog({
        width: 400,
        height: 250,
        closed: true,
        buttons: "#orderBill_contractDialog_b"
    })


    /**
     * 为添加定金订单查询客户
     */
    orderBill_customer_combogrid.combogrid({
        panelWidth: 400,
        idField: 'id',
        textField: 'name',
        url: '/customer_queryForOrder',
        method: 'get',
        columns: [[
            {field: "name", title: "用户名", width: 80},
            {field: "age", title: "年龄", width: 80},
            {field: "gender", title: "性别", width: 120},
            {field: "tel", title: "联系方式", width: 80},
            {field: "email", title: "邮箱", width: 80},
            {field: "salarylevel", title: "薪资水平", width: 80}
        ]]
    })

    orderBill_seller_combogrid.combogrid({
        panelWidth: 400,
        idField: 'id',
        textField: 'username',
        url: '/seller_list',
        method: 'get',
        columns: [[
            {field: "username", title: "用户名", width: 80},
            {field: "realname", title: "真实姓名", width: 80},
            {field: "email", title: "邮箱", width: 120},
            {field: "dept", title: "部门", width: 80, formatter: departmentFormatter}
        ]]
    })

    function departmentFormatter(value, record, index) {
        if (value) {
            return value.name;
        } else {
            return value;
        }
    }


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
            orderBillDialog.dialog("open");
            // 设置标题为新增
            orderBillDialog.dialog("setTitle", "新增");
            // 清除表单的缓存数据
            $("#orderBill_dialog_form").form("clear");
            //orderBillRoleCombotree.combobox("loadData",{rows:[]});


        },
        edit: function () {
            // 返回选中的行对象
            var rowData = orderBillDatagrid.datagrid("getSelected");
            console.log(rowData)
            if (rowData) {
                // 打开编辑对话框
                orderBillDialog.dialog("open");
                // 打开编辑对话框
                orderBillDialog.dialog("setTitle", "编辑");
                // 清除表单的缓存数据
                orderBillDialogForm.form("clear");
                // 回显定金客户名称
                if (rowData.customer) {
                    rowData["customer.id"] = rowData.customer.id;
                }
                // 回显销售人员名称
                if (rowData.seller) {
                    rowData["seller.id"] = rowData.seller.id;
                }
                // 回显表单的数据
                orderBillDialogForm.form("load", rowData);

            } else {
                $.messager.alert("温馨提示", "请选中要编辑数据", "info");
            }
        },

        del: function () {
            // 返回选中的行对象
            var row = orderBillDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定要删除这个订单的数据吗", function (yes) {
                    if (yes) {
                        // 发送请求去后台删除数据
                        $.get("/orderBill_delete?id=" + row.id, function (data) {
                            if (data.success) {
                                // 删除成功后,重新加载数据
                                orderBillDatagrid.datagrid("load");
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
            orderBillDatagrid.datagrid("load");
        },
        /**
         * 高级查询:根据关键字,录入时间,员工状态查询
         */
        searchContent: function () {
            // 创建一个json空对象:存取要查询的条件
            var param = {};
            // 获取表单中所有的数据的数组 [Object { name="keyword",  value=""}, Object {                         name="beginDate",  value=""}, Object { name="endDate",  value=""}]
            var paramArr = $("#orderBill_searchForm").serializeArray();

            // 设置格式为 Object { keyword="",  beginDate="",  endDate=""}的json对象
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            // 根据查询的条件去重新查询后台,加载到前台中
            orderBillDatagrid.datagrid("load", param);

        },
        /**保存/更新操作:
         * id:null 保存
         * id:不为null 更新
         * */
        save: function () {
            var url = null;
            // 根据id,设置发送的是保存还是更新请求的地址
            var id = $("input[name=id]").val();
            if (id) {
                url = "/orderBill_update"
            } else {
                url = "/orderBill_save"
            }

            // 表单的提交操作
            orderBillDialogForm.form("submit", {
                url: url,
                onSubmit: function (param) {
                    if (!orderBillDialogForm.form("validate")) {
                        return false;
                    }
                },
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {
                        $.messager.alert("温馨提示", data.message, "info");
                        orderBillDialog.dialog("close");
                        orderBillDatagrid.datagrid("load");
                    } else {
                        $.messager.alert("温馨提示", data.message, "warning");
                    }
                }
            });

        },
        cancel: function () {
            orderBillDialog.dialog("close");
        },

        contract_cancel: function () {
            orderBill_contractDialog.dialog("close");
        },

        //审核
        checked: function () {
            // 返回选中的行对象
            var row = orderBillDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定要审核该订单吗", function (yes) {
                    if (yes) {
                        // 发送请求去后台审核数据
                        $.get("/orderBill_checked?id=" + row.id, function (data) {
                            if (data.success) {
                                // 审核成功后,重新加载数据
                                orderBillDatagrid.datagrid("load");
                            } else {
                                $.messager.alert("温馨提示", data.message, "warning");
                            }
                        });
                    }
                });

            } else {
                $.messager.alert("温馨提示", "请选中要审核的行!", "warning");
            }
        },


        //拒绝审核
        no_checked: function () {
            // 返回选中的行对象
            var row = orderBillDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定要拒绝该订单吗", function (yes) {
                    if (yes) {
                        // 发送请求去后台审核数据
                        $.get("/orderBill_noChecked?id=" + row.id, function (data) {
                            if (data.success) {
                                // 拒绝成功后,重新加载数据
                                orderBillDatagrid.datagrid("load");
                            } else {
                                $.messager.alert("温馨提示", data.message, "warning");
                            }
                        });
                    }
                });

            } else {
                $.messager.alert("温馨提示", "请选中要审核的行!", "warning");
            }
        },


        //打开生成合同对话框
        doContract: function () {
            var row = orderBillDatagrid.datagrid("getSelected");
            if (row) {
                // 打开新增对话框
                orderBill_contractDialog.dialog("open");
                //设置标题
                orderBill_contractDialog.dialog("setTitle", "生成合同");
                // 清除表单的缓存数据
                $("#orderBill_contractDialog_form").form("clear");
            } else {
                $.messager.alert("温馨提示", "请选中要生成合同的的行!", "warning");
            }

        },
        // 生成合同
        contract_save: function () {
            var row = orderBillDatagrid.datagrid("getSelected");
            if (row.status == 3) {
                orderBill_contractDialog_form.form("submit", {
                    url: "/orderBill_doContract",
                    onSubmit: function (param) {
                        // 获取选中订单Id数组
                        param["id"] = row.id;
                        if (!orderBill_contractDialog_form.form("validate")) {
                            return false;
                        }

                    },
                    success: function (data) {
                        data = $.parseJSON(data);
                        if (data.success) {
                            $.messager.alert("温馨提示", data.message, "info");
                            orderBill_contractDialog.dialog("close");
                            orderBillDatagrid.datagrid("load");
                            orderBill_contractDialog.datagrid("load");
                        } else {
                            $.messager.alert("温馨提示", data.message, "warning");
                        }
                    }
                })
            } else {
                $.messager.alert("温馨提示", "该订单不能生成合同!", "warning");
            }
        }
    };
});




