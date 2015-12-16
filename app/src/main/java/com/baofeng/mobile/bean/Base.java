package com.baofeng.mobile.bean;

/**
 * @author zhangjunpu
 * @date 15/7/23
 */
public class Base {

    protected String id;
    protected String name;

    public Base(){
    }

    public Base(String name) {
        this.name = name;
    }

    public Base(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
