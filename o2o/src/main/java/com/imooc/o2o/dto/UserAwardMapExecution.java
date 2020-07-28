package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.UserAwardMap;
import com.imooc.o2o.enums.UserAwardMapStateEnum;

import java.util.List;

public class UserAwardMapExecution {
    // 結果狀態
    private int state;

    //狀態標示
    private String stateInfo;

    //商品數量
    private int count;

    //操作的UserAwardMap(增刪改用到)
    private UserAwardMap userAwardMap;

    //UserAwardMap列表(查詢使用)
    private List<UserAwardMap> userAwardMapList;

    public UserAwardMapExecution() {
    }

    //商品操作失敗時使用的構造器
    public UserAwardMapExecution(UserAwardMapStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //商品操作成功時使用的構造器
    public UserAwardMapExecution(UserAwardMapStateEnum stateEnum, UserAwardMap userAwardMap) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.userAwardMap = userAwardMap;
    }

    //商品操作成功時使用的構造器
    public UserAwardMapExecution(UserAwardMapStateEnum stateEnum, List<UserAwardMap> userAwardMapList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.userAwardMapList = userAwardMapList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UserAwardMap getUserAwardMap() {
        return userAwardMap;
    }

    public void setUserAwardMap(UserAwardMap userAwardMap) {
        this.userAwardMap = userAwardMap;
    }

    public List<UserAwardMap> getUserAwardMapList() {
        return userAwardMapList;
    }

    public void setUserAwardMapList(List<UserAwardMap> userAwardMapList) {
        this.userAwardMapList = userAwardMapList;
    }
}

