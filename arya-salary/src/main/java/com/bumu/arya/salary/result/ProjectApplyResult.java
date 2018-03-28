package com.bumu.arya.salary.result;

import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.model.entity.ProjectApplyEntity;
import com.bumu.function.ResultConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/3
 */
@ApiModel
public class ProjectApplyResult implements ResultConverter<ProjectApplyEntity> {

    private String id;

    /**
     * 销售人员
     */
    @ApiModelProperty(value = "销售人员")
    private String salesMan;

    /**
     * 销售部门
     */
    @ApiModelProperty(value = "销售部门")
    private String salesDepartment;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 客户简称
     */
    @ApiModelProperty(value = "客户简称")
    private String shortName;

    /**
     * 薪资人数
     */
    @ApiModelProperty(value = "薪资人数")
    private Integer salaryerNum;

    /**
     * 薪资总额
     */
    @ApiModelProperty(value = "薪资总额")
    private String salarySum;

    /**
     * 发票类型
     */
    @ApiModelProperty(value = "发票类型 0全额 1差额")
    private SalaryEnum.BillType billType;

    /**
     * 发票项目1 （0：工资 1：劳务费 2：管理费 3：服务费 4：个税 5：其他）
     */
    @ApiModelProperty(value = "发票项目1")
    private SalaryEnum.BillProject billProjectOne;

    public String getSalarySum() {
        return salarySum;
    }

    public void setSalarySum(String salarySum) {
        this.salarySum = salarySum;
    }

    public void setProfitBudget(String profitBudget) {
        this.profitBudget = profitBudget;
    }

    /**
     * 发票项目2（0：工资 1：劳务费 2：管理费 3：服务费 4：个税 5：其他）
     */

    @ApiModelProperty(value = "发票项目2")
    private SalaryEnum.BillProject billProjectTwo;

    /**
     * 利润预算
     */
    @ApiModelProperty(value = "利润预算")
    private String profitBudget;

    /**
     * email
     */
    @ApiModelProperty(value = "email")
    private String eMail;

    /**
     * 申请日期
     */
    @ApiModelProperty(value = "申请日期")
    private Long applyDate;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String telphone;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String linkMan;

    /**
     * 运营方案
     */
    @ApiModelProperty(value = "运营方案")
    private String bussinessCase;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    public String getId() {
        return id;
    }

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

    public SalaryEnum.BillType getBillType() {
        return billType;
    }

    public void setBillType(SalaryEnum.BillType billType) {
        this.billType = billType;
    }

    public String getProfitBudget() {
        return profitBudget;
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

}

