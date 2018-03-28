package com.bumu.bran.admin.employee.result;

import com.bumu.common.result.TxVersionResult;
import com.bumu.employee.model.entity.EmployeeEntity.WorkAttendanceAddState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author CuiMengxin
 * @date 2016/5/12
 */
@ApiModel
public class EmployeeListResult extends ArrayList<EmployeeListResult.EmployeeResult> {

    @ApiModel
    public static class EmployeeResult extends TxVersionResult {

        @JsonProperty("profile_progress")
        @ApiModelProperty
        private String profileProgress;

        @JsonProperty("is_profile_complete")
        @ApiModelProperty
        private Integer isProfileComplete;

        @JsonProperty("is_offer_accept")
        @ApiModelProperty
        private Integer isOfferAccept;

        @JsonProperty("employee_id")
        @ApiModelProperty(value = "员工id")
        private String employeeId;

        @ApiModelProperty(value = "工号")
        @JsonProperty("work_sn")
        private String workSn;

        @ApiModelProperty(value = "姓名")
        private String name;

        @ApiModelProperty(value = "职位id")
        @JsonProperty("position_id")
        private String positionId;

        @ApiModelProperty(value = "职位名称")
        @JsonProperty("position_name")
        private String positionName;

        @ApiModelProperty(value = "班组id")
        @JsonProperty("work_shift_id")
        private String workShiftId;

        @ApiModelProperty(value = "班组名字")
        @JsonProperty("work_shift_name")
        private String workShiftName;

        @ApiModelProperty(value = "工段id")
        @JsonProperty("work_line_id")
        private String workLineId;

        @ApiModelProperty(value = "工段名字")
        @JsonProperty("work_line_name")
        private String workLineName;

        @ApiModelProperty(value = "部门id")
        @JsonProperty("department_id")
        private String departmentId;

        @ApiModelProperty(value = "部门名字")
        @JsonProperty("department_name")
        private String departmentName;

        @ApiModelProperty(value = "身份证号")
        private String idCardNo;

        @ApiModelProperty(value = "性别")
        private Integer sex;

        @ApiModelProperty(value = "出生年月 时间戳")
        private Long bornDate;

        @ApiModelProperty(value = "婚姻状况")
        private Integer marriage;

        @ApiModelProperty(value = "户籍地")
        private String idcardAddress;

        @ApiModelProperty(value = "联系方式")
        private String telephone;

        @ApiModelProperty(value = "居住地 详细地址")
        private String address;

        @ApiModelProperty(value = "紧急联系人")
        private String urgentContact;

        @ApiModelProperty(value = "紧急联系人手机")
        private String urgentContactPhone;

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

        @JsonProperty("contract_start_time")
        @ApiModelProperty(value = "合同开始时间")
        private Long contractStartTime;

        @ApiModelProperty(value = "合同结束时间")
        @JsonProperty("contract_end_time")
        private Long contractEndTime;

        @JsonProperty("check_in_time")
        @ApiModelProperty(value = "入职时间")
        private Long checkinTime;

        @JsonProperty("attendance_no")
        @ApiModelProperty(value = "打卡号")
        private Long workAttendanceNo;

        @JsonProperty("attendance_add_state")
        @ApiModelProperty(value = "打卡状态")
        private WorkAttendanceAddState workAttendanceAddState;

        @JsonProperty("face_match")
        private Integer faceMatch;

        @JsonProperty("record_exceed")
        @ApiModelProperty(value = "不良记录上限次数")
        private int recordExceed;

        @JsonProperty("exam_id")
        @JsonIgnore
        private String exam_id;

        /**
         * 来源
         */
        @JsonProperty("create_type")
        @ApiModelProperty(value = "1:员工添加  2:企业添加  ",example = "18020262367")
        private Integer createType;

        /**
         * 注册账号:手机账号 花名册导入用
         */
        @ApiModelProperty(value = "注册账号:手机账号 花名册导入用",example = "18020262367")
        private String registerAccount;

        /**
         * 是否使用企业入职码绑定
         */
        @ApiModelProperty(value = "是否使用企业入职码绑定",example = "0:未绑定 1:绑定")
        private Integer isBinding;

        @ApiModelProperty(value = "入职状态 0:未扫码 1:填写资料中 2:填写资料完成 3:待审核")
        private Integer entryStatus = 0;

        @JsonProperty("phone_no")
        private String phoneNo;

        @JsonProperty("leave_time")
        private Long leaveTime;

        @ApiModelProperty(value = "民族")
        private String nation;

        @ApiModelProperty(value = "身份证有效期-开始时间")
        private Long expireStartTime;

        @ApiModelProperty(value = "身份证有效期-结束时间")
        private Long expireEndTime;

        @ApiModelProperty(value = "身份证有效期-结束时间-是否是长期有效")
        private Integer isLongTerm;

        public EmployeeResult() {
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getWorkSn() {
            return workSn;
        }

        public void setWorkSn(String workSn) {
            this.workSn = workSn;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public Long getBornDate() {
            return bornDate;
        }

        public void setBornDate(Long bornDate) {
            this.bornDate = bornDate;
        }

        public Integer getMarriage() {
            return marriage;
        }

        public void setMarriage(Integer marriage) {
            this.marriage = marriage;
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

        public Long getContractStartTime() {
            return contractStartTime;
        }

        public void setContractStartTime(Long contractStartTime) {
            this.contractStartTime = contractStartTime;
        }

        public Long getContractEndTime() {
            return contractEndTime;
        }

        public void setContractEndTime(Long contractEndTime) {
            this.contractEndTime = contractEndTime;
        }

        public Long getCheckinTime() {
            return checkinTime;
        }

        public void setCheckinTime(Long checkinTime) {
            this.checkinTime = checkinTime;
        }

        public Long getWorkAttendanceNo() {
            return workAttendanceNo;
        }

        public void setWorkAttendanceNo(Long workAttendanceNo) {
            this.workAttendanceNo = workAttendanceNo;
        }

        public WorkAttendanceAddState getWorkAttendanceAddState() {
            return workAttendanceAddState;
        }

        public void setWorkAttendanceAddState(WorkAttendanceAddState workAttendanceAddState) {
            this.workAttendanceAddState = workAttendanceAddState;
        }

        public String getProfileProgress() {
            return profileProgress;
        }

        public void setProfileProgress(String profileProgress) {
            this.profileProgress = profileProgress;
        }

        public Integer getIsProfileComplete() {
            return isProfileComplete;
        }

        public void setIsProfileComplete(Integer isProfileComplete) {
            this.isProfileComplete = isProfileComplete;
        }

        public Integer getIsOfferAccept() {
            return isOfferAccept;
        }

        public void setIsOfferAccept(Integer isOfferAccept) {
            this.isOfferAccept = isOfferAccept;
        }

        public Integer getFaceMatch() {
            return faceMatch;
        }

        public void setFaceMatch(Integer faceMatch) {
            this.faceMatch = faceMatch;
        }

        public int getRecordExceed() {
            return recordExceed;
        }

        public void setRecordExceed(int recordExceed) {
            this.recordExceed = recordExceed;
        }

        public String getExam_id() {
            return exam_id;
        }

        public void setExam_id(String exam_id) {
            this.exam_id = exam_id;
        }

        public Integer getCreateType() {
            return createType;
        }

        public void setCreateType(Integer createType) {
            this.createType = createType;
        }

        public String getRegisterAccount() {
            return registerAccount;
        }

        public void setRegisterAccount(String registerAccount) {
            this.registerAccount = registerAccount;
        }

        public Integer getIsBinding() {
            return isBinding;
        }

        public void setIsBinding(Integer isBinding) {
            this.isBinding = isBinding;
        }

        public Integer getEntryStatus() {
            return entryStatus;
        }

        public void setEntryStatus(Integer entryStatus) {
            this.entryStatus = entryStatus;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public Long getLeaveTime() {
            return leaveTime;
        }

        public void setLeaveTime(Long leaveTime) {
            this.leaveTime = leaveTime;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
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
            return isLongTerm;
        }

        public void setIsLongTerm(Integer isLongTerm) {
            this.isLongTerm = isLongTerm;
        }
    }
}
