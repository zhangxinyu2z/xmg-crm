$(function(){
	
	$("#dd").dialog({
		buttons:'#know_linkbutton'
	});
	
	
	 var objCmd = {
		cancelCount:function(){
			//$.get("/knowledge");
			window.location.href = "/knowledge"
		}, 
	 };
	 
	 
	 $("a[data-cmd]").on("click",function(){
		 var cmd = $(this).data("cmd");
		 objCmd[cmd]();
	 });
})