package com.baofeng.mobile.bean;

import java.io.Serializable;

/**
 * 筛选 - 年限
 * @author zhangjunpu
 * @date 15/7/23
 */
public class Year extends Base implements Serializable {

    private String year;

    public Year() {
    }

    public Year(String year, String name) {
        super(name);
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isDefault() {
        return year == null || year.equals("-1");
    }

}
