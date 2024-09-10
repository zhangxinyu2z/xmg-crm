$(function () {
    var checkInDialog, checkInDatagrid, checkInDialogForm, checkInDatagridBtn, checkInForm_combogrid;
    checkInDialog = $("#checkIn_dialog");
    checkInDatagrid = $("#checkIn_datagrid");
    checkInDialogForm = $("#checkIn_dialog_form");
    checkInDatagridBtn = $("#checkIn_datagrid_btn a");
    checkInForm_combogrid = $("#checkInForm_combogrid");
    // 获取按钮的点击事件
    $("a").on("click", function () {
        var cmd = $(this).data("cmd");
        if (cmd) {
            cmdObj[cmd]();
        }
    });

    var cmdObj = {
        //    签退
        signOut: function () {
            $.get("/checkIn_signOut", function (data) {
                if (data.success) {
                    console.log()
                    $("#signout").linkbutton("disable");
                    //$.messager.alert("温馨提示", data.msg, "info");

                } else {
                    // $.messager.alert("温馨提示", data.msg, "warning");
                }
            });
        },

        //      签到
        signIn: function () {
            $.get("/checkIn_signIn", function (data) {
                if (data.success) {
                    $("#signin").linkbutton("disable");
                    //$.messager.alert("温馨提示", data.msg, "info");
                } else {
                    // $.messager.alert("温馨提示", data.msg, "warning");
                }
            });
        }
    };

});

