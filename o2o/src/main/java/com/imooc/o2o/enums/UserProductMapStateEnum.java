package com.imooc.o2o.enums;

public enum UserProductMapStateEnum {
    SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失敗"),NULL_USERPRODUCT_ID(-1002,"userProductId為空")
    ,NULL_USERPRODUCT_INFO(-1003,"userProduct為空");

    private int state;

    private String stateInfo;

    private UserProductMapStateEnum(int state,String stateInfo){
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
