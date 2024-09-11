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
    <title>客户资源池管理</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/customerResourcePool.js"></script>
</head>
<body>
<table id="customerResourcePool_datagrid"></table>
<div id="customerResourcePool_datagrid_btn">
    <div>

        <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="admit">吸纳</a>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">刷新</a>
    </div>
    <div>
        <form id="customerResourcePool_searchForm">
            关键字：<input type="text" name="keyword" placeholder="客户名称">
            <a class="easyui-linkbutton" iconCls="icon-search" data-cmd="searchContent">查询</a>
        </form>
    </div>
</div>
<div id="customerResourcePool_dialog">
    <form id="customerResourcePool_dialog_form" method="post">
        <input type="hidden" name="id"/>
        <table align="center">
            <tr>
                <td>用户名</td>
                <td><input name="name"/></td>
                <td>年龄</td>
                <td><input name="age"/></td>
            </tr>
            <tr>
                <td>性别</td>
                <td><select name="gender">
                    <option value="1">男</option>
                    <option value="0">女</option>
                </select></td>
                <td>电话</td>
                <td><input name="tel"/></td>

            </tr>
            <tr>
                <td>邮箱</td>
                <td><input name="email"/></td>
                <td>QQ</td>
                <td><input name="qq"/></td>
            </tr>
            <tr>
                <td>微信</td>
                <td><input name="wechat"/></td>
                <td>职业</td>
                <td><input class="easyui-combobox" name="job" data-options="url: '/customerResourcePool_query?sn=job',valueField:'name',textField:'name'"/>
                </td>
            </tr>
            <tr>
                <td>收入水平</td>
                <td><input class="easyui-combobox" name="salarylevel"
                           data-options="url:'/customerResourcePool_query?sn=salaryLevel', valueField:'name',textField:'name'"/></td>
                <td>客户来源</td>
                <td><input class="easyui-combobox" name="customersource"
                           data-options=" url:'/customerResourcePool_query?sn=customerSource', valueField:'name',textField:'name'">
                </td>
            </tr>
        </table>
    </form>
    <div id="customerResourcePool_dialog_bb">
        <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
    </div>
</div>
</body>
</html>
