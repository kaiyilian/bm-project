package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2016/5/20
 */
@ApiModel
public class LeaveReasonListResult extends ArrayList<LeaveReasonListResult.LeaveReasonResult> {

    @ApiModel
	public static class LeaveReasonResult extends TxVersionResult {
		@JsonProperty("leave_reason_id")
		String leaveReasonId;

		@JsonProperty("leave_reason_name")
		String leaveReasonName;

		@JsonProperty("is_not_good")
		int isNotGood;

		public LeaveReasonResult() {

		}

		public int getIsNotGood() {
			return isNotGood;
		}

		public void setIsNotGood(int isNotGood) {
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
}
