package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by liangjun on 2017/1/9.
 */
public class EmployTypeQueryResult {


	//id --订单ID
    @JsonProperty("id")
	String id;
	//缴纳主体
    @JsonProperty("subject")
    String subject;
	//姓名
    @JsonProperty("name")
    String name;
	//身份证
    @JsonProperty("idcard")
    String idcard;
	//参保地区
    @JsonProperty("district")
    String district;
	//参保类型
    @JsonProperty("soin_type")
    String soinType;
	//服务月份
    @JsonProperty("service_year_month")
    Integer serviceYearMonth;
	//缴纳月份
    @JsonProperty("pay_year_month")
    String payMonth;
	//补缴开始月份
    @JsonProperty("back_start_year_month")
    String backStartMonth;
	//补缴结束月份
    @JsonProperty("back_end_year_month")
    String backEndMonth;
	//增减员
    @JsonProperty("modify")
    String modifyName;
	//公积金比例
    @JsonProperty("house_fund_percent")
    String housefundProportion;
	//服务费收账
    @JsonProperty("fee_in")
    String fees;

    public EmployTypeQueryResult() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSoinType() {
        return soinType;
    }

    public void setSoinType(String soinType) {
        this.soinType = soinType;
    }

    public Integer getServiceYearMonth() {
        return serviceYearMonth;
    }

    public void setServiceYearMonth(Integer serviceYearMonth) {
        this.serviceYearMonth = serviceYearMonth;
    }

    public String getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth;
    }

    public String getBackStartMonth() {
        return backStartMonth;
    }

    public void setBackStartMonth(String backStartMonth) {
        this.backStartMonth = backStartMonth;
    }

    public String getBackEndMonth() {
        return backEndMonth;
    }

    public void setBackEndMonth(String backEndMonth) {
        this.backEndMonth = backEndMonth;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public String getHousefundProportion() {
        return housefundProportion;
    }

    public void setHousefundProportion(String housefundProportion) {
        this.housefundProportion = housefundProportion;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }
}
