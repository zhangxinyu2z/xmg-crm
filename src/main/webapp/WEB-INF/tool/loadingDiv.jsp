<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 处理easyui加载数据时屏闪烁的问题 -->
<div id='loadingDiv' style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: white; text-align: center;">
	<h1 style="top: 48%; position: relative;">
		<font color="#15428B">努力加载中···</font>
	</h1>
	<!--两种方式：一种是文字loading提示，另一种找张图标（这里是个圈）-->
	<!-- <img src="/js/jquery-easyui/themes/metro/images/loading.gif"style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;width:25px;height:25px;"/> -->
</div>

<script type="text/JavaScript">
	function closeLoading() {
		$("#loadingDiv").fadeOut("normal", function() {
			$(this).remove();
		});
	}
	var no;
	$.parser.onComplete = function() {
		if (no)
			clearTimeout(no);
		no = setTimeout(closeLoading, 500);
	}
</script>