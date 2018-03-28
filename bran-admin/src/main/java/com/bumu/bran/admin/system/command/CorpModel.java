package com.bumu.bran.admin.system.command;

import com.bumu.bran.common.command.TxVersionCommand;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * majun
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CorpModel extends TxVersionCommand {

	String corpId;
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
	String corpUserName;
	String corpPassword;
	Integer corpType;
	String operateUserId;
	private Long month;

	private IdVersion[] ids;
	private String name;

	private String password;

	private String orginalPassword;

	private String confirmPassword;

	private String id;

	private Integer page;

	private Integer page_size;

	private String keyword;

	// 推送用
	@JsonIgnore
	private List<String> aryaUserIds;

	public CorpModel() {
	}

	public Integer getCorpType() {
		return corpType;
	}

	public void setCorpType(Integer corpType) {
		this.corpType = corpType;
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

	public String getCorpLicenseCode() {
		return corpLicenseCode;
	}

	public void setCorpLicenseCode(String corpLicenseCode) {
		this.corpLicenseCode = corpLicenseCode;
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

	public MultipartFile getCorpLicenseFile() {
		return corpLicenseFile;
	}

	public void setCorpLicenseFile(MultipartFile corpLicenseFile) {
		this.corpLicenseFile = corpLicenseFile;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrginalPassword() {
		return orginalPassword;
	}

	public void setOrginalPassword(String orginalPassword) {
		this.orginalPassword = orginalPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPage_size() {
		return page_size;
	}

	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}

	public IdVersion[] getIds() {
		return ids;
	}

	public void setIds(IdVersion[] ids) {
		this.ids = ids;
	}

	public List<String> getAryaUserIds() {
		return aryaUserIds;
	}

	public void setAryaUserIds(List<String> aryaUserIds) {
		this.aryaUserIds = aryaUserIds;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getMonth() {
		return month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}
}
