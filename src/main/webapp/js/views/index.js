// 页面加载完毕后执行
$(function() {
	$("#menuTree").tree({
		//		    url: '/js/data/menu.json',
		url: "/queryForMenu",
		onClick: function(node) {
			if (node.attributes) {
				// 将attributs中的url转成json格式
				node.attributes = $.parseJSON(node.attributes);
			}
			console.log(node);
			//在选项中添加新面板
			var myTab = $("#myTabs");
			//在选项卡中是否已经有该节点的面板.
			if (myTab.tabs("exists", node.text)) {
				//选中面板
				myTab.tabs("select", node.text);
			} else {
				myTab.tabs("add", {
					title: node.text,
					closable: true,
					// 只加载请求页面<body></body>中内容
					// href:node.attributes.url
					content: "<iframe src='" + node.attributes.url + "' style='width:100%;height:100%' frameborder=0></iframe>"
				});
			}

		}
	});
});


