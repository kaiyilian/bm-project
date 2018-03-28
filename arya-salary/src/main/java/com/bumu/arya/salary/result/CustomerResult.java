package com.bumu.arya.salary.result;

import com.bumu.arya.salary.common.SalaryEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/3
 */
@ApiModel
public class CustomerResult {

    @ApiModelProperty(value = "客户ID")
    private String id;

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

    @ApiModelProperty(value = "销售人员")
    private String salesMan;

    @ApiModelProperty(value = "销售部门")
    private String salesDepartment;

    @ApiModelProperty(value = "薪资人数")
    private Integer salaryerNum;

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
    @ApiModelProperty(value = "eMail")
    private String eMail;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "申请日期")
    private Long applyDate;

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

    @ApiModelProperty(value = "运营方案")
    private String bussinessCase;

    @ApiModelProperty(value = "用户余额")
    private String remainAccount;

    /**
     * 合同日期
     */
    @ApiModelProperty(value = "合同日期开始")
    private Long contractDateStart;

    @ApiModelProperty(value = "合同日期结束")
    private Long contractDateEnd;

    @ApiModelProperty(value = "合同地址")
    private String contractUrl;

    @ApiModelProperty(value = "合同路径")
    private String contractDir;

    @ApiModelProperty(value = "备注")
    private String remark;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getContractDir() {
        return contractDir;
    }

    public void setContractDir(String contractDir) {
        this.contractDir = contractDir;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getContractDateStart() {
        return contractDateStart;
    }

    public void setContractDateStart(Long contractDateStart) {
        this.contractDateStart = contractDateStart;
    }

    public Long getContractDateEnd() {
        return contractDateEnd;
    }

    public void setContractDateEnd(Long contractDateEnd) {
        this.contractDateEnd = contractDateEnd;
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

    public String getRemainAccount() {
        return remainAccount;
    }

    public void setRemainAccount(String remainAccount) {
        this.remainAccount = remainAccount;
    }
}

