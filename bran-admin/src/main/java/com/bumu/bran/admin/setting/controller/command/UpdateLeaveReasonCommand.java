package com.bumu.bran.admin.setting.controller.command;

import com.bumu.bran.common.command.TxVersionCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/6/17
 */
public class UpdateLeaveReasonCommand extends TxVersionCommand{

	@JsonProperty("leave_reason_id")
	String leaveReasonId;

	@JsonProperty("leave_reason_name")
	String leaveReasonName;

	@JsonProperty("is_not_good")
	Integer isNotGood;

	public UpdateLeaveReasonCommand() {

	}

	public Integer getIsNotGood() {
		return isNotGood;
	}

	public void setIsNotGood(Integer isNotGood) {
		this.isNotGood = isNotGood;
	}

	public String getLeaveReasonId() {
		return leaveReasonId;
	}

	public void setLeaveReasonId(String leaveReasonId) {
		this.leaveReasonId = leaveReasonId;
	}

	public String getLeaveReasonName() {
		return leaveReasonName;
	}

	public void setLeaveReasonName(String leaveReasonName) {
		this.leaveReasonName = leaveReasonName;
	}
}
