package com.imooc.o2o.dao;

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserProductMap;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.catalina.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserProductMapDaoTest {

    @Autowired
    private UserProductMapDao userProductMapDao;

    @Test
    @Order(1)
    @Ignore
    public void testAInsertUserProductMap(){
        UserProductMap userProductMap = new UserProductMap();
        PersonInfo buyer = new PersonInfo();
        buyer.setUserId(16L);
        PersonInfo operator = new PersonInfo();
        operator.setUserId(17L);
        Product product = new Product();
        product.setProductId(4L);
        Shop shop = new Shop();
        shop.setShopId(15L);
        userProductMap.setCreateTime(new Date());
        userProductMap.setOperator(operator);
        userProductMap.setPoint(3);
        userProductMap.setProduct(product);
        userProductMap.setShop(shop);
        userProductMap.setUser(buyer);
        int effectedNum = userProductMapDao.insertUserProductMap(userProductMap);
        assertEquals(1,effectedNum);
    }
    @Test
    @Order(2)
    public void testBQueryUserProductMapList(){
        //查詢全部
        UserProductMap userProductMap = new UserProductMap();
        int rowIndex = 0;
        int pageIndex = 99;
//        List<UserProductMap> list = userProductMapDao.queryUserProductMapList(userProductMap
//                , rowIndex, pageIndex);
//        assertEquals(19,list.size());
//
//        //按店鋪訊息精準查詢
//        Shop shop = new Shop();
//        shop.setShopId(34L);
//        //userProductMap設置商店
//        userProductMap.setShop(shop);
//        List<UserProductMap> list3 =userProductMapDao.queryUserProductMapList(userProductMap,rowIndex,pageIndex);
//        assertEquals(6,list3.size());

//        //按商品名稱模糊查詢
//        Product product = new Product();
//        product.setProductName("羊");
//        //userProductMap設置商品
//        userProductMap.setProduct(product);
//        List<UserProductMap> list5 = userProductMapDao.queryUserProductMapList(userProductMap,rowIndex,pageIndex);
//        assertEquals(4,list5.size());

        //顧客訊息
        PersonInfo user = new PersonInfo();
        user.setUserId(13L);
        userProductMap.setUser(user);
        List<UserProductMap> list6 = userProductMapDao.queryUserProductMapList(
                userProductMap, rowIndex, pageIndex);
        assertEquals(12,list6.size());
        //顧客購買的商品
        Product product = new Product();
        product.setProductName("冰");
        userProductMap.setProduct(product);
        List<UserProductMap> list7 = userProductMapDao.queryUserProductMapList(
                userProductMap, rowIndex, pageIndex);
        assertEquals(2,list7.size());
    }
    @Test
    @Order(3)
    @Ignore
    public void testQueryProductMapCount(){
        int count = userProductMapDao.queryProductMapCount(new UserProductMap());
        assertEquals(19,count);
    }

}
