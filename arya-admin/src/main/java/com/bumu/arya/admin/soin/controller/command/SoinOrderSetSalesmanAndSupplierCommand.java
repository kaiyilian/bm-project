package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by CuiMengxin on 16/8/19.
 */
public class SoinOrderSetSalesmanAndSupplierCommand {

    @JsonProperty("order_id")
    String orderId;

    @JsonProperty("salesman_id")
    String salesmanId;

    @JsonProperty("supplier_id")
    String supplerId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getSupplerId() {
        return supplerId;
    }

    public void setSupplerId(String supplerId) {
        this.supplerId = supplerId;
    }
}
