package com.bumu.arya.admin.soin.controller.command;

/**
 * Created by CuiMengxin on 2017/1/17.
 * 订单批量删除和查询的command
 */
public class OrderBatchDeleteCommand{

	String id;

	String keyword;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
