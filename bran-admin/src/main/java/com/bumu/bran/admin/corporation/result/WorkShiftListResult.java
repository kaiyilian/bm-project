package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2016/5/12
 */
@ApiModel
public class WorkShiftListResult extends ArrayList<WorkShiftListResult.WorkShiftResult> {

	public static class WorkShiftResult extends TxVersionResult {
		@JsonProperty("work_shift_id")
		String workShiftId;

		@JsonProperty("work_shift_name")
		String workShiftName;

		public WorkShiftResult() {
		}

		public String getWorkShiftId() {
			return workShiftId;
		}

		public void setWorkShiftId(String workShiftId) {
			this.workShiftId = workShiftId;
		}

		public String getWorkShiftName() {
			return workShiftName;
		}

		public void setWorkShiftName(String workShiftName) {
			this.workShiftName = workShiftName;
		}
	}
}
