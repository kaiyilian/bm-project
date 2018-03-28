package com.bumu.arya.salary.result;

import com.bumu.arya.salary.model.entity.CustomerAccountEntity;
import com.bumu.function.ResultConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/4
 */
@ApiModel
public class CustomerAccountResult implements ResultConverter<CustomerAccountEntity> {

    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 到账日期
     */
    @ApiModelProperty(value = "到账日期")
    private Long transAccountDate;

    @ApiModelProperty(value = "清单日期")
    private Long dealDate;

    /**
     * 到账金额
     */
    @ApiModelProperty(value = "到账金额")
    private String transAccountAmount;

    /**
     * 税前金额
     */
    @ApiModelProperty(value = "税前金额")
    private String salaryBeforeTax;

    /**
     * 税后金额
     */
    @ApiModelProperty(value = "税后金额")
    private String salaryAfterTax;

    /**
     * 个税处理费
     */
    @ApiModelProperty(value = "个税处理费")
    private String personalTaxFee;

    /**
     * 薪资服务费
     */
    @ApiModelProperty(value = "薪资服务费")
    private String salaryFee;

    /**
     * 开票金额
     */
    @ApiModelProperty(value = "开票金额")
    private String billAmount;

    /**
     * 账户余额
     */
    @ApiModelProperty(value = "账户余额")
    private String remainAccount;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    public Long getTransAccountDate() {
        return transAccountDate;
    }

    public void setTransAccountDate(Long transAccountDate) {
        this.transAccountDate = transAccountDate;
    }

    public Long getDealDate() {
        return dealDate;
    }

    public void setDealDate(Long dealDate) {
        this.dealDate = dealDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRemainAccount() {
        return remainAccount;
    }

    public void setRemainAccount(String remainAccount) {
        this.remainAccount = remainAccount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
