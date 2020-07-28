$(function () {
    var productName = '';
    //頁碼
    var pageIndex = 1;
    //一頁載入數量
    var pageSize = 999;

    //銷售列表
    getList();

    //7天的銷售Echart
    getProductSellDailyList();

    //獲取顧客購買商品的訊息列表
    function getList() {
        var url = '/o2o/shopadmin/listuserproductmapbyshop?pageIndex=' + pageIndex
            + '&pageSize=' + pageSize + '&productName=' + productName;
        //加載購買產品的客戶與時間資訊
        $.getJSON(url, function (data) {
            if (data.success) {
                var tempHtml = '';
                var productMap = data.userProductMapList;
                productMap.map(function (item, index) {
                    tempHtml +=
                        '<div class="row row-productbuycheck"><div class="col-20">'
                        + item.product.productName
                        + '</div><div class="col-40 productbuycheck-time">'
                        + new Date(item.createTime).Format('yyyy-MM-dd')
                        + '</div><div class="col-20">'
                        + item.user.name
                        + '</div><div class="col-20">'
                        + item.point
                        + '</div></div>'
                });
                $('.productbuycheck-wrap').html(tempHtml);
            };
        });
    }

    //查詢商品的銷售紀錄
    $('#search').on('change', function (e) {
        productName = $('#search').val();
        $('.productbuycheck-wrap').empty();
        getList();
    });

    //獲取商品7天的銷量
    function getProductSellDailyList() {
        var listProductSellDailyUrl = '/o2o/shopadmin/listproductselldailybyshop';
        $.getJSON(listProductSellDailyUrl,function (data) {
            if(data.success){
              var myChart = echarts.init(document.getElementById('chart'));
              var option = generateStaticEchartPart();
              option.legend.data = data.legendData;
              option.xAxis = data.xAxis;
              option.series = data.series;
              myChart.setOption(option);
            };
        });
    }

    //echart的數據框架
    function generateStaticEchartPart() {
        /** echarts逻辑部分 * */
        var option = {
            // 提示框，鼠标悬浮交互时的信息提示
            tooltip: {
                trigger: 'axis',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 鼠标移动到轴的时候，显示阴影
                }
            },
            // 图例，每个图表最多仅有一个图例
            legend: {},
            // 直角坐标系内绘图网格
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            // 直角坐标系中横轴数组，数组中每一项代表一条横轴坐标轴
            xAxis: [{}],
            // 直角坐标系中纵轴数组，数组中每一项代表一条纵轴坐标轴
            yAxis: [{
                type: 'value'
            }]
        };
        return option;
    }
});