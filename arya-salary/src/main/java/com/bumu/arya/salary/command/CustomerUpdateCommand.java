package com.bumu.arya.salary.command;

import com.bumu.arya.salary.common.SalaryEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class CustomerUpdateCommand {

    @ApiModelProperty(value = "客户ID", name = "id")
    @NotBlank
    private String id;

    @ApiModelProperty(value = "销售人员", name="salesMan")
    private String salesMan;

    @ApiModelProperty(value = "销售部门", name = "salesDepartment")
    private String salesDepartment;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称", name = "customerName")
    @NotBlank(message = "客户名称必填")
    private String customerName;

    /**
     * 客户简称
     */
    @ApiModelProperty(value = "客户简称", name = "shortName")
    @NotBlank(message = "客户简称必填")
    private String shortName;

    /**
     * 薪资人数
     */
    @ApiModelProperty(value = "薪资人数", name = "salaryerNum")
    @NotNull(message = "薪资人数必填")
    private Integer salaryerNum;

    /**
     * 薪资总额
     */
    @ApiModelProperty(value = "薪资总额", name = "salarySum")
    @NotBlank(message = "薪资总额")
    private String salarySum;

    /**
     * 发票类型
     */
    @ApiModelProperty(value = "发票类型 0全额 1差额", name = "billType")
    @NotNull(message = "薪资总额必填")
    private SalaryEnum.BillType billType;

    /**
     * 发票项目1
     */
    @ApiModelProperty(value = "发票项目1", name = "billProjectOne")
    @NotNull(message = "发票项目1必填")
    private SalaryEnum.BillProject billProjectOne;

    /**
     * 发票项目2
     */
    @ApiModelProperty(value = "发票项目2", name = "billProjectTwo")
    @NotNull(message = "发票项目2必填")
    private SalaryEnum.BillProject billProjectTwo;

    /**
     * 利润预算
     */
    @ApiModelProperty(value = "利润预算", name = "profitBudget")
    @NotBlank(message = "利润预算必填")
    private String profitBudget;

    /**
     * email
     */
    @ApiModelProperty(value = "eMail", name = "eMail")
    @NotBlank(message = "eMail必填")
    private String eMail;

    /**
     * 申请日期
     */
    @ApiModelProperty(value = "eMail", name = "eMail")
    @NotNull(message = "eMail必填")
    private Long applyDate;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址", name = "address")
    @NotBlank(message = "地址必填")
    private String address;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", name = "telphone")
    @NotBlank(message = "联系电话必填")
    private String telphone;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人", name = "linkMan")
    @NotBlank(message = "联系人必填")
    private String linkMan;

    /**
     * 运营方案
     */
    @ApiModelProperty(value = "运营方案", name = "bussinessCase")
    @NotBlank(message = "运营方案必填")
    private String bussinessCase;

    @ApiModelProperty(value = "合同期限开始", name = "contarctDateStart")
    @NotNull(message = "合同期限开始必填")
    private Long contarctDateStart;

    @ApiModelProperty(value = "合同期限结束", name = "contractDateEnd")
    @NotNull(message = "合同期限结束必填")
    private Long contractDateEnd;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    public String getProfitBudget() {
        return profitBudget;
    }

    public void setProfitBudget(String profitBudget) {
        this.profitBudget = profitBudget;
    }

    public Long getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Long applyDate) {
        this.applyDate = applyDate;
    }

    public String getBussinessCase() {
        return bussinessCase;
    }

    public void setBussinessCase(String bussinessCase) {
        this.bussinessCase = bussinessCase;
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

    public Long getContarctDateStart() {
        return contarctDateStart;
    }

    public void setContarctDateStart(Long contarctDateStart) {
        this.contarctDateStart = contarctDateStart;
    }

    public Long getContractDateEnd() {
        return contractDateEnd;
    }

    public void setContractDateEnd(Long contractDateEnd) {
        this.contractDateEnd = contractDateEnd;
    }

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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
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
}
