package com.bumu.arya.admin.system.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 创建或编辑系统角色时的参数
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateEditSysRoleCommand {

	/**
	 * 角色ID，编辑时使用
	 */
	@ApiModelProperty("角色ID，编辑时使用")
	@JsonProperty("rid")
	String rid;

	@JsonProperty("role_name")
	String roleName;

	@JsonProperty("role_desc")
	String roleDesc;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Override
	public String toString() {
		return "CreateEditSysRoleCommand{" +
				"rid='" + rid + '\'' +
				", roleName='" + roleName + '\'' +
				", roleDesc='" + roleDesc + '\'' +
				'}';
	}
}
