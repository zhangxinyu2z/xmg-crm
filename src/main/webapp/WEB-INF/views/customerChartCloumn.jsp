<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib prefix="myFn" uri="http://www.520it.com/java/crm" %>--%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/common.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>WMS-销售报表(柱状图)</title>
    <script type="text/javascript">

        var tt = $.ajax({
            url: "/ppp",
            async: false
        }).responseText;
        tt = $.parseJSON(tt);
        var userId = '${userId}';
        var times;
        var amountContracts;

        var yy = $.ajax({
            url: "/groupChart?userId=" + userId,
            data:tt,
            async: false
        }).responseText;
        yy = $.parseJSON(yy);
        console.log(yy);
        times = yy[0].times;
        amountCustomers = yy[0].amountCustomers;


        $(function () {
            $('#container').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '正式客户报表'
                },
                subtitle: {
                    text: 'Source: WorldClimate.com'
                },
                xAxis: {
                    categories: times
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '正式客户总人数'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: '分组类型',
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
