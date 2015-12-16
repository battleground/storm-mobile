package com.baofeng.mobile.bean;

import java.io.Serializable;

/**
 * 筛选 - 类型
 * @author zhangjunpu
 * @date 15/7/23
 */
public class Tag extends Base implements Serializable {
    private String tag;

    public Tag() {
    }

    public Tag(String tag, String name) {
        super(name);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isDefault() {
        return tag == null || tag.equals("-1");
    }

}
