$(function () {
    //加載 flag
    var loading = false;
    //最多加載條數
    var maxItems = 999;
    //每次添加條數
    var pageSize = 5;
    //初始化頁數
    var pageNum = 1;
    //查詢獎品名稱
    var awardName = '';
    var myAwardlistUrl = '/o2o/frontend/getmyawardlist';


    getMyAwardList(pageNum, pageSize);

    //獎品兌換列表
    function getMyAwardList(pageIndex, pageSize) {
        url = myAwardlistUrl + '?pageIndex=' + pageIndex + '&pageSize=' + pageSize + '&awardName=' + awardName;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                maxItems = data.count;
                var tempHtml = '';
                data.userAwardList.map(function (item, index) {
                    tempHtml +=
                        '<div class="card">'
                        + '<div class="card-header">'
                        + item.shop.shopName
                        + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block award-list">'
                        + '<ul><li class="item-content">'
                        + '<div class="item-inner">'
                        + '<div class="item-title-row">'
                        + '<div class="item-title">'
                        + item.award.awardName
                        + '</div></div></div></li></ul></div></div>'
                        + '<div class="card-footer">'
                        + '<span>'
                        + new Date(item.createTime).Format('yyyy-MM-dd')
                        + '</span>'
                        + '<span>消耗'
                        + item.point
                        + '積分</span></div></div>'
                });
                $('.list-div').append(tempHtml);
                var total = $('.list-div .card').length;
                if (total >= maxItems) {
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                pageNum += 1;
                loading = false;
                $.refreshScroller();
            }
        });
    }
    //搜索獎品
    $('#search').on('change',function (e) {
        awardName = $('#search').val();
        pageNum = 1;
        $('.list-div').empty();
        getMyAwardList(pageNum,pageSize);
    });

    //下拉自動刷新
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        getMyAwardList(pageNum, pageSize);
    });
    //加載側邊攔
    $('#panel-right-demo').load('sidepanel.html');
    //側邊欄開啟
    $('#me').on('click',function () {
        $.openPanel('#panel-right-demo');
    });
    //初始化
    $.init();
});