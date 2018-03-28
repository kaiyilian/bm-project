package com.bumu.bran.admin.setting.controller.command;

import com.bumu.bran.common.command.TxVersionCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/6/17
 */
public class UpdateWorkLineCommand extends TxVersionCommand{
	@JsonProperty("work_line_id")
	String workLineId;

	@JsonProperty("work_line_name")
	String workLineName;

	public UpdateWorkLineCommand() {

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
