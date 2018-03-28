package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2016/5/12
 */
public class DepartmentListResult extends ArrayList<DepartmentListResult.DepartmentResult> {

	public static class DepartmentResult extends TxVersionResult {
		@JsonProperty("parent_id")
		String parentId;

		@JsonProperty("department_id")
		String departmentId;

		@JsonProperty("department_name")
		String departmentName;
		@JsonProperty("department_count")
		private int departmentCount;

		public DepartmentResult() {
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public String getDepartmentId() {
			return departmentId;
		}

		public void setDepartmentId(String departmentId) {
			this.departmentId = departmentId;
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		public int getDepartmentCount() {
			return departmentCount;
		}

		public void setDepartmentCount(int departmentCount) {
			this.departmentCount = departmentCount;
		}
	}
}
