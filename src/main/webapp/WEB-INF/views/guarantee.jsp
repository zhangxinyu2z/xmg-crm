<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/guarantee.js"></script>
    <title>保修管理</title>
</head>
<body>

<div style="height:300px">
    <table id="guarantee_datagrid"></table>
</div>

<div style="height: 300px">
    <table id="guaranteeItem_datagrid"></table>
</div>

<!--保修单按钮  -->
<div id="guarantee_linkbutton">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
        <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="del">删除</a>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">刷新</a>
        <a class="easyui-linkbutton" iconCls="icon-help" plain="true" data-cmd="add">延保</a>
    </div>
    <form id="searchCFrom" method="post">
        <div>
            &nbsp;&nbsp;关键字查询：<input type="text" name="keyword" placeholder="请输入需要查询的内容"/>
            <a class="easyui-linkbutton" iconCls="icon-search" plain="true" data-cmd="searchC">查询</a>
        </div>
    </form>
</div>


<!--保修单对话框按钮  -->
<div id="guarantee_linkbutton_dialog">
    <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancelCount">取消</a>
</div>

<!--保修单列表  -->
<div id="guarantee_dialog">
    <form id="guarantee_from" method="post">
        <table align="center" style="margin-top: 20px">
            <input type="hidden" name="id"/>
            <tr>
                <td>保修单号</td>
                <td><input type="text" name="sn" readonly="readonly"/></td>
            </tr>

            <tr>
                <td>产品名称</td>
                <td><input type="text" name="productname"/></td>
            </tr>

            <tr>
                <td>质保到期时间</td>
                <td>
                    <input name="duetime" class="easyui-datebox" required data-options="validType:'md[\'10/11/2012\']'"
                           readonly="readonly"/>
                </td><!-- 日期格式 -->
            </tr>

            <tr>
                <td>备注</td>
                <td><input type="text" name="remark"/></td>
            </tr>

            <tr>
                <td>保修客户</td>
                <td><input class="easyui-combobox"
                           name="customer.id"
                           data-options="url:'customer_queryByName',valueField:'id',textField:'name'"
                           readonly="readonly"></td><!--下拉框  -->
            </tr>
        </table>
    </form>
</div>


<!-- 保修单明细对话框 -->
<div id="guaranteeItem_dialog">
    <form id="guaranteeItem_from" method="post">
        <table align="center" style="margin-top: 25px">

            <input type="hidden" name="id"/>
            <tr>
                <td>保修单号</td>
                <td><input class="easyui-combobox" name="guarantee.id"
                           data-options=" url: '/guaranteeitem_queryName',valueField:'id',textField:'sn'"></td>
            </tr>
            <tr>
                <td>保修时间</td>
                <td>
                    <input name="guaranteetime" class="easyui-datebox" required
                           data-options="validType:'md[\'10/11/2012\']'"/>
                </td>
            </tr>

            <tr>
                <td>保修内容</td>
                <td><input type="text" name="content"/></td>
            </tr>

            <tr>
                <td>保修状态</td>
                <td><select class="easyui-combobox" name="status" style="width:150px;">
                    <option value="0">未完成</option>
                    <option value="1">完成</option>
                    <option value="2">进行中</option>
                </select></td>
            </tr>

            <tr>
                <td>保修费用</td>
                <td><input type="text" name="cost"/></td>
            </tr>

        </table>
    </form>
</div>

<div id="guaranteeItem_linkbutton_dialog">
    <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="itemSave">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="itemCancelCount">取消</a>
</div>

<!--保修单明细按钮  -->
<div id="guaranteeItem_linkbutton">
    <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="itemAdd">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="itemEdit">编辑</a>
    <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="itemDel">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="itemRefresh">刷新</a>
</div>
</body>
</html>