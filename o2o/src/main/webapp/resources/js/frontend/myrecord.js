$(function () {
    //加載 flag
    var loading = false;
    //最多加載條數
    var maxItems = 20;
    //初始頁面
    var pageNum = 1;
    //每次添加條數
    var pageSize = 5;
    //商品名稱
    var productName = '';
    //查詢購買商品路由
    var listUrl = '/o2o/frontend/listuserproductmapsbycustomer';

    addItems(pageNum, pageSize);

    //商品購買列表
    function addItems(pageIndex, pageSize) {
        var url = listUrl + '?pageIndex=' + pageIndex + '&pageSize=' + pageSize
            + '&productName=' + productName;
        $.getJSON(url, function (data) {
            if (data.success) {
                var html = '';
                loading = true;
                maxItems = data.count;
                data.userProductMapList.map(function (item, index) {
                    html +=
                        '<div class="card" data-product-id='
                        + item.productId + '>'
                        + '<div class="card-header">' + item.shop.shopName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.product.productName
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.createTime).Format("yyyy-MM-dd")
                        + '</p>' + '<span>積分：' + item.point + '</span>'
                        + '</div>' + '</div>';
                });
                $('.list-div').append(html);
                //顯示已經加載的商品數
                var total = $('.list-div .card').length;
                if (total >= maxItems) {
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }

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
    $('#search').on('change', function (e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageNum, pageSize);
    });
    //加載側邊攔
    $('#panel-right-demo').load('sidepanel.html');
    //側邊欄開啟
    $('#me').on('click',function () {
        $.openPanel('#panel-right-demo');
    });
    $.init();
});