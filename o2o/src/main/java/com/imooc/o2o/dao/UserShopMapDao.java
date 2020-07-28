package com.imooc.o2o.dao;

import com.imooc.o2o.entity.UserShopMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userShopMapDao")
public interface UserShopMapDao {

    /**
     * 根據條件查詢某顧客在某店累積的點數
     * @Author: Zac5566
     * @Date: 2020/7/16
     * @param userShopMapCondition:
     * @param rowIndex:
     * @param pageSize:
     * @return: java.util.List<com.imooc.o2o.entity.UserShopMap>
     */
    List<UserShopMap> queryUserShopMap(@Param("userShopMapCondition") UserShopMap userShopMapCondition
            ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
    /**
     * 根據條件返回根據條件查詢某顧客在某店的數列總數
     * @Author: Zac5566
     * @Date: 2020/7/16
     * @param userShopMapCondition:
     * @return: int
     */
    int countUserShopMap(@Param("userShopMapCondition") UserShopMap userShopMapCondition);

    /**
     * 根據條件，更新用戶的點數
     * @Author: Zac5566
     * @Date: 2020/7/16
     * @param userShopMapCondition:
     * @return: int
     */
    int updateUserPoint(UserShopMap userShopMapCondition);

    /**
     * 根據條件，添加用戶的點數
     * @Author: Zac5566
     * @Date: 2020/7/18
     * @param userShopMapCondition:
     * @return: int
     */
    int insertUserPoint(UserShopMap userShopMapCondition);
}
