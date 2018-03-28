package com.bumu.bran.admin.setting.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/13
 */
public class CreatePositionCommand {

	@JsonProperty("position_name")
	String positionName;

	public CreatePositionCommand() {

	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
