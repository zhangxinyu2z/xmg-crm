<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib prefix="myFn" uri="http://www.520it.com/java/crm" %>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>销售报表</title>
    <%@include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/contractChart.js"></script>
</head>
<body>

<table id="contract_datagrid"></table>

<!-- 定义数据表格按钮 -->
<div id="contract_datagrid_bt">
    <div>
        <form id="contract_searchForm">
            <span><font color="#c71585">日期：</font></span>
            <input id="aaa" class="easyui-datebox" id='' name="beginDate">
            -
            <input id='bbb' class="easyui-datebox" name="endDate">

            <a class="easyui-linkbutton" iconCls="icon-search" data-cmd="searchContent" plain="true">
                <span><font color="#c71585">查询</font></span>
            </a>

            <span><font color="#c71585">分组信息：</font></span>
            <select id="becometime_group_count" class="easyui-combobox" name="time">
                <option value="month">月份</option>
                <option value="quarter">季度</option>
                <option value="year">年份</option>
            </select>

            <span><font style="color: #c71585">生成图表：</font></span>
            <select id="photo_group" class="easyui-combobox" name="photo">
                <option value="line">线形图</option>
                <option value="column">柱状图</option>
                <option value="pie">饼状图</option>
            </select>

            <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="refresh">
                <span><font color="#c71585">刷新</font></span>
            </a>
        </form>

    </div>
</div>
</body>
</html>