package com.bumu.arya.admin.corporation.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author CuiMengxin
 * @date 2016/4/29
 */
public class CorpModel {
	String corpId;

	String parentId;

	String checkinCode;

	String corpName;

	String corpShortName;

	String districtId;

	String districtName;

	String contactPhone;

	String contactName;

	String contactMail;

	String fax;

	String branCorpId;

	Integer status;

	String desc;

	Integer requirement;

	String corpLicenseCode;

	String address;

	String longitude;

	String latitude;

	MultipartFile corpLicenseFile;

	MultipartFile corpLogoFile;

	MultipartFile corpImageFile;

	String corpUserName;

	String corpPassword;

	Integer corpType;

	String operateUserId;

	int isGroup;

	Integer businessType;

	String welfareCorpName;

	int isHumanPoolProject;

	Integer salarySmsHours = 48;

	public CorpModel() {
	}

	public int getIsHumanPoolProject() {
		return isHumanPoolProject;
	}

	public void setIsHumanPoolProject(int isHumanPoolProject) {
		this.isHumanPoolProject = isHumanPoolProject;
	}

	public String getWelfareCorpName() {
		return welfareCorpName;
	}

	public void setWelfareCorpName(String welfareCorpName) {
		this.welfareCorpName = welfareCorpName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(int isGroup) {
		this.isGroup = isGroup;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getCorpType() {
		return corpType;
	}

	public void setCorpType(Integer corpType) {
		this.corpType = corpType;
	}

	public MultipartFile getCorpImageFile() {
		return corpImageFile;
	}

	public void setCorpImageFile(MultipartFile corpImageFile) {
		this.corpImageFile = corpImageFile;
	}

	public String getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getCheckinCode() {
		return checkinCode;
	}

	public void setCheckinCode(String checkinCode) {
		this.checkinCode = checkinCode;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBranCorpId() {
		return branCorpId;
	}

	public void setBranCorpId(String branCorpId) {
		this.branCorpId = branCorpId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpShortName() {
		return corpShortName;
	}

	public void setCorpShortName(String corpShortName) {
		this.corpShortName = corpShortName;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getRequirement() {
		return requirement;
	}

	public void setRequirement(Integer requirement) {
		this.requirement = requirement;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public MultipartFile getCorpLogoFile() {
		return corpLogoFile;
	}

	public void setCorpLogoFile(MultipartFile corpLogoFile) {
		this.corpLogoFile = corpLogoFile;
	}

	public String getCorpUserName() {
		return corpUserName;
	}

	public void setCorpUserName(String corpUserName) {
		this.corpUserName = corpUserName;
	}

	public String getCorpPassword() {
		return corpPassword;
	}

	public void setCorpPassword(String corpPassword) {
		this.corpPassword = corpPassword;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Integer getSalarySmsHours() {
		return salarySmsHours;
	}

	public void setSalarySmsHours(Integer salarySmsHours) {
		this.salarySmsHours = salarySmsHours;
	}

	public CorpModel initEntryModel(String corpId, String parentId, String districtId, String contactMail, String checkinCode, String desc,
									int type, String address, String longitude, String latitude, MultipartFile corpLogoFile, MultipartFile corpImageFile, Integer salarySmsHours){
		CorpModel corpModel = new CorpModel();
		corpModel.setCorpId(corpId);
		corpModel.setParentId(parentId);
		corpModel.setDistrictId(districtId);
		corpModel.setContactMail(contactMail);
		corpModel.setCheckinCode(checkinCode);
		corpModel.setDesc(desc);
		corpModel.setCorpType(type);
		corpModel.setAddress(address);
		corpModel.setLongitude(longitude);
		corpModel.setLatitude(latitude);
		corpModel.setCorpLogoFile(corpLogoFile);
		corpModel.setCorpImageFile(corpImageFile);
		if (null != salarySmsHours && salarySmsHours > 0) {
			corpModel.setSalarySmsHours(salarySmsHours);
		}

		return corpModel;
	}
}
