<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myFn" uri="http://crm.520it.com/myfn" %>
<html>
<head>
    <title>工资管理</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/salary.js"></script>
</head>
<body>
<table id="salary_datagrid">
    <thead>
    <tr>
        <th data-options="field:'xxx',width:100, align: 'center' ,formatter:employeeIdFormatter">员工编号</th>
        <th data-options="field:'year',width:100, align: 'center',formatter:dateYearFormatter">年份</th>
        <th data-options="field:'month',width:100, align: 'center',formatter:dateMonthFormatter">月份</th>
        <th data-options="field:'salary',width:100, align: 'center'">工资</th>
        <th data-options="field:'employee',width:100, align: 'center',formatter:employeeFormatter ">用户名称</th>
    </tr>
    </thead>
</table>

<c:if test="${myFn:checkPermission('com._520it.crm.web.controller.CheckInController:AddCheckIn')}">
    <div id="salary_datagrid_btn">
        <div>
            <form id="salary_searchForm">
                <p></p>
                年份：<input type="number" name="year" min="2000" max="2100">
                月份：<input type="number" name="month" min="1" max="12">
                员工姓名：<input type="text" name="keyword">
                <a plain="true" class="easyui-linkbutton" iconCls="icon-search" data-cmd="searchContent">查询</a>
            </form>
        </div>

        <div>
            <form method="post" id="salary_upload_form" enctype="multipart/form-data">
                <input type="file" name="file"/>
                <a class="easyui-linkbutton" iconCls="icon-print" data-cmd="uploadFile">导入excel文件</a>
            </form>
        </div>
    </div>
</c:if>
</body>
</html>
