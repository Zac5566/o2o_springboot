package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductSellDaily;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProductSellDailyDao {

    /**
     * 根據條件返回商品日銷量的統計列表
     * @Author: Zac5566
     * @Date: 2020/7/4
     * @param productSellDailyCondition:
     * @param beginTime:
     * @param endTime:
     * @return: java.util.List<com.imooc.o2o.entity.ProductSellDaily>
     */
    List<ProductSellDaily> queryProductSellDailyList(
            @Param("productSellDailyCondition")ProductSellDaily productSellDailyCondition
            , @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);

    /**
     * 統計平台所有商品的日銷量
     * @Author: Zac5566
     * @Date: 2020/7/4
     * @return: int
     */
    int insertProductSellDaily();

    /**
     * 統計平台所有商品的日銷量(當天商品沒有銷量時置為0)
     * @Author: Zac5566
     * @Date: 2020/7/7
     * @return: int
     */
    int insertDefaultProductSellDaily();
}
