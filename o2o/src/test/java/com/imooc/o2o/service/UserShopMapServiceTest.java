package com.imooc.o2o.service;

import com.imooc.o2o.dto.UserShopMapExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserAwardMap;
import com.imooc.o2o.entity.UserShopMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserShopMapServiceTest {

    @Autowired
    private UserShopMapService userShopMapService;

    @Test
    //正常的獎品兌換
    public void testUserRedeemAward(){
        UserShopMap userShopMapCondition = new UserShopMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        Shop shop = new Shop();
        shop.setShopId(34L);
        userShopMapCondition.setShop(shop);
        userShopMapCondition.setUser(user);
        UserShopMapExecution userShopMapExecution = userShopMapService.userRedeemAward(userShopMapCondition
                , 27L);
        assertEquals(1,userShopMapExecution.getState());
    }
    @Test
    //異常的獎品兌換-點數不足
    public void testUserRedeemAwardWithInsufficientPoint(){
        UserShopMap userShopMapCondition = new UserShopMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        Shop shop = new Shop();
        shop.setShopId(34L);
        userShopMapCondition.setShop(shop);
        userShopMapCondition.setUser(user);
        UserShopMapExecution userShopMapExecution = userShopMapService.userRedeemAward(userShopMapCondition
                , 30L);
        assertEquals("點數不足",userShopMapExecution.getStateInfo());
    }
    @Test
    //異常的獎品兌換-獎品狀態為0
    public void testUserRedeemAwardWith(){
        UserShopMap userShopMapCondition = new UserShopMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        Shop shop = new Shop();
        shop.setShopId(34L);
        userShopMapCondition.setShop(shop);
        userShopMapCondition.setUser(user);
        UserShopMapExecution userShopMapExecution = userShopMapService.userRedeemAward(userShopMapCondition
                , 29L);
        assertEquals("獎品為空",userShopMapExecution.getStateInfo());
    }
}
