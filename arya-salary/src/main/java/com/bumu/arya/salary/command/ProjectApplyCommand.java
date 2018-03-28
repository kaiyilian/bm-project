package com.bumu.arya.salary.command;

import com.bumu.arya.Utils;
import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.model.entity.ProjectApplyEntity;
import com.bumu.common.SessionInfo;
import com.bumu.function.EntityConverter;
import com.bumu.function.VoConverterFunction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class ProjectApplyCommand implements EntityConverter<ProjectApplyEntity>,VoConverterFunction.Add<ProjectApplyEntity, SessionInfo> {

    /**
     * 销售人员
     */
    @ApiModelProperty(value = "销售人员", name = "salesMan")
    @NotBlank(message = "销售人员必填")
    private String salesMan;

    /**
     * 销售部门
     */
    @ApiModelProperty(value = "销售部门", name = "salesDepartment")
    @NotBlank(message = "销售部门必填")
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
    @NotBlank(message = "薪资总额必填")
    private String salarySum;

    /**
     * 发票类型
     */
    @ApiModelProperty(value = "发票类型 0全额 1差额", name = "billType")
    private SalaryEnum.BillType billType;

    /**
     * 发票项目1
     */
    @ApiModelProperty(value = "发票项目1", name = "billProjectOne")
    private SalaryEnum.BillProject billProjectOne;

    /**
     * 发票项目2
     */
    @ApiModelProperty(value = "发票项目2", name = "billProjectTwo")
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
    @NotBlank(message = "eMail")
    private String eMail;

    /**
     * 申请日期
     */
    @ApiModelProperty(value = "申请日期", name = "applyDate")
    @NotNull(message = "申请日期必填")
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

    @ApiModelProperty(value = "跟踪记录", name = "followInfo")
    private String followInfo;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(String followInfo) {
        this.followInfo = followInfo;
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

    @Override
    public void begin(ProjectApplyEntity projectApplyEntity, SessionInfo info) {
        // 创建的立项申请 默认正是状态标志为0
        projectApplyEntity.setIsCustomer(0);
        projectApplyEntity.setId(Utils.makeUUID());
        projectApplyEntity.setCreateTime(System.currentTimeMillis());
        projectApplyEntity.setCreateUser(info.getUserId());
    }

    @Override
    public String toString() {
        return "ProjectApplyCommand{" +
                "salesMan='" + salesMan + '\'' +
                ", salesDepartment='" + salesDepartment + '\'' +
                ", customerName='" + customerName + '\'' +
                ", salaryerNum=" + salaryerNum +
                ", salarySum=" + salarySum +
                ", billType=" + billType +
                ", billProjectOne=" + billProjectOne +
                ", billProjectTwo=" + billProjectTwo +
                ", profitBudget=" + profitBudget +
                ", eMail='" + eMail + '\'' +
                ", applyDate=" + applyDate +
                ", address='" + address + '\'' +
                ", telphone='" + telphone + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", bussinessCase='" + bussinessCase + '\'' +
                '}';
    }
}
