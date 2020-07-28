package com.imooc.o2o.dao;

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserShopMap;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserShopMapDaoTest {

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Test
    @Order(1)
    public void testQueryUserShopMap(){
        int rowIndex = 0;
        int pageSize = 999;
        //根據店家搜索
        UserShopMap userShopMapConditionByShop = new UserShopMap();
        Shop shop = new Shop();
        shop.setShopId(34L);
        userShopMapConditionByShop.setShop(shop);
        List<UserShopMap> userShopMapListByShop = userShopMapDao.queryUserShopMap(userShopMapConditionByShop
                ,rowIndex,pageSize);
        assertEquals(2,userShopMapListByShop.size());
        //根據使用者搜索
        UserShopMap userShopMapConditionByUser = new UserShopMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        userShopMapConditionByUser.setUser(user);
        List<UserShopMap> userShopMapListByUser = userShopMapDao.queryUserShopMap(userShopMapConditionByUser
                ,rowIndex,pageSize);
        assertEquals(3,userShopMapListByUser.size());
    }
    @Test
    @Order(2)
    public void testCountUserShopMap(){
        //根據店家搜索
        UserShopMap userShopMapConditionByShop = new UserShopMap();
        Shop shop = new Shop();
        shop.setShopId(34L);
        userShopMapConditionByShop.setShop(shop);
        int countByShop = userShopMapDao.countUserShopMap(userShopMapConditionByShop);
        assertEquals(2,countByShop);
        //根據使用者搜索
        UserShopMap userShopMapConditionByUser = new UserShopMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        userShopMapConditionByUser.setUser(user);
        int countByUser = userShopMapDao.countUserShopMap(userShopMapConditionByUser);
        assertEquals(3,countByUser);
    }
    @Test
    @Order(3)
    public void testUpdateUserShopMap(){
        UserShopMap userShopMap = new UserShopMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        Shop shop = new Shop();
        shop.setShopId(34L);
        userShopMap.setUser(user);
        userShopMap.setShop(shop);
        userShopMap.setPoint(100);
        int effectedNum = userShopMapDao.updateUserPoint(userShopMap);
        assertEquals(1,effectedNum);
    }
    @Test
    @Order(4)
    public void testInsertUserPoint(){
        UserShopMap userShopMapCondition = new UserShopMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        Shop shop = new Shop();
        shop.setShopId(16L);
        userShopMapCondition.setPoint(30);
        userShopMapCondition.setUser(user);
        userShopMapCondition.setShop(shop);
        userShopMapCondition.setCreateTime(new Date());
        int effectedNum = userShopMapDao.insertUserPoint(userShopMapCondition);
        assertEquals(1,effectedNum);
    }
}
