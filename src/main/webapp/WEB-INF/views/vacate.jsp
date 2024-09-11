<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myfn" uri="http://crm.520it.com/myfn" %>
<html>
<head>
    <title>请假管理</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/vacate.js"></script>
</head>
<body>
<table id="vacate_datagrid">
    <thead>
    <tr>
        <th data-options="field:'employee',width:100, align: 'center',formatter:employeeFormatter">用户名称</th>
        <th data-options="field:'begintime',width:100, align: 'center'">请假时间</th>
        <th data-options="field:'endtime',width:100, align: 'center'">结束时间</th>
        <th data-options="field:'state',width:100, align: 'center', formatter: stateFormatter">状态</th>
        <th data-options="field:'audit',width:100, align: 'center', formatter:auditFormatter">审核人</th>
        <th data-options="field:'audittime',width:100, align: 'center'">审核时间</th>
    </tr>
    </thead>
</table>
<div id="vacate_datagrid_btn">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
        <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="del">删除</a>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.CheckInController:AddCheckIn')}">
            <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="audit">审核</a>
        </c:if>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">刷新</a>
    </div>
    <div>
        <form id="vacate_searchForm">
            日期：<input class="easyui-datetimebox" name="beginDate">
            -
            <input class="easyui-datetimebox" name="endDate">
            状态：
            <select name="state">
                <option value="-1">全部</option>
                <option value="0">待审核</option>
                <option value="1">已审核</option>
            </select>
            <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.CheckInController:AddCheckIn')}">
                员工姓名：<input type="text" name="keyword">
            </c:if>
            <a class="easyui-linkbutton" plain="true" iconCls="icon-search" data-cmd="searchContent">查询</a>
            <a class="easyui-linkbutton" plain="true" iconCls="icon-remove" data-cmd="resetSearchForm">重置</a>
        </form>
    </div>
</div>
<div id="vacate_dialog">
    <form id="vacate_dialog_form" method="post">
        <input type="hidden" name="id"/>
        <table align="center" style="margin-top: 15px">
            <tr>
                <td>用户名称</td>
                <td><select name="employee.id" id="vacateForm_combogrid" style="width: 150px;"></td>
            </tr>
            <!--  	<tr><td>状态</td>
                       <td><select name="state" class="easyui-combobox" data-options="disabled:true" >
                               <option value="0">未审核</option>
                               <option value="1">已审核</option>
                       </select></td></tr> -->

            <td>请假时间</td>
            <td><input required="required" class="easyui-datetimebox" name="begintime"></td>
            </tr>
            <tr>
                <td>结束时间</td>
                <td><input required="required" class="easyui-datetimebox" name="endtime"></td>
            </tr>
        </table>
    </form>
    <div id="vacate_dialog_bb">
        <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
    </div>
</div>
</body>
</html>
