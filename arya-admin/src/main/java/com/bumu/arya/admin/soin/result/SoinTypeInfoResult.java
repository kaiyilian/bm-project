package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.Serializable;

/**
 * Created by CuiMengxin on 2015/10/22.
 */
public class SoinTypeInfoResult implements Serializable {
	/**
	 * 社保类型名称
	 */
	@JsonProperty("type_id")
	String typeId;

	@JsonProperty("type")
	String type;

	@JsonProperty("house_fund_must")
	Boolean isHouseFundMust;

	/**
	 * 社保最低基数
	 */
	@JsonProperty("min_base")
	String minBase;

	/**
	 * 社保最高基数
	 */
	@JsonProperty("max_base")
	String maxBase;

	/**
	 * 公积金最低基数
	 */
	@JsonProperty("house_fund_min_base")
	String houseFundMinBase;

	/**
	 * 公积金最高基数
	 */
	@JsonProperty("house_fund_max_base")
	String houseFundMaxBase;

	/**
	 * 本月缴纳截止日期
	 */
	@JsonProperty("last_day")
	int lastDay;

	/**
	 * 管理费
	 */
	@JsonProperty("fee")
	String fee;

	/**
	 * 描述
	 */
	@JsonProperty("type_desc")
	String typeDesc;


	/**
	 * 养老比例
	 */
	String pension;

	/**
	 * 医疗比例
	 */
	String medical;

	/**
	 * 失业比例
	 */
	String unemployment;

	/**
	 * 工伤
	 */
	String injury;

	/**
	 * 生育
	 */
	String pregnancy;

	/**
	 * 住房公积金
	 */
	@JsonProperty("house_fund")
	String houseFund;

	public Boolean getIsHouseFundMust() {
		return isHouseFundMust;
	}

	public void setIsHouseFundMust(Boolean isHouseFundMust) {
		this.isHouseFundMust = isHouseFundMust;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public SoinTypeInfoResult() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMinBase() {
		return minBase;
	}

	public void setMinBase(String minBase) {
		this.minBase = minBase;
	}

	public String getMaxBase() {
		return maxBase;
	}

	public void setMaxBase(String maxBase) {
		this.maxBase = maxBase;
	}

	public String getHouseFundMinBase() {
		return houseFundMinBase;
	}

	public void setHouseFundMinBase(String houseFundMinBase) {
		this.houseFundMinBase = houseFundMinBase;
	}

	public String getHouseFundMaxBase() {
		return houseFundMaxBase;
	}

	public void setHouseFundMaxBase(String houseFundMaxBase) {
		this.houseFundMaxBase = houseFundMaxBase;
	}

	public int getLastDay() {
		return lastDay;
	}

	public void setLastDay(int lastDay) {
		this.lastDay = lastDay;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getPension() {
		return pension;
	}

	public void setPension(String pension) {
		this.pension = pension;
	}

	public String getMedical() {
		return medical;
	}

	public void setMedical(String medical) {
		this.medical = medical;
	}

	public String getUnemployment() {
		return unemployment;
	}

	public void setUnemployment(String unemployment) {
		this.unemployment = unemployment;
	}

	public String getInjury() {
		return injury;
	}

	public void setInjury(String injury) {
		this.injury = injury;
	}

	public String getPregnancy() {
		return pregnancy;
	}

	public void setPregnancy(String pregnancy) {
		this.pregnancy = pregnancy;
	}

	public String getHouseFund() {
		return houseFund;
	}

	public void setHouseFund(String houseFund) {
		this.houseFund = houseFund;
	}
}
