package com.bumu.arya.admin.soin.result;

import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 增加员导出
 * @author liangjun
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmployeeExportOutResult extends TxVersionResult {
	String data ;
	List<EmployeeExportOutDetail> result;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<EmployeeExportOutDetail> getResult() {
		return result;
	}

	public void setResult(List<EmployeeExportOutDetail> result) {
		this.result = result;
	}
}
