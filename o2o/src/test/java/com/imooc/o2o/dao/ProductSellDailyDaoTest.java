package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductSellDaily;
import com.imooc.o2o.entity.Shop;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductSellDailyDaoTest {

    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    @Test
    @Ignore
    public void TestAQueryProductSellDailyList() throws ParseException {
        ProductSellDaily productSellDaily = new ProductSellDaily();
        List<ProductSellDaily> sellDailyList = productSellDailyDao.queryProductSellDailyList(
                productSellDaily, null, null);
        assertEquals(58,sellDailyList.size());
        //按照店鋪查詢
        Shop shop = new Shop();
        shop.setShopId(15L);
        productSellDaily.setShop(shop);
        List<ProductSellDaily> sellDailyList2 = productSellDailyDao.queryProductSellDailyList(
                productSellDaily, null, null);
        assertEquals(13,sellDailyList2.size());
        //按照商品名稱模糊查詢
        Product product = new Product();
        product.setProductName("咖啡");
        productSellDaily.setProduct(product);
        List<ProductSellDaily> sellDailyList3 = productSellDailyDao.queryProductSellDailyList(
                productSellDaily, null, null);
        assertEquals(4,sellDailyList3.size());
        //按照日期查詢
        //beginTime
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginTime = format.parse("2017-01-01");
        List<ProductSellDaily> sellDailyList4 = productSellDailyDao.queryProductSellDailyList(
                productSellDaily,beginTime,null);
        assertEquals(4,sellDailyList4.size());
        //endTime
        Date endTime = format.parse("2018-12-31");
        List<ProductSellDaily> sellDailyList5 = productSellDailyDao.queryProductSellDailyList(
                productSellDaily,null,endTime);
        assertEquals(4,sellDailyList5.size());
        //beginTime&endTime
        List<ProductSellDaily> sellDailyList6 = productSellDailyDao.queryProductSellDailyList(
                productSellDaily,beginTime,endTime);
        assertEquals(4,sellDailyList5.size());
    }

    @Test
    @Ignore
    public void testBInsertProductSellDaily(){
        int productNum = productSellDailyDao.insertProductSellDaily();
        assertEquals(4,productNum);
    }
    @Test
    public void testCInsertDefaultProductSellDaily(){
        int productNum = productSellDailyDao.insertDefaultProductSellDaily();
        assertEquals(16,productNum);
    }
}
