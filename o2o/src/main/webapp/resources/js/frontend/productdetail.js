//獲取商品詳情
$(function () {
    //從地址取id
    var productId = getQueryString('productId');
    //詳情url
    var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId=' + productId;
    //購買連結
    var buyItUrl = '/o2o/frontend/buyproduct';

    $.getJSON(productUrl, function (data) {
        if (data.success) {
            var product = data.product;
            //商品名稱
            $('#product-name').text(product.productName);
            //商品縮圖
            $('#product-img').attr("src", getContextPath() + product.imgAddr);
            //更新時間
            $('#product-time').text(new Date(product.lastEditTime).Format("yyyy-MM-dd"));
            //商品描述
            $('#product-desc').text(product.productDesc);
            //商品點數
            $('#product-point').text(product.point);
            //商品價格(normalPrice,promotionPrice排列組合，除了皆空)
            if (product.normalPrice != undefined && product.promotionPrice != undefined) {
                $('#price').show();
                $('#normalPrice').html(
                    '<del>' + '￥' + product.normalPrice + '</del>');
                $('#promotionPrice').text('￥' + product.promotionPrice);
            } else if (product.promotionPrice == undefined) {
                $('#normalPrice').text('￥' + product.normalPrice);
            } else if (product.normalPrice == undefined && product.promotionPrice != undefined) {
                $('#promotionPrice').text('￥' + product.promotionPrice);
            }
            ;
            var imgListHtml = '';
            //詳情圖片
            product.productImgList.map(function (item, index) {
                imgListHtml +=
                    '<div><img src="'
                    + getContextPath() + item.imgAddr
                    + '" width="100%"/></div>';
            });
            $('#imgList').html(imgListHtml);
        }
    });
    //購買商品
    $('#buyIt').on('click', function () {
        $.confirm('確認購買', function () {
            $.ajax({
                url: buyItUrl,
                type: 'POST',
                async: false,
                cache: false,
                data: {
                    productId: productId
                },
                success: function (data) {
                    if (data.success) {
                        $.toast('購買成功');
                        $.confirm('查看購買紀錄',function () {
                            window.location.href = '/o2o/frontend/myrecord';
                        });
                    }else if(data.indexOf('login')){
                        //如果用戶還沒登入，則跳轉到前台登入頁面
                        window.location.href = getContextPath()+'/local/login?usertype=1';
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
    $.init();

});