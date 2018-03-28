package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.PaginationResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/6/21
 */
@ApiModel
public class CorpOperationListResult extends PaginationResult {

	List<CorpOperationResult> logs;

	public List<CorpOperationResult> getLogs() {
		return logs;
	}

	public void setLogs(List<CorpOperationResult> logs) {
		this.logs = logs;
	}

	public static class CorpOperationResult {

		String id;

		@JsonProperty("operator_id")
		String operatorId;

		@JsonProperty("operator_name")
		String operatorName;

		String log;

		long time;

		public CorpOperationResult() {
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public String getId() {

			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getOperatorId() {
			return operatorId;
		}

		public void setOperatorId(String operatorId) {
			this.operatorId = operatorId;
		}

		public String getOperatorName() {
			return operatorName;
		}

		public void setOperatorName(String operatorName) {
			this.operatorName = operatorName;
		}

		public String getLog() {
			return log;
		}

		public void setLog(String log) {
			this.log = log;
		}
	}
}
