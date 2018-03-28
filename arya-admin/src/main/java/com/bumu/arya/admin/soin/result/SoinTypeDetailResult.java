package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author CuiMengxin
 * @date 2016/3/9
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SoinTypeDetailResult implements Serializable {

	String id;

	@JsonProperty("type_name")
	String typeName;

	@JsonProperty("type_desc")
	String typeDesc;

	@JsonProperty("type_hint")
	String typeHint;

	@JsonProperty("is_house_fund_must")
	Boolean isHouseFundMust;

	@JsonProperty("last_day")
	int lastDay;

	@JsonProperty("forward_month")
	int forwardMonth;

	@JsonProperty("most_month")
	int mostMonth;

	@JsonProperty("least_month")
	int leastMonth;

	@JsonProperty("is_active")
	int active;

	BigDecimal fees;

	public SoinTypeDetailResult() {
	}

	public String getId() {

		return id;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getTypeHint() {
		return typeHint;
	}

	public void setTypeHint(String typeHint) {
		this.typeHint = typeHint;
	}

	public Boolean getHouseFundMust() {
		return isHouseFundMust;
	}

	public void setHouseFundMust(Boolean houseFundMust) {
		isHouseFundMust = houseFundMust;
	}

	public int getLastDay() {
		return lastDay;
	}

	public void setLastDay(int lastDay) {
		this.lastDay = lastDay;
	}

	public int getForwardMonth() {
		return forwardMonth;
	}

	public void setForwardMonth(int forwardMonth) {
		this.forwardMonth = forwardMonth;
	}

	public int getMostMonth() {
		return mostMonth;
	}

	public void setMostMonth(int mostMonth) {
		this.mostMonth = mostMonth;
	}

	public int getLeastMonth() {
		return leastMonth;
	}

	public void setLeastMonth(int leastMonth) {
		this.leastMonth = leastMonth;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}
}
