package com.bumu.bran.admin.setting.controller.command;

import com.bumu.bran.common.command.TxVersionCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/20
 */
public class DeleteLeaveReasonCommand extends TxVersionCommand {

	@JsonProperty("leave_reason_id")
	String leaveReasonId;

	public DeleteLeaveReasonCommand() {

	}

	public String getLeaveReasonId() {
		return leaveReasonId;
	}

	public void setLeaveReasonId(String leaveReasonId) {
		this.leaveReasonId = leaveReasonId;
	}
}
