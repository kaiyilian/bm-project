package com.bumu.arya.salary.command;

import com.bumu.arya.Utils;
import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.model.SalaryCalculateRuleBounModel;
import com.bumu.arya.salary.model.SalaryCalculateRuleGearModel;
import com.bumu.arya.salary.model.SalaryCalculateRuleModel;
import com.bumu.arya.salary.model.entity.CustomerSalaryRuleEntity;
import com.bumu.common.SessionInfo;
import com.bumu.exception.AryaServiceException;
import com.bumu.function.EntityConverter;
import com.bumu.function.VoConverterFunction;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.bumu.arya.salary.common.SalaryEnum.BillProject.salary;

/**
 * @author CuiMengxin
 * @date 2016/7/8
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerSalaryRuleCommand implements EntityConverter<CustomerSalaryRuleEntity>,VoConverterFunction.Add<CustomerSalaryRuleEntity, SessionInfo> {

	@ApiModelProperty(value = "薪资规则名称", name = "rule_name")
	@JsonProperty("rule_name")
	String ruleName;

	@ApiModelProperty(value = "计税档", name = "tax_gears")
	@JsonProperty("tax_gears")
	List<SalaryCalculateRuleTaxGear> taxGears;

	@ApiModelProperty(value = "薪资服务费率", name = "brokerage_rate")
	@JsonProperty("brokerage_rate")
	BigDecimal brokerageRate = new BigDecimal(0);//薪资服务费率

	@ApiModelProperty(value = "个税起征点", name = "threshold_tax")
	@JsonProperty("threshold_tax")
	BigDecimal thresholdTax = new BigDecimal(0);//个税起征点

	@JsonProperty("rule_type")
	@ApiModelProperty(value = "规则分类", name= "rule_type")
	@NotNull(message = "请选择规则分类")
	SalaryEnum.RuleType ruleType;

	@JsonProperty("brokerage")
	@ApiModelProperty(value = "薪资服务费", name= "brokerage")
	BigDecimal brokerage = new BigDecimal(0);//薪资服务费

	@JsonProperty("customer_id")
	@ApiModelProperty(value = "客户", name= "customer_id")
	@NotBlank(message = "请选择客户")
	String customerId;

    @JsonProperty("cost_bearing")
	@ApiModelProperty(value = "薪资服务费付款方", name= "cost_bearing")
    SalaryEnum.CostBearing costBearing;

	@JsonProperty("new_leave_absence_sub_ratio")
	@ApiModelProperty(value = "新进/离职员工旷工扣款比例", name= "new_leave_absence_sub_ratio")
	BigDecimal newLeaveAbsenceSubRatio;

	@JsonProperty("ill_sub_ratio")
	@ApiModelProperty(value = "病假扣款比例", name= "ill_sub_ratio")
	BigDecimal illSubRatio;

	@JsonProperty("absence_sub_ratio")
	@ApiModelProperty(value = "非新进、离职员工旷工扣款比例", name= "absence_sub_ratio")
	BigDecimal absenceSubRatio;

	@JsonProperty("affair_sub_ratio")
	@ApiModelProperty(value = "事假扣款比例", name= "affair_sub_ratio")
	BigDecimal affairSubRatio;

	@ApiModelProperty(value = "全勤奖", name = "fulltime_bonu_list")
	@JsonProperty("fulltime_bonu_list")
	List<FulltimeBonu> fulltimeBonuList;

	@JsonProperty("ill_sub_ratio_ordinary")
	@ApiModelProperty(value = "普通病假扣款比例", name= "ill_sub_ratio_ordinary")
	BigDecimal illSubRatioOrdinary;

	@ApiModelProperty(value = "普通个税起征点", name = "threshold_tax_ordinary")
	@JsonProperty("threshold_tax_ordinary")
	BigDecimal thresholdTaxOrdinary = new BigDecimal(0);//个税起征点

	@JsonProperty("affair_sub_ratio_ordinary")
	@ApiModelProperty(value = "普通事假扣款比例", name= "affair_sub_ratio_ordinary")
	BigDecimal affairSubRatioOrdinary;

	@JsonProperty("brokerage_ordinary")
	@ApiModelProperty(value = "普通薪资服务费", name= "brokerage_ordinary")
	BigDecimal brokerageOrdinary;

	public BigDecimal getBrokerageOrdinary() {
		return brokerageOrdinary;
	}

	public void setBrokerageOrdinary(BigDecimal brokerageOrdinary) {
		this.brokerageOrdinary = brokerageOrdinary;
	}

	public SalaryEnum.CostBearing getCostBearing() {
		return costBearing;
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

	public void setCostBearing(SalaryEnum.CostBearing costBearing) {
        this.costBearing = costBearing;
    }

    public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public List<SalaryCalculateRuleTaxGear> getTaxGears() {
		return taxGears;
	}

	public void setTaxGears(List<SalaryCalculateRuleTaxGear> taxGears) {
		this.taxGears = taxGears;
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

	public SalaryEnum.RuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(SalaryEnum.RuleType ruleType) {
		this.ruleType = ruleType;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public CustomerSalaryRuleCommand() {
		
	}



	public static class SalaryCalculateRuleTaxGear {

		@JsonProperty("gear")
		@ApiModelProperty(value = "计税档", name= "gear")
		@NotNull(message = "计税档不能为空")
		BigDecimal gear;

		@JsonProperty("tax_rate")
		@ApiModelProperty(value = "税率", name= "tax_rate")
		@NotNull(message = "税率不能为空")
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

	public static class FulltimeBonu {

		@JsonProperty("leval")
		@ApiModelProperty(value = "全勤奖级别", name= "leval")
		@NotNull(message = "全勤奖级别不能为空")
		Integer leval;

		@JsonProperty("bonu")
		@ApiModelProperty(value = "奖金", name= "bonu")
		@NotNull(message = "奖金不能为空")
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

	@Override
	public void begin(CustomerSalaryRuleEntity customerSalaryRuleEntity, SessionInfo info) {
		//基础属性
		customerSalaryRuleEntity.setId(Utils.makeUUID());
		customerSalaryRuleEntity.setCreateTime(System.currentTimeMillis());
		customerSalaryRuleEntity.setIsDelete(Constants.FALSE);
		customerSalaryRuleEntity.setCreateUser(info.getUserId());

		//业务属性
		customerSalaryRuleEntity.setName(this.ruleName);
		customerSalaryRuleEntity.setRuleType(this.ruleType);
		customerSalaryRuleEntity.setCustomerId(this.customerId);
		customerSalaryRuleEntity.setRuleType(this.ruleType);

		SalaryCalculateRuleModel ruleModel = new SalaryCalculateRuleModel();
		ruleModel.setBrokerageRate(this.brokerageRate);
		ruleModel.setBrokerage(this.brokerage);
		ruleModel.setThresholdTax(this.thresholdTax);
		ruleModel.setRuleType(this.ruleType);
		ruleModel.setNewLeaveAbsenceSubRatio(this.newLeaveAbsenceSubRatio);
		ruleModel.setIllSubRatio(this.illSubRatio);
		ruleModel.setAbsenceSubRatio(this.absenceSubRatio);
		ruleModel.setAffairSubRatio(this.affairSubRatio);
		ruleModel.setThresholdTaxOrdinary(this.thresholdTaxOrdinary);
		ruleModel.setIllSubRatioOrdinary(this.illSubRatioOrdinary);
		ruleModel.setAffairSubRatioOrdinary(this.affairSubRatioOrdinary);
		ruleModel.setBrokerageOrdinary(this.brokerageOrdinary);
		if(ruleType == SalaryEnum.RuleType.defined) {
			customerSalaryRuleEntity.setCostBearing(this.costBearing);
			ruleModel.setCostBearing(this.costBearing);
			//自定义规则-
			ruleModel.setTaxGears(taxGears.stream().map(entity -> {
				SalaryCalculateRuleGearModel salaryCalculateRuleGearModel = new SalaryCalculateRuleGearModel();
				salaryCalculateRuleGearModel.setGear(entity.getGear());
				salaryCalculateRuleGearModel.setTaxRate(entity.getTaxRate());
				return salaryCalculateRuleGearModel;
			}).collect(Collectors.toList()));
		}
		if (ruleType == SalaryEnum.RuleType.humanPool) {
			ruleModel.setFulltimeBonuList(fulltimeBonuList.stream().map(entity -> {
				SalaryCalculateRuleBounModel salaryCalculateRuleBounModel = new SalaryCalculateRuleBounModel();
				salaryCalculateRuleBounModel.setBonu(entity.getBonu());
				salaryCalculateRuleBounModel.setLeval(entity.getLeval());
				return salaryCalculateRuleBounModel;
			}).collect(Collectors.toList()));
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			customerSalaryRuleEntity.setRuleDef(mapper.writeValueAsString(ruleModel));
		} catch (JsonProcessingException e) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_JSON_FAILED);
		}
	}
}
