$(function () {
    //加載 flag
    var loading = false;
    //最多加載條數
    var maxItems = 999;
    //每次添加條數
    var pageSize = 5;
    //初始化頁數
    var pageNum = 1;
    //店家名稱查詢
    var shopName = '';
    //點數列表路由
    var myPointListUrl = '/o2o/frontend/getmypoint';
    //加載點數列表
    getMyPointList(pageNum, pageSize);

    //查詢在各商店累積的點數
    function getMyPointList(pageIndex, pageSize) {
        var url = myPointListUrl + '?pageIndex=' + pageIndex + '&pageSize=' + pageSize
            + '&shopName=' + shopName;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                var html = '';
                loading = true;
                maxItems = data.count;
                data.userPointList.map(function (item, index) {
                    html +=
                        '<div class="card">'
                        + '<div class="card-header">'
                        + item.shop.shopName
                        + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block media-list">'
                        + '<ul><li class="item-content">'
                        + '<div class="item-inner">'
                        + '<div class="item-title-row">'
                        + '<div class="item-title">本店積分：'
                        + item.point
                        + '</div></div></div></li></ul></div></div>'
                        + '<div class="card-footer">'
                        + '<span>更新時間'
                        + new Date(item.createTime).Format('yyyy-MM-dd')
                        + '</span></div></div></div>'
                });
                $('.list-div').append(html);
                //顯示已經加載的商品數
                var total = $('.list-div .card').length;
                if (total >= maxItems) {
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                ;
                //頁碼+1
                pageNum += 1;
                //加載結束
                loading = false;
                $.refreshScroller();
            }
        });
    }

    // 下滑屏幕自動進行分頁搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    // 需要查詢的商品名字發生變化後，重置頁碼，清空原先的商品列表，按照新的名字去查詢
    $('#search').on('change', function () {
        shopName = $('#search').val();
        $('.list-div').empty();
        pageNum = 1;
        getMyPointList(pageNum, pageSize);
    });

    //加載側邊攔
    $('#panel-right-demo').load('sidepanel.html');

    //側邊欄開啟
    $('#me').on('click',function () {
        $.openPanel('#panel-right-demo');
    });

    $.init();
});