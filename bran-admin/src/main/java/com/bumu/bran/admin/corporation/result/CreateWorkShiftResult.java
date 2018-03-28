package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/13
 */
public class CreateWorkShiftResult extends TxVersionResult {

	@JsonProperty("work_shift_id")
	String workShiftId;

	public CreateWorkShiftResult() {

	}

	public String getWorkShiftId() {
		return workShiftId;
	}

	public void setWorkShiftId(String workShiftId) {
		this.workShiftId = workShiftId;
	}
}
