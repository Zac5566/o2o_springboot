$(function () {
    //商品類別路由
    var listUrl = '/o2o/shopadmin/getproductcategorylist';
    //添加商品類別路由
    var addUrl = '/o2o/shopadmin/addproductcategories';
    //刪除商品類別路由
    var delUrl = '/o2o/shopadmin/removeproductcategory';
    getList();

    //獲取商品類別列表
    function getList() {
        $.ajax({
            url: listUrl,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.success) {
                    var list = result.data;
                    $('.category-wrap').html('');
                    var html = '';
                    list.map(function (item, index) {
                        html += ''
                            + '<div class="row row-product-category now">'
                            + '<div class="col-33 product-category-name">'
                            + item.productCategoryName
                            + '</div>'
                            + '<div class="col-33">'
                            + item.priority
                            + '</div>'
                            + '<div class="col-33"><a href="#" class="button delete" data-id="'
                            + item.productCategoryId
                            + '">删除</a></div>'
                            + '</div>';
                    })
                    $('.category-wrap').html(html);
                }
            }
        })
    }
    //新增類別框
    $('#new')
        .click(
            function () {
                var html =
                    '<div class="row row-product-category temp">'
                    + '<div class="col-33 "><input class="category-input category" type="text" placeholder="分類名"></div>'
                    + '<div class="col-33 "><input class="category-input priority" type="number" placeholder="優先級"></div>'
                    + '<div class="col-33"><a href="#" class="button delete"">删除</a></div>'
                    + '</div>';
                $('.category-wrap').append(html);
            });

    //刪除未提交的類別
    $('.category-wrap').on('click','.row-product-category.temp .delete'
        ,function (e) {
            // console.log($(this).parent().parent());
            $(this).parent().parent().remove();
    });
    //刪除類別
    $('.category-wrap').on('click','.row-product-category.now .delete'
        ,function (e) {
            var target = e.currentTarget;
            $.confirm("確定刪除?",function () {
                $.ajax({
                    url : delUrl,
                    type : 'POST',
                    data : {
                        productCategoryId : target.dataset.id
                    },
                    dataType: 'json',
                    success : function (data) {
                        if(data.success){
                            $.toast('刪除成功');
                            getList();
                        }else {
                            $.toast('刪除失敗'+data.errMsg);
                        }
                    }
                })
            })
        });

    //提交新增類別
    $('#submit')
        .click(
            function () {
                var tempAttr = $('.temp');
                var productCategoryList = [];
                tempAttr.map(function (index, item) {
                    var tempObj = {};
                    tempObj.productCategoryName = $(item).find(".category").val();
                    tempObj.priority = $(item).find(".priority").val();
                    if (tempObj.productCategoryName && tempObj.priority) {
                        productCategoryList.push(tempObj);
                    }
                });
                $.ajax({
                    url : addUrl,
                    type : 'POST',
                    data : JSON.stringify(productCategoryList),
                    contentType : 'application/json',
                    success : function (data) {
                        if (data.success) {
                            $.toast('提交成功');
                            getList();
                        } else {
                            $.toast('提交失敗');
                        }
                    }
                })
            });
});