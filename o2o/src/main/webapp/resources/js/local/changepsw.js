$(function() {
	// 修改密碼的controller url
	var url = '/o2o/local/changelocalpwd';
	// 從URL獲取usertype
	// usertype=1為customer,其他為shopowner
	var usertype = getQueryString('usertype');
	$('#submit').click(function() {
		// 獲取帳號
		var userName = $('#userName').val();
		// 獲取舊密碼
		var password = $('#password').val();
		// 獲取新密碼
		var newPassword = $('#newPassword').val();
		var confirmPassword = $('#confirmPassword').val();
		if (newPassword != confirmPassword) {
			$.toast('兩次的新密码不一致！');
			return;
		}
		// 添加表單數據
		var formData = new FormData();
		formData.append('userName', userName);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		// 獲取驗證碼
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('請輸入驗證碼！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		// 提交參數至後台
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					if (usertype == 1) {
						// 或用戶在前台登入則跳轉回前台
						window.location.href = '/o2o/frontend/index';
					} else {
						// 或用戶在後台登入則跳轉回後台
						window.location.href = '/o2o/shopadmin/shoplist';
					}
				} else {
					$.toast('提交失败！' + data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});
	//回上一頁
	$('#back').click(function() {
		window.history.back();
	});
});
