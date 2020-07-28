$(function () {
    //商品列表路由
    var initUrl = "/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999";
    //修改商品路由
    var modifyUrl = "/o2o/shopadmin/modifyproduct";

    getList();

    //獲取商品列表
    function getList(){
        $.getJSON(initUrl,function (data) {
            if(data.success){
                var html = '';
                var productList = data.productList;
                productList.map(function (item,index) {
                    var textOp = '將商品上架';
                    var reverseStatus = 1;
                    if (item.enableStatus==1){
                        textOp = '將商品下架';
                        reverseStatus = 0;
                    }
                    html += '<div class="row row-product">'
                        + '<div class="col-33">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-20">'
                        + item.point
                        + '</div>'
                        + '<div class="col-40">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '">编辑</a>'
                        + '<a href="#" class="status" data-id="'
                        + item.productId
                        + '"data-reverseStatus="'
                        + reverseStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.productId
                        + '">预览</a>'
                        + '</div></div>'
                })
                $('.product-wrap').html(html);
            }
        });
    }

    //"操作"連結的路由分配
    $('.product-wrap')
        .on('click','a',function (e) {
            var target = $(e.currentTarget);
            if(target.hasClass('edit')){
                //編輯
                window.location.href = '/o2o/shopadmin/productoperation?productId='+
                e.currentTarget.dataset.id;
            }else if(target.hasClass('status')){
                //上下架
                changeItemStatus(e.currentTarget.dataset.id,e.currentTarget.dataset.reversestatus);
            }else if(target.hasClass('preview')){
                //預覽
                window.location.href = '/o2o/frontend/productdetail?productId='
                    + e.currentTarget.dataset.id;
            }
        });

    //商品上下架
    function changeItemStatus(id,reverseStatus) {
        var product = {};
        product.productId = id;
        product.enableStatus = reverseStatus;
        $.confirm("確定修改?",function () {
            $.ajax({
                url : modifyUrl,
                type :'POST',
                data : {
                    'productStr' :JSON.stringify(product),
                    'statusChange' : true
                },
                success : function (data) {
                    if(data.success){
                        $.toast('修改成功');
                        getList();
                    }else {
                        $.toast('修改失敗');
                    }
                }
            })
        });
    }

})