package com.bumu.bran.admin.employee.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author CuiMengxin
 * @date 2016/5/20
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmployeeLeaveCommand {

    @ApiModelProperty("<ID> -> <ID>")
	@JsonProperty("employee_id")
	Map<String, Long> employeeIds;

	@JsonProperty("leave_reason_id")
	String leaveReasonId;

	@JsonProperty("leave_time")
	Long leaveTime;

	String remarks;

	public EmployeeLeaveCommand() {
	}

	public Map<String, Long> getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(Map<String, Long> employeeIds) {
		this.employeeIds = employeeIds;
	}

	public String getLeaveReasonId() {
		return leaveReasonId;
	}

	public void setLeaveReasonId(String leaveReasonId) {
		this.leaveReasonId = leaveReasonId;
	}

	public Long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Long leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
