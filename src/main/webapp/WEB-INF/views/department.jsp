<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部门管理</title>
<%@include file="common.jsp"%>
<script type="text/javascript" src="/js/views/department.js"></script>

</head>
<body>
	<!-- 员工数据表单  -->
	<table id="department_datagrid"></table>
	<!-- 数据表格的顶部按钮 -->
	<div id="department_datagrid_tb">
		<div>
			<a data-cmd="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
			<a id="department_datagrid_edit" data-cmd="edit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
			<a id="department_datagrid_del" data-cmd="del" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">停用</a>
			<a data-cmd="reload" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
		</div>
		<div>
			关键字查询：<input type="text" name="keyword" />
			状态：<select name="state">
				<option value=""  selected = "selected" >全部</option>
				<option value="1">正常</option>
				<option value="0">停用</option>
			</select>
			<a data-cmd="find" class="easyui-linkbutton" data-options="iconCls:'icon-search' ,plain:true">查询</a>
		</div>
	</div>
	<!--  新增department窗口 -->
	<div id="department_dialog">
		<form id="department_form" method="post">
			<input type="hidden" name="id" />
			<input type="hidden" name="state"/>
			<table align="center" style="margin-top: 15px; text-align: center">
				<tr>
					<td>部门编号：</td>
					<td><input type="text" name="sn"></td>
				</tr>
				<tr>
					<td>部门名称：</td>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
					<td>部门经理：</td>
					<td><input id="managerList" type="text" name="manager.id"></td>
				</tr>
				<tr>
					<td>上级部门：</td>
					<td><input id="parentDept" class="easyui-combobox" type="text" name="parent.id"></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 新增员工对话框窗口  -->
	<div id="department_dialog_tb">
		<a data-cmd="save" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
		<a data-cmd="cancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">取消</a>
	</div>
</body>
</html>