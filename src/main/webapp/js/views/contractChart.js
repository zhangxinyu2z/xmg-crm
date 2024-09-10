$(function () {
    //把需要的变量缓存起来
    var contractDatagrid, contractDatagridEditBtn, contractSearchForm;
    contractDatagrid = $("#contract_datagrid");
    contractDatagridEditBtn = $("#contract_datagrid_bt a");
    contractSearchForm = $("#contract_searchForm");

    contractDatagrid.datagrid({
        url: "/contractChart_list?typee=year",
        fit: true,
        pagination: true,
        rownumbers: true,
        singleSelect: true,

        columns: [[
            {title: "分组信息", field: "time", width: 1, align: "center", formatter: yearFormatter},
            {title: "总金额", field: "amountcontract", width: 1, align: "center"}
        ]],
        fitColumns: true,
        toolbar: '#contract_datagrid_bt'
    });

    //统一管理法
    var cmdObj = {
        searchContent: function () {
            var param = {};
            var paramArr = $("#contract_searchForm").serializeArray();

            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            $("#contract_datagrid").datagrid("load", param);


        },

        refresh: function () {
            contractDatagrid.datagrid("reload");
        }
    };

    //給按钮添加事件
    $("a[data-cmd]").on("click", function () {
        var cmd = $(this).data("cmd");
        if (cmd) {
            cmdObj[cmd]();
        }
    });


    $("#becometime_group_count").combobox({
        onSelect: function () {
            var ids = $(this).combobox("getValues");
            contractDatagrid.datagrid({
                url: "/contractChart_list?typee=" + ids[0],
                fit: true,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                columns: [[
                    {title: "分组信息", field: "time", width: 1, align: "center", formatter: allFormatter},
                    {title: "总金额", field: "amountcontract", width: 1, align: "center"}

                ]],
                fitColumns: true,
                toolbar: '#contract_datagrid_bt'

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
                        return "春"
                    }
                    if (value == 2) {
                        return "夏"
                    }
                    if (value == 3) {
                        return "秋"
                    }
                    if (value == 4) {
                        return "冬"
                    }
                }
            }

        }
    });

    $("#photo_group").combobox({

        onSelect: function () {
            var m;
            var begin = $("#aaa").datebox("getValues");
            var end = $("#bbb").datebox("getValues");
            var id = $("#becometime_group_count").combobox("getValues");
            var ids = $(this).combobox("getValues");
            if (ids == 'line') {

                if (begin == "" && end == "") {
                    m = "/contractLine_pictrue?typee=" + id[0];
                }
                if (end == "" && begin != "") {
                    m = "/contractLine_pictrue?typee=" + id[0] + "&beginDate=" + begin[0];
                }
                if (end != "" && begin == "") {
                    m = "/contractLine_pictrue?typee=" + id[0] + "&endDate=" + end[0];
                }
                if (end != "" && begin != "") {
                    m = "/contractLine_pictrue?typee=" + id[0] + "&beginDate=" + begin[0] + "&endDate=" + end[0];
                }
                window.open(m, "", "dialogWidth=500px;dialogHeight=500px");
            }
            if (ids == 'column') {
                if (begin == "" && end == "") {
                    m = "/contractCloumn_pictrue?typee=" + id[0];
                }
                if (end == "" && begin != "") {
                    m = "/contractCloumn_pictrue?typee=" + id[0] + "&beginDate=" + begin[0];
                }
                if (end != "" && begin == "") {
                    m = "/contractCloumn_pictrue?typee=" + id[0] + "&endDate=" + end[0];
                }
                if (end != "" && begin != "") {
                    m = "/contractCloumn_pictrue?typee=" + id[0] + "&beginDate=" + begin[0] + "&endDate=" + end[0];
                }

                window.open(m, "", "dialogWidth=500px;dialogHeight=500px");
            }
            if (ids == 'pie') {
                if (begin == "" && end == "") {
                    m = "/contractPie_pictrue?typee=" + id[0];
                }
                if (end == "" && begin != "") {
                    m = "/contractPie_pictrue?typee=" + id[0] + "&beginDate=" + begin[0];
                }
                if (end != "" && begin == "") {
                    m = "/contractPie_pictrue?typee=" + id[0] + "&endDate=" + end[0];
                }
                if (end != "" && begin != "") {
                    m = "/contractPie_pictrue?typee=" + id[0] + "&beginDate=" + begin[0] + "&endDate=" + end[0];
                }


                window.open(m, "", "dialogWidth=500px;dialogHeight=500px");
            }

        }
    })

});


function refresh() {
}

function yearFormatter(value, record, index) {
    if (value) {
        return value + "年"
    }
}



