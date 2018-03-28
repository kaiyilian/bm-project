package com.bumu.arya.salary.model;

import java.math.BigDecimal;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/13
 */
public class SalaryExcelMode {

    public static String KEY_DISTRICT = "district";

    private String id;

    /**
     * 地区名称
     */
    private String district;

    /**
     * 地区主键
     */
    private String districtId;

    /**
     * 客户主键
     */
    private String customerId;

    /**
     * 公司名称
     */
    private String corpName;

    /**
     * 已发应资
     */
    private Float receiveSalary;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户身份证
     */
    private String idCardNo;

    /**
     * 用户手机号
     */
    private String phoneNo;

    /**
     * 银行卡号
     */
    private String bankAccount;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 员工装填 (非新进离职/新进/离职)
     */
    private String staffStatus;

    /**
     * 基本薪资
     */
    private BigDecimal salaryBase;

    /**
     * 排版天数
     */
    private Float workDays;

    /**
     * 加班基数
     */
    private BigDecimal salaryBaseOvertime;

    /**
     * 病假天数
     */
    private Float illDays;

    /**
     * 事假天数
     */
    private Float affairDays;

    /**
     * 缺勤天数
     */
    private Float absenseDays;

    /**
     * 年假天数
     */
    private Float annualDays;

    /**
     * 产假天数
     */
    private Float precreateDays;

    /**
     * 婚丧假天数
     */
    private Float marryDays;

    /**
     * 丧假天数
     */
    private Float funeralDays;

    /**
     * 新进离职天数
     */
    private Float newLeaveDays;

    /**
     * 平时加班工时
     */
    private Float overtimeWorkDay;

    /**
     * 休假日加班工时
     */
    private Float overtimeWeekend;

    /**
     * 正常出勤时间
     */
    private Float workHours;

    /**
     * 国产加班工时
     */
    private Float overtimeNational;

    /**
     * 新进离职小时
     */
    private Float newLeaveHours;

    /**
     * 税前薪资
     */
    private BigDecimal taxableSalary;

    /**
     * 个税
     */
    private BigDecimal personalTax;

    /**
     * 薪资服务费
     */
    private BigDecimal brokerage;

    /**
     * 税后薪资
     */
    private BigDecimal netSalary;

    /**
     * 其他扣款（用于普通计算规则其他假种扣款）
     */
    private Float otherSub;

    /**
     * 额外信息 JSON格式
     */
    private String extra;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Float getReceiveSalary() {
        return receiveSalary;
    }

    public void setReceiveSalary(Float receiveSalary) {
        this.receiveSalary = receiveSalary;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
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

    public BigDecimal getSalaryBase() {
        return salaryBase;
    }

    public void setSalaryBase(BigDecimal salaryBase) {
        this.salaryBase = salaryBase;
    }

    public Float getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Float workDays) {
        this.workDays = workDays;
    }

    public BigDecimal getSalaryBaseOvertime() {
        return salaryBaseOvertime;
    }

    public void setSalaryBaseOvertime(BigDecimal salaryBaseOvertime) {
        this.salaryBaseOvertime = salaryBaseOvertime;
    }

    public Float getIllDays() {
        return illDays;
    }

    public void setIllDays(Float illDays) {
        this.illDays = illDays;
    }

    public Float getAffairDays() {
        return affairDays;
    }

    public void setAffairDays(Float affairDays) {
        this.affairDays = affairDays;
    }

    public Float getAbsenseDays() {
        return absenseDays;
    }

    public void setAbsenseDays(Float absenseDays) {
        this.absenseDays = absenseDays;
    }

    public Float getAnnualDays() {
        return annualDays;
    }

    public void setAnnualDays(Float annualDays) {
        this.annualDays = annualDays;
    }

    public Float getPrecreateDays() {
        return precreateDays;
    }

    public void setPrecreateDays(Float precreateDays) {
        this.precreateDays = precreateDays;
    }

    public Float getMarryDays() {
        return marryDays;
    }

    public void setMarryDays(Float marryDays) {
        this.marryDays = marryDays;
    }

    public Float getNewLeaveDays() {
        return newLeaveDays;
    }

    public void setNewLeaveDays(Float newLeaveDays) {
        this.newLeaveDays = newLeaveDays;
    }

    public Float getOvertimeWorkDay() {
        return overtimeWorkDay;
    }

    public void setOvertimeWorkDay(Float overtimeWorkDay) {
        this.overtimeWorkDay = overtimeWorkDay;
    }

    public Float getOvertimeWeekend() {
        return overtimeWeekend;
    }

    public void setOvertimeWeekend(Float overtimeWeekend) {
        this.overtimeWeekend = overtimeWeekend;
    }

    public Float getOvertimeNational() {
        return overtimeNational;
    }

    public void setOvertimeNational(Float overtimeNational) {
        this.overtimeNational = overtimeNational;
    }

    public Float getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Float workHours) {
        this.workHours = workHours;
    }

    public Float getNewLeaveHours() {
        return newLeaveHours;
    }

    public void setNewLeaveHours(Float newLeaveHours) {
        this.newLeaveHours = newLeaveHours;
    }

    public String getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(String staffStatus) {
        this.staffStatus = staffStatus;
    }

    public Float getFuneralDays() {
        return funeralDays;
    }

    public void setFuneralDays(Float funeralDays) {
        this.funeralDays = funeralDays;
    }

    public Float getOtherSub() {
        return otherSub;
    }

    public void setOtherSub(Float otherSub) {
        this.otherSub = otherSub;
    }
}
