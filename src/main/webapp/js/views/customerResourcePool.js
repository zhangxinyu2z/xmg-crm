$(function () {
    var customerResourcePoolDialog, customerResourcePoolDatagrid, customerResourcePoolDialogForm, customerResourcePoolDatagridBtn;
    customerResourcePoolDialog = $("#customerResourcePool_dialog");
    customerResourcePoolDatagrid = $("#customerResourcePool_datagrid");
    customerResourcePoolDialogForm = $("#customerResourcePool_dialog_form");
    customerResourcePoolDatagridBtn = $("#customerResourcePool_datagrid_btn a");


    customerResourcePoolDatagrid.datagrid({
        url: "customerResourcePool_list",
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        pageList: [1, 5, 10, 20],
        toolbar: "#customerResourcePool_datagrid_btn",
        onClickRow: function (rowIndex, rowData) {

        },
        columns: [[
            {field: "name", title: "姓名", width: 1, align: "center"},
            {field: "age", title: "年龄", width: 1, align: "center"},
            {field: "gender", title: "性别", width: 1, align: "center"},
            {field: "tel", title: "联系电话", width: 1, align: "center"},
            {field: "email", title: "邮箱", width: 1, align: "center"},
            {field: "qq", title: "QQ", width: 1, align: "center"},
            {field: "wechat", title: "微信", width: 1, align: "center"},
            {field: "job", title: "工资", width: 1, align: "center"},
            {field: "salarylevel", title: "收入水平", width: 1, align: "center"},
            {field: "customersource", title: "客户来源", width: 1, align: "center"},
            {field: "inputtime", title: "创建时间", width: 1, align: "center"},
            {field: "inputuser", title: "创建人", width: 1, align: "center", formatter: inputuserFormatter},
            {field: "inchargeuser", title: "负责人", width: 1, align: "center", formatter: inchargeuserFormatter}
        ]],

    });

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


    customerResourcePoolDialog.dialog({
        width: 600,
        height: 300,
        resizable: true,
        buttons: "#customerResourcePool_dialog_bb",
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

        admit: function () {
            // 返回选中的行对象
            var rowData = customerResourcePoolDatagrid.datagrid("getSelected");
            if (rowData) {
                $.messager.confirm("温馨提示", "您确认要吸纳这个客户吗?", function (yes) {
                    if(yes) {
                        $.get("/customer_admit?id=" + rowData.id, function (data) {
                            if (data.success) {
                                $.messager.alert("温馨提示", data.message, "info");
                                customerResourcePoolDatagrid.datagrid("reload");
                            } else {
                                $.messager.alert("温馨提示", data.message, "info");
                            }
                        });
                    }
                })
            } else {
                $.messager.alert("温馨提示", "请选中要吸纳的客户", "info");
            }
        },


        /**
         * 刷新列表
         */
        refresh: function () {
            customerResourcePoolDatagrid.datagrid("reload");
        },
        /**
         * 高级查询:根据关键字,录入时间,员工状态查询
         */
        searchContent: function () {
            // 创建一个json空对象:存取要查询的条件
            var param = {};
            // 获取表单中所有的数据的数组 [Object { name="keyword",  value=""}, Object {                         name="beginDate",  value=""}, Object { name="endDate",  value=""}]
            var paramArr = $("#customerResourcePool_searchForm").serializeArray();

            // 设置格式为 Object { keyword="",  beginDate="",  endDate=""}的json对象
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            // 根据查询的条件去重新查询后台,加载到前台中
            customerResourcePoolDatagrid.datagrid("load", param);

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
                url = "/customerResourcePool_update"
            } else {
                url = "/customerResourcePool_save"
            }

            // 表单的提交操作
            customerResourcePoolDialogForm.form("submit", {
                url: url,
                // 传递选中角色的Id到后台中
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {
                        $.messager.alert("温馨提示", data.message, "info");
                        customerResourcePoolDialog.dialog("close");
                        customerResourcePoolDatagrid.datagrid("reload");
                    } else {
                        $.messager.alert("温馨提示", data.message, "warning");
                    }
                }
            });

        },
        cancel: function () {
            customerResourcePoolDialog.dialog("close");
        }
    };

});




