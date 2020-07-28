package com.imooc.o2o.dao;

import com.imooc.o2o.entity.UserProductMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userProductMapDao")
public interface UserProductMapDao {

    /**
     * 根據條件查詢用戶的購買紀錄列表
     * @Author: Zac5566
     * @Date: 2020/7/3
     * @param userProductMapCondition:
     * @param rowIndex:
     * @param pageSize:
     * @return: java.util.List<com.imooc.o2o.entity.UserProductMap>
     */
    List<UserProductMap> queryUserProductMapList(
            @Param("userProductMapCondition")UserProductMap userProductMapCondition
            ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 根據條件返回用戶購買商品的紀錄總數
     * @Author: Zac5566
     * @Date: 2020/7/3
     * @param userProductMapCondition:
     * @return: int
     */
    int queryProductMapCount(@Param("userProductMapCondition")UserProductMap userProductMapCondition);
    /**
     * 添加用戶購買商品的紀錄
     * @Author: Zac5566
     * @Date: 2020/7/3
     * @param userProductMap:
     * @return: int
     */
    int insertUserProductMap(UserProductMap userProductMap);

}
