package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Award;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AwardDaoTest {

    @Autowired
    private AwardDao awardDao;

    @Test
    @Order(1)
    public void testInsetAward(){
        Award award = new Award();
        award.setAwardName("可樂");
        award.setAwardDesc("冰可樂");
        award.setAwardImg("\\upload\\images\\item\\shop\\34\\2020070116131069342.jpg");
        award.setPoint(2);
        award.setPriority(3);
        award.setCreateTime(new Date());
        award.setLastEditTime(new Date());
        award.setEnableStatus(1);
        award.setShopId(34L);
        int effectedNum = awardDao.insertAward(award);
        assertEquals(1,effectedNum);
    }
    @Test
    @Order(2)
    public void testUpdateAward(){
        Award award = new Award();
        award.setAwardId(17L);
        award.setAwardName("可樂2");
        award.setAwardDesc("冰可樂2");
        award.setAwardImg("\\upload\\item\\shop\\34\\2020070116131069342.jpg");
        award.setPoint(3);
        award.setPriority(4);
        award.setLastEditTime(new Date());
        award.setEnableStatus(0);
        award.setShopId(34L);
        int effectedNum = awardDao.updateAward(award);
        assertEquals(1,effectedNum);
    }
    @Test
    @Order(3)
    public void testQueryAwardByCondition(){
        int rowIndex = 0;
        int pageSize = 999;
        Award awardCondition = new Award();
        //查全部
        List<Award> awardList1 =awardDao.queryAwardByCondition(awardCondition,rowIndex,pageSize);
        assertEquals(10,awardList1.size());
        //測名子
        awardCondition.setAwardName("可");
        List<Award> awardList2 = awardDao.queryAwardByCondition(awardCondition,rowIndex,pageSize);
        assertEquals(6,awardList2.size());
        //狀態
        awardCondition.setEnableStatus(0);
        List<Award> awardList4 = awardDao.queryAwardByCondition(awardCondition,rowIndex,pageSize);
        assertEquals(4,awardList4.size());
        //shopId
        awardCondition.setShopId(34L);
        List<Award> awardList5 = awardDao.queryAwardByCondition(awardCondition,rowIndex,pageSize);
        assertEquals(4,awardList5.size());
    }
    @Test
    @Order(4)
    public void testQueryAwardById(){
        Award award = awardDao.queryAwardById(17L);
        assertEquals("可樂2",award.getAwardName());
    }
    @Test
    public void testCountAwardByCondition(){
        Award awardCondition = new Award();
        awardCondition.setShopId(34L);
        int count = awardDao.countAwardByCondition(awardCondition);
        assertEquals(5,count);
    }
}
