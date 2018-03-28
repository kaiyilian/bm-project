package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuiMengxin on 2017/1/9.
 */
public class SoinBillDetailResult {

	String id;//详情id

	@JsonProperty("order_id")
	String orderId;

	@JsonProperty("year_month")
	String yearMonth;

	String fees;

	@JsonProperty("total_in")
	String totalIn;

	//缴纳状态
	Integer status;

	/**
	 * 缴纳失败原因
	 */
	String reason;


	@JsonProperty("rule_details")
	List<RuleDetail> ruleDetails;

	public SoinBillDetailResult() {
		this.ruleDetails = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(String totalIn) {
		this.totalIn = totalIn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<RuleDetail> getRuleDetails() {
		return ruleDetails;
	}

	public void setRuleDetails(List<RuleDetail> ruleDetails) {
		this.ruleDetails = ruleDetails;
	}

	public static class RuleDetail {

		String name;

		String injury;

		String medical;

		String pregnancy;

		String pension;

		String unemployment;

		@JsonProperty("house_fund")
		String houseFund;

		String disable;

		@JsonProperty("severe_illness")
		String severeIllness;

		//采暖费
		@JsonProperty("heating")
		String heating;

		@JsonProperty("injury_addition")
		String injuryAddition;

		@JsonProperty("house_fund_addition")
		String houseFundAddition;

		public String getHeating() {
			return heating;
		}

		public void setHeating(String heating) {
			this.heating = heating;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getInjury() {
			return injury;
		}

		public void setInjury(String injury) {
			this.injury = injury;
		}

		public String getMedical() {
			return medical;
		}

		public void setMedical(String medical) {
			this.medical = medical;
		}

		public String getPregnancy() {
			return pregnancy;
		}

		public void setPregnancy(String pregnancy) {
			this.pregnancy = pregnancy;
		}

		public String getPension() {
			return pension;
		}

		public void setPension(String pension) {
			this.pension = pension;
		}

		public String getUnemployment() {
			return unemployment;
		}

		public void setUnemployment(String unemployment) {
			this.unemployment = unemployment;
		}

		public String getHouseFund() {
			return houseFund;
		}

		public void setHouseFund(String houseFund) {
			this.houseFund = houseFund;
		}

		public String getDisable() {
			return disable;
		}

		public void setDisable(String disable) {
			this.disable = disable;
		}

		public String getSevereIllness() {
			return severeIllness;
		}

		public void setSevereIllness(String severeIllness) {
			this.severeIllness = severeIllness;
		}

		public String getInjuryAddition() {
			return injuryAddition;
		}

		public void setInjuryAddition(String injuryAddition) {
			this.injuryAddition = injuryAddition;
		}

		public String getHouseFundAddition() {
			return houseFundAddition;
		}

		public void setHouseFundAddition(String houseFundAddition) {
			this.houseFundAddition = houseFundAddition;
		}
	}

}
