package com.bumu.bran.admin.setting.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/27
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateDepartmentCommand {

	@JsonProperty("parent_id")
	String parentId;

	@JsonProperty("department_name")
	String departmentName;

	public CreateDepartmentCommand() {

	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
