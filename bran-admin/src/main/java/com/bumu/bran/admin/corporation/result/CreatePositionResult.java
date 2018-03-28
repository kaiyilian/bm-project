package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/13
 */
public class CreatePositionResult extends TxVersionResult {

	@JsonProperty("position_id")
	String  positionId;

	public CreatePositionResult() {

	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
}
