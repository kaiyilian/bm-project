package com.bumu.bran.admin.corporation.controller.command;

import com.bumu.arya.command.SessionHttpCommand;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/5/12
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateDepartmentCommand extends SessionHttpCommand {

	@JsonProperty("update_departments")
	List<UpdateDepartmentCMD> updateDepartments;

	@JsonProperty("delete_departments")
	String[] deleteDepartmentIds;

	public UpdateDepartmentCommand() {
	}

	public List<UpdateDepartmentCMD> getUpdateDepartments() {
		return updateDepartments;
	}

	public void setUpdateDepartments(List<UpdateDepartmentCMD> updateDepartments) {
		this.updateDepartments = updateDepartments;
	}

	public String[] getDeleteDepartmentIds() {
		return deleteDepartmentIds;
	}

	public void setDeleteDepartmentIds(String[] deleteDepartmentIds) {
		this.deleteDepartmentIds = deleteDepartmentIds;
	}

	public static class UpdateDepartmentCMD {
		@JsonProperty("parent_id")
		String parentId;

		@JsonProperty("department_id")
		String departmentId;

		@JsonProperty("department_name")
		String departmentName;

		public UpdateDepartmentCMD() {
		}

		public String getDepartmentId() {
			return departmentId;
		}

		public void setDepartmentId(String departmentId) {
			this.departmentId = departmentId;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}
	}


}
