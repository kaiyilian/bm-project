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
public class CustomerAccountTotalQueryResult  {

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 到账金额
     */
    @ApiModelProperty(value = "到账金额")
    private String transAccountAmountTotal = "0";

    /**
     * 税前金额
     */
    @ApiModelProperty(value = "税前金额")
    private String salaryBeforeTaxTotal = "0";

    /**
     * 税后金额
     */
    @ApiModelProperty(value = "税后金额")
    private String salaryAfterTaxTotal = "0";

    /**
     * 个税处理费
     */
    @ApiModelProperty(value = "个税处理费")
    private String personalTaxFeeTotal = "0";

    /**
     * 薪资服务费
     */
    @ApiModelProperty(value = "薪资服务费")
    private String salaryFeeTotal = "0";

    /**
     * 账户余额
     */
    @ApiModelProperty(value = "账户余额")
    private String remainAccount = "0";

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTransAccountAmountTotal() {
        return transAccountAmountTotal;
    }

    public void setTransAccountAmountTotal(String transAccountAmountTotal) {
        this.transAccountAmountTotal = transAccountAmountTotal;
    }

    public String getSalaryBeforeTaxTotal() {
        return salaryBeforeTaxTotal;
    }

    public void setSalaryBeforeTaxTotal(String salaryBeforeTaxTotal) {
        this.salaryBeforeTaxTotal = salaryBeforeTaxTotal;
    }

    public String getSalaryAfterTaxTotal() {
        return salaryAfterTaxTotal;
    }

    public void setSalaryAfterTaxTotal(String salaryAfterTaxTotal) {
        this.salaryAfterTaxTotal = salaryAfterTaxTotal;
    }

    public String getPersonalTaxFeeTotal() {
        return personalTaxFeeTotal;
    }

    public void setPersonalTaxFeeTotal(String personalTaxFeeTotal) {
        this.personalTaxFeeTotal = personalTaxFeeTotal;
    }

    public String getSalaryFeeTotal() {
        return salaryFeeTotal;
    }

    public void setSalaryFeeTotal(String salaryFeeTotal) {
        this.salaryFeeTotal = salaryFeeTotal;
    }

    public String getRemainAccount() {
        return remainAccount;
    }

    public void setRemainAccount(String remainAccount) {
        this.remainAccount = remainAccount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
