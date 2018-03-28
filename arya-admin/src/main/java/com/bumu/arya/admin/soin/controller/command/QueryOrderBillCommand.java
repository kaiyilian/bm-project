package com.bumu.arya.admin.soin.controller.command;

/**
 * Created by CuiMengxin on 16/8/7.
 */
public class QueryOrderBillCommand {

    String customerId;

    String districtId;

    String supplierId;

    int year;

    int month;

    int source;

    String payedStatus;

    int page;

    int pageSize;

    public String getPayedStatus() {
        return payedStatus;
    }

    public void setPayedStatus(String payedStatus) {
        this.payedStatus = payedStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
