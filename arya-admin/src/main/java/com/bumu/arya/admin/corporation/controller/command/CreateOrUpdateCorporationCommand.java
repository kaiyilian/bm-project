package com.bumu.arya.admin.corporation.controller.command;

import com.bumu.arya.admin.corporation.model.CorpModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by DaiAoXiang on 2017/4/27.
 */
@ApiModel
public class CreateOrUpdateCorporationCommand {

	@ApiModelProperty("集团或公司id',如果是更新则必填，新增则不填")
	String corpId;

	@ApiModelProperty("上级公司id")
	@JsonProperty("parent_id")
	String parentId;

	@ApiModelProperty("公司或集团名称")
	String name;

	@ApiModelProperty("简称")
	@JsonProperty("short_name")
	String shortName;

	@ApiModelProperty("联系人姓名")
	@JsonProperty("contact_name")
	String contactName;

	@ApiModelProperty("联系人电话")
	@JsonProperty("contact_phone")
	String contactPhone;

	@ApiModelProperty("已开通业务")
	@JsonProperty("business_type")
	Integer businessType;

	@ApiModelProperty("是否为集团 0否 1是")
	@JsonProperty("is_group")
	Integer isGroup;

	@ApiModelProperty("是否是汇思项目 0否 1是")
	@JsonProperty("is_human_pool_project")
	Integer isHumanPoolProject;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Integer isGroup) {
		this.isGroup = isGroup;
	}

	public Integer getIsHumanPoolProject() {
		return isHumanPoolProject;
	}

	public void setIsHumanPoolProject(Integer isHumanPoolProject) {
		this.isHumanPoolProject = isHumanPoolProject;
	}

	public CorpModel init(CreateOrUpdateCorporationCommand command) {
		CorpModel corpModel = new CorpModel();
		corpModel.setCorpId(command.getCorpId());
		corpModel.setParentId(command.getParentId());
		corpModel.setCorpName(command.getName());
		corpModel.setCorpShortName(command.getShortName());
		corpModel.setContactName(command.getContactName());
		corpModel.setContactPhone(command.getContactPhone());
		corpModel.setBusinessType(command.getBusinessType());
		corpModel.setIsGroup(command.getIsGroup());
		corpModel.setIsHumanPoolProject(command.getIsHumanPoolProject());
		return corpModel;
	}
}