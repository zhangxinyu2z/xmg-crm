<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>小码哥客户关系管理系统</title>
    <link rel="stylesheet" href="css/style.css">
    <%@include file="/WEB-INF/views/common.jsp" %>
    <script type="text/javascript">
        function submitForm() {
            $.post("${pageContext.request.contextPath}/login", $("form").serialize(), function (data) {
                if (data.success) {
                    console.log(data);
                    window.location.href = "${pageContext.request.contextPath}/index";
                } else {
                    console.log(data);
                    $.messager.alert("温馨提示", data.msg, "warning");
                }
            });
        }

        function resetForm() {
            /*$("form")[0].reset();*/
            $("form").form("clear");
        }

        /* 监听键盘事件，如果用户输入enter，允许登录 */
        $(document).keyup(function (event) {
            //		console.log(event);
            if (event.keyCode == 13) {
                submitForm();
            }
        })
/*        document.onkeydown = function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode == 13) {
                submitForm();
            }
        }*/



    </script>
</head>
<body>
<section class="container">
    <div class="login">
        <h1>用户登录</h1>
        <form method="post">
            <p>
                <input type="text" name="username" value="" placeholder="账号"/>
            </p>
            <p>
                <input type="password" name="password" value="" placeholder="密码"/>
            </p>
            <p class="submit">
                <input onclick="submitForm()" type="button" value="登录"/>
                <input onclick="resetForm()" type="button"
                       value="重置"/>
            </p>
        </form>
    </div>
</section>
<div style="text-align: center;" class="login-help">
    <p>Copyright ©2015 广州小码哥教育科技有限公司</p>
</div>
</body>
</html>