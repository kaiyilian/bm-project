package com.bumu.arya.admin.salary.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/7/8
 */
public class SalaryCalculateRuleResult {

	String id;

	@JsonProperty("rule_name")
	String ruleName;

	@JsonProperty("tax_gears")
	List<SalaryCalculateRuleResult.SalaryCalculateRuleTaxGearResult> taxGears;

	@JsonProperty("service_charge_tax_rate")
	BigDecimal serviceChargeTaxRate;//个税服务费率

	@JsonProperty("brokerage_rate")
	BigDecimal brokerageRate;//薪资服务费率

	@JsonProperty("threshold_tax")
	BigDecimal thresholdTax;//个税起征点

	BigDecimal brokerage;//薪资服务费

	public SalaryCalculateRuleResult() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public List<SalaryCalculateRuleTaxGearResult> getTaxGears() {
		return taxGears;
	}

	public void setTaxGears(List<SalaryCalculateRuleTaxGearResult> taxGears) {
		this.taxGears = taxGears;
	}

	public BigDecimal getServiceChargeTaxRate() {
		return serviceChargeTaxRate;
	}

	public void setServiceChargeTaxRate(BigDecimal serviceChargeTaxRate) {
		if (serviceChargeTaxRate != null) {
			this.serviceChargeTaxRate = serviceChargeTaxRate.multiply(new BigDecimal("100"));
		} else {
			this.serviceChargeTaxRate = serviceChargeTaxRate;
		}
	}

	public BigDecimal getBrokerageRate() {
		return brokerageRate;
	}

	public void setBrokerageRate(BigDecimal brokerageRate) {
		if (brokerageRate != null) {
			this.brokerageRate = brokerageRate.multiply(new BigDecimal("100"));
		} else {
			this.brokerageRate = brokerageRate;
		}
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

	public static class SalaryCalculateRuleTaxGearResult {
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
			if (taxRate != null) {
				this.taxRate = taxRate.multiply(new BigDecimal("100"));
			} else {
				this.taxRate = taxRate;
			}
		}

		public SalaryCalculateRuleTaxGearResult() {

		}
	}
}
