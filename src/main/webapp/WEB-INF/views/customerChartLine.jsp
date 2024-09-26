<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib prefix="myFn" uri="http://www.520it.com/java/crm" %>--%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/common.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>WMS-销售报表(线形图)</title>
    <script type="text/javascript">
        var tt = $.ajax({
            url: "/qqq",
            async: false
        }).responseText;
        tt = $.parseJSON(tt);
        var userId = '${userId}';
        var times;
        var amountContracts;
        var yy = $.ajax({
            url: "/groupCustomer?userId=" + userId,
            data: tt,
            async: false
        }).responseText;
        yy = $.parseJSON(yy);
        console.log(yy);
        times = yy[0].times;
        amountCustomers = yy[0].amountCustomers;
        $(function () {
            $('#container').highcharts({
                title: {
                    text: '正式客户报表(线形图)',
                    x: -20 //center
                },
                subtitle: {
                    text: '按照时间分组',
                    x: -20
                },
                xAxis: {
                    categories: times
                },
                yAxis: {
                    title: {
                        text: '正式客户总数'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: '°C'
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                    name: '正式客户',
                    data: amountCustomers
                }]
            });
        });
    </script>

</head>
<body>


<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>
