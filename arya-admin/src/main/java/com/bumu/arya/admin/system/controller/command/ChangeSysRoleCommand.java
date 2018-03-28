package com.bumu.arya.admin.system.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

/**
 * 表示改变系统用户状态的命令，如果需要传递参数则继承新的子类
 * Created by allen on 15/11/13.
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChangeSysRoleCommand {

	String rid;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}
}
