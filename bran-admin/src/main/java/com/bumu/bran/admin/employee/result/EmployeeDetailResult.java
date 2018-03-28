package com.bumu.bran.admin.employee.result;

import com.bumu.bran.employee.result.UserDefinedDetailsResult;
import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/5/17
 */
@ApiModel
public class EmployeeDetailResult extends TxVersionResult {

	@JsonProperty("profile_progress")
	String profileProgress;

	@JsonProperty("is_profile_complete")
	int isProfileComplete;

	EmployeeDetailProfileResult profile;

	List<EmployeeDetailCareerResult> career;

	EmployeeDetailEducationResult education;

	List<EmployeeDetailAttachmentResult> attachment;

	@JsonProperty("leave_reason")
	EmployeeDetailLeaveReasonResult leaveReason;

	// 自定义列
	private List<UserDefinedDetailsResult> userDefinedResults;

	public EmployeeDetailResult() {
	}

	public String getProfileProgress() {
		return profileProgress;
	}

	public void setProfileProgress(String profileProgress) {
		this.profileProgress = profileProgress;
	}

	public int getIsProfileComplete() {
		return isProfileComplete;
	}

	public void setIsProfileComplete(int isProfileComplete) {
		this.isProfileComplete = isProfileComplete;
	}

	public EmployeeDetailProfileResult getProfile() {
		return profile;
	}

	public void setProfile(EmployeeDetailProfileResult profile) {
		this.profile = profile;
	}

	public List<EmployeeDetailCareerResult> getCareer() {
		return career;
	}

	public void setCareer(List<EmployeeDetailCareerResult> career) {
		this.career = career;
	}

	public EmployeeDetailEducationResult getEducation() {
		return education;
	}

	public void setEducation(EmployeeDetailEducationResult education) {
		this.education = education;
	}

	public List<EmployeeDetailAttachmentResult> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<EmployeeDetailAttachmentResult> attachment) {
		this.attachment = attachment;
	}

	public EmployeeDetailLeaveReasonResult getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(EmployeeDetailLeaveReasonResult leaveReason) {
		this.leaveReason = leaveReason;
	}

	public List<UserDefinedDetailsResult> getUserDefinedResults() {
		return userDefinedResults;
	}

	public void setUserDefinedResults(List<UserDefinedDetailsResult> userDefinedResults) {
		this.userDefinedResults = userDefinedResults;
	}

	public static class EmployeeDetailProfileResult {

		String name;

		@JsonProperty("face_url")
		String faceUrl;

		@JsonProperty("work_sn")
		String workSn;

		String sex;

		Integer age;

		Long birthday;

		String marriage;

		String nation;

		@JsonProperty("idcard_no")
		String idcardNo;

		String address;

		@JsonProperty("origin_district")
		String originDistrict;

		@JsonProperty("phone_no")
		String phoneNo;

		String email;

		@JsonProperty("politics_status")
		String politicsStatus;

		@JsonProperty("urgent_contact")
		String urgentContact;

		@JsonProperty("urgent_contact_phone")
		String urgentContactPhone;
		// 银行卡账户信息
		private String bankAccount;
		// 银行卡卡号
		private String bankNum;
		// 班车点
		private String busAddress;
		// 面试日期
		private Long interviewDate;
		// 供应来源
		private String sourceOfSupply;
		// 员工性质
		private String employeeNature;

		public EmployeeDetailProfileResult() {
		}

		public String getFaceUrl() {
			return faceUrl;
		}

		public void setFaceUrl(String faceUrl) {
			this.faceUrl = faceUrl;
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

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public Long getBirthday() {
			return birthday;
		}

		public void setBirthday(Long birthday) {
			this.birthday = birthday;
		}

		public String getMarriage() {
			return marriage;
		}

		public void setMarriage(String marriage) {
			this.marriage = marriage;
		}

		public String getNation() {
			return nation;
		}

		public void setNation(String nation) {
			this.nation = nation;
		}

		public String getIdcardNo() {
			return idcardNo;
		}

		public void setIdcardNo(String idcardNo) {
			this.idcardNo = idcardNo;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getOriginDistrict() {
			return originDistrict;
		}

		public void setOriginDistrict(String originDistrict) {
			this.originDistrict = originDistrict;
		}

		public String getPhoneNo() {
			return phoneNo;
		}

		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPoliticsStatus() {
			return politicsStatus;
		}

		public void setPoliticsStatus(String politicsStatus) {
			this.politicsStatus = politicsStatus;
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
	}

	public static class EmployeeDetailCareerResult {
		@JsonProperty("start_time")
		long startTime;

		@JsonProperty("end_time")
		long endTime;

		@JsonProperty("work_time")
		String workTime;

		@JsonProperty("company_name")
		String companyName;

		@JsonProperty("company_introduce")
		String companyIntroduce;

		@JsonProperty("position_name")
		String positionName;

		@JsonProperty("department_name")
		String departmentName;

		@JsonProperty("industry_name")
		String industryName;

		public EmployeeDetailCareerResult() {
		}

		public long getStartTime() {
			return startTime;
		}

		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}

		public long getEndTime() {
			return endTime;
		}

		public void setEndTime(long endTime) {
			this.endTime = endTime;
		}

		public String getWorkTime() {
			return workTime;
		}

		public void setWorkTime(String workTime) {
			this.workTime = workTime;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getCompanyIntroduce() {
			return companyIntroduce;
		}

		public void setCompanyIntroduce(String companyIntroduce) {
			this.companyIntroduce = companyIntroduce;
		}

		public String getPositionName() {
			return positionName;
		}

		public void setPositionName(String positionName) {
			this.positionName = positionName;
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		public String getIndustryName() {
			return industryName;
		}

		public void setIndustryName(String industryName) {
			this.industryName = industryName;
		}
	}

	public static class EmployeeDetailEducationResult {
		@JsonProperty("start_time")
		long startTime;

		@JsonProperty("end_time")
		long endTime;

		@JsonProperty("school_name")
		String schoolName;

		@JsonProperty("major_name")
		String majorName;

		@JsonProperty("education_name")
		String educationName;

		public EmployeeDetailEducationResult() {
		}

		public long getStartTime() {
			return startTime;
		}

		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}

		public long getEndTime() {
			return endTime;
		}

		public void setEndTime(long endTime) {
			this.endTime = endTime;
		}

		public String getSchoolName() {
			return schoolName;
		}

		public void setSchoolName(String schoolName) {
			this.schoolName = schoolName;
		}

		public String getMajorName() {
			return majorName;
		}

		public void setMajorName(String majorName) {
			this.majorName = majorName;
		}

		public String getEducationName() {
			return educationName;
		}

		public void setEducationName(String educationName) {
			this.educationName = educationName;
		}
	}

	public static class EmployeeDetailAttachmentResult {

		@JsonProperty("idcard_face")
		String idcardFace;

		@JsonProperty("idcard_front")
		String idcardFront;

		@JsonProperty("idcard_back")
		String idcardBack;

		@JsonProperty("leave_cert")
		String leaveCert;

		@JsonProperty("education_cert")
		String educationCert;

		@JsonProperty("bank_card")
		private String bankCard;

		public EmployeeDetailAttachmentResult() {
		}

		public String getIdcardFace() {
			return idcardFace;
		}

		public void setIdcardFace(String idcardFace) {
			this.idcardFace = idcardFace;
		}

		public String getIdcardFront() {
			return idcardFront;
		}

		public void setIdcardFront(String idcardFront) {
			this.idcardFront = idcardFront;
		}

		public String getIdcardBack() {
			return idcardBack;
		}

		public void setIdcardBack(String idcardBack) {
			this.idcardBack = idcardBack;
		}

		public String getLeaveCert() {
			return leaveCert;
		}

		public void setLeaveCert(String leaveCert) {
			this.leaveCert = leaveCert;
		}

		public String getEducationCert() {
			return educationCert;
		}

		public void setEducationCert(String educationCert) {
			this.educationCert = educationCert;
		}

		public String getBankCard() {
			return bankCard;
		}

		public void setBankCard(String bankCard) {
			this.bankCard = bankCard;
		}
	}

	public static class EmployeeDetailLeaveReasonResult {

		String reason;

		@JsonProperty("leave_time")
		long leaveTime;

		String memo;

		public EmployeeDetailLeaveReasonResult() {
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public long getLeaveTime() {
			return leaveTime;
		}

		public void setLeaveTime(long leaveTime) {
			this.leaveTime = leaveTime;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}
	}
}
