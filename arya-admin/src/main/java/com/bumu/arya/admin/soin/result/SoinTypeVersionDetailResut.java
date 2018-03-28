package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * 注释参照SoinTypeVersionEntity
 *
 * @author CuiMengxin
 * @date 2016/3/9
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SoinTypeVersionDetailResut {

	String id;

	@JsonProperty("soin_type_id")
	String soinTypeId;

	@JsonProperty("effect_year")
	Integer effectYear;

	@JsonProperty("effect_month")
	Integer effectMonth;

	@JsonProperty("base_accordant")
	Integer baseAccordant;

	@JsonProperty("rule_pension")
	SoinTypeVersionRuleResult rulePension;

	@JsonProperty("rule_medical")
	SoinTypeVersionRuleResult ruleMedical;

	@JsonProperty("rule_unemployment")
	SoinTypeVersionRuleResult ruleUnemployment;

	@JsonProperty("rule_injury")
	SoinTypeVersionRuleResult ruleInjury;

	@JsonProperty("rule_pregnancy")
	SoinTypeVersionRuleResult rulePregnancy;

	@JsonProperty("rule_disability")
	SoinTypeVersionRuleResult ruleDisability;

	@JsonProperty("rule_severe_illness")
	SoinTypeVersionRuleResult ruleSevereIllness;

	@JsonProperty("rule_injury_addition")
	SoinTypeVersionRuleResult ruleInjuryAddition;

	@JsonProperty("rule_house_fund")
	SoinTypeVersionRuleResult ruleHouseFund;

	@JsonProperty("rule_house_fund_addition")
	SoinTypeVersionRuleResult ruleHouseFundAddition;

	//采暖费
	@JsonProperty("rule_heating")
	SoinTypeVersionRuleResult ruleHeating;

	@JsonProperty("is_active")
	int active;

	@JsonProperty("at_least")
	Integer atLeast;

	@JsonProperty("at_most")
	Integer atMost;

	@JsonProperty("late_fee")
	BigDecimal lateFee;

	@JsonProperty("cross_year")
	Integer crossYear;

	public SoinTypeVersionDetailResut() {
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSoinTypeId() {
		return soinTypeId;
	}

	public void setSoinTypeId(String soinTypeId) {
		this.soinTypeId = soinTypeId;
	}

	public Integer getEffectYear() {
		return effectYear;
	}

	public void setEffectYear(Integer effectYear) {
		this.effectYear = effectYear;
	}

	public Integer getEffectMonth() {
		return effectMonth;
	}

	public void setEffectMonth(Integer effectMonth) {
		this.effectMonth = effectMonth;
	}

	public Integer getBaseAccordant() {
		return baseAccordant;
	}

	public void setBaseAccordant(Integer baseAccordant) {
		this.baseAccordant = baseAccordant;
	}

	public SoinTypeVersionRuleResult getRulePension() {
		return rulePension;
	}

	public void setRulePension(SoinTypeVersionRuleResult rulePension) {
		this.rulePension = rulePension;
	}

	public SoinTypeVersionRuleResult getRuleMedical() {
		return ruleMedical;
	}

	public void setRuleMedical(SoinTypeVersionRuleResult ruleMedical) {
		this.ruleMedical = ruleMedical;
	}

	public SoinTypeVersionRuleResult getRuleUnemployment() {
		return ruleUnemployment;
	}

	public void setRuleUnemployment(SoinTypeVersionRuleResult ruleUnemployment) {
		this.ruleUnemployment = ruleUnemployment;
	}

	public SoinTypeVersionRuleResult getRuleInjury() {
		return ruleInjury;
	}

	public void setRuleInjury(SoinTypeVersionRuleResult ruleInjury) {
		this.ruleInjury = ruleInjury;
	}

	public SoinTypeVersionRuleResult getRulePregnancy() {
		return rulePregnancy;
	}

	public void setRulePregnancy(SoinTypeVersionRuleResult rulePregnancy) {
		this.rulePregnancy = rulePregnancy;
	}

	public SoinTypeVersionRuleResult getRuleDisability() {
		return ruleDisability;
	}

	public void setRuleDisability(SoinTypeVersionRuleResult ruleDisability) {
		this.ruleDisability = ruleDisability;
	}

	public SoinTypeVersionRuleResult getRuleSevereIllness() {
		return ruleSevereIllness;
	}

	public void setRuleSevereIllness(SoinTypeVersionRuleResult ruleSevereIllness) {
		this.ruleSevereIllness = ruleSevereIllness;
	}

	public SoinTypeVersionRuleResult getRuleInjuryAddition() {
		return ruleInjuryAddition;
	}

	public void setRuleInjuryAddition(SoinTypeVersionRuleResult ruleInjuryAddition) {
		this.ruleInjuryAddition = ruleInjuryAddition;
	}

	public SoinTypeVersionRuleResult getRuleHouseFund() {
		return ruleHouseFund;
	}

	public void setRuleHouseFund(SoinTypeVersionRuleResult ruleHouseFund) {
		this.ruleHouseFund = ruleHouseFund;
	}

	public SoinTypeVersionRuleResult getRuleHouseFundAddition() {
		return ruleHouseFundAddition;
	}

	public void setRuleHouseFundAddition(SoinTypeVersionRuleResult ruleHouseFundAddition) {
		this.ruleHouseFundAddition = ruleHouseFundAddition;
	}

	public Integer getAtLeast() {
		return atLeast;
	}

	public void setAtLeast(Integer atLeast) {
		this.atLeast = atLeast;
	}

	public Integer getAtMost() {
		return atMost;
	}

	public void setAtMost(Integer atMost) {
		this.atMost = atMost;
	}

	public BigDecimal getLateFee() {
		return lateFee;
	}

	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}

	public Integer getCrossYear() {
		return crossYear;
	}

	public void setCrossYear(Integer crossYear) {
		this.crossYear = crossYear;
	}

	public SoinTypeVersionRuleResult getRuleHeating() {
		return ruleHeating;
	}

	public void setRuleHeating(SoinTypeVersionRuleResult ruleHeating) {
		this.ruleHeating = ruleHeating;
	}
}
