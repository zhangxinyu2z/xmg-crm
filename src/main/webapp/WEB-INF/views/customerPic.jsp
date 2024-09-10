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
            url: "/customerChart_pie?userId="+userId,
            data:tt,
            async: false
        }).responseText;
        yy = $.parseJSON(yy);
        console.log(yy);


        $(function () {
            $('#container').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: 1,//null,
                    plotShadow: false
                },
                title: {
                    text: '潜在客户饼状图'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: '潜在客户总数比例',
                    data: yy[0]
                }]
            });
        });
    </script>

</head>
<body>


<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>
