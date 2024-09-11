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
    <title>线索管理</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/clew.js"></script>
</head>
<body>
<table id="clew_datagrid"></table>
<div id="clew_datagrid_btn">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">重新加载lucene库</a>
        <a class="easyui-linkbutton" iconCls="icon-no" plain="true" data-cmd="del">无效信息</a>
    </div>
    <div>
        <form id="clew_searchForm">
            关键字:<input type="text" name="keyword" placeholder="标题或内容">
            <a class="easyui-linkbutton" iconCls="icon-search" data-cmd="searchContent">查询</a>
        </form>
    </div>
    <div id="clew_dialog"></div>

</div>
</body>
</html>
