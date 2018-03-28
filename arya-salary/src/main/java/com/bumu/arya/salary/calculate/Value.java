package com.bumu.arya.salary.calculate;

/**
 * 包含计算用的值（浮点型）和其中文名称
 * Created by allen on 2017/6/22.
 */
public class Value<T> {

    /**
     * 计算值
     */
    T value;

    /**
     * 计算值标题
     */
    String title = "[未知名称]";

    public Value(T value) {
        this.value = value;
    }

    public Value(T value, String title) {
        this.value = value;
        this.title = title;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
