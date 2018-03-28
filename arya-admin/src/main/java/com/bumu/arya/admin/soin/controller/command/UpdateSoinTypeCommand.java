package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.command.HttpCommand;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by CuiMengxin on 2015/10/23.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateSoinTypeCommand extends HttpCommand {

	@JsonProperty("type_id")
	String  typeId;

	@JsonProperty("type")
	String name;

	String injury;

	String medical;

	String pregnancy;

	String pension;

	String unemployment;

	@JsonProperty("house_fund")
	String houseFund;

	@JsonProperty("min_base")
	String minBase;

	@JsonProperty("max_base")
	String maxBase;

	@JsonProperty("house_fund_must")
	Boolean isHouseFundMust;

	@JsonProperty("house_fund_min_base")
	String minHouseFundBase;

	@JsonProperty("house_fund_max_base")
	String maxHouseFundBase;

	@JsonProperty("type_desc")
	String  typeDesc;

	String fee;

	@JsonProperty("last_day")
	String lastDay;

	public UpdateSoinTypeCommand() {
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInjury() {
		return injury;
	}

	public void setInjury(String injury) {
		this.injury = injury;
	}

	public String getMedical() {
		return medical;
	}

	public void setMedical(String medical) {
		this.medical = medical;
	}

	public String getPregnancy() {
		return pregnancy;
	}

	public void setPregnancy(String pregnancy) {
		this.pregnancy = pregnancy;
	}

	public String getPension() {
		return pension;
	}

	public void setPension(String pension) {
		this.pension = pension;
	}

	public String getUnemployment() {
		return unemployment;
	}

	public void setUnemployment(String unemployment) {
		this.unemployment = unemployment;
	}

	public String getHouseFund() {
		return houseFund;
	}

	public void setHouseFund(String houseFund) {
		this.houseFund = houseFund;
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

	public String getMinHouseFundBase() {
		return minHouseFundBase;
	}

	public void setMinHouseFundBase(String minHouseFundBase) {
		this.minHouseFundBase = minHouseFundBase;
	}

	public String getMaxHouseFundBase() {
		return maxHouseFundBase;
	}

	public void setMaxHouseFundBase(String maxHouseFundBase) {
		this.maxHouseFundBase = maxHouseFundBase;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getLastDay() {
		return lastDay;
	}

	public void setLastDay(String lastDay) {
		this.lastDay = lastDay;
	}
}
