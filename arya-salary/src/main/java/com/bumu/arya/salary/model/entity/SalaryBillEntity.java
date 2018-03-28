package com.bumu.arya.salary.model.entity;

import javax.persistence.*;

/**
 * 跟踪信息
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/3
 */
@Entity
@Table(name = "SALARY_BILL")
public class SalaryBillEntity extends BaseSalaryEntity {

    @Id()
    @Column(name = "ID", columnDefinition = "char(32)")
    private String id;

    /**
     * 开票公司
     */
    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    /**
     * 开票公司
     */
    @Column(name = "CORP_NAME")
    private String corpName;

    /**
     * 开票金额
     */
    @Column(name = "TOTAL_MONEY")
    private String totalMoney;

    /**
     * 管理费
     */
    @Column(name = "MANAGER_FEE")
    private String managerFee;

    /**
     * 税前工资
     */
    @Column(name = "NET_SALARY")
    private String netSalary;

    /**
     * 个税
     */
    @Column(name = "PERSONAL_TAX")
    private String personalTax;

    /**
     * 开票申请日期
     */
    @Column(name = "BILL_APPLY_DATE")
    private Long billApplyDate;

    /**
     * 开票
     */
    @Column(name = "BILL_DATE")
    private Long billDate;

    /**
     * 邮寄日期
     */
    @Column(name = "MAIL_DATE")
    private Long mailDate;

    /**
     * 接收人
     */
    @Column(name = "RECEIVER")
    private String receiver;

    /**
     * 接受日期
     */
    @Column(name = "RECEIVE_DATE")
    private Long receiveDate;

    /**
     * 签收日期
     */
    @Column(name = "receive_Info")
    private String receiveInfo;

    /**
     * 汇款情况
     */
    @Column(name = "back_Info")
    private String backInfo;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    @Override
    public String getId() {
        return id;
    }

    @Override
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
