package com.bumu.arya.admin.soin.result;

import com.bumu.arya.admin.soin.model.OrderBillImportMessage;
import com.bumu.arya.admin.soin.model.entity.SoinOrderBillExcelReadModel;

import java.util.List;

/**
 * Created by CuiMengxin on 16/8/3.
 * 订单对账文件读取结果
 */
public class SoinOrderBillExcelFileReadResult {

	List<SoinOrderBillExcelReadModel> models;

	OrderBillImportMessage messages;

	public SoinOrderBillExcelFileReadResult() {
	}

	public List<SoinOrderBillExcelReadModel> getModels() {
		return models;
	}

	public void setModels(List<SoinOrderBillExcelReadModel> models) {
		this.models = models;
	}

	public OrderBillImportMessage getMessages() {
		return messages;
	}

	public void setMessages(OrderBillImportMessage messages) {
		this.messages = messages;
	}
}
