package com.baofeng.mobile.test;

import org.lee.java.util.ToString;

import java.util.Arrays;

class JsonResult {
    Status status;
    Data data;

    @Override
    public String toString() {
        return status + "\n"
                + data;
    }
}

class Status {
    int code;
    String msg;

    @Override
    public String toString() {
        return "code:" + code + ", msg:" + msg;
    }
}

class Data {
    Tag[] tags;

    Image[] thumbs;

    public boolean isEmpty() {
        return tags == null || tags.length == 0;
    }

    @Override
    public String toString() {
        return "tags:" + (isEmpty() ? " is empty" : (" size is " + tags.length + "\n"
                + ToString.toString(Arrays.asList(tags))));
    }
}

class Tag {
    String tag;
    int hot;

    @Override
    public String toString() {
        return "tag:" + tag + ", hot:" + hot;
    }
}

class Image{
    String picture_id;
    String thumb_url;
    String column;
}