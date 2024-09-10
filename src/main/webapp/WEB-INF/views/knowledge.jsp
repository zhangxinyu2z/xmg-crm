<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/knowledge.js"></script>
    <title>客户知识库</title>
</head>
<body>

<div id="cc" class="easyui-layout" fit="true" style="width:700px;height:350px;">
    <!-- 左 -->
    <div data-options="region:'west'" style="width:250px;">
        <!--手风琴  -->
        <div id="aa" class="easyui-accordion" fit="true">
            <div title="知识分类列表" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
                <!--菜单树  -->
                <ul id="knowledge_tree"></ul>
            </div>
        </div>
    </div>
    <!--右  -->
    <div data-options="region:'center'">
        <!--选项卡  -->
        <div id="bb" class="easyui-tabs" fit="true" style="width: 500px; height: 250px;">
            <!--知识首页  -->
            <div title="知识首页">
                <div>
                    <table style="height:250px " id="knowledge_dadagrid"></table>
                </div>
                <div>
                    <div id="tt" fit="true" class="easyui-tabs" style="width:500px;height:250px;">
                        <div title="热点知识" style="padding:20px;">
                            <a href="/know.jsp"><font color="red" size="4">4G是第四代</font></a><br/>
                            <a href="/know.jsp"><font color="red" size="4">地下钻出来</font></a><br/>
                            <a href="/know.jsp"><font color="red" size="4">送上门的</font></a><br/>
                            <a href="/know.jsp"><font color="red" size="4">绑架来得</font></a><br/>
                        </div>
                        <div title="知识排行" style="overflow:auto;padding:20px;">
                            <a href="/know.jsp"><font color="red" size="4">天上来掉下来</font></a><br/>
                            <a href="/know.jsp"><font color="red" size="4">地下钻出来</font></a><br/>
                            <a href="/know.jsp"><font color="red" size="4">送上门的</font></a><br/>
                            <a href="/know.jsp"><font color="red" size="4">绑架来得</font></a><br/>
                        </div>

                    </div>


                </div>
            </div>
        </div>

    </div>
</div>


<div id="dadagrid_linknutton" align="center">
    <form id="know_from" method="post">
        关键字查询<input type="text" name="keyword" placeholder="请输入需要查询内容"/>
        <a class="easyui-linkbutton" iconCls="icon-search" plain="true" data-cmd="searchCount">全文检索</a>
    </form>
</div>

<!-- 加载类容框 -->
<div id="know2_dialog">

</div>


<div id="know_dialog">
    <h1 align="center" style="color: blue">客户相关</h1><br/>
        <a href="/know.jsp"><font color="red" size="4">天上来掉下来</font></a><br/>
        <a href="/know.jsp"><font color="red" size="4">地下钻出来</font></a><br/>
        <a href="/know.jsp"><font color="red" size="4">送上门的</font></a><br/>
        <a href="/know.jsp"><font color="red" size="4">绑架来得</font></a><br/>
</div>


<div id="complaint_dialog">
    <h1 align="center" style="color: blue">投诉相关</h1><br/>
        <a href="/know.jsp"><font color="red" size="4">处理投诉的技巧</font></a><br/>
        <a href="/know.jsp"><font color="red" size="4">怎么处理投诉</font></a><br/>
        <a href="/know.jsp"><font color="red" size="4">面对难缠的客户怎么办</font></a><br/>
</div>

<div id="meet_dialog">
    <h1 align="center" style="color: blue">应急相关</h1><br/>
        <a href="/know.jsp"><font color="red" size="4">发生火灾时该怎么办</font></a><br/>
        <a href="/know.jsp"><font color="red" size="4">安全知识</font></a><br/>
        <a href="/know.jsp"><font color="red" size="4">面对难缠的客户怎么办</font></a><br/>
</div>

<div id="know_linknutton">
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancelCoun">取消</a>
</div>

<div id="complain_linknutton">
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancelCou">取消</a>
</div>

<div id="knowledge_linknutton">
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancelCount">取消</a>
</div>
</body>
</html>