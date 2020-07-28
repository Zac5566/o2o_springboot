package com.imooc.o2o.service;

import com.imooc.o2o.entity.Area;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AreaServiceTest{

    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheService cacheService;

    @Test
    public void testAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("西苑", ((Area)areaList.get(0)).getAreaName());
        cacheService.removeFromCache(AreaService.AREALISTKEY);
        areaList = this.areaService.getAreaList();

    }
}
