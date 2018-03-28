package com.bumu.arya.admin.soin.model;

import com.bumu.arya.admin.misc.model.ImportMessage;

/**
 * 社保订单
 * Created by CuiMengxin on 2016/12/28.
 */
public class OrderBillImportMessage extends ImportMessage {

	//总条数
	Integer totalRows;

	//保存的行数
	Integer saveRows;

	//删除的行数
	Integer deleteRows;

	//增员数
	Integer modifyAddRows;

	//减员数
	Integer modifySubtractRows;

	//不增不减数
	Integer notModifyRows;

	public OrderBillImportMessage() {
		totalRows = 0;
		saveRows = 0;
		deleteRows = 0;
		modifyAddRows = 0;
		modifySubtractRows = 0;
		notModifyRows = 0;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getSaveRows() {
		return saveRows;
	}

	public void setSaveRows(Integer saveRows) {
		this.saveRows = saveRows;
	}

	public Integer getDeleteRows() {
		return deleteRows;
	}

	public void setDeleteRows(Integer deleteRows) {
		this.deleteRows = deleteRows;
	}

	public Integer getModifyAddRows() {
		return modifyAddRows;
	}

	public void setModifyAddRows(Integer modifyAddRows) {
		this.modifyAddRows = modifyAddRows;
	}

	public Integer getModifySubtractRows() {
		return modifySubtractRows;
	}

	public void setModifySubtractRows(Integer modifySubtractRows) {
		this.modifySubtractRows = modifySubtractRows;
	}

	public Integer getNotModifyRows() {
		return notModifyRows;
	}

	public void setNotModifyRows(Integer notModifyRows) {
		this.notModifyRows = notModifyRows;
	}
}
