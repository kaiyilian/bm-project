package com.bumu.bran.admin.corporation.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * @author CuiMengxin
 * @date 2016/5/16
 */
@ApiModel
public class CreateProspectiveEmployeeResult extends TxVersionResult {

	@JsonProperty("employee_id")
	String employeeId;

	String msg;

	public CreateProspectiveEmployeeResult() {

	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
}
