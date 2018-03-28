package com.bumu.arya.admin.soin.model.entity;

/**
 * Created by CuiMengxin on 16/8/5.
 */
public class SoinOrderBillCountModel {

    int totalCount;

    int wrongCount;

    int dumplicateCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(int wrongCount) {
        this.wrongCount = wrongCount;
    }

    public int getDumplicateCount() {
        return dumplicateCount;
    }

    public void setDumplicateCount(int dumplicateCount) {
        this.dumplicateCount = dumplicateCount;
    }
}
