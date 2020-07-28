package com.imooc.o2o.enums;

public enum UserShopMapStateEnum {
    SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失敗"), EMPTY(-1002, "獎品為空")
    ,REDEEM_FAIL(-1003,"點數不足");

    private int state;
    private String stateInfo;

    private UserShopMapStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
