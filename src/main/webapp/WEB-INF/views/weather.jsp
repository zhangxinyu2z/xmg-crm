<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%--天气插件，演示--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="/js/weather/js/jquery.min.js"></script>
    <%@include file="common.jsp" %>
    <title>Insert title here</title>
    <style>

        #demo {
            width: 200px;
            margin: 5px auto;
            background: #e74314;
            color: #FFF;
            padding: 25px 0 30px 0;
            overflow: hidden;
        }

        #demo i {
            background: no-repeat top left;
            display: inline-block;
            height: 30px;
            line-height: 10px;
            margin: 0 auto 20px auto;
            font-size: 30px;
            padding-left: 100px;
            font-style: normal;
            text-align: center;
            font-weight: bold;
        }

        #demo i.icon-xiaoyu {
            background-image: url(/js/weather/icon/xiaoyu.png);
        }

        #demo i.icon-zhongyu {
            background-image: url(/js/weather/icon/zhongyu.png);
        }

        #demo i.icon-dayu {
            background-image: url(/js/weather/icon/dayu.png);
        }

        #demo i.icon-qing {
            background-image: url(/js/weather/icon/qing.png);
        }

        #demo i.icon-duoyun {
            background-image: url(/js/weather/icon/duoyun.png);
        }

        #demo i.icon-yin {
            background-image: url(/js/weather/icon/yin.png);
        }

        /* #demo p { background:rgba(0,0,0,.3); margin:0 100px; padding:20px; border-radius:100px; font-size:16px; }  */
        #demo p span {
            margin: 0 15px;
        }

        #demo2 {
            width: 200px;
            margin: 0 auto;
            background: #e94515;
            padding: 20px 0;
            overflow: hidden;
        }
    </style>
</head>
<body>
<!-- <div>
    <input type="text" name="city" placeholder="请输入需要查询的城市" /> <a
        class="easyui-linkbutton" iconCls="icon-search">查询</a>
</div> -->
<div id="demo">
    <script>
        $('#demo')
            .leoweather(
                {
                    format: '<i class="icon-{图标}">{气温}℃</i><p>{城市}<span>|</span>{天气}<span>|</span>{风向}{风级}级</p>'
                });
    </script>
</div>


<div id="demo2">
    <script>$('#demo2').leoweather();</script>
</div>
</body>
</html>