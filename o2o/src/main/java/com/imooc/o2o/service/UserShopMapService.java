package com.imooc.o2o.service;

import com.imooc.o2o.dto.UserShopMapExecution;
import com.imooc.o2o.entity.UserShopMap;
import com.imooc.o2o.exceptions.UserProductMapOperationException;

public interface UserShopMapService {

    /**
     * 根據查詢條件返回用戶在店家累計的點數
     * @Author: Zac5566
     * @Date: 2020/7/14
     * @param userShopMapCondition:
     * @param rowIndex:
     * @param pageSize:
     * @return: com.imooc.o2o.dto.UserShopMapExecution
     */
    UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, int rowIndex, int pageSize);

    /**
     * 用戶兌換了獎品
     * @Author: Zac5566
     * @Date: 2020/7/16
     * @param userShopMapCondition:
     * @return: com.imooc.o2o.dto.UserShopMapExecution
     */
    UserShopMapExecution userRedeemAward(UserShopMap userShopMapCondition,Long awardId)
            throws UserProductMapOperationException;
}
