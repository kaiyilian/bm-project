package com.bumu.arya.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by DaiAoXiang on 2017/4/27.
 */
@ApiModel
public class GetCorporationEntryDetailResult {
	@ApiModelProperty("公司id")
	String id;

	@ApiModelProperty("地区名称字符串'")
	String district;

	@ApiModelProperty("地区id字符串 用冒号:隔开")
	@JsonProperty("district_id")
	String districtId;

	@ApiModelProperty("企业联系人邮箱")
	@JsonProperty("contact_mail")
	String mail;

	@ApiModelProperty("描述")
	String desc;

	@ApiModelProperty("企业入职码")
	@JsonProperty("checkin_code")
	String checkinCode;

	@ApiModelProperty("企业详细地址")
	String address;

	@ApiModelProperty("企业营业执照编号")
	@JsonProperty("license_code")
	String licenseCode;

	@ApiModelProperty("经度")
	String longitude;

	@ApiModelProperty("纬度")
	String latitude;

	@ApiModelProperty("创建时间")
	@JsonProperty("create_time")
	String createTime;

	@ApiModelProperty("公司LOGOurl")
	@JsonProperty("logo_url")
	String logoUrl;

	@ApiModelProperty("营业执照url")
	@JsonProperty("license_url")
	String licenseUrl;

	@ApiModelProperty("公司图片url")
	@JsonProperty("corp_image_url")
	String corpImageUrl;

	@ApiModelProperty("公司性质")
	@JsonProperty("enterprise_nature")
	int enterpriseNature;

	@ApiModelProperty("薪资单短信通知时间")
	@JsonProperty("salarySmsHours")
	Integer salarySmsHours;

	public Integer getSalarySmsHours() {
		return salarySmsHours;
	}

	public void setSalarySmsHours(Integer salarySmsHours) {
		this.salarySmsHours = salarySmsHours;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
		this.address = address;
	}

	public String getLicenseCode() {
		return licenseCode;
	}

	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
}
