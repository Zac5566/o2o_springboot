$(function () {
    var awardId = getQueryString("awardId");
    var awardDetailUrl = '/o2o/frontend/getawarddetail?awardId=' + awardId;
    getAwardDetail();
    function getAwardDetail(){
        $.getJSON(awardDetailUrl, function (data) {
            if (data.success) {
                var award = data.award;
                $('#award-name').text(award.awardName);
                $('#award-img').attr("src",getContextPath()+award.awardImg);
                $('#award-time').text(new Date(award.lastEditTime).Format('yyyy-MM-dd'));
                $('#award-point').text(award.point);
                $('#award-desc').text(award.awardDesc);
            }
        });
    }
    //加載側邊攔
    $('#panel-right-demo').load('sidepanel.html');
    //側邊欄開啟
    $('#me').on('click',function () {
        $.openPanel('#panel-right-demo');
    });
});