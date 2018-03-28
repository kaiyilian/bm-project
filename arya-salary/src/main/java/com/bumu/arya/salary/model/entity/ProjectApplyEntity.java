package com.bumu.arya.salary.model.entity;

import com.bumu.arya.salary.common.SalaryEnum;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 立项申请-潜在客户
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/3
 */
@Entity
@Table(name = "SALARY_PROJECT_APPLY")
public class ProjectApplyEntity extends BaseSalaryEntity {

    @Id()
    @Column(name = "ID", columnDefinition = "char(32)")
    private String id;

    /**
     * 销售人员名称
     */
    @Column(name = "SALES_MAN")
    private String salesMan;

    /**
     * 销售部门名称
     */
    @Column(name = "SALES_DEPARTMENT")
    private String salesDepartment;

    /**
     * 客户名称
     */
    @Column(name = "CUSTOMER_NAME", length = 64)
    private String customerName;

    /**
     * 客户简称
     */
    @Column(name = "SHORT_NAME", length = 64)
    private String shortName;

    /**
     * 薪资人数
     */
    @Column(name = "SALARYER_NUM")
    private Integer salaryerNum;

    /**
     * 薪资总额
     */
    @Column(name = "SALARY_SUM")
    private String salarySum;

    /**
     * 发票类型
     */
    @Column(name = "BILL_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private SalaryEnum.BillType billType;

    /**
     * 发票项目1
     */
    @Column(name = "BILL_TYPE_ONE")
    @Enumerated(EnumType.ORDINAL)
    private SalaryEnum.BillProject billProjectOne;

    /**
     * 发票项目2
     */
    @Column(name = "BILL_TYPE_TWO")
    @Enumerated(EnumType.ORDINAL)
    private SalaryEnum.BillProject billProjectTwo;

    /**
     * 利润预算
     */
    @Column(name = "PROFIT_BUDGET")
    private String profitBudget;

    /**
     * email
     */
    @Column(name = "E_MAIL", length = 64)
    private String eMail;

    /**
     * 申请日期
     */
    @Column(name = "APPLY_DATE")
    private Long applyDate;

    /**
     * 地址
     */
    @Column(name = "ADDRESS", length = 128)
    private String address;

    /**
     * 联系电话
     */
    @Column(name = "TELPHONE", length = 64)
    private String telphone;

    /**
     * 联系人
     */
    @Column(name = "LINK_MAN", length = 64)
    private String linkMan;

    /**
     * 运营方案
     */
    @Column(name = "BUSSINESS_CASE", length = 1024)
    private String bussinessCase;

    /**
     * 是否是正式客户
     */
    @Column(name = "IS_CUSTOMER")
    private Integer isCustomer;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getSalesMan() {
        return salesMan;
    }

    public void setSalesMan(String salesMan) {
        this.salesMan = salesMan;
    }

    public String getSalesDepartment() {
        return salesDepartment;
    }

    public void setSalesDepartment(String salesDepartment) {
        this.salesDepartment = salesDepartment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getSalaryerNum() {
        return salaryerNum;
    }

    public void setSalaryerNum(Integer salaryerNum) {
        this.salaryerNum = salaryerNum;
    }

    public String getSalarySum() {
        return salarySum;
    }

    public void setSalarySum(String salarySum) {
        this.salarySum = salarySum;
    }

    public SalaryEnum.BillType getBillType() {
        return billType;
    }

    public void setBillType(SalaryEnum.BillType billType) {
        this.billType = billType;
    }

    public SalaryEnum.BillProject getBillProjectOne() {
        return billProjectOne;
    }

    public void setBillProjectOne(SalaryEnum.BillProject billProjectOne) {
        this.billProjectOne = billProjectOne;
    }

    public SalaryEnum.BillProject getBillProjectTwo() {
        return billProjectTwo;
    }

    public void setBillProjectTwo(SalaryEnum.BillProject billProjectTwo) {
        this.billProjectTwo = billProjectTwo;
    }

    public String getProfitBudget() {
        return profitBudget;
    }

    public void setProfitBudget(String profitBudget) {
        this.profitBudget = profitBudget;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Long getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Long applyDate) {
        this.applyDate = applyDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getBussinessCase() {
        return bussinessCase;
    }

    public void setBussinessCase(String bussinessCase) {
        this.bussinessCase = bussinessCase;
    }

    public Integer getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Integer isCustomer) {
        this.isCustomer = isCustomer;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return "ProjectApplyEntity{" +
                "id='" + id + '\'' +
                ", salesMan='" + salesMan + '\'' +
                ", salesDepartment='" + salesDepartment + '\'' +
                ", customerName='" + customerName + '\'' +
                ", salaryerNum=" + salaryerNum +
                ", salarySum='" + salarySum + '\'' +
                ", billType=" + billType +
                ", billProjectOne=" + billProjectOne +
                ", billProjectTwo=" + billProjectTwo +
                ", profitBudget='" + profitBudget + '\'' +
                ", eMail='" + eMail + '\'' +
                ", applyDate=" + applyDate +
                ", address='" + address + '\'' +
                ", telphone='" + telphone + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", bussinessCase='" + bussinessCase + '\'' +
                ", isCustomer=" + isCustomer +
                '}';
    }
}
