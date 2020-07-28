package com.imooc.o2o.service;

import com.imooc.o2o.entity.ProductSellDaily;

import java.util.Date;
import java.util.List;

public interface ProductSellDailyService {

    //每日定時對所有店鋪的產品銷量統計
    void dailyCalculate();

    /**
     * 根據查詢條件返回商品日銷售的統計列表
     * @Author: Zac5566
     * @Date: 2020/7/5
     * @param productSellDailyCondition:
     * @param beginTime:
     * @param endTime:
     * @return: java.util.List<com.imooc.o2o.entity.ProductSellDaily>
     */
    List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition
            , Date beginTime, Date endTime);
}
