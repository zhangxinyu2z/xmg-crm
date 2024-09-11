<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>
<!-- 最好加一下。我自己在写的时候，发现JSP无法解析EL表达式，可能是忽略了。加在common中，当其他页面引用common，就作用于其他页面 -->
<%@ page isELIgnored="false"%>
<!-- 样式文件 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui/themes/default/easyui.css">
<!--图标样式  -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui/themes/icon.css">
<!-- jQuery核心库 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.min.js"></script>
<!-- EasyUI核心库 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.edatagrid.js"></script>
<!-- 语言包 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<%--解决easyui版本中，按钮置灰后仍可点击弹窗的bug--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/base.js"></script>


