package com.bumu.arya.admin.system.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/1/28
 */
public class ChangePwdCommand {

	@JsonProperty("old_pwd")
	String oldPwd;

	@JsonProperty("new_pwd")
	String newPwd;

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public ChangePwdCommand() {

	}
}
