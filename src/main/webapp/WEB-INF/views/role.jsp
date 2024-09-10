<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>角色管理</title>
<%@include file="common.jsp"%>
<script type="text/javascript" src="/js/views/role.js"></script>

</head>
<body>
	<!-- 员工数据表单  -->
	<table id="role_datagrid"></table>
	<!-- 数据表格的顶部按钮 -->
	<div id="role_datagrid_tb">
		<div>
			<a data-cmd="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
			<a id="role_datagrid_edit" data-cmd="edit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
			<a id="role_datagrid_del" data-cmd="del" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
			<a data-cmd="reload" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
		</div>
		<div>
			关键字查询：<input type="text" name="keyword" />
			<a data-cmd="find" class="easyui-linkbutton" data-options="iconCls:'icon-search' ,plain:true">查询</a>
		</div>
	</div>
	<!--  新增role窗口 -->
	<div id="role_dialog">
		<form id="role_form" method="post">
			<input type="hidden" name="id" />
			<table align="center" style="margin-top: 15px">
				<tr>
					<td>角色编号<input type="text" name="sn"></td>
					<td>角色名称<input type="text" name="name"></td>
				</tr>
				<tr>
					<td><table id="allPermissions"></table></td>
					<td><table id="selfPermissions"></table></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 新增员工对话框窗口  -->
	<div id="role_dialog_tb">
		<a data-cmd="save" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
		<a data-cmd="cancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">取消</a>
	</div>
</body>
</html>