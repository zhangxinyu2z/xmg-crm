$(function () {
    //把需要的变量缓存起来
    var customerDatagrid, customerDatagridEditBtn, customerSearchForm;
    customerDatagrid = $("#customerChart_datagrid");
    customerDatagridEditBtn = $("#customerChart_datagrid_bt a");
    customerSearchForm = $("#customerChart_searchForm");

    customerDatagrid.datagrid({
        url: "/customerChart_list?typee=year",
        fit: true,
        pagination: true,
        rownumbers: true,
        singleSelect: true,

        columns: [[
            {title: "分组信息", field: "time", width: 1, align: "center", formatter: yearFormatter},
            {title: "员工姓名", field: "employee", width: 1, align: "center", formatter: employeeFormatter},
            {title: "潜在客户新增数量", field: "amountCustomer", width: 1, align: "center"}
        ]],
        fitColumns: true,
        toolbar: '#customerChart_datagrid_bt'
    });

    //统一管理法
    var cmdObj = {
        searchContent: function () {
            var param = {};
            var paramArr = $("#customerChart_searchForm").serializeArray();
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            $("#customerChart_datagrid").datagrid("load", param);
        },
        refresh: function () {
            customerDatagrid.datagrid("reload");
        }

    };

    //給按钮添加事件
    $("a[data-cmd]").on("click", function () {
        var cmd = $(this).data("cmd");
        if (cmd) {
            cmdObj[cmd]();
        }
    });

    $("#becometime_group").combobox({
        onSelect: function () {
            var ids = $(this).combobox("getValues");
            customerDatagrid.datagrid({
                url: "/customerChart_list?typee=" + ids[0],
                fit: true,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                columns: [[
                    {title: "分组信息", field: "time", width: 1, align: "center", formatter: allFormatter},
                    {title: "员工姓名", field: "employee", width: 1, align: "center", formatter: employeeFormatter},
                    {title: "潜在客户新增数量", field: "amountCustomer", width: 1, align: "center"}
                ]],
                fitColumns: true,
                toolbar: '#customer_datagrid_bt'
            });

            function allFormatter(value, record, index) {
                if (ids[0] == 'year') {
                    if (value) {
                        return value + "年"
                    }
                }
                if (ids[0] == 'month') {
                    if (value) {
                        return value + "月"
                    }
                }
                if (ids[0] == 'quarter') {
                    if (value == 1) {
                        return "一季度"
                    }
                    if (value == 2) {
                        return "二季度"
                    }
                    if (value == 3) {
                        return "三季度"
                    }
                    if (value == 4) {
                        return "四季度"
                    }
                }
            }

        }
    });

    $("#photo_group").combobox({

        onSelect: function () {
            var ids = $(this).combobox("getValues");
            if (ids == 'line') {
                window.open("/customerChart_pictrue1", "", "width=500px,height=500px");
            }
            if (ids == 'column') {
                window.showModalDialog("/customerChart_pictrue3", "", "dialogWidth=500px;dialogHeight=500px");
            }
            if (ids == 'pie') {
                window.showModalDialog("/customerChart_pictrue2", "", "dialogWidth=500px;dialogHeight=500px");
            }

        }
    })

});

function employeeFormatter(value, record, index) {
    if (value) {
        return value.username
    } else {
        return value;
    }
}

function yearFormatter(value, record, index) {
    if (value) {
        return value + "年"
    }
}



