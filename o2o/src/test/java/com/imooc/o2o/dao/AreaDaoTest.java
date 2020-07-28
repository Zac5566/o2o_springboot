package com.imooc.o2o.dao;

import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class AreaDaoTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryAll(){
        List<Area> areaList = areaDao.queryAll();
        assertEquals(5,areaList.size());
    }
}
