package com.baofeng.mobile.bean;

import java.util.List;

/**
 * 首页影片推荐Model，包含属性：类别名、影片数组等
 *
 * @author zhangjunpu
 * @date 15/7/29
 */
public class VideoRecommend {

    private String category;
    private String name;
    private String type;
    private List<Video> list;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Video> getList() {
        return list;
    }

    public void setList(List<Video> list) {
        this.list = list;
    }

    public boolean isTop() {
        return "1".equals(type);
    }
}
