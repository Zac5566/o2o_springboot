package com.imooc.o2o.service;

import com.imooc.o2o.dto.UserAwardMapExecution;
import com.imooc.o2o.entity.UserAwardMap;

public interface UserAwardMapService {

    /**
     * 根據條件查詢用戶獎品兌換紀錄
     * @Author: Zac5566
     * @Date: 2020/7/15
     * @param userAwardMapCondition:
     * @param pageIndex:
     * @param pageSize:
     * @return: com.imooc.o2o.dto.UserAwardMapExecution
     */
    UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardMapCondition, int pageIndex, int pageSize);

}
