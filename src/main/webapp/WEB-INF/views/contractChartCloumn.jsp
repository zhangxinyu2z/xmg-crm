<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib prefix="myFn" uri="http://www.520it.com/java/crm" %>--%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/common.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <script type="text/javascript">
        var t = '${typee}';
        var times;
        var amountContracts;
        var m;

        var begin ='${beginDate}';
        begin.forma
        console.log(begin);
        var end = '${endDate}';
        console.log(end);
        if(begin=="" && end==""){
            m="/groupContract?typee="+t;
        }
        if(end=="" && begin!=""){
            m= "/groupContract?typee="+t+"&beginDate="+begin;
        }
        if(end!="" && begin==""){
            m= "/groupContract?typee="+t+"&endDate="+end;
        }
        if(end!="" && begin!=""){
            m="/groupContract?typee="+t+"&beginDate="+begin+"&endDate="+end;
        }

        $(function () {
            var yy = $.ajax({
                url: "/groupContract?typee="+t+"&beginDate="+begin+"&endDate="+end,
                async: false
            }).responseText;
            yy = $.parseJSON(yy);
            console.log(yy);
            times = yy[0].times;
            amountContracts = yy[0].amountCustomers;

            $('#container').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '销售金额报表'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: times
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Rainfall (mm)'
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
                    name: '销售总金额',
                    data: amountContracts

                }]
            });
        });
    </script>

</head>
<body>


<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>
