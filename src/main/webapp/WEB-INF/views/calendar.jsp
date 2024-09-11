<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/14
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myfn" uri="http://crm.520it.com/myfn" %>
<html>
<head>
    <title>日历管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/js/fullcalendar/lib/cupertino/jquery-ui.min.css">
    <link href='/js/fullcalendar/fullcalendar.css' rel='stylesheet'/>
    <link rel="stylesheet" type="text/css" href="/js/fullcalendar/fullcalendar.print.css" media="print">
    <script src='/js/fullcalendar/lib/moment.min.js'></script>
    <script src='/js/fullcalendar/lib/jquery.min.js'></script>
    <script src='/js/fullcalendar/fullcalendar.min.js'></script>
    <script type="text/javascript" src="/js/views/calendar.js"></script>
    <script src='/js/fullcalendar/locale/zh-cn.js'></script>
    <script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
    <style>

        /* 语言选择 */
        #top {
            background: #eee;
            border-bottom: 1px solid #ddd;
            padding: 0 10px;
            line-height: 40px;
            font-size: 12px;
        }

        /* 日历 */
        #calendar {
            margin: 40px auto;
            padding: 0 10px;
        }

        /* Event 参数 className 的值 */
        .done:before {
            content: "【 已完成 】";
            background-color: yellow;
            color: green;
            text-align: center;
            font-weight: bold;
            width: 100%;
        }

        /* Event 参数 className 的值 */
        .doing:before {
            content: "【 未完成 】";
            background-color: yellow;
            color: red;
            text-align: center;
            font-weight: bold;
        }

    </style>
</head>
<body>
<div id='calendar'></div>


<div id="calendar_dialog">
    <form id="calendar_dialog_form" method="post">
        <input type="hidden" name="id"/>
        <table style="margin-top: 15px" align="center">
            <tr>
                <td>日程内容</td>
                <td>
                    <input name="title"/>
                </td>
            </tr>
            <tr>
                <td>开始时间</td>
                <td><input class="easyui-datebox" name="start"/></td>
            </tr>
            <tr>
                <td>结束时间</td>
                <td><input class="easyui-datebox" name="end"/></td>
            </tr>
            <tr>
                <td>背景颜色</td>
                <td>
                    <select name="color">
                        <option value="red">红</option>
                        <option value="yellow">黄</option>
                        <option value="blue">蓝</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>数据样式</td>
                <td>
                    <select name="className">
                        <option value="done">done</option>
                        <option value="doing">doing</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>文本颜色</td>
                <td>
                    <select name="textColor">
                        <option value="black">黑</option>
                        <option value="red">红</option>
                        <option value="pink">粉</option>
                        <option value="white">白</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="calendar_dialog_tb">
    <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
    <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="del">删除</a>
</div>
</body>
</html>
