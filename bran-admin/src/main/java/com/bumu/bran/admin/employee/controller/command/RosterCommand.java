package com.bumu.bran.admin.employee.controller.command;

import com.bumu.bran.employee.command.ModelCommand;
import com.bumu.bran.employee.result.UserDefinedDetailsResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * majun
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel
public class RosterCommand extends ModelCommand {

    @ApiModelProperty(value = "注册账号:手机账号 花名册导入用", example = "18020262367")
    private String registerAccount;
    @ApiModelProperty(value = "姓名", example = "马俊")
    private String realName;
    @ApiModelProperty(value = "身份证号")
    private String idCardNo;
    @ApiModelProperty(value = "工号前缀id")
    private String workSnPrefixId;
    @ApiModelProperty(value = "工号前缀名字")
    private String prefixName;
    @ApiModelProperty(value = "工号")
    private String workSn;
    @ApiModelProperty(value = "部门id")
    private String departmentId;
    @ApiModelProperty(value = "部门")
    private String departmentName;
    @ApiModelProperty(value = "职位id")
    private String positionId;
    @ApiModelProperty(value = "职位")
    private String positionName;
    @ApiModelProperty(value = "班组id")
    private String workShiftId;
    @ApiModelProperty(value = "班组")
    private String workShiftName;
    @ApiModelProperty(value = "工段id")
    private String workLineId;
    @ApiModelProperty(value = "工段")
    private String workLineName;
    @ApiModelProperty(value = "性别")
    private Integer sex;
    @ApiModelProperty(value = "婚姻状况")
    private Integer marriage;
    @ApiModelProperty(value = "民族")
    private String nation;
    @ApiModelProperty(value = "户籍地")
    private String idcardAddress;
    @ApiModelProperty(value = "联系方式")
    private String telephone;
    @ApiModelProperty(value = "现居地省份id")
    private String provinceId;
    @ApiModelProperty(value = "现居地省份")
    private String provinceName;
    @ApiModelProperty(value = "现居地城市id")
    private String cityId;
    @ApiModelProperty(value = "现居地城市")
    private String cityName;
    @ApiModelProperty(value = "现居地行政区id")
    private String countyId;
    @ApiModelProperty(value = "现居地行政区id")
    private String countyName;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "紧急联系人")
    private String urgentContact;
    @ApiModelProperty(value = "紧急联系人手机")
    private String urgentContactPhone;
    @ApiModelProperty(value = "班车点")
    private String busAddress;
    @ApiModelProperty(value = "面试日期")
    private Long interviewDate;
    @ApiModelProperty(value = "入职日期")
    private Long checkinTime;
    @ApiModelProperty(value = "合同开始时间")
    private Long startTime;
    @ApiModelProperty(value = "合同结束时间")
    private Long endTime;
    @ApiModelProperty(value = "是否无期限 0:否 1:是")
    private Integer isNolimit;
    @ApiModelProperty(value = "供应来源")
    private String sourceOfSupply;
    @ApiModelProperty(value = "员工性质")
    private String employeeNature;
    @ApiModelProperty(value = "开户行信息")
    private String bankAccount;
    @ApiModelProperty(value = "银行卡号")
    private String bankNum;
    @ApiModelProperty(value = "出生年月 时间戳")
    private Long bornDate;

    @ApiModelProperty(value = "政治面貌 0:党员 1:团员 2:群众")
    private Integer politicalStatus;

    @ApiModelProperty(value = "社保类型")
    private String socialSecurityType;

    @ApiModelProperty(value = "文化程度 0:小学 1:初中 2:高中 3: 中专 4:大专 5:本科 6:大专 7:本科 8:硕士 9:博士")
    private Integer degreeOfEducation;

    @ApiModelProperty(value = "毕业院校")
    private String graduatedSchool;

    @ApiModelProperty(value = "专业类别")
    private String professionalCategory;

    @ApiModelProperty(value = "毕业时间")
    private Long graduationTime = 0L;

    @ApiModelProperty(value = "身份证有效期-开始时间")
    private Long expireStartTime;

    @ApiModelProperty(value = "身份证有效期-结束时间")
    private Long expireEndTime;

    @ApiModelProperty(value = "身份证有效期-结束时间-是否是长期有效")
    private Integer isLongTerm;

    @ApiModelProperty(name = "userDefined_details", value = "自定义列")
    @JsonProperty(value = "userDefined_details")
    private List<UserDefinedDetailsResult> userDefinedDetailsResultList;

    public String getRegisterAccount() {
        return registerAccount;
    }

    public void setRegisterAccount(String registerAccount) {
        this.registerAccount = registerAccount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getWorkSnPrefixId() {
        return workSnPrefixId;
    }

    public void setWorkSnPrefixId(String workSnPrefixId) {
        this.workSnPrefixId = workSnPrefixId;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    public String getWorkSn() {
        return workSn;
    }

    public void setWorkSn(String workSn) {
        this.workSn = workSn;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getWorkShiftId() {
        return workShiftId;
    }

    public void setWorkShiftId(String workShiftId) {
        this.workShiftId = workShiftId;
    }

    public String getWorkShiftName() {
        return workShiftName;
    }

    public void setWorkShiftName(String workShiftName) {
        this.workShiftName = workShiftName;
    }

    public String getWorkLineId() {
        return workLineId;
    }

    public void setWorkLineId(String workLineId) {
        this.workLineId = workLineId;
    }

    public String getWorkLineName() {
        return workLineName;
    }

    public void setWorkLineName(String workLineName) {
        this.workLineName = workLineName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getMarriage() {
        return marriage;
    }

    public void setMarriage(Integer marriage) {
        this.marriage = marriage;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getIdcardAddress() {
        return idcardAddress;
    }

    public void setIdcardAddress(String idcardAddress) {
        this.idcardAddress = idcardAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrgentContact() {
        return urgentContact;
    }

    public void setUrgentContact(String urgentContact) {
        this.urgentContact = urgentContact;
    }

    public String getUrgentContactPhone() {
        return urgentContactPhone;
    }

    public void setUrgentContactPhone(String urgentContactPhone) {
        this.urgentContactPhone = urgentContactPhone;
    }

    public String getBusAddress() {
        return busAddress;
    }

    public void setBusAddress(String busAddress) {
        this.busAddress = busAddress;
    }

    public Long getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Long interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Long getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Long checkinTime) {
        this.checkinTime = checkinTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        if(endTime == null){
            return 0L;
        }
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getIsNolimit() {
        if(isNolimit == null){
            return 0;
        }
        return isNolimit;
    }

    public void setIsNolimit(Integer isNolimit) {
        this.isNolimit = isNolimit;
    }

    public String getSourceOfSupply() {
        return sourceOfSupply;
    }

    public void setSourceOfSupply(String sourceOfSupply) {
        this.sourceOfSupply = sourceOfSupply;
    }

    public String getEmployeeNature() {
        return employeeNature;
    }

    public void setEmployeeNature(String employeeNature) {
        this.employeeNature = employeeNature;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public List<UserDefinedDetailsResult> getUserDefinedDetailsResultList() {
        return userDefinedDetailsResultList;
    }

    public void setUserDefinedDetailsResultList(List<UserDefinedDetailsResult> userDefinedDetailsResultList) {
        this.userDefinedDetailsResultList = userDefinedDetailsResultList;
    }

    public Long getBornDate() {
        return bornDate;
    }

    public void setBornDate(Long bornDate) {
        this.bornDate = bornDate;
    }

    public Integer getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(Integer politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getSocialSecurityType() {
        return socialSecurityType;
    }

    public void setSocialSecurityType(String socialSecurityType) {
        this.socialSecurityType = socialSecurityType;
    }

    public Integer getDegreeOfEducation() {
        return degreeOfEducation;
    }

    public void setDegreeOfEducation(Integer degreeOfEducation) {
        this.degreeOfEducation = degreeOfEducation;
    }

    public String getGraduatedSchool() {
        return graduatedSchool;
    }

    public void setGraduatedSchool(String graduatedSchool) {
        this.graduatedSchool = graduatedSchool;
    }

    public String getProfessionalCategory() {
        return professionalCategory;
    }

    public void setProfessionalCategory(String professionalCategory) {
        this.professionalCategory = professionalCategory;
    }

    public Long getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(Long graduationTime) {
        this.graduationTime = graduationTime;
    }

    public Long getExpireStartTime() {
        return expireStartTime;
    }

    public void setExpireStartTime(Long expireStartTime) {
        this.expireStartTime = expireStartTime;
    }

    public Long getExpireEndTime() {
        return expireEndTime;
    }

    public void setExpireEndTime(Long expireEndTime) {
        this.expireEndTime = expireEndTime;
    }

    public Integer getIsLongTerm() {
        if(isLongTerm == null){
            return 0;
        }
        return isLongTerm;
    }

    public void setIsLongTerm(Integer isLongTerm) {
        this.isLongTerm = isLongTerm;
    }
}
