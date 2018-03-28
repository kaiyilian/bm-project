package com.bumu.bran.admin.setting.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/13
 */
public class CreateWorkLineCommand {

	@JsonProperty("work_line_name")
	String workLineName;

	public CreateWorkLineCommand() {

	}

	public String getWorkLineName() {
		return workLineName;
	}

	public void setWorkLineName(String workLineName) {
		this.workLineName = workLineName;
	}
}
