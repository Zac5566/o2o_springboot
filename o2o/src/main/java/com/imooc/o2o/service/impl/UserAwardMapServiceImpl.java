package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.UserAwardMapDao;
import com.imooc.o2o.dto.UserAwardMapExecution;
import com.imooc.o2o.entity.UserAwardMap;
import com.imooc.o2o.enums.UserAwardMapStateEnum;
import com.imooc.o2o.service.UserAwardMapService;
import com.imooc.o2o.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userAwardMapService")
public class UserAwardMapServiceImpl implements UserAwardMapService {

    @Autowired
    private UserAwardMapDao userAwardMapDao;

    @Override
    public UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardMapCondition, int pageIndex
            , int pageSize) {
        int rowIndex = PageUtil.pageIndexToRowIndex(pageIndex, pageSize);
        List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMap(userAwardMapCondition
                , rowIndex, pageSize);
        int count = userAwardMapDao.countUserAwardMap(userAwardMapCondition);
        //封裝查詢結果
        UserAwardMapExecution execution = new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS);
        execution.setUserAwardMapList(userAwardMapList);
        execution.setCount(count);
        return execution;
    }
}
