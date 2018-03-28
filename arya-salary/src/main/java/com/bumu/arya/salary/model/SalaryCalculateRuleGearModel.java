package com.bumu.arya.salary.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author CuiMengxin
 * @date 2016/7/15
 */
public class SalaryCalculateRuleGearModel {
	@JsonProperty("gear")
	BigDecimal gear;

	@JsonProperty("tax_rate")
	BigDecimal taxRate;

	public BigDecimal getGear() {
		return gear;
	}

	public void setGear(BigDecimal gear) {
		this.gear = gear;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public SalaryCalculateRuleGearModel() {

	}
}
