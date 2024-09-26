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
    <title>潜在客户管理</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/views/customerPotential.js"></script>
</head>
<body>
<table id="potentialCustomer_datagrid"></table>
<div id="potentialCustomer_datagrid_btn">
    <div>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.PotentialCustomerController:save')}">
            <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.PotentialCustomerController:update')}">
            <a id="potential_edit" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.PotentialCustomerController:updateInCharge')}">
            <a id="potential_share"  class="easyui-linkbutton" iconCls="icon-tip" plain="true" data-cmd="share">共享</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.PotentialCustomerController:ALL')}">
            <a id="potential_move"  class="easyui-linkbutton" iconCls="icon-tip" plain="true" data-cmd="turnOver">移交</a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.PotentialCustomerController:developFailed')}">
            <a id="potential_fail"  class="easyui-linkbutton" iconCls="icon-no" plain="true"
               data-cmd="developFalse">开发失败
            </a>
        </c:if>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.PotentialCustomerController:become')}">
            <a id="potential_become" class="easyui-linkbutton" iconCls="icon-ok" plain="true" data-cmd="become">转正</a>
        </c:if>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">刷新</a>
    </div>
    <div>
        <form id="potentialCustomer_searchForm">
            关键字：<input type="text" name="keyword" placeholder="姓名和手机号">
            状态：
            <select name="status">
                <option value="3">全部</option>
                <option value="-1">开发失败</option>
                <option value="0">潜在客户</option>
            </select>
            <a class="easyui-linkbutton" iconCls="icon-search" plain="true" data-cmd="searchContent">查询</a>
        </form>
    </div>
</div>
<div id="potentialCustomer_dialog">
    <form id="potentialCustomer_dialog_form" method="post">
        <input type="hidden" name="id"/>
        <table align="center">
            <tr>
                <td>用户名</td>
                <td><input class="easyui-validatebox" data-options="required:true,validType:'length[3,10]'"
                           name="name"/></td>
                <td>年龄</td>
                <td><input class="easyui-numberbox" required name="age"/></td>
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
                <td><input class="easyui-validatebox" data-options="required:true,validType:'email'" name="email"/></td>
                <td>QQ</td>
                <td><input name="qq"/></td>
            </tr>
            <tr>
                <td>微信</td>
                <td><input name="wechat"/></td>
                <td>职业</td>
                <td><input class="easyui-combobox" name="job"
                           data-options="url: '/sysDic_queryBySn?sn=job',valueField:'name',textField:'name'"/>
                </td>
            </tr>
            <tr>
                <td>收入水平</td>
                <td><input class="easyui-combobox" name="salarylevel"
                           data-options="url:'/sysDic_queryBySn?sn=salaryLevel', valueField:'name',textField:'name'"/>
                </td>
                <td>客户来源</td>
                <td><input class="easyui-combobox" name="customersource"
                           data-options=" url:'/sysDic_queryBySn?sn=customerSource', valueField:'name',textField:'name'">
                </td>
            </tr>
        </table>
    </form>
    <div id="potentialCustomer_dialog_bb">
        <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
    </div>
</div>

<!--共享和移交对话框-->
<div id="potential_shareOrturn_dialog">
    <form id="potential_shareOrturn_dialog_form" method="post">
        <input type="hidden" name="id"/>
        <input type="hidden" name="inchargeuser.id"/>
        <table align="center">
            <tr>
                <td>当前客户</td>
                <td><input name="name" disabled/></td>
            </tr>
            <tr>
                <td>当前客户负责人</td>
                <td><input name="inchargeuser.name" disabled/></td>
            </tr>
            <tr>
                <td>移交给</td>
                <td><input class="easyui-combobox" name="inchargeId"
                           data-options="url:'/listCommissionerEmployees',valueField:'id',textField:'username'"></td>
            </tr>
            <tr>
                <td>移交原因</td>
                <td><textarea name="reason">

                </textarea></td>
            </tr>
        </table>
    </form>
    <div id="potential_shareOrTransfer_dialog_bb">
        <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="shareOrTransferSave">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="shareOrTransferCancel">取消</a>
    </div>

</div>

</body>
</html>
