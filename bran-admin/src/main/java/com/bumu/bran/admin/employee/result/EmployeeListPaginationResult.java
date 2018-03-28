package com.bumu.bran.admin.employee.result;

import com.bumu.common.result.PaginationResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/12
 */
@ApiModel
public class EmployeeListPaginationResult extends PaginationResult {

	@ApiModelProperty
	EmployeeListResult employees;

	public EmployeeListPaginationResult() {

	}

	public EmployeeListResult getEmployees() {
		return employees;
	}

	public void setEmployees(EmployeeListResult employees) {
		this.employees = employees;
	}
}
