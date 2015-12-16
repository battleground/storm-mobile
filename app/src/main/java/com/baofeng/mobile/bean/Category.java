package com.baofeng.mobile.bean;

import com.google.gson.Gson;

public class Category {
    public String id;
    public String name;
    public String iconResId;
    public int count;

    public Category(String id, String name, String iconResId, int count) {
        this.id = id;
        this.name = name;
        this.iconResId = iconResId;
        this.count = count;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
//        return super.toString();
    }
}