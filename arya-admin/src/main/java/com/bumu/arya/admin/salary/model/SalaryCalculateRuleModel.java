package com.bumu.arya.admin.salary.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/7/8
 */
public class SalaryCalculateRuleModel {

	@JsonProperty("tax_gears")
	List<SalaryCalculateRuleGearModel> taxGears;

	@JsonProperty("service_charge_tax_rate")
	BigDecimal serviceChargeTaxRate;//个税服务费率

	@JsonProperty("brokerage_rate")
	BigDecimal brokerageRate;//薪资服务费率

	@JsonProperty("threshold_tax")
	BigDecimal thresholdTax;//个税起征点

	BigDecimal brokerage;//薪资服务费

	public List<SalaryCalculateRuleGearModel> getTaxGears() {
		return taxGears;
	}

	public void setTaxGears(List<SalaryCalculateRuleGearModel> taxGears) {
		this.taxGears = taxGears;
	}

	public BigDecimal getServiceChargeTaxRate() {
		return serviceChargeTaxRate;
	}

	public void setServiceChargeTaxRate(BigDecimal serviceChargeTaxRate) {
		this.serviceChargeTaxRate = serviceChargeTaxRate;
	}

	public BigDecimal getBrokerageRate() {
		return brokerageRate;
	}

	public void setBrokerageRate(BigDecimal brokerageRate) {
		this.brokerageRate = brokerageRate;
	}

	public BigDecimal getThresholdTax() {
		return thresholdTax;
	}

	public void setThresholdTax(BigDecimal thresholdTax) {
		this.thresholdTax = thresholdTax;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public SalaryCalculateRuleModel() {

	}
}
