package com.bumu.arya.admin.salary.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/7/8
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateSalaryCalculateRuleCommand {

	@JsonProperty("rule_name")
	String ruleName;

	@JsonProperty("tax_gears")
	List<CreateSalaryCalculateRuleCommand.SalaryCalculateRuleTaxGear> taxGears;

	@JsonProperty("service_charge_tax_rate")
	BigDecimal serviceChargeTaxRate;//个税服务费率

	@JsonProperty("brokerage_rate")
	BigDecimal brokerageRate;//薪资服务费率

	@JsonProperty("threshold_tax")
	BigDecimal thresholdTax;//个税起征点

	@JsonProperty("rule_type")
	Integer ruleType;

	@JsonProperty("group_id")
	String groupId;

	@JsonProperty("department_id")
	String departmentId;

	BigDecimal brokerage;//薪资服务费

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public List<SalaryCalculateRuleTaxGear> getTaxGears() {
		return taxGears;
	}

	public void setTaxGears(List<SalaryCalculateRuleTaxGear> taxGears) {
		this.taxGears = taxGears;
	}

	public BigDecimal getServiceChargeTaxRate() {
		return serviceChargeTaxRate;
	}

	public void setServiceChargeTaxRate(BigDecimal serviceChargeTaxRate) {
		if (serviceChargeTaxRate != null) {
			this.serviceChargeTaxRate = serviceChargeTaxRate.multiply(new BigDecimal("0.01"));
		} else {
			this.serviceChargeTaxRate = serviceChargeTaxRate;
		}
	}

	public BigDecimal getBrokerageRate() {
		return brokerageRate;
	}

	public void setBrokerageRate(BigDecimal brokerageRate) {
		if (brokerageRate != null) {
			this.brokerageRate = brokerageRate.multiply(new BigDecimal("0.01"));
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

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public CreateSalaryCalculateRuleCommand() {
		
	}

	public static class SalaryCalculateRuleTaxGear {
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
				this.taxRate = taxRate.multiply(new BigDecimal("0.01"));
			} else {
				this.taxRate = taxRate;
			}
		}

		public SalaryCalculateRuleTaxGear() {

		}
	}
}
