package com.bumu.bran.admin.prospective.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author CuiMengxin
 * @date 2016/5/13
 */
public class DeleteEmployeeCommand {

	@JsonProperty("employee_id")
	Map<String, Long> employeeIds;

	public DeleteEmployeeCommand() {

	}

	public Map<String, Long> getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(Map<String, Long> employeeIds) {
		this.employeeIds = employeeIds;
	}
}
