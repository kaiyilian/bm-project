package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/20
 */
public class CreateLeaveReasonResult extends TxVersionResult {

	@JsonProperty("leave_reason_id")
	String leaveReasonId;

	public CreateLeaveReasonResult() {

	}

	public String getLeaveReasonId() {
		return leaveReasonId;
	}

	public void setLeaveReasonId(String leaveReasonId) {
		this.leaveReasonId = leaveReasonId;
	}
}
