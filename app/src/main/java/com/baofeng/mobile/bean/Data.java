package com.baofeng.mobile.bean;

import org.lee.java.util.ToString;

import java.util.Arrays;

public class Data<JSON_TYPE> {
    public JSON_TYPE[] data;

    public boolean isEmpty() {
        return data == null || data.length == 0;
    }

    @Override
    public String toString() {
        return "data:" + (isEmpty() ? " is empty" : (" size is " + data.length + "\n"
                + ToString.toString(Arrays.asList(data))));
    }
}