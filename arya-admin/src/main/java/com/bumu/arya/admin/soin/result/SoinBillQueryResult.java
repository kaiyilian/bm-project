package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by CuiMengxin on 2017/1/9.
 */
public class SoinBillQueryResult {

	String id;

	String subject;

	String name;

	String idcard;

	String district;

	@JsonProperty("soin_type")
	String soinType;

	@JsonProperty("service_year_month")
	String serviceYearMonth;

	@JsonProperty("pay_year_month")
    StringStatusResult payYearMonth;


	@JsonProperty("back_month")
	List<StringStatusResult> backMonth;

	@JsonProperty("total_in")
	String totalIn;

	@JsonProperty("total_out")
	String totalOut;

	//供应商
	String supplier;

	String salesman;

	//增减员
	String modify;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getServiceYearMonth() {
		return serviceYearMonth;
	}

	public void setServiceYearMonth(String serviceYearMonth) {
		this.serviceYearMonth = serviceYearMonth;
	}

	public StringStatusResult getPayYearMonth() {
		return payYearMonth;
	}

	public void setPayYearMonth(StringStatusResult payYearMonth) {
		this.payYearMonth = payYearMonth;
	}

	public List<StringStatusResult> getBackMonth() {
		return backMonth;
	}

	public void setBackMonth(List<StringStatusResult> backMonth) {
		this.backMonth = backMonth;
	}

	public String getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(String totalIn) {
		this.totalIn = totalIn;
	}

	public String getTotalOut() {
		return totalOut;
	}

	public void setTotalOut(String totalOut) {
		this.totalOut = totalOut;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}
}
