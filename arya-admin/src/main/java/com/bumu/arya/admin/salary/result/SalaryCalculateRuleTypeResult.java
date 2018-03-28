package com.bumu.arya.admin.salary.result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by DaiAoXiang on 2017/1/13.
 */
public class SalaryCalculateRuleTypeResult {
	@JsonProperty("rule_type")
	int ruleType;

	public int getRuleType() {
		return ruleType;
	}

	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}
}
