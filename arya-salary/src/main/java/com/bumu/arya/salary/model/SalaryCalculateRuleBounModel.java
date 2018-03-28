package com.bumu.arya.salary.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author CuiMengxin
 * @date 2016/7/15
 */
public class SalaryCalculateRuleBounModel {
	@JsonProperty("leval")
	Integer leval;

	@JsonProperty("bonu")
	BigDecimal bonu;

	public Integer getLeval() {
		return leval;
	}

	public void setLeval(Integer leval) {
		this.leval = leval;
	}

	public BigDecimal getBonu() {
		return bonu;
	}

	public void setBonu(BigDecimal bonu) {
		this.bonu = bonu;
	}

	public SalaryCalculateRuleBounModel() {

	}
}
