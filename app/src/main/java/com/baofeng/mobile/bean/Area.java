package com.baofeng.mobile.bean;

import java.io.Serializable;

/**
 * 筛选 - 地区
 * @author zhangjunpu
 * @date 15/7/23
 */
public class Area extends Base implements Serializable {

    private String area;

    public Area() {
    }

    public Area(String area, String name) {
        super(name);
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isDefault() {
        return area == null || area.equals("-1");
    }

}
