package com.bumu.bran.admin.employee.controller.command;

import com.bumu.common.result.ModelResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * majun
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SelectModelResult<T extends ModelResult> {

	private List<T> models;
	@JsonProperty(value = "total_page")
	private int totalPage;
	private int totalCount;


	public List<T> getModels() {
		return models;
	}

	public void setModels(List<T> models) {
		this.models = models;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
