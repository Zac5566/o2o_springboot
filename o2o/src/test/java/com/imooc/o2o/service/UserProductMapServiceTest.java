package com.imooc.o2o.service;

import com.imooc.o2o.dto.UserProductMapExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserProductMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UserProductMapServiceTest {

    @Autowired
    private UserProductMapService userProductMapService;

    @Test
    public void testInsertUserPurchaseRecordWithUpdateUserShopMap(){
        UserProductMap userProductMapCondition = new UserProductMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        Shop shop = new Shop();
        shop.setShopId(34L);
        Product product = new Product();
        product.setProductId(16L);
        userProductMapCondition.setUser(user);
        userProductMapCondition.setShop(shop);
        userProductMapCondition.setProduct(product);
        UserProductMapExecution execution = userProductMapService.insertUserPurchaseRecord(userProductMapCondition);
        assertEquals("操作成功",execution.getStateInfo());

    }
    @Test
    public void testInsertUserPurchaseRecordWithInsertUserShopMap(){
        UserProductMap userProductMapCondition = new UserProductMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        Shop shop = new Shop();
        shop.setShopId(16L);
        Product product = new Product();
        product.setProductId(9L);
        userProductMapCondition.setUser(user);
        userProductMapCondition.setShop(shop);
        userProductMapCondition.setProduct(product);
        UserProductMapExecution execution = userProductMapService.insertUserPurchaseRecord(userProductMapCondition);
        assertEquals("操作成功",execution.getStateInfo());

    }
}
