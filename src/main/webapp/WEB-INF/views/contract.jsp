<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/14
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myfn" uri="http://www.crm.cn/java/crm" %>
<html>
<head>
    <title>合同管理</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/contract.js"></script>
</head>
<body>
<table id="contract_datagrid"></table>
<div id="contract_datagrid_btn">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
        <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="del">删除</a>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">刷新</a>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.ContractController:checked')}">
            <a class="easyui-linkbutton" iconCls="icon-tip" plain="true" data-cmd="checked">审核</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.ContractController:departmentChecked')}">
            <a class="easyui-linkbutton" iconCls="icon-cog" plain="true" data-cmd="checked">部门审核</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.ContractController:financeChecked')}">
            <a class="easyui-linkbutton" iconCls="icon-user" plain="true" data-cmd="checked">财务审核</a>
        </c:if>
        <a class="easyui-linkbutton" iconCls="icon-no" plain="true" data-cmd="no_checked">拒绝审核</a>
        <a class="easyui-linkbutton" iconCls="icon-redo" plain="true" data-cmd="doGuarantee">生成维修单</a>
    </div>
    <div>
        <form id="contract_searchForm">
            关键字：<input type="text" name="keyword">
            日期：<input class="easyui-datebox" name="beginDate"> ~ <input class="easyui-datebox" name="endDate">

            订单状态：
            <select name="status">
                <option value="">全部</option>
                <option value="4">拒绝审核</option>
                <option value="3">已审核</option>
                <option value="2">财务未审核</option>
                <option value="1">部门未审核</option>
                <option value="0">初始录入</option>
            </select>
            <a class="easyui-linkbutton" plain="true" iconCls="icon-search" data-cmd="searchContent">查询</a>
        </form>
    </div>
</div>
<div id="contract_dialog">
    <form id="contract_dialog_form" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id"/>
        <table align="center" style="margin-top: 15px">
            <tr>
                <td>合同客户</td>
                <td>
                    <select id="contract_customer_combogrid" class="easyui-validatebox" required name="customer.id"
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
                    <select id="contract_seller_combogrid" class="easyui-validatebox" required name="seller.id"
                            style="width: 150px;"></select>
                </td>
            </tr>
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
                <td>附件</td>
                <td><input type="file" name="pic"/></td>
            </tr>
            <tr>
                <td>合同摘要</td>
                <td><textarea name="intro"></textarea></td>
            </tr>
        </table>
    </form>
    <div id="contract_guaranteeDialog">
        <form id="contract_guaranteeDialog_form" method="post">
            <table align="center" style="margin-top: 10px">
                <tr>
                    <td>产品名称</td>
                    <td><input name="productname"/></td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td><input name="remark"/></td>
                </tr>
            </table>
        </form>
    </div>

    <div id="contract_dialog_b">
        <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
    </div>
    <div id="contract_guaranteeDialog_b">
        <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="guarantee_save">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="guarantee_cancel">取消</a>
    </div>
</div>

</body>
</html>
