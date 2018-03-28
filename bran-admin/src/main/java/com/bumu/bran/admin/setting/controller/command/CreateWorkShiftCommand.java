package com.bumu.bran.admin.setting.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/13
 */
public class CreateWorkShiftCommand {
	@JsonProperty("work_shift_name")
	String workShiftName;

	public CreateWorkShiftCommand() {

	}

	public String getWorkShiftName() {
		return workShiftName;
	}

	public void setWorkShiftName(String workShiftName) {
		this.workShiftName = workShiftName;
	}
}
