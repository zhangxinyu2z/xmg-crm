<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SystemDictionary</title>
<%@include file="common.jsp"%>
<script type="text/javascript" src="/js/views/systemDictionary.js"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="width: 700px; height: 350px;">
		<div data-options="region:'west',split:true" title="数据字典" style="width: 500px;">
			<table id="systemDictionary_datagrid"></table>
		</div>
		<div data-options="region:'center',title:'字典目录明细',iconCls:'icon-ok'">
			<table id="systemDictionaryItem_datagrid"></table>
			<div id="systemDictionaryItem_tb">
				<a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
				<a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
				<a id="forbiddenId" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="del">禁用</a>
				<a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="reload">刷新</a>
			</div>
		</div>
		<div id="systemDictionaryItem_dialog">
			<form id="systemDictionaryItem_form" method="post">
				<table align="center" style="margin-top: 15px;">
					<input type="hidden" name="id" />
					<tr>
						<td>字典名称</td>
						<td><input class="easyui-combobox" name="parent.id" data-options="valueField:'id',textField:'name',url:'/sysDic_queryAllDic'" /></td>
					</tr>
					<tr>
						<td>字典明细名称</td>
						<td><input type="text" name="name" /></td>
					</tr>
					<tr>
						<td valign="top">字典明细简介</td>
						<td><textarea style="width: 240px; height: 180px; resize: none" name="intro"></textarea></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="dictionaryItem_b">
			<a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
		</div>
	</div>
</body>
</html>