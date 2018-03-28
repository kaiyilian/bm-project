package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/12
 */
public class CreateDepartmentResult extends TxVersionResult {

	@JsonProperty("department_id")
	String departmentId;

	public CreateDepartmentResult() {

	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}
