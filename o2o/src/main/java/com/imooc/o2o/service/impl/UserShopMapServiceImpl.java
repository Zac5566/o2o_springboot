package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.AwardDao;
import com.imooc.o2o.dao.UserAwardMapDao;
import com.imooc.o2o.dao.UserShopMapDao;
import com.imooc.o2o.dto.UserShopMapExecution;
import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.UserAwardMap;
import com.imooc.o2o.entity.UserShopMap;
import com.imooc.o2o.enums.UserShopMapStateEnum;
import com.imooc.o2o.exceptions.UserProductMapOperationException;
import com.imooc.o2o.service.UserShopMapService;
import com.imooc.o2o.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("userShopMapService")
public class UserShopMapServiceImpl implements UserShopMapService {

    @Autowired
    private UserShopMapDao userShopMapDao;
    @Autowired
    private AwardDao awardDao;
    @Autowired
    private UserAwardMapDao userAwardMapDao;

    @Override
    public UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, int pageIndex
            , int pageSize) {
        if (userShopMapCondition != null && pageIndex > -1 && pageSize > -1) {
            int rowIndex = PageUtil.pageIndexToRowIndex(pageIndex, pageSize);
            List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMap(userShopMapCondition
                    , rowIndex, pageSize);
            int count = userShopMapDao.countUserShopMap(userShopMapCondition);
            //封裝結果
            UserShopMapExecution ue = new UserShopMapExecution(UserShopMapStateEnum.SUCCESS);
            ue.setUserShopMapList(userShopMapList);
            ue.setCount(count);
            return ue;
        } else {
            return new UserShopMapExecution(UserShopMapStateEnum.EMPTY);

        }
    }

    @Override
    @Transactional
    public UserShopMapExecution userRedeemAward(UserShopMap userShopMapCondition, Long awardId)
            throws UserProductMapOperationException {
        //先用傳入的shopId與userId查詢該用戶在該店有沒有累積點數
        UserShopMap userShopMapSourcedFromDB = userShopMapDao.queryUserShopMap(userShopMapCondition
                , 0, 1).get(0);
        //查詢獎品是否存在&需要的點數
        Award award = awardDao.queryAwardById(awardId);
        //確認
        //  1.該用戶於該店有點數紀錄
        //  2.獎品存在
        //  3.獎品屬於該店鋪
        //  4.獎品可用狀態為1(可用)
        if (userShopMapSourcedFromDB != null && award != null
                && userShopMapSourcedFromDB.getShop().getShopId() == award.getShopId()
                && award.getEnableStatus() == 1) {
            int point = userShopMapSourcedFromDB.getPoint() - award.getPoint();
            if (point >= 0) {
                userShopMapSourcedFromDB.setPoint(point);
                try {
                    //更新用戶的累積點數
                    int userShopMapNum = userShopMapDao.updateUserPoint(userShopMapSourcedFromDB);
                    //封裝用戶兌換紀錄
                    UserAwardMap userAwardMap = encapsuleUserAward(userShopMapSourcedFromDB, award);
                    //更新用戶兌換紀錄
                    if (userShopMapNum == 1) {
                        userAwardMapDao.insertUserRedeemAwardRecord(userAwardMap);
                        return new UserShopMapExecution(UserShopMapStateEnum.SUCCESS);
                    } else {
                        return new UserShopMapExecution(UserShopMapStateEnum.EMPTY);
                    }
                } catch (Exception e) {
                    throw new UserProductMapOperationException("兌換失敗" + e.getMessage());
                }
            } else {
                return new UserShopMapExecution(UserShopMapStateEnum.REDEEM_FAIL);
            }
        } else {
            return new UserShopMapExecution(UserShopMapStateEnum.EMPTY);
        }
    }

    /**
     * 封裝用戶兌獎紀錄
     *
     * @param userShopMapSourcedFromDB:
     * @param award:
     * @Author: Zac5566
     * @Date: 2020/7/17
     * @return: com.imooc.o2o.entity.UserAwardMap
     */
    private UserAwardMap encapsuleUserAward(UserShopMap userShopMapSourcedFromDB, Award award) {
        UserAwardMap userAwardMap = new UserAwardMap();
        userAwardMap.setUser(userShopMapSourcedFromDB.getUser());
        userAwardMap.setAward(award);
        userAwardMap.setShop(userShopMapSourcedFromDB.getShop());
        userAwardMap.setCreateTime(new Date());
        userAwardMap.setUsedStatus(1);
        userAwardMap.setPoint(award.getPoint());
        return userAwardMap;
    }

}
