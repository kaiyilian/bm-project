package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 详细注释参照SoinRuleEntity
 * @author CuiMengxin
 * @date 2016/3/9
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SoinTypeVersionRuleResult implements Serializable {

	String id;

	String name;

	String desc;

	@JsonProperty("min_base")
	BigDecimal minBase;

	@JsonProperty("max_base")
	BigDecimal maxBase;

	@JsonProperty("percentage_corp")
	String percentageCorp;

	@JsonProperty("percentage_person")
	String percentagePerson;

	@JsonProperty("extra_corp")
	BigDecimal extraCorp;

	@JsonProperty("extra_person")
	BigDecimal extraPerson;

	@JsonProperty("pay_month")
	int payMonth;

	@JsonProperty("limit_moth")
	Integer limitMonth;

	public SoinTypeVersionRuleResult() {
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public BigDecimal getMinBase() {
		return minBase;
	}

	public void setMinBase(BigDecimal minBase) {
		this.minBase = minBase;
	}

	public BigDecimal getMaxBase() {
		return maxBase;
	}

	public void setMaxBase(BigDecimal maxBase) {
		this.maxBase = maxBase;
	}

	public String getPercentageCorp() {
		return percentageCorp;
	}

	public void setPercentageCorp(String percentageCorp) {
		this.percentageCorp = percentageCorp;
	}

	public String getPercentagePerson() {
		return percentagePerson;
	}

	public void setPercentagePerson(String percentagePerson) {
		this.percentagePerson = percentagePerson;
	}

	public BigDecimal getExtraCorp() {
		return extraCorp;
	}

	public void setExtraCorp(BigDecimal extraCorp) {
		this.extraCorp = extraCorp;
	}

	public BigDecimal getExtraPerson() {
		return extraPerson;
	}

	public void setExtraPerson(BigDecimal extraPerson) {
		this.extraPerson = extraPerson;
	}

	public int getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(int payMonth) {
		this.payMonth = payMonth;
	}

	public Integer getLimitMonth() {
		return limitMonth;
	}

	public void setLimitMonth(Integer limitMonth) {
		this.limitMonth = limitMonth;
	}
}
