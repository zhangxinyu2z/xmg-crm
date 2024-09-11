
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myfn" uri="http://crm.520it.com/myfn" %>
<html>
<head>
    <title>考勤记录管理</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/attendance.js"></script>
</head>
<body>
<table id="attendance_datagrid">
	 <thead>   
        <tr>   
            <th data-options="field:'employee',width:100, align: 'center' ,formatter:employeeFormatter">用户名称</th>   
            <th data-options="field:'date',width:100, align: 'center'">日期</th>   
            <th data-options="field:'signinday',width:100, align: 'center' ">出勤天数</th>   
            <th data-options="field:'lateday',width:100, align: 'center'">迟到天数</th>  
            <th data-options="field:'earlyday',width:100, align: 'center'">早退天数</th> 
            <th data-options="field:'vacateday',width:100, align: 'center'">请假次数</th> 
        </tr>   
    </thead>
</table>
<div id="attendance_datagrid_btn">
    <div>
            <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.CheckInController:addCheckIn')}">
            <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
            <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
           	<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="del">删除</a>
            <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">刷新</a>
            <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="loadSignIn">导入考勤记录</a>
           	</c:if>
    </div>
    
    <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.CheckInController:AddCheckIn')}">
    <div>
        <form id="attendance_searchForm">
           员工姓名：<input type="text" name="keyword">
            <a class="easyui-linkbutton" plain="true" iconCls="icon-search" data-cmd="searchContent">查询</a>
            <%--<a class="easyui-linkbutton" plain="true" iconCls="icon-remove" data-cmd="resetSearchForm">重置</a>--%>
      	    <a class="easyui-linkbutton"  iconCls="icon-print" data-cmd="exportAttendance">导出到Excel表</a>
        </form>
    </div>
    </c:if>
</div>

<div id="attendance_dialog">
    <form id="attendance_dialog_form" method="post">
        <input type="hidden" name="id"/>
        <table align="center">
        	<tr>
                <td>用户名称</td>
                	<td><select name="employee.id"  id="attendanceForm_combogrid"  style="width: 150px;"></select></td>
            	</tr> 
            	<tr>
	                <td>日期</td>
	                	<td><input class="easyui-datetimebox" name="date"></td>
	            	</tr>
            	<tr>
                	<td>出勤次数</td>
               		<td><input type="number" min="0" max="31" name="signinday"></td>
            	</tr> 
            	<tr>
                	<td>迟到次数</td>
               		<td><input type="number" min="0" max="31" name="lateday"></td>
            	</tr> 
            	<tr>
                	<td>早退次数</td>
               		<td><input type="number" min="0" max="31" name="earlyday"></td>
            	</tr> 
        </table> 
    </form>
    <div id="attendance_dialog_bb">
        <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
    </div>
</div>
</body>
</html>
