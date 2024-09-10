$(function () {
    var calendar_dialog, calendar_dialog_form;
    calendar_dialog = $("#calendar_dialog");
    calendar_dialog_form = $("#calendar_dialog_form");

    calendar_dialog.dialog({
        width: 350,
        height: 250,
        closed:true,
        top: 200,
        toolbar:"#calendar_dialog_tb"
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

    var obj = {};
    $(document).ready(function () {
        var initialLangCode = 'en';

        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            weekends: true,
            weekMode: 'liquid',
            defaultView: 'month',
            allDayText: '全天',
            businessHours: true,
            defaultEventMinutes: 120,
            eventLimit: true,
            dayClick: function (date) {
                // 打开新增对话框
                calendar_dialog.dialog("open");
                // 设置标题为新增
                calendar_dialog.dialog("setTitle", "新增");
                calendar_dialog_form.form("clear");
            },
            events: {
                url: "/calendar_list"
            },
            //设置是否可被单击或者拖动选择
            selectable: true,
            //点击或者拖动选择时，是否显示时间范围的提示信息，该属性只在agenda视图里可用
            selectHelper: true,
            //点击或者拖动选中之后，点击日历外的空白区域是否取消选中状态 true为取消 false为不取消，只有重新选择时才会取消
            unselectAuto: true,
            select: function (start, end) {
                //do something here...
                console.log('select触发的开始时间为：', start.format());
                console.log('select触发的结束时间为：', end.format());
            },
            eventClick: function (event) {
                // 打开编辑对话框
                calendar_dialog.dialog("open");
                // 打开编辑对话框
                calendar_dialog.dialog("setTitle", "编辑");
                // 清除表单的缓存数据
                calendar_dialog_form.form("clear");
                console.log(event);
                if (event) {
                    obj["id"] = event.id;
                }
                if (event) {
                    obj["title"] = event.title;
                }
                if (event) {
                    obj["start"] = event._start._i;
                }
                if (event) {
                    obj["allDay"] = event._allDay;
                }
                if (event) {
                    obj["color"] = event.color;
                }
                if (event) {
                    obj["end"] = event._end._i;
                }
                if (event) {
                    obj["className"] = event.className;
                }
                if (event) {
                    obj["textColor"] = event.textColor;
                }
                calendar_dialog_form.form("load", obj);
            },
            eventMouseover: function (event) {
                //do something here...
                console.log('鼠标经过 ...');
                console.log('eventMouseover被执行，选中Event的title属性值为：', event.title);
                // ...
            },
            eventMouseout: function (event) {
                //do something here...
                console.log('eventMouseout被执行，选中Event的title属性值为：', event.title);
                console.log('鼠标离开 ...');
                // ...
            },
            //Event是否可被拖动或者拖拽
            editable: true,
            //Event被拖动时的不透明度
            dragOpacity: 0.5,
            eventDrop: function (event, dayDelta, revertFunc) {
                //do something here...
                console.log('eventDrop --- start ---');
                console.log('eventDrop被执行，Event的title属性值为：', event.title);
                if (dayDelta._days != 0) {
                    console.log('eventDrop被执行，Event的start和end时间改变了：', dayDelta._days + '天！');

                    //动态拖拽
                    $.get("/calendar_updateDayDelta?id=" + event.id+"&start="+dayDelta._days + "&end="+dayDelta._days, function (data) {
                        if (data.success) {
                            window.location.reload();
                        } else {
                            $.messager.alert("温馨提示", data.msg, "warning");
                        }
                    });


                } else if (dayDelta._milliseconds != 0) {
                    console.log('eventDrop被执行，Event的start和end时间改变了：', dayDelta._milliseconds / 1000 + '秒！');
                } else {
                    console.log('eventDrop被执行，Event的start和end时间没有改变！');
                }
                //revertFunc();
                console.log('eventDrop --- end ---');
                // ...


            },
            eventResize: function (event, dayDelta, revertFunc) {
                //do something here...
                console.log(' --- start --- eventResize');
                console.log('eventResize被执行，Event的title属性值为：', event.title);
                if (dayDelta._days != 0) {
                    console.log('eventResize被执行，Event的start和end时间改变了：', dayDelta._days + '天！');
                } else if (dayDelta._milliseconds != 0) {
                    console.log('eventResize被执行，Event的start和end时间改变了：', dayDelta._milliseconds / 1000 + '秒！');
                } else {
                    console.log('eventResize被执行，Event的start和end时间没有改变！');
                }
                //revertFunc();
                console.log('--- end --- eventResize');
                // ...
            },
            aspectRatio: 2.0
        });

        //初始化语言选择的下拉菜单值
        $.each($.fullCalendar.langs, function (langCode) {
            $('#lang-selector').append(
                $('<option/>')
                    .attr('value', langCode)
                    .prop('selected', langCode == initialLangCode)
                    .text(langCode)
            );
        });

        //当选择一种语言时触发
        $('#lang-selector').on('change', function () {
            if (this.value) {
                $('#calendar').fullCalendar('option', 'lang', this.value);
            }
        });
    });

    var cmdObj = {
        cancel: function () {
            calendar_dialog.dialog("close");
        },

        save: function () {
            var url = null;
            // 根据id,设置发送的是保存还是更新请求的地址
            var id = $("input[name=id]").val();
            if (id) {
                url = "/calendar_update"
            } else {
                url = "/calendar_save"
            }
            // 表单的提交操作
            calendar_dialog_form.form("submit", {
                url: url,
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        $.messager.alert("温馨提示", data.msg, "info");
                        calendar_dialog.dialog("close");
                        window.location.reload();
                    } else {
                        $.messager.alert("温馨提示", data.msg, "warning");
                    }
                }
            });
        },
        
        del:function (event) {
            $.messager.confirm("温馨提示", "你确定要删除该数据吗", function (yes) {
                if (yes) {
                    // 发送请求去后台删除数据
                    $.get("/calendar_delete?id=" + obj.id, function (data) {
                        if (data.success) {
                            // 删除成功后,重新加载数据
                            calendar_dialog.dialog("close");
                            window.location.reload();
                        } else {
                            $.messager.alert("温馨提示", data.msg, "warning");
                        }
                    });
                }
            });
        }
    }
});
