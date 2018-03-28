package com.bumu.bran.admin.setting.controller.command;

import com.bumu.bran.common.command.TxVersionCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/6/17
 */
public class UpdateWorkShiftCommand extends TxVersionCommand{

	@JsonProperty("work_shift_id")
	String workShiftId;

	@JsonProperty("work_shift_name")
	String workShiftName;

	public UpdateWorkShiftCommand() {

	}

	public String getWorkShiftId() {
		return workShiftId;
	}

	public void setWorkShiftId(String workShiftId) {
		this.workShiftId = workShiftId;
	}

	public String getWorkShiftName() {
		return workShiftName;
	}

	public void setWorkShiftName(String workShiftName) {
		this.workShiftName = workShiftName;
	}
}
