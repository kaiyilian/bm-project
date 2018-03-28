package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2016/5/12
 */
public class PositionListResult extends ArrayList<PositionListResult.PositionResult> {

	public static class PositionResult extends TxVersionResult {
		@JsonProperty("position_id")
		String positionId;

		@JsonProperty("position_name")
		String positionName;

		public PositionResult() {

		}

		public String getPositionId() {
			return positionId;
		}

		public void setPositionId(String positionId) {
			this.positionId = positionId;
		}

		public String getPositionName() {
			return positionName;
		}

		public void setPositionName(String positionName) {
			this.positionName = positionName;
		}
	}
}
