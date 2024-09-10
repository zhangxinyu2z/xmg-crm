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
        console.log(t);

        var begin ='${beginDate}';
        console.log(begin);
        var end = '${endDate}';
        console.log(end);
        var m;
        var times;
        var amountContracts;
        if(begin=="" && end==""){
            m="/contractChart_pie?typee="+t;
        }
        if(end=="" && begin!=""){
            m= "/contractChart_pie?typee="+t+"&beginDate="+begin;
        }
        if(end!="" && begin==""){
            m= "/contractChart_pie?typee="+t+"&endDate="+end;
        }
        if(end!="" && begin!=""){
            m="/contractChart_pie?typee="+t+"&beginDate="+begin+"&endDate="+end;
        }
        console.log(m);

            var yy = $.ajax({
                url: m,
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
                    text: '销售总额报表'
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
                    name: '销售总额',
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
