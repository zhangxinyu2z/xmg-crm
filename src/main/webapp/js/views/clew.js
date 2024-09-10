$(function () {
        var clewDatagrid, clewDatagridBtn;
        clewDatagrid = $("#clew_datagrid");
        clewDatagridBtn = $("#clew_datagrid_btn a");

        clewDatagrid.datagrid({
            url: "clew_list",
            fit: true,
            rowNumbers: true,
            fitColumns: true,
            singleSelect: true,
            pagination: true,
            pageList: [1, 5, 10, 20],
            toolbar: "#clew_datagrid_btn",
            onDblClickRow: function (rowIndex, rowData) {
                var html = $.ajax({
                    url: "/clew_getContentById",
                    type: "POST",
                    data: "id=" + rowData.id,
                    async: false
                }).responseText;
                html = $.parseJSON(html);
                $("#clew_dialog").dialog({
                    title: "内容",
                    width: 400,
                    height: 300,
                    maximizable: true,
                    minimizable: true,
                    collapsible: true,
                    closed: true,
                    content: html.content
                });
                $("#clew_dialog").dialog("open");

            },
            columns: [[
                {field: "inputtime", title: "录入时间", width: 1, align: "center"},
                {field: "state", title: "状态", width: 1, align: "center"},
                {field: "title", title: "标题", width: 1, align: "center"},
                {field: "name", title: "信息来源", width: 1, align: "center"},
                {field: "url", title: "IP地址", width: 1, align: "center", formatter: URLFomrmatter},
                {field: "content", title: "内容", width: 1, align: "center"}
            ]],

        });

        $("#clew_dialog").dialog({
            title: "内容",
            width: 400,
            height: 300,
            maximizable: true,
            minimizable: true,
            collapsible: true,
            closed: true
        })


        function URLFomrmatter(value, rows, index) {
            if (value) {
                return "<a href='" + value + "'>" + value + "</a>"
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

            add: function () {
                $.messager.confirm("温馨提示", "重新加载Lucene库将要消耗比较长时间,你确定要重新加载吗?", function (yes) {
                    if (yes) {
                        // 发送请求去后台加载数据
                        $.get("/clew_loadFromDataSorue", function (data) {
                            if (data.success) {
                                // 加载成功后,重新加载数据
                                $.messager.alert("温馨提示", data.msg, "info");
                                clewDatagrid.datagrid("reload");
                            } else {
                                $.messager.alert("温馨提示", data.msg, "warning");
                            }
                        });
                    }

                })
            },

            searchContent: function () {
                // 创建一个json空对象:存取要查询的条件
                var param = {};
                // 获取表单中所有的数据的数组 [Object { name="keyword",  value=""}, Object {                         name="beginDate",  value=""}, Object { name="endDate",  value=""}]
                var paramArr = $("#clew_searchForm").serializeArray();
                for (var i = 0; i < paramArr.length; i++) {
                    param[paramArr[i].name] = paramArr[i].value;
                }
                // 根据查询的条件去重新查询后台,加载到前台中
                clewDatagrid.datagrid("load", param);

            }

            ,

            del: function () {
                // 返回选中的行对象
                var row = clewDatagrid.datagrid("getSelected");
                if (row) {
                    $.messager.confirm("温馨提示", "你确定要标记为无效数据吗", function (yes) {
                        if (yes) {
                            // 发送请求去后台删除数据
                            $.get("/clew_delete?id=" + row.id, function (data) {
                                if (data.success) {
                                    // 删除成功后,重新加载数据
                                    $.messager.alert("温馨提示", data.msg, "info");
                                    clewDatagrid.datagrid("reload");
                                } else {
                                    $.messager.alert("温馨提示", data.msg, "warning");
                                }
                            });
                        }
                    });

                } else {
                    $.messager.alert("温馨提示", "请选中要标记为无效数据的行!", "warning");
                }
            }
        }

    }
);




