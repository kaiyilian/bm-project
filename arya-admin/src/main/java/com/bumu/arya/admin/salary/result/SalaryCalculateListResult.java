package com.bumu.arya.admin.salary.result;

import com.bumu.common.result.PaginationResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/3/28
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SalaryCalculateListResult extends PaginationResult {

	@JsonProperty("file_name")
	String fileName;

	@JsonProperty("rule_type")
	int ruleType;

	String log;

	@JsonProperty("can_import")
	Boolean isCanImport;

	@JsonProperty("calculate_result")
	List<SalaryCalculateResult> calculateResults;

	SalaryCalculateStatisticsResultList statistics;

	public Boolean getCanImport() {
		return isCanImport;
	}

	public void setCanImport(Boolean canImport) {
		isCanImport = canImport;
	}

	public SalaryCalculateListResult() {
		log = new String();
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public SalaryCalculateStatisticsResultList getStatistics() {
		return statistics;
	}

	public void setStatistics(SalaryCalculateStatisticsResultList statistics) {
		this.statistics = statistics;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getRuleType() {
		return ruleType;
	}

	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}

	public List<SalaryCalculateResult> getCalculateResults() {
		return calculateResults;
	}

	public void setCalculateResults(List<SalaryCalculateResult> calculateResults) {
		this.calculateResults = calculateResults;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class SalaryCalculateResult {

		String id;

		UserName name;

		@JsonProperty("idcard_no")
		UserIdcard idcardNo;

		@JsonProperty("department_name")
		String departmentName;

		City city;

		Company corp;

		/**
		 * 税前薪资
		 */
		@JsonProperty("taxable_salary")
		TaxableSalary taxableSalary;

		/**
		 * 个税
		 */
		@JsonProperty("personal_tax")
		PersonalTax personalTax;

		/**
		 * 个税服务费
		 */
		@JsonProperty("service_charge")
		ServiceCharge serviceCharge;

		/**
		 * 薪资服务费
		 */
		Brokerage brokerage;

		/**
		 * 税后薪资
		 */
		@JsonProperty("net_salary")
		NetSalary netSalary;

		/**
		 * 账号
		 */
		@JsonProperty("bank_account")
		BankAccount bankAccount;

		/**
		 * 该记录的覆盖状态
		 */
		@JsonProperty("override_status")
		Integer overrideStatus;

		/**
		 * 该条是否被忽略
		 *
		 * @return
		 */
		@JsonProperty("is_ignore")
		Boolean isIgnore;

		Phone phone;

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		public Phone getPhone() {
			return phone;
		}

		public void setPhone(Phone phone) {
			this.phone = phone;
		}

		public Boolean getIgnore() {
			return isIgnore;
		}

		public void setIgnore(Boolean ignore) {
			isIgnore = ignore;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public UserName getName() {
			return name;
		}

		public void setName(UserName name) {
			this.name = name;
		}

		public BankAccount getBankAccount() {
			return bankAccount;
		}

		public void setBankAccount(BankAccount bankAccount) {
			this.bankAccount = bankAccount;
		}

		public UserIdcard getIdcardNo() {
			return idcardNo;
		}

		public void setIdcardNo(UserIdcard idcardNo) {
			this.idcardNo = idcardNo;
		}

		public City getCity() {
			return city;
		}

		public void setCity(City city) {
			this.city = city;
		}

		public Company getCorp() {
			return corp;
		}

		public void setCorp(Company corp) {
			this.corp = corp;
		}

		public TaxableSalary getTaxableSalary() {
			return taxableSalary;
		}

		public void setTaxableSalary(TaxableSalary taxableSalary) {
			this.taxableSalary = taxableSalary;
		}

		public PersonalTax getPersonalTax() {
			return personalTax;
		}

		public void setPersonalTax(PersonalTax personalTax) {
			this.personalTax = personalTax;
		}

		public ServiceCharge getServiceCharge() {
			return serviceCharge;
		}

		public void setServiceCharge(ServiceCharge serviceCharge) {
			this.serviceCharge = serviceCharge;
		}

		public Brokerage getBrokerage() {
			return brokerage;
		}

		public void setBrokerage(Brokerage brokerage) {
			this.brokerage = brokerage;
		}

		public NetSalary getNetSalary() {
			return netSalary;
		}

		public void setNetSalary(NetSalary netSalary) {
			this.netSalary = netSalary;
		}

		public Integer getOverrideStatus() {
			return overrideStatus;
		}

		public void setOverrideStatus(Integer overrideStatus) {
			this.overrideStatus = overrideStatus;
		}

		public SalaryCalculateResult() {

		}

		public static class UserName {
			String name;

			/**
			 * 是否是新用户
			 */
			@JsonProperty("is_new")
			Boolean isNewUser;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public Boolean getNewUser() {
				return isNewUser;
			}

			public void setNewUser(Boolean newUser) {
				isNewUser = newUser;
			}

			public UserName() {

			}
		}

		public static class UserIdcard {
			@JsonProperty("idcard_no")
			String idcardNo;

			/**
			 * 是否是新用户
			 */
			@JsonProperty("idcard_status")
			int idcardStatus;

			public String getIdcardNo() {
				return idcardNo;
			}

			public void setIdcardNo(String idcardNo) {
				this.idcardNo = idcardNo;
			}

			public int getIdcardStatus() {
				return idcardStatus;
			}

			public void setIdcardStatus(int idcardStatus) {
				this.idcardStatus = idcardStatus;
			}

			public UserIdcard() {

			}
		}

		public static class City {
			String city;

			/**
			 * 是否地区不正确
			 */
			@JsonProperty("is_district_wrong")
			Boolean isDistrictWrong;

			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public Boolean getDistrictWrong() {
				return isDistrictWrong;
			}

			public void setDistrictWrong(Boolean districtWrong) {
				isDistrictWrong = districtWrong;
			}

			public City() {

			}
		}

		public static class Company {
			/**
			 * 是否是新增的公司
			 */
			@JsonProperty("is_new")
			Boolean isNewCorp;

			String corp;

			public Boolean getNewCorp() {
				return isNewCorp;
			}

			public void setNewCorp(Boolean newCorp) {
				isNewCorp = newCorp;
			}

			public String getCorp() {
				return corp;
			}

			public void setCorp(String corp) {
				this.corp = corp;
			}

			public Company() {

			}
		}

		public static class TaxableSalary {
			/**
			 * 税前薪资
			 */
			@JsonProperty("taxable_salary")
			String taxableSalary;

			@JsonProperty("is_new")
			Boolean isNew;

			public String getTaxableSalary() {
				return taxableSalary;
			}

			public void setTaxableSalary(String taxableSalary) {
				this.taxableSalary = taxableSalary;
			}

			public Boolean getNew() {
				return isNew;
			}

			public void setNew(Boolean aNew) {
				isNew = aNew;
			}

			public TaxableSalary() {

			}
		}

		public static class PersonalTax {

			@JsonProperty("personal_tax")
			String personalTax;

			@JsonProperty("is_new")
			Boolean isNew;

			public String getPersonalTax() {
				return personalTax;
			}

			public void setPersonalTax(String personalTax) {
				this.personalTax = personalTax;
			}

			public Boolean getNew() {
				return isNew;
			}

			public void setNew(Boolean aNew) {
				isNew = aNew;
			}

			public PersonalTax() {
			}
		}

		public static class ServiceCharge {
			@JsonProperty("service_charge")
			String serviceCharge;

			@JsonProperty("is_new")
			Boolean isNew;

			public String getServiceCharge() {
				return serviceCharge;
			}

			public void setServiceCharge(String serviceCharge) {
				this.serviceCharge = serviceCharge;
			}

			public Boolean getNew() {
				return isNew;
			}

			public void setNew(Boolean aNew) {
				isNew = aNew;
			}

			public ServiceCharge() {
			}
		}

		public static class Brokerage {

			String brokerage;

			@JsonProperty("is_new")
			Boolean isNew;

			public String getBrokerage() {
				return brokerage;
			}

			public void setBrokerage(String brokerage) {
				this.brokerage = brokerage;
			}

			public Boolean getNew() {
				return isNew;
			}

			public void setNew(Boolean aNew) {
				isNew = aNew;
			}

			public Brokerage() {
			}
		}

		public static class NetSalary {

			@JsonProperty("net_salary")
			String netSalary;

			@JsonProperty("is_new")
			Boolean isNew;

			public String getNetSalary() {
				return netSalary;
			}

			public void setNetSalary(String netSalary) {
				this.netSalary = netSalary;
			}

			public Boolean getNew() {
				return isNew;
			}

			public void setNew(Boolean aNew) {
				isNew = aNew;
			}

			public NetSalary() {
			}
		}

		public static class BankAccount {
			@JsonProperty("bank_account_id")
			String bankAccountId;

			@JsonProperty("is_new")
			Boolean isNew;

			/**
			 * 是否账号不正确
			 */
			@JsonProperty("is_account_wrong")
			Boolean isAccountWrong;

			public Boolean getAccountWrong() {
				return isAccountWrong;
			}

			public void setAccountWrong(Boolean accountWrong) {
				isAccountWrong = accountWrong;
			}

			public BankAccount() {
			}

			public String getBankAccountId() {
				return bankAccountId;
			}

			public void setBankAccountId(String bankAccountId) {
				this.bankAccountId = bankAccountId;
			}

			public Boolean getNew() {
				return isNew;
			}

			public void setNew(Boolean aNew) {
				isNew = aNew;
			}
		}

		public static class Phone {
			@JsonProperty("phone_status")
			int phoneStatus;

			@JsonProperty("phone_no")
			String phoneNo;

			public Phone() {
			}

			public String getPhoneNo() {
				return phoneNo;
			}

			public void setPhoneNo(String phoneNo) {
				this.phoneNo = phoneNo;
			}

			public int getPhoneStatus() {
				return phoneStatus;
			}

			public void setPhoneStatus(int phoneStatus) {
				this.phoneStatus = phoneStatus;
			}
		}
	}
}
