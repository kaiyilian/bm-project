package com.bumu.arya.salary.result;

import com.bumu.arya.salary.common.SalaryEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/7/8
 */
public class CustomerSalaryRuleResult {

	@ApiModelProperty(value = "薪资规则主键")
	String id;

	@JsonProperty("rule_type")
	@ApiModelProperty(value = "薪资规则分类")
	SalaryEnum.RuleType ruleType;

	@ApiModelProperty(value = "薪资规则名称")
	@JsonProperty("rule_name")
	String ruleName;

	@ApiModelProperty(value = "计税档")
	@JsonProperty("tax_gears")
	List<CustomerSalaryRuleResult.SalaryCalculateRuleTaxGearResult> taxGears;

	@ApiModelProperty(value = "薪资服务费率")
	@JsonProperty("brokerage_rate")
	BigDecimal brokerageRate = new BigDecimal(0);//薪资服务费率

	@ApiModelProperty(value = "个税起征点")
	@JsonProperty("threshold_tax")
	BigDecimal thresholdTax = new BigDecimal(0);//个税起征点

	@ApiModelProperty(value = "薪资服务费")
	@JsonProperty("brokerage")
	BigDecimal brokerage = new BigDecimal(0);//薪资服务费

	@ApiModelProperty(value = "薪资服务费付款方")
	@JsonProperty("cost_bearing")
	SalaryEnum.CostBearing costBearing;

	@JsonProperty("new_leave_absence_sub_ratio")
	@ApiModelProperty(value = "新进/离职员工旷工扣款比例", name= "newLeaveAbsenceSubRatio")
	BigDecimal newLeaveAbsenceSubRatio;

	@JsonProperty("ill_sub_ratio")
	@ApiModelProperty(value = "病假扣款比例")
	BigDecimal illSubRatio;

	@JsonProperty("absence_sub_ratio")
	@ApiModelProperty(value = "非新进、离职员工旷工扣款比例")
	BigDecimal absenceSubRatio;

	@JsonProperty("affair_sub_ratio")
	@ApiModelProperty(value = "事假扣款比例")
	BigDecimal affairSubRatio;

	@ApiModelProperty(value = "全勤奖")
	@JsonProperty("fulltime_bonu_list")
	List<FulltimeBonu> fulltimeBonuList;

	@JsonProperty("ill_sub_ratio_ordinary")
	@ApiModelProperty(value = "普通病假扣款比例", name= "ill_sub_ratio_ordinary")
	BigDecimal illSubRatioOrdinary;

	@ApiModelProperty(value = "普通个税起征点", name = "threshold_tax_ordinary")
	@JsonProperty("threshold_tax_ordinary")
	BigDecimal thresholdTaxOrdinary = new BigDecimal(0);//个税起征点

	@JsonProperty("affair_sub_ratio_ordinary")
	@ApiModelProperty(value = "普通扣款比例", name = "affair_sub_ratio_ordinary")
	BigDecimal affairSubRatioOrdinary;

	@ApiModelProperty(value = "普通薪资服务费", name = "brokerage_ordinary")
	@JsonProperty("brokerage_ordinary")
	BigDecimal brokerageOrdinary = new BigDecimal(0);//薪资服务费

	public CustomerSalaryRuleResult() {
	}

	public BigDecimal getBrokerageOrdinary() {
		return brokerageOrdinary;
	}

	public void setBrokerageOrdinary(BigDecimal brokerageOrdinary) {
		this.brokerageOrdinary = brokerageOrdinary;
	}

	public BigDecimal getIllSubRatioOrdinary() {
		return illSubRatioOrdinary;
	}

	public void setIllSubRatioOrdinary(BigDecimal illSubRatioOrdinary) {
		this.illSubRatioOrdinary = illSubRatioOrdinary;
	}

	public BigDecimal getThresholdTaxOrdinary() {
		return thresholdTaxOrdinary;
	}

	public void setThresholdTaxOrdinary(BigDecimal thresholdTaxOrdinary) {
		this.thresholdTaxOrdinary = thresholdTaxOrdinary;
	}

	public BigDecimal getAffairSubRatioOrdinary() {
		return affairSubRatioOrdinary;
	}

	public void setAffairSubRatioOrdinary(BigDecimal affairSubRatioOrdinary) {
		this.affairSubRatioOrdinary = affairSubRatioOrdinary;
	}

	public SalaryEnum.CostBearing getCostBearing() {
		return costBearing;
	}

	public void setCostBearing(SalaryEnum.CostBearing costBearing) {
		this.costBearing = costBearing;
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

	public SalaryEnum.RuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(SalaryEnum.RuleType ruleType) {
		this.ruleType = ruleType;
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

	public BigDecimal getNewLeaveAbsenceSubRatio() {
		return newLeaveAbsenceSubRatio;
	}

	public void setNewLeaveAbsenceSubRatio(BigDecimal newLeaveAbsenceSubRatio) {
		this.newLeaveAbsenceSubRatio = newLeaveAbsenceSubRatio;
	}

	public BigDecimal getIllSubRatio() {
		return illSubRatio;
	}

	public void setIllSubRatio(BigDecimal illSubRatio) {
		this.illSubRatio = illSubRatio;
	}

	public BigDecimal getAbsenceSubRatio() {
		return absenceSubRatio;
	}

	public void setAbsenceSubRatio(BigDecimal absenceSubRatio) {
		this.absenceSubRatio = absenceSubRatio;
	}

	public BigDecimal getAffairSubRatio() {
		return affairSubRatio;
	}

	public void setAffairSubRatio(BigDecimal affairSubRatio) {
		this.affairSubRatio = affairSubRatio;
	}

	public List<FulltimeBonu> getFulltimeBonuList() {
		return fulltimeBonuList;
	}

	public void setFulltimeBonuList(List<FulltimeBonu> fulltimeBonuList) {
		this.fulltimeBonuList = fulltimeBonuList;
	}

	public static class SalaryCalculateRuleTaxGearResult {
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
			if (taxRate != null) {
				this.taxRate = taxRate.multiply(new BigDecimal("100"));
			} else {
				this.taxRate = taxRate;
			}
		}

		public SalaryCalculateRuleTaxGearResult() {

		}
	}

	public static class FulltimeBonu {

		@JsonProperty("leval")
		Integer leval;

		@JsonProperty("bonu")
		BigDecimal bonu;

		public FulltimeBonu(){

		}

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
	}
}
