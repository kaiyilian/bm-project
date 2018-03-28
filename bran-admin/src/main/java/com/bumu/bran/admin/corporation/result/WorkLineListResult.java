package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2016/5/12
 */
public class WorkLineListResult extends ArrayList<WorkLineListResult.WorkLineResult>{

	public static class WorkLineResult extends TxVersionResult {
		@JsonProperty("work_line_id")
		String workLineId;

		@JsonProperty("work_line_name")
		String workLineName;

		public WorkLineResult() {
		}

		public String getWorkLineId() {
			return workLineId;
		}

		public void setWorkLineId(String workLineId) {
			this.workLineId = workLineId;
		}

		public String getWorkLineName() {
			return workLineName;
		}

		public void setWorkLineName(String workLineName) {
			this.workLineName = workLineName;
		}
	}
}
