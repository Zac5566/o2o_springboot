package com.imooc.o2o.service;

import com.imooc.o2o.dto.AwardExecution;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.entity.Award;
import com.imooc.o2o.service.impl.AwardServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AwardServiceTest {

    @Autowired
    private AwardService awardService;

    @Test
    @Order(1)
    public void testAddAward() throws FileNotFoundException {
        Award award = new Award();
        award.setShopId(34L);
        award.setAwardName("溫可樂");
        award.setPoint(3);
        award.setPriority(1);
        award.setEnableStatus(1);
        award.setAwardDesc("溫可樂");
        ImageHolder imageHolder = new ImageHolder();
        InputStream is = new FileInputStream(new File("C:\\Users\\L\\Desktop\\coca.jpg"));
        imageHolder.setImage(is);
        imageHolder.setImageName("coca.jpg");
        AwardExecution awardExecution = awardService.addAward(award, imageHolder);
        assertEquals("操作成功", awardExecution.getStateInfo());
    }

    @Test
    @Order(2)
    public void testModifyAward() throws FileNotFoundException {
        Award award = new Award();
        award.setAwardId(25L);
        award.setShopId(34L);
        award.setAwardName("冰可樂");
        award.setPoint(1);
        award.setPriority(0);
        award.setEnableStatus(0);
        award.setAwardDesc("冰可樂");
        ImageHolder imageHolder = new ImageHolder();
        InputStream is = new FileInputStream(new File("C:\\Users\\L\\Desktop\\icecoca.jpg"));
        imageHolder.setImage(is);
        imageHolder.setImageName("icecoca.jpg");
        AwardExecution awardExecution = awardService.modifyAward(award, imageHolder);
        assertEquals("操作成功", awardExecution.getStateInfo());
    }

    @Test
    @Order(3)
    public void testQueryAwardList() {
        int rowIndex = 0;
        int pageSize = 999;
        Award awardCondition = new Award();
        awardCondition.setShopId(34L);
        awardCondition.setEnableStatus(0);
        AwardExecution awardExecution = awardService.queryAwardList(awardCondition, rowIndex, pageSize);
        assertEquals(2, awardExecution.getAwardList().size());
    }

}
