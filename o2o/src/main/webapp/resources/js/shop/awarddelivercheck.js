$(function () {
    //用戶兌換獎品紀錄路由
    var userAwardMapUrl = '/o2o/shopadmin/getawarddelivercheck';
    //頁碼
    var pageIndex = 1;
    //一頁加載多少紀錄
    var pageSize = 999;
    //可以用用戶名查詢紀錄
    var userName = '';

    getUserAwardMap();

    //獲取用戶兌換獎品紀錄
    function getUserAwardMap() {
        var requestUrl = userAwardMapUrl + '?pageIndex=' + pageIndex + '&pageSize=' + pageSize
            + '&name=' + userName;
        $.getJSON(requestUrl,function (data) {
            var tempHtml = '';
            if(data.success){
                data.userAwardMapList.map(function (item,index) {
                    tempHtml +=
                        '<div class="row row-awarddeliver">'
                        + '<div class="col-15 ">'
                        + item.award.awardName
                        + '</div>'
                        + '<div class="col-45">'
                        + new Date(item.createTime).Format('yyyy-MM-dd')
                        + '</div>'
                        + '<div class="col-25">'
                        + item.user.name
                        + '</div>'
                        + '<div class="col-15">'
                        + item.point
                        + '</div></div>'
                });
                $('.awarddeliver-wrap').html(tempHtml);
            }
        });
    }
    //利用用戶名查詢
    $('#search').on('change',function () {
        userName = $(this).val();
        getUserAwardMap();
    });
});