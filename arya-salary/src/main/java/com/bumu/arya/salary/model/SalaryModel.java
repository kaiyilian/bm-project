package com.bumu.arya.salary.model;

import java.math.BigDecimal;

/**
 * @author CuiMengxin
 * @date 2016/3/29
 */
public class SalaryModel {

	public static final int NORMAL = 0;//0未覆盖
	public static final int BE_OVERRIDE = 1;//1被覆盖
	public static final int OVERRIDER_FILE = 2;//2覆盖同文件记录
	public static final int OVERRIDER_DB = 3;//3覆盖DB记录

	public static final int PHONE_NORMAL = 0;//正常
	public static final int PHONE_NEW = 1;//新手机号
	public static final int PHONE_WRONG = 2;//手机号格式错误
	public static final int PHONE_CONFLICT = 3;//手机号与他人冲突
	public static final int PHONE_SYSTEM = 4;//系统生成手机号

	public static final int IDCARD_NORMAL = 0;//身份证号码正常
	public static final int IDCARD_NEW = 1;//新身份证号码
	public static final int IDCARD_WRONG = 2;//身份证号码错误
	public static final int IDCARD_CONFLICT = 3;//身份证号与他人冲突

	String id;

	String district;

	String districtId;

	String corp;

	String corpId;

	String departmentId;

	String departmentName;

	String userId;

	String name;

	String idcardNo;

	String phoneNo;

	String bankAccount;

	boolean isBankAccountWrong;

	int ruleType;

	/**
	 * 覆盖情况，0未覆盖，1被覆盖，2覆盖同文件记录，3覆盖DB
	 */
	int overrideStatus;

	/**
	 * 税前薪资
	 */
	BigDecimal taxableSalary;

	/**
	 * 个税
	 */
	BigDecimal personalTax;

	/**
	 * 个税服务费
	 */
	BigDecimal serviceCharge;

	/**
	 * 薪资服务费
	 */
	BigDecimal brokerage;

	/**
	 * 税后薪资
	 */
	BigDecimal netSalary;

	/**
	 * 是否忽略
	 */
	boolean isIgnore;

	/**
	 * 是否有问题
	 */
	boolean isWrong;

	boolean isNewUser;

	boolean isNewCorp;

	boolean isDistrictWrong;

	/**
	 * 手机号码是否校验过，是否不存在与他人冲突
	 */
	boolean phoneNoChecked;

	int idCardStatus;

	int phoneNoStatus;

	public SalaryModel() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isBankAccountWrong() {
		return isBankAccountWrong;
	}

	public void setBankAccountWrong(boolean bankAccountWrong) {
		isBankAccountWrong = bankAccountWrong;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public boolean isWrong() {
		return isWrong;
	}

	public void setWrong(boolean wrong) {
		isWrong = wrong;
	}

	public boolean isPhoneNoChecked() {
		return phoneNoChecked;
	}

	public void setPhoneNoChecked(boolean phoneNoChecked) {
		this.phoneNoChecked = phoneNoChecked;
	}

	public int getIdCardStatus() {
		return idCardStatus;
	}

	public void setIdCardStatus(int idCardStatus) {
		this.idCardStatus = idCardStatus;
	}

	public int getPhoneNoStatus() {
		return phoneNoStatus;
	}

	public void setPhoneNoStatus(int phoneNoStatus) {
		this.phoneNoStatus = phoneNoStatus;
	}

	public int getRuleType() {
		return ruleType;
	}

	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Boolean getNewUser() {
		return isNewUser;
	}

	public void setNewUser(Boolean newUser) {
		isNewUser = newUser;
	}

	public Boolean getNewCorp() {
		return isNewCorp;
	}

	public void setNewCorp(Boolean newCorp) {
		isNewCorp = newCorp;
	}

	public Boolean getDistrictWrong() {
		return isDistrictWrong;
	}

	public void setDistrictWrong(Boolean districtWrong) {
		isDistrictWrong = districtWrong;
	}

	public Boolean getIgnore() {
		return isIgnore;
	}

	public void setIgnore(Boolean ignore) {
		isIgnore = ignore;
	}

	public int getOverrideStatus() {
		return overrideStatus;
	}

	public void setOverrideStatus(int overrideStatus) {
		this.overrideStatus = overrideStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public BigDecimal getTaxableSalary() {
		return taxableSalary;
	}

	public void setTaxableSalary(BigDecimal taxableSalary) {
		this.taxableSalary = taxableSalary;
	}

	public BigDecimal getPersonalTax() {
		return personalTax;
	}

	public void setPersonalTax(BigDecimal personalTax) {
		this.personalTax = personalTax;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public BigDecimal getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
	}
}
