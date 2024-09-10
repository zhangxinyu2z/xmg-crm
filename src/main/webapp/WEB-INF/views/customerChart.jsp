<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib prefix="myFn" uri="http://www.520it.com/java/crm" %>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>员工管理</title>
    <%@include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/customerChart.js"></script>
</head>
<body>

<table id="customerChart_datagrid"></table>

<!-- 定义数据表格按钮 -->
<div id="customerChart_datagrid_bt">
    <div>
        <form id="customerChart_searchForm">
            关键字:<input type="text" name="keyword">
            日期:<input id="begin" class="easyui-datebox" name="beginDate">
            -
            <input id="end" class="easyui-datebox" name="endDate">
            <a class="easyui-linkbutton" iconCls="icon-search" data-cmd="searchContent">查询</a>
            分组信息:
            <select id="becometime_group" class="easyui-combobox" name="time" style="width:200px;">
                <option value="year">年份</option>
                <option value="month">月份</option>
                <option value="quarter">季度</option>
            </select>

            <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">刷新</a>

            生成图表:
            <select id="photo_group" class="easyui-combobox" name="photo" style="width:200px;">
                <option value="line">线形图</option>
                <option value="column">柱状图</option>
                <option value="pie">饼状图</option>
            </select>
        </form>
    </div>
</div>


</body>
</html>