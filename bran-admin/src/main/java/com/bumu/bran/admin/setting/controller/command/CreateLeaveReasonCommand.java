package com.bumu.bran.admin.setting.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/20
 */
public class CreateLeaveReasonCommand {

	@JsonProperty("leave_reason_name")
	String leaveReasonName;

	@JsonProperty("is_not_good")
	Integer isNotGood;

	public CreateLeaveReasonCommand() {

	}

	public Integer getIsNotGood() {
		return isNotGood;
	}

	public void setIsNotGood(Integer isNotGood) {
		this.isNotGood = isNotGood;
	}

	public String getLeaveReasonName() {
		return leaveReasonName;
	}

	public void setLeaveReasonName(String leaveReasonName) {
		this.leaveReasonName = leaveReasonName;
	}
}
