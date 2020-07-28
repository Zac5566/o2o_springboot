package com.imooc.o2o.dto;

import java.util.HashSet;

public class EchartXAxis {
    private String type = "category";
    //儲存日期，使用hashSet就不會有重複的出現
    private HashSet<String> data;

    public String getType() {
        return type;
    }

    public HashSet<String> getData() {
        return data;
    }

    public void setData(HashSet<String> data) {
        this.data = data;
    }
}
