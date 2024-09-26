$(function () {

    $("#guarantee_datagrid").datagrid({
        url: '/guarantee_list',
        title: '保修单',
        fit: true,
        pagination: true,
        fitColumns: true,
        rownumbers: true,
        singleSelect: true,
        toolbar: '#guarantee_linkbutton',
        onClickRow: function (rowIndex, rowData) {
            //alert(1)
            $("#guaranteeItem_datagrid").datagrid("load", {
                'id': rowData.id
                //'url':'/guaranteeItem_list'
            });
            /*$.post("/guaranteeItem_list?id="+rowData.id,function(data){
                $("#guaranteeItem_datagrid").datagrid("load",data);
                //console.debug(data);
            });*/


        },
        columns: [
            [
                {field: 'sn', title: '保修单号', width: 1, align: 'center'},
                {field: 'productname', title: '产品名称', width: 1, align: 'center'},
                {field: 'customer', title: '保修客户', width: 1, formatter: customerFormatter, align: 'center'},
                {field: 'duetime', title: '质保到期时间', width: 1, align: 'center'},
                {field: 'remark', title: '备注', width: 1, align: 'center'},
            ]

        ]
    });


    //保修单面板
    $("#guarantee_dialog").dialog({

        width: 300,
        height: 300,
        closed: true,
        buttons: '#guarantee_linkbutton_dialog',
    });

    var objGuarantee = {

        //保修单延保
        add: function () {
            var rowData = $("#guarantee_datagrid").datagrid("getSelected");
            if (rowData) {
                console.debug(rowData);
                $.messager.confirm('温馨提示', '你确定要延保?', function (yes) {
                    if (yes) {
                        $.get("/guarantee_keep?id=" + rowData.id, function (data) {
                            //console.debug(data);
                            //data = parseJSON(data);
                            if (data.success) {
                                $.messager.alert('温馨提示', data.msg, 'info', function () {
                                    $("#guarantee_datagrid").datagrid("reload");
                                    $("#guaranteeItem_datagrid").datagrid("reload");
                                });
                            } else {
                                $.messager.alert('温馨提示', data.msg, 'error');
                            }


                        });
                    }
                });
            } else {
                $.messager.alert('温馨提示', "请选择需要延长保修期的数据", 'warning');
            }
        },


        //保修单删除
        del: function () {
            var rowData = $("#guarantee_datagrid").datagrid("getSelected");
            if (rowData) {
                $.messager.confirm('温馨提示', '你确定要删除?', function (yes) {
                    if (yes) {
                        $.get("/guarantee_delete?id=" + rowData.id, function (data) {
                            //console.debug(data);
                            //data = parseJSON(data);
                            if (data.success) {
                                $.messager.alert('温馨提示', data.msg, 'info', function () {
                                    $("#guarantee_datagrid").datagrid("reload");
                                    $("#guaranteeItem_datagrid").datagrid("reload");
                                });
                            } else {
                                $.messager.alert('温馨提示', data.msg, 'error');
                            }


                        });
                    }
                });
            } else {
                $.messager.alert('温馨提示', '请选择你需要删除的数据!', 'warning');
            }
        },


        //保修单编辑
        edit: function () {
            var rowData = $("#guarantee_datagrid").datagrid("getSelected");
            if (rowData) {
                //console.debug(rowData);
                $("#guarantee_dialog").dialog("open");
                $("#guarantee_dialog").dialog("setTitle", "保修编辑");
                $("#guarantee_from").form("clear");
                //客户回显
                if (rowData.customer) {
                    rowData["customer.id"] = rowData.customer.id;
                }
                //数据回显
                $("#guarantee_from").form("load", rowData);


            } else {
                $.messager.alert('温馨提示', '请选择你需要编辑的数据!', 'warning');
            }

        },


        //保修单保存操作
        save: function () {
            //alert(1)
            //提交表单
            $("#guarantee_from").form("submit", {
                url: '/guarantee_update',
                success: function (data) {
                    //console.debug(data);
                    data = $.parseJSON(data);
                    if (data.success) {
                        $.messager.alert('温馨提示', data.msg, 'info');
                        $("#guarantee_dialog").dialog("close");
                        $("#guarantee_datagrid").datagrid("reload");

                    } else {
                        $.messager.alert('温馨提示', data.msg, 'error');
                    }
                }

            });

        },


        /**
         * 高级查询:根据关键字,录入时间,员工状态查询
         */
        searchC: function () {
            // 创建一个json空对象:存取要查询的条件
            var param = {};

            // 获取表单中所有的数据的数组 [Object { name="keyword",  value=""}, Object {                         name="beginDate",  value=""}, Object { name="endDate",  value=""}]
            var paramArr = $("#searchCFrom").serializeArray();

            // 设置格式为 Object { keyword="",  beginDate="",  endDate=""}的json对象
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
                //(paramArr[i].name+"==="+paramArr[i].value)
            }
            // 根据查询的条件去重新查询后台,加载到前台中
            $("#guarantee_datagrid").datagrid("load", param);

        },


        //保修单取消
        cancelCount: function () {
            $("#guarantee_dialog").dialog("close");

        },

        //保修单刷新
        refresh: function () {
            $("#guarantee_datagrid").datagrid("reload");
        },


        //保修明细新增
        itemAdd: function () {

            $("#guaranteeItem_dialog").dialog("open");
            $("#guaranteeItem_dialog").dialog("setTitle", "保修明细新增");
            $("#guaranteeItem_from").form("clear");

            //数据回显
        },


        //保修单明细编辑
        itemEdit: function () {

            var rowData = $("#guaranteeItem_datagrid").datagrid("getSelected");
            //console.debug(rowData)
            if (rowData) {
                $("#guaranteeItem_dialog").dialog("open");
                $("#guaranteeItem_dialog").dialog("setTitle", "保修明细编辑");
                $("#guaranteeItem_from").form("clear");
                //保修单号hui回显
                if (rowData.guarantee) {
                    rowData['guarantee.id'] = rowData.guarantee.id;
                }
                ////数据回显
                $("#guaranteeItem_from").form("load", rowData);
            } else {
                $.messager.alert('温馨提示', "请选择你需要编辑的内容", 'error');
            }


        },


        //保修单明细保存或新曾
        itemSave: function () {
            var url;
            var id = $("#guaranteeItem_from input[name ='id']").val();
            //console.debug("id+==="+id);
            if (id) {
                //编辑
                url = '/guaranteeItem_update';
            } else {
                //新增
                url = '/guaranteeItem_save';
            }


            $("#guaranteeItem_from").form("submit", {
                url: url,
                success: function (data) {
                    console.debug(data);
                    data = $.parseJSON(data);
                    if (data.success) {
                        $.messager.alert('温馨提示', data.msg, 'info');
                        $("#guaranteeItem_dialog").dialog("close");
                        $("#guaranteeItem_datagrid").datagrid("reload");

                    } else {
                        $.messager.alert('温馨提示', data.msg, 'error');
                        $("#guaranteeItem_dialog").dialog("close");
                    }
                }

            });
        },

        //删除单条明细
        itemDel: function () {
            var rowData = $("#guaranteeItem_datagrid").datagrid("getSelected");
            if (rowData) {
                $.messager.confirm('温馨提示', '你确定要删除?', function (yes) {
                    if (yes) {
                        $.get("/guaranteeItem_delete?id=" + rowData.id, function (data) {
                            //console.debug(data);
                            //data = parseJSON(data);
                            if (data.success) {
                                $.messager.alert('温馨提示', data.msg, 'info', function () {
                                    $("#guaranteeItem_datagrid").datagrid("reload");
                                });
                            } else {
                                $.messager.alert('温馨提示', data.msg, 'error');
                            }


                        });
                    }
                });
            } else {
                $.messager.alert('温馨提示', "请选择需要删除的数据", 'error');
            }
        },


        //明细刷新
        itemRefresh: function () {
            $("#guaranteeItem_datagrid").datagrid("reload");
        },

        //明细取消按钮
        itemCancelCount: function () {
            $("#guaranteeItem_dialog").dialog("close");
        },

    };


    $("a[data-cmd]").on("click", function () {
        var cmd = $(this).data("cmd");
        //console.debug(cmd);
        if (cmd) {
            objGuarantee[cmd]();
        }
    });

});


//客户姓名回显
function customerFormatter(value, rows, index) {
    //console.debug(arguments)
    if (value) {
        return value.name;
    } else {
        return value;
    }
}


//保修单明细
$(function () {
    $("#guaranteeItem_datagrid").datagrid({
        title: '保修单明细',
        url: '/guaranteeItem_list',
        fit: true,
        fitColumns: true,
        rownumbers: true,
        singleSelect: true,
        toolbar: '#guaranteeItem_linkbutton',
        columns: [
            [
                {field: 'guaranteetime', title: '保修时间', width: 1},
                {field: 'content', title: '保修内容', width: 1},
                {field: 'status', title: '保修状态', width: 1, formatter: statusFormatter},
                {field: 'cost', title: '保修费用', width: 1, formatter: costFormatter},
                {field: 'guarantee', title: '保修单号', width: 1, formatter: guaranteeFormatter},
            ]
        ],
        onClickRow: function (rowIndex, rowData) {
            //var rows =  $("#guaranteeItem_datagrid").datagrid("getSelected");
            if (rowData.status == 1) {
                $("#guaranteeItem_linkbutton a[data-cmd =itemEdit]").linkbutton("disable");
            } else {
                $("#guaranteeItem_linkbutton a").linkbutton("enable");
            }
        }

    });

    $("#guaranteeItem_dialog").dialog({

        width: 300,
        height: 300,
        closed: true,
        buttons: '#guaranteeItem_linkbutton_dialog',
    });
})

//显示订单编号
function guaranteeFormatter(value, rows, index) {
    if (value) {
        return value.sn;
    } else {
        return value;
    }
}

//状态显示
function statusFormatter(value, rows, index) {
    if (value == 0) {
        return "<font color = 'red'>未完成</font>";
    } else if (value == 1) {
        return "<font color = 'blue'>完成</font>";
    } else if (value == 2) {
        return "<font color = 'green'>进行中</font>";
    }
}

//
function costFormatter(value, rows, index) {
    if (value == 0) {
        return "<font color = 'red'>免费保修</font>";
    } else {
        return value;
    }
}
