package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Award;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("awardDao")
public interface AwardDao {


    int insertAward(Award award);

    int updateAward(Award award);

    /**
     * 根據id查詢獎品
     *
     * @param awardId:
     * @Author: Zac5566
     * @Date: 2020/7/12
     * @return: com.imooc.o2o.entity.Award
     */
    Award queryAwardById(Long awardId);

    /**
     * 根據條件查詢獎品
     *
     * @param AwardCondition:
     * @param rowIndex:
     * @param pageSize:
     * @Author: Zac5566
     * @Date: 2020/7/15
     * @return: java.util.List<com.imooc.o2o.entity.Award>
     */
    List<Award> queryAwardByCondition(@Param("awardCondition") Award AwardCondition
            , @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 依條件返回查詢總數
     * @Author: Zac5566
     * @Date: 2020/7/15
     * @param AwardCondition:
     * @return: int
     */
    int countAwardByCondition(@Param("awardCondition")Award AwardCondition);
}
