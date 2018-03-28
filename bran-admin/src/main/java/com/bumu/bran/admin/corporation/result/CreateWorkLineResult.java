package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/13
 */
public class CreateWorkLineResult extends TxVersionResult {
	@JsonProperty("work_line_id")
	String workLineId;

	public CreateWorkLineResult() {

	}

	public String getWorkLineId() {
		return workLineId;
	}

	public void setWorkLineId(String workLineId) {
		this.workLineId = workLineId;
	}
}
