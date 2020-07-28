package com.imooc.o2o.service;

import com.imooc.o2o.dto.AwardExecution;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.entity.Award;
import com.imooc.o2o.exceptions.AwardOperationException;

import java.util.List;

public interface AwardService {

    /**
     * 根據條件查詢獎品
     * @Author: Zac5566
     * @Date: 2020/7/15
     * @param awardCondition:
     * @param pageIndex:
     * @param pageSize:
     * @return: com.imooc.o2o.dto.AwardExecution
     */
    AwardExecution queryAwardList(Award awardCondition,int pageIndex,int pageSize);

    /**
     * 添加商品
     * @Author: Zac5566
     * @Date: 2020/7/13
     * @param award:
     * @param thumbnail: 獎品縮圖
     * @return: com.imooc.o2o.dto.AwardExecution
     */
    AwardExecution addAward(Award award, ImageHolder thumbnail) throws AwardOperationException;

    /**
     * 修改商品
     * @Author: Zac5566
     * @Date: 2020/7/13
     * @param award:
     * @param thumbnail:
     * @return: com.imooc.o2o.dto.AwardExecution
     */
    AwardExecution modifyAward(Award award, ImageHolder thumbnail) throws AwardOperationException;

    /**
     * 根據id查詢商品
     * @Author: Zac5566
     * @Date: 2020/7/12
     * @param awardId:
     * @return: com.imooc.o2o.entity.Award
     */
    Award queryAwardById(Long awardId);
}
