package com.bumu.arya.salary.result;

import com.bumu.arya.salary.model.entity.SalaryCalculateDetailEntity;
import com.bumu.arya.salary.model.entity.SalaryUserEntity;
import com.bumu.engine.excelimport.model.ICResult;
import com.bumu.common.result.PaginationResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CuiMengxin
 * @date 2016/3/28
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SalaryCalculateListResult extends PaginationResult {

	@ApiModelProperty(value = "薪资计算结果", name = "calculate_result")
	@JsonProperty("calculate_result")
	List<SalaryCalculateResult> calculateResults = new ArrayList<>();

	@ApiModelProperty(value = "薪资计算分页", name = "calculate_result_pager")
	@JsonProperty("calculate_result_pager")
	List<SalaryCalculateResult> calculateResultPager;

	@ApiModelProperty(value = "薪资统计结果", name = "salary_calculate_count_result_list")
	@JsonProperty("salary_calculate_count_result_list")
	List<SalaryCalculateCountResult> salaryCalculateCountResultList;

	@ApiModelProperty(value = "导入操作反馈信息", name = "icResult")
	ICResult icResult;

	@ApiModelProperty(value = "本次导入的批次编号", name = "week")
	Long week;

	public Long getWeek() {
		return week;
	}

	public void setWeek(Long week) {
		this.week = week;
	}

	public ICResult getIcResult() {
		return icResult;
	}

	public void setIcResult(ICResult icResult) {
		this.icResult = icResult;
	}

	public List<SalaryCalculateCountResult> getSalaryCalculateCountResultList() {
		return salaryCalculateCountResultList;
	}

	public void setSalaryCalculateCountResultList(List<SalaryCalculateCountResult> salaryCalculateCountResultList) {
		this.salaryCalculateCountResultList = salaryCalculateCountResultList;
	}

	public List<SalaryCalculateResult> getCalculateResults() {
		return calculateResults;
	}

	public void setCalculateResults(List<SalaryCalculateResult> calculateResults) {
		this.calculateResults = calculateResults;
	}

	public List<SalaryCalculateResult> getCalculateResultPager() {
		return calculateResultPager;
	}

	public void setCalculateResultPager(List<SalaryCalculateResult> calculateResultPager) {
		this.calculateResultPager = calculateResultPager;
	}

	@ApiModel
	public static class SalaryCalculateResult {

		@ApiModelProperty(value = "薪资ID")
		String id;

		@ApiModelProperty(value = "薪资人名称")
		String name;

		@ApiModelProperty(value = "用户ID")
		String userId;

		@ApiModelProperty(value = "薪资人身份证")
		String idcardNo;

		@ApiModelProperty(value = "地区ID")
		String districtId;

		@ApiModelProperty(value = "地区名称")
		String districtName;

		@ApiModelProperty(value = "企业名称")
		String corpName;

		/**
		 * 税前薪资
		 */
		@ApiModelProperty(value = "税前薪资")
		String taxableSalary;

		/**
		 * 本次税前薪资
		 */
		@ApiModelProperty(value = "本次导入税前薪资")
		String taxableWeekSalary;

		/**
		 * 个税
		 */
		@ApiModelProperty(value = "个税")
		String personalTax;

		/**
		 * 薪资服务费
		 */
		@ApiModelProperty(value = "薪资服务费")
		String brokerage;

		/**
		 * 税后薪资
		 */
		@ApiModelProperty(value = "税后薪资")
		String netSalary;

		/**
		 * 账号
		 */
		@ApiModelProperty(value = "账号")
		String bankAccount;

		@ApiModelProperty(value = "开户行名称")
		String bankName;

		/**
		 * 手机号
		 */
		@ApiModelProperty(value = "手机号")
		String phone;

		/**
		 * excel信息
		 */
		List<Map<String, String>> excelInfo = new ArrayList<>();

		public String getBankName() {
			return bankName;
		}

		public void setBankName(String bankName) {
			this.bankName = bankName;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIdcardNo() {
			return idcardNo;
		}

		public void setIdcardNo(String idcardNo) {
			this.idcardNo = idcardNo;
		}

		public String getDistrictId() {
			return districtId;
		}

		public void setDistrictId(String districtId) {
			this.districtId = districtId;
		}

		public String getDistrictName() {
			return districtName;
		}

		public void setDistrictName(String districtName) {
			this.districtName = districtName;
		}

		public String getCorpName() {
			return corpName;
		}

		public void setCorpName(String corpName) {
			this.corpName = corpName;
		}

		public String getTaxableSalary() {
			return taxableSalary;
		}

		public void setTaxableSalary(String taxableSalary) {
			this.taxableSalary = taxableSalary;
		}

		public String getPersonalTax() {
			return personalTax;
		}

		public void setPersonalTax(String personalTax) {
			this.personalTax = personalTax;
		}

		public String getBrokerage() {
			return brokerage;
		}

		public void setBrokerage(String brokerage) {
			this.brokerage = brokerage;
		}

		public String getNetSalary() {
			return netSalary;
		}

		public void setNetSalary(String netSalary) {
			this.netSalary = netSalary;
		}

		public String getBankAccount() {
			return bankAccount;
		}

		public void setBankAccount(String bankAccount) {
			this.bankAccount = bankAccount;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public List<Map<String, String>> getExcelInfo() {
			return excelInfo;
		}

		public void setExcelInfo(List<Map<String, String>> excelInfo) {
			this.excelInfo = excelInfo;
		}

		public String getTaxableWeekSalary() {
			return taxableWeekSalary;
		}

		public void setTaxableWeekSalary(String taxableWeekSalary) {
			this.taxableWeekSalary = taxableWeekSalary;
		}
	}

	public static SalaryCalculateResult convert(SalaryCalculateDetailEntity salaryCalculateDetailEntity, SalaryUserEntity salaryUserEntity){
		SalaryCalculateListResult.SalaryCalculateResult salaryCalculateResult = new SalaryCalculateListResult.SalaryCalculateResult();
		salaryCalculateResult.setId(salaryCalculateDetailEntity.getId());
		salaryCalculateResult.setBankAccount(salaryUserEntity.getBankAccount());
		salaryCalculateResult.setDistrictName(salaryUserEntity.getDistrictName());
		salaryCalculateResult.setName(salaryUserEntity.getName());
		salaryCalculateResult.setIdcardNo(salaryUserEntity.getIdCardNo());
		salaryCalculateResult.setPhone(salaryUserEntity.getPhoneNo());
		salaryCalculateResult.setBankAccount(salaryUserEntity.getBankAccount());
		salaryCalculateResult.setBankName(salaryUserEntity.getBankName());
		salaryCalculateResult.setNetSalary(salaryCalculateDetailEntity.getNetSalary().toString());
		salaryCalculateResult.setPersonalTax(salaryCalculateDetailEntity.getPersonalTax().toString());
		salaryCalculateResult.setBrokerage(salaryCalculateDetailEntity.getBrokerage().toString());
		salaryCalculateResult.setTaxableSalary(salaryCalculateDetailEntity.getTaxableSalary().toString());
		salaryCalculateResult.setExcelInfo(new Gson().fromJson(salaryCalculateDetailEntity.getExcelInfo(), ArrayList.class));
		salaryCalculateResult.setCorpName(salaryCalculateDetailEntity.getCorpName());
		salaryCalculateResult.setUserId(salaryCalculateDetailEntity.getUserId());
		salaryCalculateResult.setTaxableWeekSalary(salaryCalculateDetailEntity.getTaxableWeekSalary().toString());
		return salaryCalculateResult;
	}
}
