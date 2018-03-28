package com.bumu.arya.salary.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/3
 */
@Entity
@Table(name = "SALARY_CUSTOMER_ACCOUNT")
public class CustomerAccountEntity extends BaseSalaryEntity {

    @Id()
    @Column(name = "ID", columnDefinition = "char(32)")
    private String id;

    @Column(name = "CUSTOMER_ID", columnDefinition = "char(32)")
    private String customerId;

    /**
     * 到账日期
     */
    @Column(name = "TRANS_ACCOUNT_DATE")
    private Long transAccountDate;

    /**
     * 清单日期
     */
    @Column(name = "DEAL_DATE")
    private Long dealDate;

    /**
     * 到账金额
     */
    @Column(name = "TRANS_ACCOUNT_AMOUNT")
    private String transAccountAmount = "0";

    /**
     * 税前金额
     */
    @Column(name = "SALARY_BEFORE_TAX")
    private String salaryBeforeTax = "0";

    /**
     * 税后金额
     */
    @Column(name = "SALARY_AFTER_TAX")
    private String salaryAfterTax = "0";

    /**
     * 个税处理费
     */
    @Column(name = "PERSONAL_TAX_FEE")
    private String personalTaxFee = "0";

    /**
     * 薪资服务费
     */
    @Column(name = "SALARY_FEE")
    private String salaryFee = "0";

    /**
     * 余额
     */
    @Column(name = "REMAIN_ACCOUNT")
    private String remainAccount = "0";

    /**
     * 开票金额
     */
    @Column(name = "BILL_AMOUNT")
    private String billAmount = "0";

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 薪资的批次
     */
    @Column(name = "WEEK")
    private Long week;

    /**
     * 账单产生的交易金额
     */
    @Column(name = "ACCOUNT_AMOUNT")
    private String accountAmount;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(String accountAmount) {
        this.accountAmount = accountAmount;
    }

    public void setTransAccountDate(Long transAccountDate) {
        this.transAccountDate = transAccountDate;
    }

    public Long getWeek() {
        return week;
    }

    public void setWeek(Long week) {
        this.week = week;
    }

    public String getRemainAccount() {
        return remainAccount;
    }

    public void setRemainAccount(String remainAccount) {
        this.remainAccount = remainAccount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getTransAccountDate() {
        return transAccountDate;
    }

    public Long getDealDate() {
        return dealDate;
    }

    public void setDealDate(Long dealDate) {
        this.dealDate = dealDate;
    }

    public String getTransAccountAmount() {
        return transAccountAmount;
    }

    public void setTransAccountAmount(String transAccountAmount) {
        this.transAccountAmount = transAccountAmount;
    }

    public String getSalaryBeforeTax() {
        return salaryBeforeTax;
    }

    public void setSalaryBeforeTax(String salaryBeforeTax) {
        this.salaryBeforeTax = salaryBeforeTax;
    }

    public String getSalaryAfterTax() {
        return salaryAfterTax;
    }

    public void setSalaryAfterTax(String salaryAfterTax) {
        this.salaryAfterTax = salaryAfterTax;
    }

    public String getPersonalTaxFee() {
        return personalTaxFee;
    }

    public void setPersonalTaxFee(String personalTaxFee) {
        this.personalTaxFee = personalTaxFee;
    }

    public String getSalaryFee() {
        return salaryFee;
    }

    public void setSalaryFee(String salaryFee) {
        this.salaryFee = salaryFee;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
