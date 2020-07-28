package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.AwardDao;
import com.imooc.o2o.dto.AwardExecution;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.entity.Award;
import com.imooc.o2o.enums.AwardStateEnum;
import com.imooc.o2o.exceptions.AwardOperationException;
import com.imooc.o2o.service.AwardService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("awardService")
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardDao awardDao;

    @Override
    public AwardExecution queryAwardList(Award awardCondition,int pageIndex,int pageSize) {
        int rowIndex = PageUtil.pageIndexToRowIndex(pageIndex, pageSize);
        List<Award> awardList = awardDao.queryAwardByCondition(awardCondition,rowIndex,pageSize);
        int count = awardDao.countAwardByCondition(awardCondition);
        //封裝結果
        AwardExecution execution = new AwardExecution(AwardStateEnum.SUCCESS);
        execution.setAwardList(awardList);
        execution.setCount(count);
        return execution;
    }

    @Override
    @Transactional
    public AwardExecution addAward(Award award, ImageHolder thumbnail) throws AwardOperationException {
        if (award != null && award.getShopId() != null) {
            award.setEnableStatus(1);
            award.setCreateTime(new Date());
            award.setLastEditTime(new Date());
            if (thumbnail != null) {
                addThunbnail(award, thumbnail);
            }
            try {
                int effedtedNum = awardDao.insertAward(award);
                if (effedtedNum <= 0) {
                    throw new AwardOperationException("創建獎品失敗");
                }
                return new AwardExecution(AwardStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new AwardOperationException(e.getMessage());
            }
        } else {
            return new AwardExecution(AwardStateEnum.EMPTY);
        }
    }

    private void addThunbnail(Award award, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(award.getShopId());
        String relativeAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        award.setAwardImg(relativeAddr);
    }

    @Override
    @Transactional
    public AwardExecution modifyAward(Award award, ImageHolder thumbnail) throws AwardOperationException {
        if (award != null && award.getShopId() != null) {
            award.setLastEditTime(new Date());
            if (thumbnail != null) {
                Award oldAward = awardDao.queryAwardById(award.getAwardId());
                if (oldAward != null && oldAward.getAwardImg() != null) {
                    //修改縮圖時先將舊的刪除
                    ImageUtil.deleteFileOrPath(oldAward.getAwardImg());
                }
                addThunbnail(award, thumbnail);
            }
            try {
                int effedtedNum = awardDao.updateAward(award);
                if (effedtedNum <= 0) {
                    throw new AwardOperationException("修改獎品失敗");
                }
                return new AwardExecution(AwardStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new AwardOperationException(e.getMessage());
            }
        } else {
            return new AwardExecution(AwardStateEnum.EMPTY);
        }
    }

    @Override
    public Award queryAwardById(Long awardId) {
        return awardDao.queryAwardById(awardId);
    }
}
