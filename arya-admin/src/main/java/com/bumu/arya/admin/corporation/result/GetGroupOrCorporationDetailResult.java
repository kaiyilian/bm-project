package com.bumu.arya.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by DaiAoXiang on 2017/4/27.
 */
@ApiModel
public class GetGroupOrCorporationDetailResult {

	@ApiModelProperty("公司id")
	String id;

	@ApiModelProperty("公司名称")
	String name;

	@ApiModelProperty("公司简称")
	@JsonProperty("short_name")
	String shortName;

	@ApiModelProperty("公司类型")
	@JsonProperty("is_group")
	int isGroup;

	@ApiModelProperty("联系人姓名")
	@JsonProperty("contact_name")
	String contactName;

	@ApiModelProperty("联系人电话")
	@JsonProperty("contact_phone")
	String contactPhone;

	@ApiModelProperty("是否是汇思项目 1.是 0.否")
	@JsonProperty("is_human_pool_project")
	int isHumanPoolProject;

	@ApiModelProperty("已开通业务类型")
	@JsonProperty("business_type")
	int businessType;

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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(int isGroup) {
		this.isGroup = isGroup;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public int getIsHumanPoolProject() {
		return isHumanPoolProject;
	}

	public void setIsHumanPoolProject(int isHumanPoolProject) {
		this.isHumanPoolProject = isHumanPoolProject;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
}
