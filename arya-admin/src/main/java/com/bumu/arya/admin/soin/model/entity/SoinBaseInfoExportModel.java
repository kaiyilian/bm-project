package com.bumu.arya.admin.soin.model.entity;

import java.math.BigDecimal;

/**
 * Created by CuiMengxin on 2016/11/15.
 */
public class SoinBaseInfoExportModel {

	String sheng;

	String shi;

	String qu;

	String name;

	String effectTime;//生效时间

	String houseFoundMust;//是否必须缴纳公积金

	String forwardMonth;//必须提前多少月缴纳

	String endDay;//每月截止几号停止缴费

	String leastBuyCount;//至少连续购买几个月

	String soinLinkWithfoundFound;//社保与公积金基数是否联动

	RuleDetail pension;

	RuleDetail medical;

	RuleDetail unemployment;

	RuleDetail injury;

	RuleDetail pregnancy;

	RuleDetail houseFund;

	RuleDetail disability;

	RuleDetail severeIllness;

	RuleDetail injuryAddition;

	RuleDetail houseFundAddition;

	BigDecimal corpSoinTotal;

	BigDecimal personSoinTotal;

	BigDecimal soinTotal;

	BigDecimal houseFundTotal;

	BigDecimal total;

	public BigDecimal getSoinTotal() {
		return soinTotal;
	}

	public void setSoinTotal(BigDecimal soinTotal) {
		this.soinTotal = soinTotal;
	}

	public BigDecimal getCorpSoinTotal() {
		return corpSoinTotal;
	}

	public void setCorpSoinTotal(BigDecimal corpSoinTotal) {
		this.corpSoinTotal = corpSoinTotal;
	}

	public BigDecimal getPersonSoinTotal() {
		return personSoinTotal;
	}

	public void setPersonSoinTotal(BigDecimal personSoinTotal) {
		this.personSoinTotal = personSoinTotal;
	}

	public BigDecimal getHouseFundTotal() {
		return houseFundTotal;
	}

	public void setHouseFundTotal(BigDecimal houseFundTotal) {
		this.houseFundTotal = houseFundTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public RuleDetail getPension() {
		return pension;
	}

	public void setPension(RuleDetail pension) {
		this.pension = pension;
	}

	public RuleDetail getMedical() {
		return medical;
	}

	public void setMedical(RuleDetail medical) {
		this.medical = medical;
	}

	public RuleDetail getUnemployment() {
		return unemployment;
	}

	public void setUnemployment(RuleDetail unemployment) {
		this.unemployment = unemployment;
	}

	public RuleDetail getInjury() {
		return injury;
	}

	public void setInjury(RuleDetail injury) {
		this.injury = injury;
	}

	public RuleDetail getPregnancy() {
		return pregnancy;
	}

	public void setPregnancy(RuleDetail pregnancy) {
		this.pregnancy = pregnancy;
	}

	public RuleDetail getHouseFund() {
		return houseFund;
	}

	public void setHouseFund(RuleDetail houseFund) {
		this.houseFund = houseFund;
	}

	public RuleDetail getDisability() {
		return disability;
	}

	public void setDisability(RuleDetail disability) {
		this.disability = disability;
	}

	public RuleDetail getSevereIllness() {
		return severeIllness;
	}

	public void setSevereIllness(RuleDetail severeIllness) {
		this.severeIllness = severeIllness;
	}

	public RuleDetail getInjuryAddition() {
		return injuryAddition;
	}

	public void setInjuryAddition(RuleDetail injuryAddition) {
		this.injuryAddition = injuryAddition;
	}

	public RuleDetail getHouseFundAddition() {
		return houseFundAddition;
	}

	public void setHouseFundAddition(RuleDetail houseFundAddition) {
		this.houseFundAddition = houseFundAddition;
	}

	public static class RuleDetail {

		String name;

		String corpPer;//比例

		String personPer;

		BigDecimal base;

		String memo;

		BigDecimal corpPay;

		BigDecimal personPay;

		public BigDecimal getCorpPay() {
			return corpPay;
		}

		public void setCorpPay(BigDecimal corpPay) {
			this.corpPay = corpPay;
		}

		public BigDecimal getPersonPay() {
			return personPay;
		}

		public void setPersonPay(BigDecimal personPay) {
			this.personPay = personPay;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCorpPer() {
			return corpPer;
		}

		public void setCorpPer(String corpPer) {
			this.corpPer = corpPer;
		}

		public String getPersonPer() {
			return personPer;
		}

		public void setPersonPer(String personPer) {
			this.personPer = personPer;
		}

		public BigDecimal getBase() {
			return base;
		}

		public void setBase(BigDecimal base) {
			this.base = base;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}
	}

	public String getSheng() {
		return sheng;
	}

	public void setSheng(String sheng) {
		this.sheng = sheng;
	}

	public String getShi() {
		return shi;
	}

	public void setShi(String shi) {
		this.shi = shi;
	}

	public String getQu() {
		return qu;
	}

	public void setQu(String qu) {
		this.qu = qu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(String effectTime) {
		this.effectTime = effectTime;
	}

	public String getHouseFoundMust() {
		return houseFoundMust;
	}

	public void setHouseFoundMust(String houseFoundMust) {
		this.houseFoundMust = houseFoundMust;
	}

	public String getForwardMonth() {
		return forwardMonth;
	}

	public void setForwardMonth(String forwardMonth) {
		this.forwardMonth = forwardMonth;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getLeastBuyCount() {
		return leastBuyCount;
	}

	public void setLeastBuyCount(String leastBuyCount) {
		this.leastBuyCount = leastBuyCount;
	}

	public String getSoinLinkWithfoundFound() {
		return soinLinkWithfoundFound;
	}

	public void setSoinLinkWithfoundFound(String soinLinkWithfoundFound) {
		this.soinLinkWithfoundFound = soinLinkWithfoundFound;
	}

}
