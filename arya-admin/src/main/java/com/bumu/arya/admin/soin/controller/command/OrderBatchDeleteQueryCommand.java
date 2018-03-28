package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.admin.welfare.controller.command.PaginationCommand;

/**
 * Created by CuiMengxin on 2017/1/17.
 * 订单批量删除查询参数
 */
public class OrderBatchDeleteQueryCommand  extends PaginationCommand {
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
