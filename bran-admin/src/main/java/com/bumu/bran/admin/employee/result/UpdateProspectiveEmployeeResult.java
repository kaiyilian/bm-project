package com.bumu.bran.admin.employee.result;

import com.bumu.common.result.TxVersionResult;

/**
 * @author CuiMengxin
 * @date 2016/5/30
 */
public class UpdateProspectiveEmployeeResult extends TxVersionResult {

	String msg;

	public UpdateProspectiveEmployeeResult() {

	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
