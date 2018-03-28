package com.bumu.bran.admin.setting.controller.command;

import com.bumu.bran.common.command.TxVersionCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/19
 */
public class DeleteWorkLineCommand extends TxVersionCommand {
	@JsonProperty("work_line_id")
	String workLineId;

	public DeleteWorkLineCommand() {

	}

	public String getWorkLineId() {
		return workLineId;
	}

	public void setWorkLineId(String workLineId) {
		this.workLineId = workLineId;
	}
}
