package com.bumu.bran.admin.salary.result;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 确认导入薪资条
 *
 * @author majun
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ImportSalaryConfirmResult {
	private int flag;
	private int total;

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
