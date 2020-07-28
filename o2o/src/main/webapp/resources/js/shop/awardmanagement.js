$(function () {
    //頁碼
    var pageIndex = 1;
    //一頁加載多少紀錄
    var pageSize = 999;
    //awardList路由
    var awardListUrl = '/o2o/shopadmin/getawardlist?pageIndex=' + pageIndex +'&pageSize=' + pageSize;
    //修改狀態
    var modifyUrl ='/o2o/shopadmin/modifyaward';
    getAwardList();

    //顯示列表
    function getAwardList() {
        $.getJSON(awardListUrl,function (data) {
            var tempHtml  = '';
            if(data.success){
                data.awardList.map(function (item,index) {
                    var textOp = '將獎品上架';
                    var reverseStatus = 1;
                    if(item.enableStatus==1){
                        textOp ='將獎品下架';
                        reverseStatus = 0;
                    }
                    tempHtml +=
                        '<div class="row row-award">'
                        + '<div class="col-33">'
                        + item.awardName
                        + '</div>'
                        + '<div class="col-20">'
                        + item.point
                        + '</div>'
                        + '<div class="col-40">'
                        + '<a href="#" class="edit" data-id="'
                        + item.awardId
                        + '"">編輯</a>'
                        + '<a href="#" class="status" data-id="'
                        + item.awardId
                        + '"data-reversestatus="'
                        + reverseStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.awardId
                        + '">預覽</a></div></div>';
                });
                $('.award-wrap').html(tempHtml);
            }
        });
    }
    //編輯商品
    $('.award-wrap').on('click','a',function (e) {
        var target = $(e.currentTarget);
        var awardId = e.currentTarget.dataset.id;
        var enableStatus = e.currentTarget.dataset.reversestatus;
        if(target.hasClass('edit')){
            window.location.href = '/o2o/shopadmin/awardoperation?awardId='+ awardId;
        }else if(target.hasClass('status')){
            changeItemStatus(awardId,enableStatus);
        }else if(target.hasClass('preview')){
            window.location.href = '/o2o/frontend/awarddetail?awardId=' + awardId;
        }
    });
    //上下架設定
    function changeItemStatus(awardId,reverseStatus) {
        var award = {};
        award.awardId = awardId;
        award.enableStatus = reverseStatus;
        $.confirm("確定修改?",function () {
            $.ajax({
                url : modifyUrl,
                type :'POST',
                data : {
                    'awardStr' :JSON.stringify(award),
                    'statusChange' : true
                },
                success : function (data) {
                    if(data.success){
                        $.toast('修改成功');
                        getAwardList();
                    }else {
                        $.toast('修改失敗');
                    }
                }
            })
        })

    }
});