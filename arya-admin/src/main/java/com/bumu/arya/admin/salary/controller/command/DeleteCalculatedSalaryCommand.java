package com.bumu.arya.admin.salary.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/7/12
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeleteCalculatedSalaryCommand {

	/**
	 * 删除选中的
	 */
	@JsonProperty("delete_ids")
	List<String> deleteIds;

	/**
	 * 删除所有的除了选中的
	 */
	@JsonProperty("delete_all_without_ids")
	List<String> deleteAllWithoutIds;

	public DeleteCalculatedSalaryCommand() {
	}

	public List<String> getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(List<String> deleteIds) {
		this.deleteIds = deleteIds;
	}

	public List<String> getDeleteAllWithoutIds() {
		return deleteAllWithoutIds;
	}

	public void setDeleteAllWithoutIds(List<String> deleteAllWithoutIds) {
		this.deleteAllWithoutIds = deleteAllWithoutIds;
	}
}
