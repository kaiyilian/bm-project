package com.bumu.arya.admin.corporation.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CuiMengxin
 * @date 2016/7/13
 */
@ApiModel
public class CreateAryaDepartmentCommand {

	@ApiModelProperty("集团id,只有集团和一级公司才可以新增通用部门")
	@JsonProperty("group_id")
	String groupId;

	@ApiModelProperty("部门名称")
	String name;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CreateAryaDepartmentCommand() {

	}
}
