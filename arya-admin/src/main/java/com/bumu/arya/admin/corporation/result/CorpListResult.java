package com.bumu.arya.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2016/3/28
 */

public class CorpListResult extends ArrayList<CorpListResult.CorpInfo> {

	public CorpListResult() {
	}

	public static class CorpInfo {
		String id;

		@JsonProperty("bran_corp_id")
		String branCorpId;

		String name;

		String district;

		@JsonProperty("district_id")
		String districtId;

		@JsonProperty("short_name")
		String shortName;

		//企业类型
		@JsonProperty("is_group")
		int isGroup;

		/**
		 * 联系人
		 */
		@JsonProperty("contact_name")
		String contactName;

		@JsonProperty("contact_phone")
		String contactPhone;

		@JsonProperty("contact_mail")
		String mail;

		int status;

		String desc;

		@JsonProperty("soin_person_count")
		int soinPersonCount;

		String requirement;

		@JsonProperty("checkin_code")
		String checkinCode;

		@JsonProperty("is_checkin_code_temporary")
		Boolean checkinCodeTemporary;

		String address;

		@JsonProperty("license_code")
		String licenseCode;

		String longitude;

		String latitude;

		@JsonProperty("create_time")
		String createTime;

		@JsonProperty("logo_url")
		String logoUrl;

		@JsonProperty("license_url")
		String licenseUrl;

		@JsonProperty("corp_image_url")
		String corpImageUrl;

		@JsonProperty("business_type")
		int businessType;

		@JsonProperty("enterprise_nature")
		int enterpriseNature;

		@JsonProperty("welfare_corp_name")
		String welfareCorpName;

		@JsonProperty("is_human_pool_project")
		int isHumanPoolProject;

		public int getIsHumanPoolProject() {
			return isHumanPoolProject;
		}

		public void setIsHumanPoolProject(int isHumanPoolProject) {
			this.isHumanPoolProject = isHumanPoolProject;
		}

		public CorpInfo() {
		}

		public String getWelfareCorpName() {
			return welfareCorpName;
		}

		public void setWelfareCorpName(String welfareCorpName) {
			this.welfareCorpName = welfareCorpName;
		}

		public String getCorpImageUrl() {
			return corpImageUrl;
		}

		public void setCorpImageUrl(String corpImageUrl) {
			this.corpImageUrl = corpImageUrl;
		}

		public int getEnterpriseNature() {
			return enterpriseNature;
		}

		public void setEnterpriseNature(int enterpriseNature) {
			this.enterpriseNature = enterpriseNature;
		}

		public int getBusinessType() {
			return businessType;
		}

		public void setBusinessType(int businessType) {
			this.businessType = businessType;
		}

		public int getIsGroup() {
			return isGroup;
		}

		public void setIsGroup(int isGroup) {
			this.isGroup = isGroup;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			if (StringUtils.isAnyBlank(desc)) {
				return;
			}
			this.desc = desc;
		}

		public Boolean getCheckinCodeTemporary() {
			return checkinCodeTemporary;
		}

		public void setCheckinCodeTemporary(Boolean checkinCodeTemporary) {
			this.checkinCodeTemporary = checkinCodeTemporary;
		}

		public String getLogoUrl() {
			return logoUrl;
		}

		public void setLogoUrl(String logoUrl) {
			this.logoUrl = logoUrl;
		}

		public String getLicenseUrl() {
			return licenseUrl;
		}

		public void setLicenseUrl(String licenseUrl) {
			this.licenseUrl = licenseUrl;
		}

		public String getBranCorpId() {
			return branCorpId;
		}

		public void setBranCorpId(String branCorpId) {
			this.branCorpId = branCorpId;
		}

		public String getCheckinCode() {
			return checkinCode;
		}

		public void setCheckinCode(String checkinCode) {
			this.checkinCode = checkinCode;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			if (StringUtils.isAnyBlank(address)) {
				return;
			}
			this.address = address;
		}

		public String getLicenseCode() {
			return licenseCode;
		}

		public void setLicenseCode(String licenseCode) {
			if (StringUtils.isAnyBlank(licenseCode)) {
				return;
			}
			this.licenseCode = licenseCode;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			if (StringUtils.isAnyBlank(longitude)) {
				return;
			}
			this.longitude = longitude;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			if (StringUtils.isAnyBlank(latitude)) {
				return;
			}
			this.latitude = latitude;
		}

		public int getSoinPersonCount() {
			return soinPersonCount;
		}

		public void setSoinPersonCount(int soinPersonCount) {
			this.soinPersonCount = soinPersonCount;
		}

		public String getRequirement() {
			return requirement;
		}

		public String getDistrictId() {
			return districtId;
		}

		public void setDistrictId(String districtId) {
			this.districtId = districtId;
		}

		public void setRequirement(String requirement) {
			this.requirement = requirement;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getDistrict() {
			return district;
		}

		public void setDistrict(String district) {
			if (StringUtils.isAnyBlank(district)) {
				return;
			}
			this.district = district;
		}

		public String getShortName() {
			return shortName;
		}

		public void setShortName(String shortName) {
			if (StringUtils.isAnyBlank(shortName)) {
				return;
			}
			this.shortName = shortName;
		}

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			if (StringUtils.isAnyBlank(contactName)) {
				return;
			}
			this.contactName = contactName;
		}

		public String getContactPhone() {
			return contactPhone;
		}

		public void setContactPhone(String contactPhone) {
			if (StringUtils.isAnyBlank(contactPhone)) {
				return;
			}
			this.contactPhone = contactPhone;
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			if (StringUtils.isAnyBlank(mail)) {
				return;
			}
			this.mail = mail;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
