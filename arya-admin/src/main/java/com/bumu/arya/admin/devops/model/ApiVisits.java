package com.bumu.arya.admin.devops.model;

/**
 * @author Allen 2017-11-27
 **/
public class ApiVisits {

    // 标识这个访问的维度
    String tag;

    long pv;

    long uv;

    public ApiVisits(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }
}
