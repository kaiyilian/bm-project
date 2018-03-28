package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by CuiMengxin on 16/8/3.
 */
public class OrderBillStatisticsResult {

    String subject;

    @JsonProperty("soin_district")
    String soinDistrict;

    @JsonProperty("soin_type")
    String soinType;

    @JsonProperty("service_month")
    String serviceMonth;

    @JsonProperty("pay_month")
    String payMonth;

    @JsonProperty("collection_total")
    BigDecimal collectionTotal;

    @JsonProperty("charge_total")
    BigDecimal chargeTotal;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSoinDistrict() {
        return soinDistrict;
    }

    public void setSoinDistrict(String soinDistrict) {
        this.soinDistrict = soinDistrict;
    }

    public String getSoinType() {
        return soinType;
    }

    public void setSoinType(String soinType) {
        this.soinType = soinType;
    }

    public String getServiceMonth() {
        return serviceMonth;
    }

    public void setServiceMonth(String serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    public String getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth;
    }

    public BigDecimal getCollectionTotal() {
        return collectionTotal;
    }

    public void setCollectionTotal(BigDecimal collectionTotal) {
        this.collectionTotal = collectionTotal;
    }

    public BigDecimal getChargeTotal() {
        return chargeTotal;
    }

    public void setChargeTotal(BigDecimal chargeTotal) {
        this.chargeTotal = chargeTotal;
    }

    public OrderBillStatisticsResult() {

    }
}
