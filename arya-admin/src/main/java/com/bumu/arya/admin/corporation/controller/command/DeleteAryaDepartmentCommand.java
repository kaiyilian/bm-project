package com.bumu.arya.admin.corporation.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CuiMengxin
 * @date 2016/7/13
 */
@ApiModel
public class DeleteAryaDepartmentCommand {

	@ApiModelProperty("通用部门id")
	@JsonProperty("department_id")
	String departmentId;

	public DeleteAryaDepartmentCommand() {
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}
