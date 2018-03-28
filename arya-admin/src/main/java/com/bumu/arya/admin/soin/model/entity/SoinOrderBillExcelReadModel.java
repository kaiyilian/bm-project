package com.bumu.arya.admin.soin.model.entity;

import java.math.BigDecimal;

/**
 * Created by CuiMengxin on 16/8/3.
 */
public class SoinOrderBillExcelReadModel {

	int no;//编号

	/**
	 * 缴纳主体
	 */
	String subject;

	String name;

	String idcardNo;

	String phoneNo;
	// 服务年月
	String serviceYearMonth;
	// 缴纳年月
	String payYearMonth;

	String backStartYearMonth;//补缴开始年月

	String backEndYearMonth;//补缴结束年月

	String soinDistrict;

	String soinType;

	String modify;//增减员文字

	BigDecimal soinBase;

	BigDecimal housefundBase;

	BigDecimal housefundPercent;

	String soinCode;

	String houseFundCode;

	String hukouType;

	String hukouDistrict;

	BigDecimal collectionServiceFee;

	BigDecimal chargeServiceFee;

	String salesman;

	String postponeMonth;

	String templateType;

	public SoinOrderBillExcelReadModel() {
	}

	public String getBackStartYearMonth() {
		return backStartYearMonth;
	}

	public void setBackStartYearMonth(String backStartYearMonth) {
		this.backStartYearMonth = backStartYearMonth;
	}

	public String getBackEndYearMonth() {
		return backEndYearMonth;
	}

	public void setBackEndYearMonth(String backEndYearMonth) {
		this.backEndYearMonth = backEndYearMonth;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getHouseFundCode() {
		return houseFundCode;
	}

	public void setHouseFundCode(String houseFundCode) {
		this.houseFundCode = houseFundCode;
	}

	public String getSoinCode() {
		return soinCode;
	}

	public void setSoinCode(String soinCode) {
		this.soinCode = soinCode;
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

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getServiceYearMonth() {
		return serviceYearMonth;
	}

	public void setServiceYearMonth(String serviceYearMonth) {
		this.serviceYearMonth = serviceYearMonth;
	}

	public String getPayYearMonth() {
		return payYearMonth;
	}

	public void setPayYearMonth(String payYearMonth) {
		this.payYearMonth = payYearMonth;
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

	public BigDecimal getSoinBase() {
		return soinBase;
	}

	public void setSoinBase(BigDecimal soinBase) {
		this.soinBase = soinBase;
	}

	public BigDecimal getHousefundBase() {
		return housefundBase;
	}

	public void setHousefundBase(BigDecimal housefundBase) {
		this.housefundBase = housefundBase;
	}

	public BigDecimal getHousefundPercent() {
		return housefundPercent;
	}

	public void setHousefundPercent(BigDecimal housefundPercent) {
		this.housefundPercent = housefundPercent;
	}

	public String getHukouType() {
		return hukouType;
	}

	public void setHukouType(String hukouType) {
		this.hukouType = hukouType;
	}

	public String getHukouDistrict() {
		return hukouDistrict;
	}

	public void setHukouDistrict(String hukouDistrict) {
		this.hukouDistrict = hukouDistrict;
	}

	public BigDecimal getCollectionServiceFee() {
		return collectionServiceFee;
	}

	public void setCollectionServiceFee(BigDecimal collectionServiceFee) {
		this.collectionServiceFee = collectionServiceFee;
	}

	public BigDecimal getChargeServiceFee() {
		return chargeServiceFee;
	}

	public void setChargeServiceFee(BigDecimal chargeServiceFee) {
		this.chargeServiceFee = chargeServiceFee;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getPostponeMonth() {
		return postponeMonth;
	}

	public void setPostponeMonth(String postponeMonth) {
		this.postponeMonth = postponeMonth;
	}
}
