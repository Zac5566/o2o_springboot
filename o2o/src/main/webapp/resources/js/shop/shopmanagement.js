$(function() {
	//獲取shopId
	var shopId = getQueryString('shopId');
	//商店管理路由
	var shopInfoUrl = '/o2o/shopadmin/getshopmanagementinfo?shopId=' + shopId;
	$.getJSON(shopInfoUrl, function(data) {
		if (data.redirect) {
			//後台判斷是否跳轉
			window.location.href = data.url;
		} else {
			//接收後台的shopId
			if (data.shopId != undefined && data.shopId != null) {
				shopId = data.shopId;
			}
			$('#shopInfo')
					.attr('href', '/o2o/shopadmin/shopoperation?shopId=' + shopId);
		}
	});
});