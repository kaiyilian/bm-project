package com.bumu.arya.salary.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/4
 */
@ApiModel
public class SalaryBillResult {

    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 开票公司
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 开票公司
     */
    @ApiModelProperty(value = "开票公司")
    private String corpName;

    /**
     * 开票金额
     */
    @ApiModelProperty(value = "开票金额")
    private String totalMoney;

    /**
     * 管理费
     */
    @ApiModelProperty(value = "管理费")
    private String managerFee;

    /**
     * 税前工资
     */
    @ApiModelProperty(value = "税前工资")
    private String netSalary;

    /**
     * 个税
     */
    @ApiModelProperty(value = "个税")
    private String personalTax;

    /**
     * 开票申请日期
     */
    @ApiModelProperty(value = "开票申请日期")
    private Long billApplyDate;

    /**
     * 开票
     */
    @ApiModelProperty(value = "开票日期")
    private Long billDate;

    /**
     * 邮寄日期
     */
    @ApiModelProperty(value = "邮寄日期")
    private Long mailDate;

    /**
     * 接收人
     */
    @ApiModelProperty(value = "接收人")
    private String receiver;

    /**
     * 接受日期
     */
    @ApiModelProperty(value = "接受日期")
    private Long receiveDate;

    /**
     * 签收日期
     */
    @ApiModelProperty(value = "签收信息")
    private String receiveInfo;

    /**
     * 汇款情况
     */
    @ApiModelProperty(value = "汇款情况")
    private String backInfo;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getManagerFee() {
        return managerFee;
    }

    public void setManagerFee(String managerFee) {
        this.managerFee = managerFee;
    }

    public String getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(String netSalary) {
        this.netSalary = netSalary;
    }

    public String getPersonalTax() {
        return personalTax;
    }

    public void setPersonalTax(String personalTax) {
        this.personalTax = personalTax;
    }

    public Long getBillApplyDate() {
        return billApplyDate;
    }

    public void setBillApplyDate(Long billApplyDate) {
        this.billApplyDate = billApplyDate;
    }

    public Long getBillDate() {
        return billDate;
    }

    public void setBillDate(Long billDate) {
        this.billDate = billDate;
    }

    public Long getMailDate() {
        return mailDate;
    }

    public void setMailDate(Long mailDate) {
        this.mailDate = mailDate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Long receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveInfo() {
        return receiveInfo;
    }

    public void setReceiveInfo(String receiveInfo) {
        this.receiveInfo = receiveInfo;
    }

    public String getBackInfo() {
        return backInfo;
    }

    public void setBackInfo(String backInfo) {
        this.backInfo = backInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
