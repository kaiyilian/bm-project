package com.bumu.bran.admin.setting.controller.command;

import com.bumu.bran.common.command.TxVersionCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/19
 */
public class DeleteWorkShiftCommand extends TxVersionCommand {

	@JsonProperty("work_shift_id")
	String workShiftId;

	public DeleteWorkShiftCommand() {

	}

	public String getWorkShiftId() {
		return workShiftId;
	}

	public void setWorkShiftId(String workShiftId) {
		this.workShiftId = workShiftId;
	}
}
