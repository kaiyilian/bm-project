package com.bumu.bran.admin.setting.controller.command;

import com.bumu.bran.common.command.TxVersionCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/19
 */
public class DeleteDepartmentCommand extends TxVersionCommand {

	@JsonProperty("department_id")
	String departmentId;

	public DeleteDepartmentCommand() {

	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}
