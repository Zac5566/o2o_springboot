$(function () {
    // edit flag
    var isEdit = false;
    //如果是編輯頁面url會有id
    var awardId = getQueryString("awardId");

    getAward();

    //取得要編輯的商品
    function getAward() {
        //取得準備編輯的商品
        var awardDetailUrl = '/o2o/shopadmin/getaward?awardId=' + awardId;
        $.getJSON(awardDetailUrl, function (data) {
            if (data.success) {
                var award = data.award;
                $('#award-name').val(award.awardName);
                $('#priority').val(award.priority);
                $('#point').val(award.point);
                $('#award-desc').val(award.awardDesc);
                isEdit = true;
            }
        });
    }

    $("#submit").click(function () {
        //確認驗證碼
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast("請輸入驗證碼");
            return;
        }
        //封裝award
        var award = {};
        award.awardName = $('#award-name').val();
        award.priority = $('#priority').val();
        award.point = $('#point').val();
        award.awardDesc = $('#award-desc').val();
        //判斷是編輯還是新增
        if (isEdit) {
            var postAwardUrl = '/o2o/shopadmin/modifyaward'
            award.awardId = awardId;
        } else {
            var postAwardUrl = '/o2o/shopadmin/addaward';
        }
        //處理縮圖
        var thumbnail = $('#small-img')[0].files[0];
        var formData = new FormData();
        formData.append('verifyCodeActual', verifyCodeActual);
        formData.append('awardStr', JSON.stringify(award));
        formData.append('thumbnail', thumbnail);

        $.ajax({
            url: postAwardUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('添加成功');
                } else {
                    $.toast('添加失敗');
                }
                $('#captcha_img').click();
            }
        });
    });


});