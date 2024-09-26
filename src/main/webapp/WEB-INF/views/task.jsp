<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myfn" uri="http://crm.520it.com/myfn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="common.jsp"%>
    <title>今日计划</title>
    <script type="text/javascript" src="/js/views/task.js"></script>
</head>
<body>
<table id="task_tb"></table>
<div id="task_tabletools">
    <div>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.TaskController:save')}">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" data-cmd="add">分配任务</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.TaskController:update')}">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" data-cmd="eidte">编辑任务</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.TaskController:delete')}">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" data-cmd="del">删除任务</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.TaskController:complete')}">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" data-cmd=complete>标记完成</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.TaskController:lose')}">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-no',plain:true" data-cmd="lose">标记失败</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.TaskController:addDescribe')}">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" data-cmd="adddes">添加处理描述</a>
        </c:if>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cut',plain:true" data-cmd="editdes">修改处理描述</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" data-cmd="reflush">刷新</a>
    </div>
    <div>
        <form id="searchbut">
            员工姓名：<input type="text" name="keyword">
            开始时间：<input class="easyui-datebox" name="start"/>
            结束时间：<input class="easyui-datebox" name="end"/>
            <a class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-undo'" data-cmd="searchPage">查询</a>
        </form>
    </div>

</div>
<div id="task_dialogbut">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" data-cmd="tosave">保存</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" data-cmd="tocancel">取消</a>
</div>
<div id="handle_dialogbut">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" data-cmd="newhandle">保存</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" data-cmd="handlecancel">取消</a>
</div>
<div id="task_dialog">
    <form id="taskform" method="post">
        <input type="hidden" name="id">
        <table align="center" style="margin-top: 20px;">
            <tr>
                <td>员工选择:</td>
                <td><input id="employeesele" type="text" name="handleuser.id"></td>
            </tr>
            <tr>
                <td>计划任务结束时间</td>
                <td><input type="text" name="end" class="easyui-datebox"></td>
            </tr>
            <tr align="">
                <td >任务描述</td> <%--colspan="2"--%>
                <td><textarea name="title" style="height:150px;width: 200px;"></textarea></td>
            </tr>
            <tr align="center">

            </tr>
        </table>
    </form>
</div>
<!-- 编辑处理描述 -->
<div id="handle_dialog">
    <form id="handle_form" method="post">
        <input type="hidden" name="aid">
        <table align="center" style="margin-top: 40px;">
            <tr align="center">
                <td colspan="2">处理描述</td>
            </tr>
            <tr align="center">
                <td colspan="2"><textarea name="remark" style="height:150px;width: 200px;"></textarea></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>