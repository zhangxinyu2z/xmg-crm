$(function () {
    var contractDialog, contractDatagrid, contractDialogForm, contractDatagridBtn,contract_seller_combogrid,contract_customer_combogrid,contract_guaranteeDialog,contract_guaranteeDialog_form;
    contractDialog = $("#contract_dialog");
    contractDatagrid = $("#contract_datagrid");
    contractDialogForm = $("#contract_dialog_form");
    contractDatagridBtn = $("#contract_datagrid_btn a");
    contract_seller_combogrid = $("#contract_seller_combogrid");
    contract_customer_combogrid = $("#contract_customer_combogrid");
    contract_guaranteeDialog = $("#contract_guaranteeDialog");
    contract_guaranteeDialog_form = $("#contract_guaranteeDialog_form");


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

    // 显示合同客户名称
    function customerFormatter(value, record, index) {
        if (value) {
            return value.name;

        } else {
            return null;
        }
    }

    function stateFormatter(value, record, index) {
        if (value == 5) {
            return "<font color='purple'>已生成维修单</font>"
        }else if (value == 4) {
            return "<font color='#ff1493'>审核拒绝</font>"
        }else if (value == 3) {
            return "<font color='#ff00ff'>审核通过</font>"
        } else if (value == 2) {
            return "<font color='green'>财务未审核</font>"

        } else if (value == 1) {
            return "<font color='red'>部门未审核</font>";
        } else {
            return "<font color='blue'>初始录入</font>";
        }
    }


    contractDatagrid.datagrid({
        url: "/contract_list",
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        pageList: [1, 5, 10, 20],
        toolbar: "#contract_datagrid_btn",
        onClickRow: function (rowIndex, rowData) {
            if (rowData.status == 0) {
                contractDatagridBtn.eq(1).linkbutton("enable");
                contractDatagridBtn.eq(4).linkbutton("enable");
                contractDatagridBtn.eq(7).linkbutton("enable");
                contractDatagridBtn.eq(5).linkbutton("disable");
                contractDatagridBtn.eq(6).linkbutton("disable");
                contractDatagridBtn.eq(8).linkbutton("disable");
            } else if (rowData.status == 1) {
                contractDatagridBtn.eq(5).linkbutton("enable");
                contractDatagridBtn.eq(7).linkbutton("enable");
                contractDatagridBtn.eq(1).linkbutton("disable");
                contractDatagridBtn.eq(4).linkbutton("disable");
                contractDatagridBtn.eq(6).linkbutton("disable");
                contractDatagridBtn.eq(8).linkbutton("disable");
            } else if (rowData.status == 2) {
                contractDatagridBtn.eq(6).linkbutton("enable");
                contractDatagridBtn.eq(7).linkbutton("enable");
                contractDatagridBtn.eq(1).linkbutton("disable");
                contractDatagridBtn.eq(4).linkbutton("disable");
                contractDatagridBtn.eq(5).linkbutton("disable");
                contractDatagridBtn.eq(8).linkbutton("disable");
            } else if (rowData.status == 3) {
                contractDatagridBtn.eq(1).linkbutton("disable")
                contractDatagridBtn.eq(4).linkbutton("disable")
                contractDatagridBtn.eq(5).linkbutton("disable")
                contractDatagridBtn.eq(6).linkbutton("disable")
                contractDatagridBtn.eq(7).linkbutton("disable")
                contractDatagridBtn.eq(8).linkbutton("enable")
            }else if(rowData.status == 4){
                contractDatagridBtn.eq(1).linkbutton("disable")
                contractDatagridBtn.eq(4).linkbutton("disable")
                contractDatagridBtn.eq(5).linkbutton("disable")
                contractDatagridBtn.eq(6).linkbutton("disable")
                contractDatagridBtn.eq(7).linkbutton("disable")
                contractDatagridBtn.eq(8).linkbutton("disable")
            }
        },
        columns: [[
            {field: "sn", title: "合同单号", width: 1, align: "center"},
            {field: "customer", title: "合同客户", width: 1, align: "center", formatter: customerFormatter},
            {field: "signtime", title: "签订时间", width: 1, align: "center"},
            {field: "seller", title: "营销人员", width: 1, align: "center", formatter: sellerFormatter},
            {field: "contractsum", title: "合同金额", width: 1, align: "center"},
            {field: "money", title: "付款金额", width: 1, align: "center"},
            {field: "paytime", title: "付款时间", width: 1, align: "center"},
            {field: "intro", title: "合同摘要", width: 1, align: "center"},
            {field: "file", title: "附件", width: 1, align: "center"},
            {field: "modifyuser", title: "最近修改人", width: 1, align: "center", formatter: modifyuserFormatter},
            {field: "modifytime", title: "最近修改时间", width: 1, align: "center"},
            {field: "status", title: "审核状态", width: 1, align: "center", formatter: stateFormatter}
        ]],
        view: detailview,
        detailFormatter: function (rowIndex, rowData) {
            console.log(rowData);
            console.log(rowIndex);
            return '<table><tr>' +
                '<td rowspan=2 style="border:0"><a class="fancybox" href="' + rowData.file + '"  data-fancybox-group="gallery"><img src="' + rowData.file + '" style="height:50px;"></a></td>' +
                '</tr></table>';
        }

    });


    contractDialog.dialog({
        width: 500,
        height: 350,
        closed: true,
        buttons: "#contract_dialog_b"
    });


    contract_guaranteeDialog.dialog({
        width: 400,
        height: 180,
        closed: true,
        buttons: "#contract_guaranteeDialog_b"
    })


    contract_customer_combogrid.combogrid({
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

    contract_seller_combogrid.combogrid({
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
            contractDialog.dialog("open");
            // 设置标题为新增
            contractDialog.dialog("setTitle", "新增");
            // 清除表单的缓存数据
            $("#contract_dialog_form").form("clear");
            //contractRoleCombotree.combobox("loadData",{rows:[]});


        },
        edit: function () {
            // 返回选中的行对象
            var rowData = contractDatagrid.datagrid("getSelected");
            console.log(rowData)
            if (rowData) {
                // 打开编辑对话框
                contractDialog.dialog("open");
                // 打开编辑对话框
                contractDialog.dialog("setTitle", "编辑");
                // 清除表单的缓存数据
                contractDialogForm.form("clear");
                // 回显定金客户名称
                if (rowData.customer) {
                    rowData["customer.id"] = rowData.customer.id;
                }
                // 回显销售人员名称
                if (rowData.seller) {
                    rowData["seller.id"] = rowData.seller.id;
                }
                // 回显表单的数据
                contractDialogForm.form("load", rowData);

            } else {
                $.messager.alert("温馨提示", "请选中要编辑数据", "info");
            }
        },

        del: function () {
            // 返回选中的行对象
            var row = contractDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定要删除改合同的数据吗", function (yes) {
                    if (yes) {
                        // 发送请求去后台删除数据
                        $.get("/contract_delete?id=" + row.id, function (data) {
                            if (data.success) {
                                // 删除成功后,重新加载数据
                                contractDatagrid.datagrid("load");
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
            contractDatagrid.datagrid("load");
        },
        /**
         * 高级查询:根据关键字,录入时间,员工状态查询
         */
        searchContent: function () {
            // 创建一个json空对象:存取要查询的条件
            var param = {};
            // 获取表单中所有的数据的数组 [Object { name="keyword",  value=""}, Object {                         name="beginDate",  value=""}, Object { name="endDate",  value=""}]
            var paramArr = $("#contract_searchForm").serializeArray();

            // 设置格式为 Object { keyword="",  beginDate="",  endDate=""}的json对象
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            // 根据查询的条件去重新查询后台,加载到前台中
            contractDatagrid.datagrid("load", param);

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
                url = "/contract_update"
            } else {
                url = "/contract_save"
            }

            // 表单的提交操作
            contractDialogForm.form("submit", {
                url: url,
                onSubmit: function (param) {
                    if (!contractDialogForm.form("validate")) {
                        return false;
                    }
                },
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {
                        $.messager.alert("温馨提示", data.msg, "info");
                        contractDialog.dialog("close");
                        contractDatagrid.datagrid("load");
                    } else {
                        $.messager.alert("温馨提示", data.msg, "warning");
                    }
                }
            });

        },
        cancel: function () {
            contractDialog.dialog("close");
        },

        //审核
        checked: function () {
            // 返回选中的行对象
            var row = contractDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定要审核该订单吗", function (yes) {
                    if (yes) {
                        // 发送请求去后台审核数据
                        $.get("/contract_checked?id=" + row.id, function (data) {
                            if (data.success) {
                                // 审核成功后,重新加载数据
                                contractDatagrid.datagrid("load");
                            } else {
                                $.messager.alert("温馨提示", data.msg, "warning");
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
            var row = contractDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定要拒绝该订单吗", function (yes) {
                    if (yes) {
                        // 发送请求去后台审核数据
                        $.get("/contract_noChecked?id=" + row.id, function (data) {
                            if (data.success) {
                                // 拒绝成功后,重新加载数据
                                contractDatagrid.datagrid("load");
                            } else {
                                $.messager.alert("温馨提示", data.msg, "warning");
                            }
                        });
                    }
                });

            } else {
                $.messager.alert("温馨提示", "请选中要审核的行!", "warning");
            }
        },


        //生成维修单
        doGuarantee:function () {
            var row = contractDatagrid.datagrid("getSelected");
            if (row){
                // 打开新增对话框
                contract_guaranteeDialog.dialog("open");
                //设置标题
                contract_guaranteeDialog.dialog("setTitle", "生成合同");
                // 清除表单的缓存数据
                $("#contract_guaranteeDialog_form").form("clear");
            }else{
                $.messager.alert("温馨提示", "请选中要生成合同的的行!", "warning");
            }

        },

        guarantee_save:function () {
            var row = contractDatagrid.datagrid("getSelected");
            if(row.status == 3){
                contract_guaranteeDialog_form.form("submit",{
                    url: "/contract_doGuarantee",
                    onSubmit: function (param) {
                        // 获取选中订单Id数组
                        console.log(param);
                        param["id"]=row.id;
                        console.log(param);
                    },
                    success: function(data) {
                        data = $.parseJSON(data);
                        if (data.success) {
                            $.messager.alert("温馨提示", data.msg, "info");
                            contract_guaranteeDialog.dialog("close");
                            contractDatagrid.datagrid("load");
                            contract_guaranteeDialog.datagrid("load");
                        } else {
                            $.messager.alert("温馨提示", data.msg, "warning");
                        }
                    }
                })
            }else{
                $.messager.alert("温馨提示", "改行不能生成合同!", "warning");
            }
        },
        guarantee_cancel:function () {
            contract_guaranteeDialog.dialog("close");
        }
    };
});




