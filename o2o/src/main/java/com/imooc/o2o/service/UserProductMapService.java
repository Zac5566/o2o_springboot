package com.imooc.o2o.service;

import com.imooc.o2o.dto.UserProductMapExecution;
import com.imooc.o2o.entity.UserProductMap;
import com.imooc.o2o.exceptions.UserProductMapOperationException;

public interface UserProductMapService {

    /**
     * 根據條件查出用戶消費訊息列表
     * @Author: Zac5566
     * @Date: 2020/7/5
     * @param userProductMapCondition:
     * @param pageIndex:
     * @param pageSize:
     * @return: com.imooc.o2o.dto.UserProductMapExecution
     */
    UserProductMapExecution listUserProductMap(UserProductMap userProductMapCondition
            ,Integer pageIndex,Integer pageSize);


    /**
     * 用戶購買商品的方法
     * @Author: Zac5566
     * @Date: 2020/7/18
     * @param userProductMapCondition:
     * @return: com.imooc.o2o.dto.UserProductMapExecution
     */
    UserProductMapExecution insertUserPurchaseRecord(UserProductMap userProductMapCondition)
            throws UserProductMapOperationException;
}
