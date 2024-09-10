<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>网盘</title>

    <%@include file="/WEB-INF/views/common.jsp" %>
    <style>
        .datagrid-btable tr {
            height: 50px;
        }
    </style>
    <script type="text/javascript" src="/js/views/netdisk.js"></script>

</head>
<body>

<table id="datagrid_netdisk" class="datagrid-btable" data-options="
url:'/netdisk_list',fit:true,fitColumns:true,pagination:true,rownumbers:true,onClickCell:clickCell,onAfterEdit:afterEdit">
    <thead>
    <th data-options="field:'type',width:1,formatter:fileTypeFormatter">文件类型</th>
    <th data-options="field:'name',editor:'text',width:2">文件名</th>
    <th data-options="field:'user',width:1,formatter:usernameFormatter">用户</th>
    <th data-options="field:'uploadtime',width:1">上传时间</th>
    </thead>

    <tbody>
    </tbody>
</table>
<!--顶部按钮-->
<div id="tb">
    <div>
        <form id="searchform_netdisk" action="#" method="post">
            <input type="hidden" name="pid" id="pid">
            <input type="hidden" id="currentDirId">
            <%--  关键字<input name="keyword" style="width: 100px">
              <a class="easyui-linkbutton" id="btn_search" data-cmd="search" iconCls="icon-search">search</a>--%>
        </form>
    </div>
    <div>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" data-cmd="toParent">上级目录</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-upload',plain:true" data-cmd="upload">上传</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-rename',plain:true" data-cmd="rename">重命名</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-download',plain:true" data-cmd="download">下载</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-new_folder',plain:true" data-cmd="mkdir">新建文件夹</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-back',plain:true" data-cmd="toRoot">根目录</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" data-cmd="reload">刷新</a>
    </div>

</div>
<%----%>
<!--重命名dialog-->
<div id="dialog_netdisk" class="easyui-dialog" closed="true"
     data-options="top:100,width:300,height:150,draggable:true,buttons:'#bb'">
    <form id="form_netdisk" action="#" method="post">
        <input type="hidden" name="id">
        文件名:<input name="name">
    </form>
</div>
<!--表单按钮-->
<div id="bb">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" data-cmd="save">保存</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" data-cmd="cancel">取消</a>
</div>
<form id="form_upload" action="#" method="post" enctype="multipart/form-data">
    <input type="file" id="file_upload" name="mf" style="display: none">
</form>


</body>
</html>