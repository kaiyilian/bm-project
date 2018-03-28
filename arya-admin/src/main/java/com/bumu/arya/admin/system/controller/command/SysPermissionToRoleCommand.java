package com.bumu.arya.admin.system.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * 添加或删除用户和角色的关联
 * Created by allen on 15/11/17.
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SysPermissionToRoleCommand {

	@JsonProperty("role_id")
	String roleId;

	@JsonProperty("sys_permission_id")
	String sysPermissionId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSysPermissionId() {
		return sysPermissionId;
	}

	public void setSysPermissionId(String sysPermissionId) {
		this.sysPermissionId = sysPermissionId;
	}

	@Override
	public String toString() {
		return "SysUserToRoleCommand{" +
				"roleId='" + roleId + '\'' +
				", sysUserId='" + sysPermissionId + '\'' +
				'}';
	}
}
