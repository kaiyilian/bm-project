package com.bumu.bran.admin.login.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/6/17
 */
public class ChangePasswordCommand {
	String id;
	@JsonProperty("old_password")
	String oldPassword;
	@JsonProperty("new_password")
	String newPassword;
	private String account;

	public ChangePasswordCommand() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
