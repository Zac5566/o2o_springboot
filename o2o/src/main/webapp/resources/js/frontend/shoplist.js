$(function () {
    // 加載flag
    var loading = false;
    // 最多加載的數量
    var maxItems = 999;
    // 一頁最大的顯示數量
    var pageSize = 4;
    // 店鋪url
    var listUrl = '/o2o/frontend/listshops';
    //區域、分類初始化url
    var searchDivUrl = '/o2o/frontend/listshopspageinfo';
    //頁碼
    var pageNum = 1;
    // 取出url中的參數
    var parentId = getQueryString('parentId');
    var shopName = '';
    var shopCategoryId = '';
    var areaId = '';
    //加載店鋪列表跟區域
    getSearchDivData();
    //加載店鋪
    addItems(pageSize, pageNum);

    /**
     * 獲取店鋪列表及區域訊息
     * @Author: Zac5566
     * @Date: 2020/6/27
     * @return: null
     */
    function getSearchDivData() {
        var url = searchDivUrl + '?' + 'parentId=' + parentId;
        $.getJSON(url, function (data) {
            if (data.success) {
                var shopCategoryList = data.shopCategoryList;
                var html = '';
                html += '<a href="#" class="button" data-category-id=""> 全部類別  </a>';
                //商店分類
                shopCategoryList.map(function (item, index) {
                    html += '<a href="#" class="button" data-category-id='
                        + item.shopCategoryId
                        + '>'
                        + item.shopCategoryName
                        + '</a>';
                });
                $('#shoplist-search-div').html(html);
                //區域下拉
                var selectOptions = '<option value="">全部街道</option>';
                data.areaList.map(function (item, index) {
                    selectOptions += '<option value="'
                        + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#area-search').html(selectOptions);
            }
        });
    }

    /**
     * 獲取分頁展示的店铺列表
     * @Author: Zac5566
     * @Date: 2020/6/27
     * @return: null
     * @param pageSize
     * @param pageIndex
     */
    function addItems(pageSize, pageIndex) {
        var url = listUrl + '?shopName=' + shopName + '&parentId=' + parentId
            + '&shopCategoryId=' + shopCategoryId + '&areaId=' + areaId + '&pageSize='
            + pageSize + '&pageNum=' + pageIndex;
        //設定加載flag，若還在後台取數據則不能再次訪問後台，避免多次重複加載
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                maxItems = data.count;
                var html = '';
                data.shopList.map(function (item, index) {
                    html += '' +
                        '<div class="card" data-shop-id="'
                        + item.shopId
                        + '"><div class="card-header">'
                        + item.shopName
                        + '</div><div class="card-content"><div class="list-block media-list">'
                        + '<ul><li class="item-content"><div class="item-media"><img src="'
                        + getContextPath()
                        + item.shopImg
                        + '" width="44"></div><div class="item-inner"><div class="item-subtitle"">'
                        + item.shopDesc
                        + '</div></div></li></ul></div></div><div class="card-footer"><span>'
                        + new Date(item.lastEditTime).Format('yyyy-MM-dd')
                        + '更新</span><span>'
                        + '點擊查看</span></div></div>'
                });
                $('.list-div').append(html);
                //獲取已加載的card數量
                var total = $('.list-div .card').length;
                //達到數量就不再加載
                if (total >= maxItems) {
                    // 隐藏提示符
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $.attachInfiniteScroll($('.infinite-scroll'));
                    $('.infinite-scroll-preloader').show();
                }
                pageNum += 1;
                // 加載結束
                loading = false;
                //刷新滾動條
                $.refreshScroller();
            }
        });
    }

    // 無限滾動，分頁搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });
    //點擊店鋪進入詳情
    $('.shop-list').on('click', '.card', function (e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
    });
    // 點擊店鋪類別後，重置頁碼，清空原先的店铺列表，按照新類別查询
    $('#shoplist-search-div').on('click', '.button', function (e) {
        if (parentId) {
            // 如果傳遞過來的是一個父類下的子類
            shopCategoryId = e.target.dataset.categoryId;
            // 若之前已選定了別的category,則移除其選定效果，改成選定新的
            if ($(e.target).hasClass('button-fill')) {
                $(e.target).removeClass('button-fill');
                shopCategoryId = '';
            } else {
                $(e.target).addClass('button-fill').siblings()
                    .removeClass('button-fill');
            }
            // 由於查詢條件改變，清空店鋪列表再進行查詢
            $('.list-div').empty();
            // 重置頁碼
            pageNum = 1;
            addItems(pageSize, pageNum);
        } else {
            //如果傳遞過來的父類為空，則按照父類查詢
            parentId = e.target.dataset.categoryId;
            if ($(e.target).hasClass('button-fill')) {
                $(e.target).removeClass('button-fill');
                parentId = '';
            } else {
                $(e.target).addClass('button-fill').siblings()
                    .removeClass('button-fill');
            }
            // 查詢條件改變，清空店鋪列表再進行查詢
            $('.list-div').empty();
            // 重置頁碼
            pageNum = 1;
            addItems(pageSize, pageNum);
            parentId = '';
        }
    });
    // 點擊區域後，得到區域的值，並清空頁碼與顯示的商店
    $('#area-search').on('change', function () {
        areaId = $('#area-search').val();
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });
    //店名搜索
    $('#search').on('change', function () {
        shopName = $(this).val();
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    //加載側邊攔
    $('#panel-right-demo').load('sidepanel.html');
    //側邊欄開啟
    $('#me').on('click', function () {
        $.openPanel('#panel-right-demo');
    });
    // 初始化頁面
    $.init();
});