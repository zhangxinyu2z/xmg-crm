$(function () {
    var salaryDialog, salaryDatagrid, salaryDialogForm, salaryDatagridBtn, salaryForm_combogrid;
    salaryDialog = $("#salary_dialog");
    salaryDatagrid = $("#salary_datagrid");
    salaryDatagridBtn = $("#salary_datagrid_btn a");
    salaryForm_combogrid = $("#salaryForm_combogrid");


    salaryDatagrid.datagrid({
        toolbar: '#salary_datagrid_btn',
        url: "salary_list",
        rownumbers: true,
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        pageList: [10, 20, 30]
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

        //上传Ecxel
        uploadFile: function () {
            $("#salary_upload_form").form('submit', {
                url: "salary_upload",
                success: function (data) {
                    var data = $.parseJSON(data);
                    if (data.success) {
                        //刷新数据
                        salaryDatagrid.datagrid("load");
                        $.messager.alert("温馨提示", data.msg, "info");
                    } else {
                        $.messager.alert("温馨提示", data.msg, "warning");
                    }
                }
            })
        },

        searchContent: function () {
            var param = {};
            var paramArr = $("#salary_searchForm").serializeArray();
            for (var i = 0; i < paramArr.length; i++) {
                param[paramArr[i].name] = paramArr[i].value;
            }
            // 根据查询的条件去重新查询后台,加载到前台中
            salaryDatagrid.datagrid("load", param);
        },
    }
});

// 显示员工的名称
function employeeFormatter(value, record, index) {
    //console.log(arguments);
    if (value) {
        return value.username;
    } else {
        return null;
    }
}

function employeeIdFormatter(value, record, index) {
    //console.log(arguments);
    if (record.employee) {
        return record.employee.id;
    } else {
        return null;
    }
}

function dateYearFormatter(value, record, index) {
    //console.log(arguments);
    if (record) {
        return new Date(record.date).getFullYear();
    } else {
        return null;
    }
}

function dateMonthFormatter(value, record, index) {
    if (record) {
        return (new Date(record.date).getMonth() + 1);
    } else {
        return null;
    }
}



