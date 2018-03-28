package com.bumu.arya.admin.salary.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/3/30
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SalaryCalculateImportCommand {
	@JsonProperty("group_id")
	String groupId;

	@JsonProperty("department_id")
	String departmentId;

	@JsonProperty("settlement_interval")
	Integer settlementInterval;

	@JsonProperty("year_month")
	String yearMonth;

	@JsonProperty("week")
	Integer week;

	@JsonProperty("file_name")
	String fileName;

	public SalaryCalculateImportCommand() {
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getSettlementInterval() {
		return settlementInterval;
	}

	public void setSettlementInterval(Integer settlementInterval) {
		this.settlementInterval = settlementInterval;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
