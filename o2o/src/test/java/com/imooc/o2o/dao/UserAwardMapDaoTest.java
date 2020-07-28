package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserAwardMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserAwardMapDaoTest {

    @Autowired
    private UserAwardMapDao userAwardMapDao;

    @Test
    public void testQueryUserAwardMapWithNullCondition() {
        int pageIndex = 0;
        int pageSize = 999;
        List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMap(
                new UserAwardMap(), pageIndex, pageSize);
        assertEquals(4,userAwardMapList.size());
    }
    @Test
    public void testQueryUserAwardMapWithShopId() {
        int pageIndex = 0;
        int pageSize = 999;
        UserAwardMap userAwardMapCondition = new UserAwardMap();
        Shop shop = new Shop();
        shop.setShopId(34L);
        List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMap(
                userAwardMapCondition, pageIndex, pageSize);
        assertEquals(4,userAwardMapList.size());
    }
    @Test
    public void testQueryUserAwardMapWithShopName() {
        int pageIndex = 0;
        int pageSize = 999;
        UserAwardMap userAwardMapCondition = new UserAwardMap();
        Shop shop = new Shop();
        shop.setShopName("龙");
        List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMap(
                userAwardMapCondition, pageIndex, pageSize);
        assertEquals(4,userAwardMapList.size());
    }
    @Test
    public void testQueryUserAwardMapWithUserId() {
        int pageIndex = 0;
        int pageSize = 999;
        UserAwardMap userAwardMapCondition = new UserAwardMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        userAwardMapCondition.setUser(user);
        List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMap(
                userAwardMapCondition, pageIndex, pageSize);
        assertEquals(3,userAwardMapList.size());
    }
    @Test
    public void testQueryUserAwardMapWithUserName() {
        int pageIndex = 0;
        int pageSize = 999;
        UserAwardMap userAwardMapCondition = new UserAwardMap();
        PersonInfo user = new PersonInfo();
        user.setName("ad");
        userAwardMapCondition.setUser(user);
        List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMap(
                userAwardMapCondition, pageIndex, pageSize);
        assertEquals(3,userAwardMapList.size());
    }
    @Test
    public void testQueryUserAwardMapWithAwardName() {
        int pageIndex = 0;
        int pageSize = 999;
        UserAwardMap userAwardMapCondition = new UserAwardMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        userAwardMapCondition.setUser(user);
        Award award = new Award();
        award.setAwardName("3");
        userAwardMapCondition.setAward(award);
        List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMap(
                userAwardMapCondition, pageIndex, pageSize);
        assertEquals(1,userAwardMapList.size());
    }
    @Test
    public void testCountUserAwardMap() {
        UserAwardMap userAwardMapCondition = new UserAwardMap();
        PersonInfo user = new PersonInfo();
        int count = userAwardMapDao.countUserAwardMap(userAwardMapCondition);
        assertEquals(4,count);
    }
    @Test
    public void testInsertUserRedeemAwardRecord(){
        UserAwardMap userAwardMap = new UserAwardMap();
        //用戶
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        userAwardMap.setUser(user);
        //獎品
        Award award = new Award();
        award.setAwardId(25L);
        userAwardMap.setAward(award);
        //商店
        Shop shop = new Shop();
        shop.setShopId(34L);
        userAwardMap.setShop(shop);
        //其他
        userAwardMap.setCreateTime(new Date());
        userAwardMap.setOperator(null);
        userAwardMap.setPoint(3);
        userAwardMap.setUsedStatus(1);
        int effectedNum = userAwardMapDao.insertUserRedeemAwardRecord(userAwardMap);
        assertEquals(1,effectedNum);
    }
}
