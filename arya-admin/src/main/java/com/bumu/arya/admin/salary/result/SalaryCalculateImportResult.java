package com.bumu.arya.admin.salary.result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/4/5
 */
public class SalaryCalculateImportResult {

	@JsonProperty("total_count")
	int totalCount;

	@JsonProperty("success_count")
	int successCount;

	@JsonProperty("failed_count")
	int failedCount;

	@JsonProperty("ignored_count")
	int ignoredCount;

	String log;

	public SalaryCalculateImportResult() {
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public int getIgnoredCount() {
		return ignoredCount;
	}

	public void setIgnoredCount(int ignoredCount) {
		this.ignoredCount = ignoredCount;
	}
}
