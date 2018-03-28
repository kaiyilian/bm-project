package com.bumu.arya.admin.system.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 表示改变系统用户状态的命令，如果需要传递参数则继承新的子类
 * Created by allen on 15/11/13.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChangeSysUserCommand {

	String uid;

	/**
	 * 1为正常，2为冻结
	 */
	int status;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
