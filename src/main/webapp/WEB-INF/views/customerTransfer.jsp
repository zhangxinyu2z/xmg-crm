<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://crm.520it.com/myfn" prefix="myfn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>客户移交记录管理</title>
<%@include file="common.jsp"%>
<%@include file="../tool/loadingDiv.jsp" %>
<script type="text/javascript" src="/js/views/customerTransfer.js"></script>
</head>
<body>
	<table id="customerTransfer_datagrid">
	</table>
	<div id="customerTransfer_toolbar">
		<div>
			<a data-cmd="add" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建客户移交</a>
			<a data-cmd="refresh" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>
		</div>
		<div>
			客户姓名：<input type="text" name="customername"> 开始时间：<input name="begintime" type="text" class="easyui-datebox" /> -<input name="endtime" type="text" class="easyui-datebox" />
			<a data-cmd="search" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</div>
	</div>

	<div id="transfer_dialog">
		<form id="transfer_form" method="post">
			<!--  当前客户的id -->
			<!-- 原市场专员id -->
			<!-- 新市场专员id -->
			<table align="center" style="margin-top: 15px;">
				<tr>
					<td>选择客户</td>
					<td><input name="customer.id" id="customerCombogrid" type="text"/></td>
				</tr>
				<tr>
					<td>市场专员</td>
					<td><input name="newseller.id" id="newsellerCombogrid" type="text" /></td>
				</tr>
				<tr>
					<td valign="top">移交原因</td>
					<td><textarea name="transreason" style="width: 240px; height: 180px; resize: none"></textarea></td>
				</tr>
			</table>
		</form>

		<div id="transfer_tb">
			<a data-cmd="transfersave" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
			<a data-cmd="transfercancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">取消</a>
		</div>
	</div>


</body>
</html>