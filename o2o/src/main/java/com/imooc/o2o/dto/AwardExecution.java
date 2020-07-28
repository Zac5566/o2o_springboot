package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.enums.AwardStateEnum;
import com.imooc.o2o.enums.ProductStateEnum;

import java.util.List;

public class AwardExecution {
    // 結果狀態
    private int state;

    //狀態標示
    private String stateInfo;

    //商品數量
    private int count;

    //操作的award(增刪改用到)
    private Award award;

    //award列表(查詢使用)
    private List<Award> awardList;

    public AwardExecution() {
    }

    //商品操作失敗時使用的構造器
    public AwardExecution(AwardStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //商品操作成功時使用的構造器
    public AwardExecution(AwardStateEnum stateEnum, Award award) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.award = award;
    }

    //商品操作成功時使用的構造器
    public AwardExecution(AwardStateEnum stateEnum, List<Award> awardList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.awardList = awardList;
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

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public List<Award> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<Award> awardList) {
        this.awardList = awardList;
    }
}

