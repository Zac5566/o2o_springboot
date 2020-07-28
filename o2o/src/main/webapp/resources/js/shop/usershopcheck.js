$(function () {
    //當前頁數
    var pageIndex = 0;
    //每頁顯示
    var pageSize = 999;
    //顧客累積點數列表
    var userPointUrl = '/o2o/shopadmin/getuserpointlist?pageIndex=' + pageIndex + '&pageSize=' + pageSize;
    //頁面初始化
    listUserPoint();

    //請求用戶點數列表
    function listUserPoint(){
        $.getJSON(userPointUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                data.userProductMapList.map(function (item,index) {
                    tempHtml +=
                        '<div class="row row-usershopcheck">'
                        + '<div class="col-50">'
                        + item.user.name
                        + '</div>'
                        + '<div class="col-50">'
                        + item.point
                        + '</div></div>';
                });
                $('.usershopcheck-wrap').html(tempHtml);
            }
        });
    }

});