package com.imooc.o2o.dto;

import com.imooc.o2o.entity.UserShopMap;
import com.imooc.o2o.enums.UserShopMapStateEnum;

import java.util.List;

public class UserShopMapExecution {

    private int state;

    private String stateInfo;

    private int count;

    private UserShopMap UserShopMap;

    private List<UserShopMap> UserShopMapList;

    public UserShopMapExecution(){}

    public UserShopMapExecution(UserShopMapStateEnum stateEnum){
        state = stateEnum.getState();
        stateInfo = stateEnum.getStateInfo();
    }
    public UserShopMapExecution(UserShopMapStateEnum stateEnum, UserShopMap UserShopMap){
        state = stateEnum.getState();
        stateInfo = stateEnum.getStateInfo();
        this.UserShopMap = UserShopMap;
    }
    public UserShopMapExecution(UserShopMapStateEnum stateEnum, List<UserShopMap> UserShopMapList){
        state = stateEnum.getState();
        stateInfo = stateEnum.getStateInfo();
        this.UserShopMapList = UserShopMapList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public UserShopMap getUserShopMap() {
        return UserShopMap;
    }

    public void setUserShopMap(UserShopMap UserShopMap) {
        this.UserShopMap = UserShopMap;
    }

    public List<UserShopMap> getUserShopMapList() {
        return UserShopMapList;
    }

    public void setUserShopMapList(List<UserShopMap> UserShopMapList) {
        this.UserShopMapList = UserShopMapList;
    }
}
