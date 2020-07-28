package com.imooc.o2o.dao;

import com.imooc.o2o.entity.UserAwardMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userAwardMapDao")
public interface UserAwardMapDao {

    /**
     * 根據條件返回用戶點數兌換商品紀錄
     * @Author: Zac5566
     * @Date: 2020/7/15
     * @param userAwardMapCondition:
     * @param rowIndex:
     * @param pageSize:
     * @return: java.util.List<com.imooc.o2o.entity.UserAwardMap>
     */
    List<UserAwardMap> queryUserAwardMap(@Param("userAwardMapCondition") UserAwardMap userAwardMapCondition,
                                         @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 根據條件返回用戶商品兌換紀錄列表的總數
     * @Author: Zac5566
     * @Date: 2020/7/15
     * @param userAwardMapCondition:
     * @return: int
     */
    int countUserAwardMap(@Param("userAwardMapCondition") UserAwardMap userAwardMapCondition);

    /**
     * 添加用戶兌換獎品的紀錄
     * @Author: Zac5566
     * @Date: 2020/7/16
     * @param userAwardMap:
     * @return: int
     */
    int insertUserRedeemAwardRecord(UserAwardMap userAwardMap);
}
