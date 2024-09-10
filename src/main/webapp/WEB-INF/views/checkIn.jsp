<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myfn" uri="http://www.crm.cn/java/crm" %>
<html>
<head>
    <title>考勤签到管理</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/checkIn.js"></script>
</head>
<body>
<table id="checkIn_datagrid">
    <thead>
    <tr>
        <th data-options="field:'employee',width:100, align: 'center',formatter:employeeFormatter">用户名称</th>
        <th data-options="field:'userip',width:100, align: 'center'">签到IP</th>
        <th data-options="field:'signintime',width:100, align: 'center', formatter: signInFormatter">签到时间</th>
        <th data-options="field:'signouttime',width:100, align: 'center', formatter: signOutFormatter">签退时间</th>
        <th data-options="field:'state',width:100, align: 'center', formatter: stateFormatter">状态</th>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.CheckInController:AddCheckIn')}">
            <th data-options="field:'checkman',width:100, align: 'center', formatter:checkmanFormatter">补签人</th>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.CheckInController:AddCheckIn')}">
            <th data-options="field:'checktime',width:100, align: 'center'">补签时间</th>
        </c:if>
    </tr>
    </thead>

</table>
<div id="checkIn_datagrid_btn">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="signIn">签到</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="signOut">签退</a>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.CheckInController:addCheckIn')}">
            <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="check">补签</a>
        </c:if>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">刷新</a>
    </div>
    <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.CheckInController:AddCheckIn')}">
        <div>
            <form id="checkIn_searchForm">
                日期：<input class="easyui-datetimebox" name="beginDate">
                -
                <input class="easyui-datetimebox" name="endDate">
                状态：
                <select name="state">
                    <option value="-1">全部</option>
                    <option value="1">正常</option>
                    <option value="2">迟到</option>
                    <option value="3">早退</option>
                    <option value="4">迟到+早退</option>
                </select>
                员工姓名：<input type="text" name="keyword">
                <a class="easyui-linkbutton" plain="true" iconCls="icon-remove" data-cmd="resetSearchForm">重置</a>
                <a class="easyui-linkbutton" plain="true" iconCls="icon-search" data-cmd="searchContent">查询</a>
            </form>
        </div>
    </c:if>
</div>
<div id="checkIn_dialog">
    <form id="checkIn_dialog_form" method="post">
        <input type="hidden" name="id"/>
        <table align="center">
            <tr>
                <td>用户名称</td>
                <td><select name="employee.id" id="checkInForm_combogrid" style="width: 150px;"></td>
            </tr>
            <tr>
                <td>状态</td>
                <td><select name="state">
                    <option value="2">迟到</option>
                    <option value="3">早退</option>
                    <option value="4">迟到+早退</option>
                </select></td>
            </tr>
            <tr>
                <td>签到时间</td>
                <td><input class="easyui-datetimebox" name="signintime"></td>
            </tr>
            <tr>
                <td>签退时间</td>
                <td><input class="easyui-datetimebox" name="signouttime"></td>
            </tr>
        </table>
    </form>
    <div id="checkIn_dialog_bb">
        <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
    </div>
</div>
</body>
</html>
