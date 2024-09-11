<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/14
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myfn" uri="http://crm.520it.com/myfn" %>
<html>
<head>
    <title>正式客户开发计划</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/customerDevPlan.js"></script>
</head>
<body>
<table id="customerDevPlan_datagrid"></table>
<div id="customerDevPlan_datagrid_btn">
    <div>

        <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
        <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="del">删除</a>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">刷新</a>
    </div>
    <div>
        <form id="customerDevPlan_searchForm">
            客户名称：<input type="text" name="keyword">
            日期：<input class="easyui-datebox" name="beginDate">
            -
            <input class="easyui-datebox" name="endDate">
            跟进效果：
            <select name="traceresult">
                <option value="">全部</option>
                <option value="3">优</option>
                <option value="2">中</option>
                <option value="1">差</option>
            </select>
            <a class="easyui-linkbutton" iconCls="icon-search" data-cmd="searchContent">查询</a>
        </form>
    </div>
</div>
<div id="customerDevPlan_dialog">
    <form id="customerDevPlan_dialog_form" method="post">
        <input type="hidden" name="id"/>
        <table align="center">
            <tr>
                <td>客户选择</td>
                <td><select name="customer.id" id="customerDevPlan_select_combogrid" style="width: 150px;"></select>
                </td>
                <td>计划主题</td>
                <td><input name="plansubject"/></td>
            </tr>
            <tr>
                <td>计划时间</td>
                <td><input class="easyui-datebox" name="plantime"></td>
                <td>备注</td>
                <td><input name="remark"/></td>
            </tr>
            </tr>
            <tr>
                <td>跟进效果</td>
                <td><select name="traceresult" style="width: 60px;">
                    <option value="3">优</option>
                    <option value="2">中</option>
                    <option value="1">差</option>
                </select>
                </td>
                <td>实施方式</td>
                <td><input class="easyui-combobox" name="plantype"
                           data-options="url:'/potentialCustomer_query?sn=plantype', valueField:'name',textField:'name'"/>
                </td>
            </tr>
            <tr>
                <td colspan="2"> 详细计划内容:</td>
            </tr>
        </table>
        <div align="center">
            <textarea name="plandetails" rows="10" cols="40"></textarea>

        </div>

    </form>
    <div id="customerDevPlan_dialog_bb">
        <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
    </div>
</div>


</body>
</html>
