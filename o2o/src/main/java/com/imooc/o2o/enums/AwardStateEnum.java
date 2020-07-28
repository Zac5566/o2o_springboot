package com.imooc.o2o.enums;

public enum  AwardStateEnum {
    SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失敗"),EMPTY(-1002,"獎品為空");

    private int state;
    private String stateInfo;

    private AwardStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static AwardStateEnum stateOf(int index){
        for(AwardStateEnum stateEnum:values()){
            if(stateEnum.getState() == index){
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
