package com.baofeng.mobile.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 筛选
 * @author zhangjunpu
 * @date 15/7/23
 */
public class FilterBean implements Serializable {

    private String sid;
    private String category;
    private String name;
    private List<Tag> tags; // 类型
    private List<Area> areas; // 地区
    private List<Year> years; // 年限

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public List<Year> getYears() {
        return years;
    }

    public void setYears(List<Year> years) {
        this.years = years;
    }
}
