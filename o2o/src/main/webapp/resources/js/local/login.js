$(function () {
    // 登入驗證的controller url
    var loginUrl = '/o2o/local/logincheck';
    // 從URL獲取usertype
    // usertype=1為customer,其他為shopowner
    var usertype = getQueryString('usertype');
    // 登入次數，累積登入三次失敗之後自動彈出驗證碼驗證
    var loginCount = 0;

    $('#submit').click(function () {
        // 獲取帳號
        var userName = $('#username').val();
        // 獲取密碼
        var password = $('#psw').val();
        // 獲取驗證碼
        var verifyCodeActual = $('#j_captcha').val();
        // 是否需要驗證碼驗證，默认为false
        var needVerify = false;
        // 如果登入三次都失败，就做驗證碼驗證
        if (loginCount >= 3) {
            if (!verifyCodeActual) {
                $.toast('請輸入驗證碼！');
                return;
            } else {
                needVerify = true;
            }
        }
        // 請求後台登入驗證
        $.ajax({
            url: loginUrl,
            async: false,
            cache: false,
            type: "post",
            dataType: 'json',
            data: {
                userName: userName,
                password: password,
                verifyCodeActual: verifyCodeActual,
                //是否需要做驗證碼校驗
                needVerify: needVerify
            },
            success: function (data) {
                if (data.success) {
                    $.toast('登入成功！');
                    if (usertype == 1) {
                        // 1為前台登入
                        if (history.length >= 2) {
                            window.history.back();
                        } else {
                            window.location.href = '/o2o/frontend/index';
                        }
                    } else {
                        // 2為後台登入
                        window.location.href = '/o2o/shopadmin/shoplist';
                    }
                } else {
                    $.toast('登入失敗！' + data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        // 登入失敗三次，需要做驗證碼校驗
                        $('#verifyPart').show();
                    }
                }
            }
        });
    });
});