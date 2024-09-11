<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/14
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="myFn" uri="http://crm.520it.com/myfn" %>
<html>
<head>
    <title>订单管理</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/orderBill.js"></script>
</head>
<body>
<table id="orderBill_datagrid"></table>
<div id="orderBill_datagrid_btn">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
        <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="del">删除</a>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">刷新</a>
        <c:if test="${myFn:checkPermission('com._520it.crm.web.controller.OrderBillController:checked')}">
            <a class="easyui-linkbutton" iconCls="icon-tip" plain="true" data-cmd="checked">审核</a>
        </c:if>
        <c:if test="${myFn:checkPermission('com._520it.crm.web.controller.OrderBillController:departmentChecked')}">
            <a class="easyui-linkbutton" iconCls="icon-cog" plain="true" data-cmd="checked">部门审核</a>
        </c:if>
        <c:if test="${myFn:checkPermission('com._520it.crm.web.controller.OrderBillController:financeChecked')}">
            <a class="easyui-linkbutton" iconCls="icon-user" plain="true" data-cmd="checked">财务审核</a>
        </c:if>
        <a class="easyui-linkbutton" iconCls="icon-no" plain="true" data-cmd="no_checked">拒绝审核</a>
        <a class="easyui-linkbutton" iconCls="icon-undo" plain="true" data-cmd="doContract">生成合同</a>
    </div>
    <div>
        <form id="orderBill_searchForm">
            关键字：<input type="text" name="keyword">
            日期：<input class="easyui-datebox" name="beginDate"> ~ <input class="easyui-datebox" name="endDate">

            订单状态：
            <select name="status">
                <option value="">全部</option>
                <option value="5">已生成合同</option>
                <option value="4">已退款</option>
                <option value="3">审核通过</option>
                <option value="2">财务未审核</option>
                <option value="1">部门未审核</option>
                <option value="0">初始录入</option>
            </select>
            <a class="easyui-linkbutton" plain="true" iconCls="icon-search" data-cmd="searchContent">查询</a>
        </form>
    </div>
</div>
<div id="orderBill_dialog">
    <form id="orderBill_dialog_form" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id"/>
        <table align="center" style="margin-top: 20px">
            <tr>
                <td>定金客户</td>
                <td>
                    <select id="orderBill_customer_combogrid" class="easyui-validatebox" required name="customer.id"
                            style="width: 150px;"></select>
                </td>
            </tr>
            <tr>
                <td>签订时间</td>
                <td><input class="easyui-datebox" name="signtime"/></td>
            </tr>
            <tr>
                <td>营销人员</td>
                <td>
                    <select id="orderBill_seller_combogrid" class="easyui-validatebox" required name="seller.id"
                            style="width: 150px;"></select>
                </td>
            </tr>
            <tr>
                <td>总金额</td>
                <td><input class="easyui-numberbox" required name="totalsum"/></td>
            </tr>
            <tr>
                <td>定金金额</td>
                <td><input class="easyui-numberbox" required name="bargainmoney"/></td>
            </tr>
            <tr>
                <td>附件</td>
                <td><input type="file" name="pic"/></td>
            </tr>
            <tr>
                <td>摘要</td>
                <td>
                    <textarea name="intro"></textarea>
                </td>
            </tr>
        </table>
    </form>


    <div id="orderBill_contractDialog">
        <form id="orderBill_contractDialog_form" method="post" enctype="multipart/form-data">
            <table align="center" style="margin-top: 15px">
                <tr>
                    <td>合同金额</td>
                    <td><input class="easyui-numberbox" required name="contractsum"/></td>
                </tr>
                <tr>
                    <td>付款金额</td>
                    <td><input class="easyui-numberbox" required name="money"/></td>
                </tr>
                <tr>
                    <td>付款时间</td>
                    <td><input class="easyui-datebox" name="paytime"/></td>
                </tr>
                <tr>
                    <td>摘要</td>
                    <td><textarea name="intro"></textarea></td>
                </tr>
            </table>
        </form>

        <div id="orderBill_dialog_b">
            <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
            <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
        </div>

        <div id="orderBill_contractDialog_b">
            <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="contract_save">保存</a>
            <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="contract_cancel">取消</a>
        </div>
    </div>


</body>
</html>
