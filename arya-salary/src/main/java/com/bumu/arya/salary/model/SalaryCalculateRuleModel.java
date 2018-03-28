package com.bumu.arya.salary.model;

import com.bumu.arya.salary.common.SalaryEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/7/8
 */
public class SalaryCalculateRuleModel {

	@JsonProperty("tax_gears")
	List<SalaryCalculateRuleGearModel> taxGears;

	@JsonProperty("brokerage_rate")
	BigDecimal brokerageRate;//薪资服务费率

	@JsonProperty("threshold_tax")
	BigDecimal thresholdTax;//个税起征点

	@JsonProperty("brokerage")
	BigDecimal brokerage;//薪资服务费

	@JsonProperty("cost_bearing")
	SalaryEnum.CostBearing costBearing;//费用承担方

	@JsonProperty("rule_type")
	SalaryEnum.RuleType ruleType;

	@JsonProperty("new_leave_absence_sub_ratio")
	BigDecimal newLeaveAbsenceSubRatio;

	@JsonProperty("ill_sub_ratio")
	BigDecimal illSubRatio;

	@JsonProperty("absence_sub_ratio")
	BigDecimal absenceSubRatio;

	@JsonProperty("affair_sub_ratio")
	BigDecimal affairSubRatio;

	@JsonProperty("threshold_tax_ordinary")
	BigDecimal thresholdTaxOrdinary;

	@JsonProperty("ill_sub_ratio_ordinary")
	BigDecimal illSubRatioOrdinary;

	@JsonProperty("affair_sub_ratio_ordinary")
	BigDecimal affairSubRatioOrdinary;

	@JsonProperty("brokerage_ordinary")
	BigDecimal brokerageOrdinary;

	@ApiModelProperty(value = "全勤奖", name = "fulltime_bonu_list")
	List<SalaryCalculateRuleBounModel> fulltimeBonuList;

	public BigDecimal getBrokerageOrdinary() {
		return brokerageOrdinary;
	}

	public void setBrokerageOrdinary(BigDecimal brokerageOrdinary) {
		this.brokerageOrdinary = brokerageOrdinary;
	}

	public BigDecimal getThresholdTaxOrdinary() {
		return thresholdTaxOrdinary;
	}

	public void setThresholdTaxOrdinary(BigDecimal thresholdTaxOrdinary) {
		this.thresholdTaxOrdinary = thresholdTaxOrdinary;
	}

	public BigDecimal getIllSubRatioOrdinary() {
		return illSubRatioOrdinary;
	}

	public void setIllSubRatioOrdinary(BigDecimal illSubRatioOrdinary) {
		this.illSubRatioOrdinary = illSubRatioOrdinary;
	}

	public BigDecimal getAffairSubRatioOrdinary() {
		return affairSubRatioOrdinary;
	}

	public void setAffairSubRatioOrdinary(BigDecimal affairSubRatioOrdinary) {
		this.affairSubRatioOrdinary = affairSubRatioOrdinary;
	}

	public SalaryEnum.RuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(SalaryEnum.RuleType ruleType) {
		this.ruleType = ruleType;
	}

	public SalaryEnum.CostBearing getCostBearing() {
		return costBearing;
	}

	public void setCostBearing(SalaryEnum.CostBearing costBearing) {
		this.costBearing = costBearing;
	}

	public List<SalaryCalculateRuleGearModel> getTaxGears() {
		return taxGears;
	}

	public void setTaxGears(List<SalaryCalculateRuleGearModel> taxGears) {
		this.taxGears = taxGears;
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

	public List<SalaryCalculateRuleBounModel> getFulltimeBonuList() {
		return fulltimeBonuList;
	}

	public void setFulltimeBonuList(List<SalaryCalculateRuleBounModel> fulltimeBonuList) {
		this.fulltimeBonuList = fulltimeBonuList;
	}

	public SalaryCalculateRuleModel() {

	}
}
