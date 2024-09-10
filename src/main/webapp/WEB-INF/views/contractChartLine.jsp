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
        var t = '${typee}';

        var begin ='${beginDate}';
        console.log(begin);
        var end = '${endDate}';
        console.log(end);
        var m;
        var times;
        var amountContracts;
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
        console.log(m);


        var yy = $.ajax({
            url:m,
            async: false
        }).responseText;
        yy = $.parseJSON(yy);
        console.log(yy);
        times=yy[0].times;
        amountCustomers=yy[0].amountCustomers;
        names=yy[0].names;
        $(function () {
            $('#container').highcharts({
                title: {
                    text: '销售总额报表',
                    x: -20 //center
                },
                subtitle: {
                    text: '按照xxx',
                    x: -20
                },
                xAxis: {
                    categories: times
                },
                yAxis: {
                    title: {
                        text: 'Temperature (°C)'
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
                    name: '销售总额',
                    data:  amountCustomers
                }]
            });
        });
    </script>

</head>
<body>


<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>
