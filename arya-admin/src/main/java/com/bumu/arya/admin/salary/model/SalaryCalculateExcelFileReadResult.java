package com.bumu.arya.admin.salary.model;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/4/8
 */
public class SalaryCalculateExcelFileReadResult {
	List<SalaryModel> models;

	List<String> log;

	public List<String> getLog() {
		return log;
	}

	public void setLog(List<String> log) {
		this.log = log;
	}

	public List<SalaryModel> getModels() {
		return models;
	}

	public void setModels(List<SalaryModel> models) {
		this.models = models;
	}

	public SalaryCalculateExcelFileReadResult() {

	}
}
