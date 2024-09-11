<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myfn" uri="http://crm.520it.com/myfn" %>
<html>
<head>
    <title>快速签到</title>
    <%@ include file="common.jsp" %>
    <script type="text/javascript" src="/js/views/fastCheckIn.js"></script>

    <script type="text/javascript">
        $(function () {
            $("#checkIn_datagrid_btn a>span").addClass("bigClass");
        });
    </script>
    <style type="text/css">
        .bigClass {
            width: 90px;
            height: 90px;
        }

    </style>
</head>
<body>

<div id="checkIn_datagrid_btn">
    <div>
        <a class="easyui-linkbutton" id="signin" style="background: url('/img/qiandao.png')" plain="true"
           data-cmd="signIn"></a>
        <a class="easyui-linkbutton" id="signout" style="background: url('/img/qiantui.png')" plain="true"
           data-cmd="signOut"></a>
    </div>
</div>
</body>
</html>
