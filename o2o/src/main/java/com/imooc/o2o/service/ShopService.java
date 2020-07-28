package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

public interface ShopService {
    ShopExecution addShop(Shop shop, ImageHolder thumbnail);
    ShopExecution updateShop(Shop shop, ImageHolder thumbnail);
    Shop queryByShopId(Long shopId);
    ShopExecution getShopList(@Param("shopCondition") Shop shopCondition, @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);
}
