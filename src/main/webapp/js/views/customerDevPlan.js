$(function () {
    var customerDevPlanDialog, customerDevPlanDatagrid, customerDevPlanDialogForm, customerDevPlanDatagridBtn,customerDevPlanSelectCombogrid;
    customerDevPlanDialog = $("#customerDevPlan_dialog");
    customerDevPlanDatagrid = $("#customerDevPlan_datagrid");
    customerDevPlanDialogForm = $("#customerDevPlan_dialog_form");
    customerDevPlanDatagridBtn = $("#customerDevPlan_datagrid_btn a");
    customerDevPlanSelectCombogrid = $("#customerDevPlan_select_combogrid");


    // 显示创建人的名称
    function inputuserFormatter(value, record, index) {
        if (value) {
            return value.username;
        } else {
            return null;
        }
    }

    // 显示客户的名称
    function customerFormatter(value, record, index) {
        if (value) {
            return value.name;

        } else {
            return null;
        }
    }

    // 显示客户的名称
    function traceresultFormatter(value, record, index) {
        if (value == '3') {
            return "<font color='blue'>优</font>";

        } else if (value == '2') {
            return "<font color='green'>中</font>";
        } else {
            return "<font color='red'>差</font>";
        }
    }


    customerDevPlanSelectCombogrid.combogrid({
        rowNumbers:true,
        panelWidth: 400,
        idField: 'id',
        textField: 'name',
        url: '/potentialCustomer_list',
        method: 'get',
        columns: [[
            {field: "name", title: "姓名", width: 50},
            {field: "tel", title: "电话", width: 100},
            {field: "email", title: "邮箱", width: 100},
            {field: "job", title: "职业", width: 50},
            {field: "salarylevel", title: "收入水平", width: 60}
        ]]
    })



    customerDevPlanDatagrid.datagrid({
        url: "customerDevPlan_list",
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        pageList: [1, 5, 10, 20],
        toolbar: "#customerDevPlan_datagrid_btn",
        columns: [[
            {field: "customer", title: "客户名称", width: 1, align: "center", formatter: customerFormatter},
            {field: "plansubject", title: "计划主题", width: 1, align: "center"},
            {field: "plantime", title: "计划时间", width: 1, align: "center"},
            {field: "plantype", title: "实施方式", width: 1, align: "center"},
            {field: "traceresult", title: "跟进效果", width: 1, align: "center", formatter: traceresultFormatter},
            {field: "inputuser", title: "创建人", width: 1, align: "center", formatter: inputuserFormatter},
            {field: "remark", title: "备注", width: 1, align: "center"},
            {field: "inputtime", title: "创建时间", width: 1, align: "center"},

        ]],

    });

    customerDevPlanDialog.dialog({
        width: 600,
        height: 400,
        buttons: "#customerDevPlan_dialog_bb",
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

        add: function () {
            // 打开新增对话框
            customerDevPlanDialog.dialog("open");
            // 设置标题为新增
            customerDevPlanDialog.dialog("setTitle", "新增");
            // 清除表单的缓存数据
            $("#customerDevPlan_dialog_form").form("clear");


        },
        edit: function () {
            // 返回选中的行对象
            var rowData = customerDevPlanDatagrid.datagrid("getSelected");
            if (rowData) {
                // 打开编辑对话框
                customerDevPlanDialog.dialog("open");
                // 打开编辑对话框
                customerDevPlanDialog.dialog("setTitle", "编辑");
                // 清除表单的缓存数据
                customerDevPlanDialogForm.form("clear");
                //  $("#customerDevPlan_role_combobox").combobox("clear");
                // 回显员工
                if(rowData.customer){
                    rowData["customer.id"]=rowData.customer.id;
                }

                // 回显表单的数据
                customerDevPlanDialogForm.form("load", rowData);

            } else {
                $.messager.alert("温馨提示", "请选中要编辑数据", "info");
            }
        },

        del: function () {
            // 返回选中的行对象
            var row = customerDevPlanDatagrid.datagrid("getSelected");
            if (row) {
                $.messager.confirm("温馨提示", "你确定要删除这个员工的数据吗", function (yes) {
                    if (yes) {
                        // 发送请求去后台删除数据
                        $.get("/customerDevPlan_delete?id=" + row.id, function (data) {
                            if (data.success) {
                                // 删除成功后,重新加载数据
                                customerDevPlanDatagrid.datagrid("reload");
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
            customerDevPlanDatagrid.datagrid("reload");
        },
        /**
         * 高级查询:根据关键字,录入时间,员工状态查询
         */
        searchContent: function () {
            // 创建一个json空对象:存取要查询的条件
            var param = {};
            // 获取表单中所有的数据的数组 [Object { name="keyword",  value=""}, Object {                         name="beginDate",  value=""}, Object { name="endDate",  value=""}]
            var paramArr = $("#customerDevPlan_searchForm").serializeArray();

            // 设置格式为 Object { keyword="",  beginDate="",  endDate=""}的json对象
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            // 根据查询的条件去重新查询后台,加载到前台中
            customerDevPlanDatagrid.datagrid("load", param);

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
                url = "/customerDevPlan_update"
            } else {
                url = "/customerDevPlan_save"
            }

            // 表单的提交操作
            customerDevPlanDialogForm.form("submit", {
                url: url,
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {
                        $.messager.alert("温馨提示", data.msg, "info");
                        customerDevPlanDialog.dialog("close");
                        customerDevPlanDatagrid.datagrid("reload");
                    } else {
                        $.messager.alert("温馨提示", data.msg, "warning");
                    }
                }
            });

        },
        cancel: function () {
            customerDevPlanDialog.dialog("close");
        }


    };

});




