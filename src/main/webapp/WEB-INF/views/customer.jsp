<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://crm.520it.com/myfn" prefix="myfn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>潜在客户管理</title>
    <%@include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/customer.js"></script>
</head>
<body>
<div id='loadingDiv' style="position: absolute; z-index: 1000; top: 0px; left: 0px;
width: 100%; height: 100%; background: white; text-align: center;">
    <!--下面的img可以自行修改-->.
    <img src="/js/jquery-easyui/themes/metro/images/loading.gif"
         style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;width:25px;height:25px;"/>
</div>
<script type="text/JavaScript">
    function closeLoading() {
        $("#loadingDiv").fadeOut("normal", function () {
            $(this).remove();
        });
    }

    var no;
    $.parser.onComplete = function () {
        if (no) clearTimeout(no);
        no = setTimeout(closeLoading, 100);
    }
</script>

<table id="customer_datagrid">
</table>
<div id="customer_toolbar">
    <div>
        <a data-cmd="add" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
        <a data-cmd="edit" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
        <c:if test="${myfn:checkPermission('cn.xy.crm.web.controller.CustomerController:moveToResourcePool')}">
            <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="moveToResource">移入资源池</a>
        </c:if>
        <a data-cmd="share" class="easyui-linkbutton" iconCls="icon-tip" plain="true">共享</a>
        <c:if test="${myfn:checkPermission('com._520it.crm.web.controller.CustomerController:transfer') }">
            <a data-cmd="transfer" class="easyui-linkbutton" iconCls="icon-tip" plain="true">移交</a>
        </c:if>
        <c:if test="${myfn:checkPermission('cn.xy.crm.web.controller.CustomerController:customerLost')}">
            <a  class="easyui-linkbutton" iconCls="icon-no" plain="true" data-cmd="lost">流失</a>
        </c:if>
        <a data-cmd="refresh" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="export">导出</a>
    </div>
    <div>
        关键字：<input type="text" name="keyword" placeholder="姓名和手机号"> 状态：<select name="state">
        <option value="" selected="selected">全部</option>
        <!-- 包括0和-1 -->
        <option value="1">正式客户</option>
        <option value="-2">流失客户</option>
        </select>
        <a data-cmd="search" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </div>
</div>

<div id="customer_dialog">
    <form id="customer_form" method="post">
        <input type="hidden" name="id">
        <table align="center" style="margin-top: 15px">
            <tr>
                <td>姓名</td>
                <td><input type="text" name="name"></td>
            </tr>
            <tr>
                <td>年龄</td>
                <td><input type="text" name="age"></td>
            </tr>
            <tr>
                <td>性别</td>
                <td><select name="gender">
                    <option value="1">男</option>
                    <option value="0">女</option>
                </select></td>
            </tr>
            <tr>
                <td>电话号码</td>
                <td><input type="text" name="tel"></td>
            </tr>
            <tr>
                <td>邮箱</td>
                <td><input type="text" name="email"></td>
            </tr>
            <tr>
                <td>QQ</td>
                <td><input type="text" name="qq"></td>
            </tr>
            <tr>
                <td>微信</td>
                <!--  id用来获取combobox的数组值，提交时传递 -->
                <td><input type="text" name="wechat"/></td>
            </tr>
            <tr>
                <td>职业</td>
                <td><input name="job" id="job" class="easyui-combobox"
                           data-options="valueField:'name',textField:'name',editable:false,url:'/customer_queryDicItem?id=1'">
                </td>
            </tr>
            <tr>
                <td>收入水平</td>
                <td><input name="salarylevel" id="salarylevel" class="easyui-combobox"
                           data-options="valueField:'name',textField:'name',editable:false,url:'/customer_queryDicItem?id=2'">
                </td>
            </tr>
            <tr>
                <td>客户来源</td>
                <td><input name="customersource" id="customersource" class="easyui-combobox"
                           data-options="valueField:'name',textField:'name',editable:false,url:'/customer_queryDicItem?id=3'">
                </td>
            </tr>
        </table>
    </form>
    <!-- 新增员工对话框窗口  -->
    <div id="customer_dialog_tb">
        <a data-cmd="save" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
        <a data-cmd="cancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">取消</a>
    </div>

</div>

<div id="transfer_dialog">
    <form id="transfer_form" method="post">
        <input type="hidden" name="customerId"/>
        <!--  当前潜在客户的id -->
        <%--inchargeuser.id--%>
        <input type="hidden" name="oldSellerId"/>
        <!-- 原市场专员的id -->
        <table align="center">
            <tr>
                <td>当前客户</td>
            </tr>
            <tr>
                <td><input type="text" name="name" readonly=“readonly” disabled/></td>
            </tr>
            <tr>
                <td>当前客户负责人</td>
            </tr>
            <tr>
                <td><input type="text" name="oldSellerName" readonly=“readonly”/></td>
            </tr>
            <tr>
                <td>移交给</td>
            </tr>
            <tr>
                <td><input name="newSellerId" class="easyui-combobox"
                           data-options="valueField:'id',textField:'username',editable:false,url:'/listCommissionerEmployees'"/>
                </td>
            </tr>
            <tr>
                <td>移交原因</td>
                <td><textarea name="transReason"></textarea></td>
            </tr>
        </table>
    </form>

    <div id="transfer_tb">
        <a data-cmd="transferSave" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
        <a data-cmd="transferCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">取消</a>
    </div>
</div>


</body>
</html>