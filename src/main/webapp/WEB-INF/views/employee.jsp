<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://crm.520it.com/myfn" prefix="myfn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>员工管理</title>
    <%@include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/employee.js"></script>
</head>
<body>
<!-- 员工数据表单  -->
<table id="employee_datagrid"></table>
<!-- 数据表格的顶部按钮 -->
<div id="employee_datagrid_tb">
    <div>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.EmployeeController:save') }">
            <a data-cmd="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.EmployeeController:update') }">
            <a id="employee_datagrid_edit" data-cmd="edit" class="easyui-linkbutton"
               data-options="iconCls:'icon-edit',plain:true">编辑</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.EmployeeController:delete') }">
            <a id="employee_datagrid_del" data-cmd="del" class="easyui-linkbutton"
               data-options="iconCls:'icon-remove',plain:true">离职</a>
        </c:if>
        <a data-cmd="reload" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
        <a data-cmd="export" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">导出</a>
    </div>
    <div>
        关键字查询：<input type="text" name="keyword"/>
        <a data-cmd="find" class="easyui-linkbutton" data-options="iconCls:'icon-search' ,plain:true">查询</a>
    </div>
</div>
<!--  新增employee窗口 -->
<div id="employee_dialog" style="display:none;">
    <form id="employee_form" method="post">
        <input type="hidden" name="id">
        <table align="center" style="margin-top: 15px; text-align: center">
            <tr>
                <td>员工账号</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>真实姓名</td>
                <td><input type="text" name="realname"></td>
            </tr>
            <tr>
                <td>电话</td>
                <td><input type="text" name="tel"></td>
            </tr>
            <tr>
                <td>邮箱</td>
                <td><input type="text" name="email"></td>
            </tr>
            <tr>
                <td>部门</td>
                <td><input class="easyui-combobox" name="dept.id"
                           data-options="valueField:'id',textField:'name',url:'/dep_queryDept'"></td>
            </tr>
            <tr>
                <td>入职时间</td>
                <td><input type="text" name="inputtime" class="easyui-datebox"></td>
            </tr>
            <tr>
                <td>角色</td>
                <!--  id用来获取combobox的数组值，提交时传递 -->
                <td><input id="emp_roles" class="easyui-combobox"
                           data-options="valueField:'id',textField:'name',url:'/role_queryForEmp',multiple:true"></td>
            </tr>
        </table>
    </form>
</div>
<!-- 新增员工对话框窗口  -->
<div id="employee_dialog_tb">
    <a data-cmd="save" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
    <a data-cmd="cancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">取消</a>
</div>
</body>
</html>