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
    //正在訪問的店家
    var shopId = getQueryString("shopId");
    //獎品列表路由
    var awardListUrl = '/o2o/frontend/getawardlist';
    //兌換獎品
    var redeemAwardUrl = '/o2o/frontend/redeemAward';
    //加載獎品
    getAwardList(pageNum, pageSize);

    function getAwardList(pageIndex, pageSize) {
        var url = awardListUrl + '?shopId=' + shopId + '&pageIndex=' + pageIndex + '&pageSize=' + pageSize
            + '&awardName=' + awardName;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                maxItems = data.count;
                var tempHtml = '';
                data.awardList.map(function (item, index) {
                    tempHtml +=
                        '<div class="card" data-id="'
                        + item.awardId
                        + '">'
                        + '<div class="card-header">'
                        + '<span class="pull-left">'
                        + item.awardName
                        + '</span>'
                        + '<span class="pull-right">需要'
                        + item.point
                        + '積分</span>'
                        + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block media-list">'
                        + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">'
                        + '<img src="'
                        + getContextPath() + item.awardImg
                        + '"width="44">'
                        + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-title-row">'
                        + '<div class="item-title">'
                        + item.awardDesc
                        + '</div></div></div></li></ul></div></div>'
                        + '<div class="card-footer">'
                        + '<span>'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</span>'
                        + '<span><a>'
                        + '點擊領取</a></span></div></div>'
                });
                $('.list-div').append(tempHtml);
                //獲取當前加載了幾個card
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

    //下拉自動刷新
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        getAwardList(pageNum, pageSize);
    });
    //點擊card領取獎品
    $('.award-list').on('click', '.card', function (e) {
        var id = e.currentTarget.dataset.id;
        $.confirm("確認兌換獎品?", function () {
            $.ajax({
                url: redeemAwardUrl,
                type: 'POST',
                async: false,
                cache: false,
                data: {
                    awardId: id,
                    shopId: shopId
                },
                success: function (data) {
                    if (data.success) {
                        $.toast("兌換成功");
                    }else if(data.indexOf('login')){
                        window.location.href = getContextPath()+'/local/login?usertype=1';
                    }else {
                        $.toast(data.errMsg);
                    }
                }
            });
        });
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