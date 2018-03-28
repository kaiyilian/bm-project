package com.bumu.arya.salary.model.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 薪资详情
 *
 * @author Gujianchao
 */

@Entity
@Table(name = "SALARY_CALCULATE_DETAIL")
public class SalaryCalculateDetailEntity extends BaseSalaryEntity{

    @Transient
    private Logger logger = LoggerFactory.getLogger(SalaryCalculateDetailEntity.class);

    @Id()
    @Column(name = "ID", columnDefinition = "char(32) COMMENT 'ID'")
    private String id;

    @Column(name = "CUSTOMER_ID", columnDefinition = "char(32) COMMENT '客户'")
    private String customerId;

    @Column(name = "DISTRICT_ID", columnDefinition = "char(32) COMMENT '地区'")
    private String districtId;

    @Column(name = "SETTLEMENT_INTERVAL")
    private Integer settlementInterval;

    @Column(name = "CORP_NAME")
    private String corpName;

    @Column(name = "WEEK")
    private Long week;

    @Column(name = "USER_ID", columnDefinition = "char(32) COMMENT '薪资人员'")
    private String userId;

    @Column(name = "TAXABLE_SALARY")
    private BigDecimal taxableSalary;

    @Column(name = "TAXABLE_WEEK_SALARY")
    private BigDecimal taxableWeekSalary;

    @Column(name = "PERSONAL_TAX")
    private BigDecimal personalTax;

    @Column(name = "BROKERAGE")
    private BigDecimal brokerage;

    @Column(name = "NET_SALARY")
    private BigDecimal netSalary;

    @Column(name = "MONTH")
    private Integer month;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "EXCEL_INFO", length = 2000)
    private String excelInfo;

    /**
     * 是否已经确认扣款，0未 1确认扣款
     */
    @Column(name = "IS_DEDUCT")
    private Integer isDeduct;

    public Integer getIsDeduct() {
        return isDeduct;
    }

    public void setIsDeduct(Integer isDeduct) {
        this.isDeduct = isDeduct;
    }

    public BigDecimal getTaxableWeekSalary() {
        return taxableWeekSalary;
    }

    public void setTaxableWeekSalary(BigDecimal taxableWeekSalary) {
        this.taxableWeekSalary = taxableWeekSalary;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getSettlementInterval() {
        return settlementInterval;
    }

    public void setSettlementInterval(Integer settlementInterval) {
        this.settlementInterval = settlementInterval;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public Long getWeek() {
        return week;
    }

    public void setWeek(Long week) {
        this.week = week;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getExcelInfo() {
        return excelInfo;
    }

    public void setExcelInfo(String excelInfo) {
        this.excelInfo = excelInfo;
    }
}
