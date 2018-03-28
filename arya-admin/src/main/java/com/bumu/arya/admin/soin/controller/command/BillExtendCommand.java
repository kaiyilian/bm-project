package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.admin.welfare.controller.command.PaginationCommand;

/**
 * Created by CuiMengxin on 2017/1/6.
 * 批量顺延查询command
 */
public class BillExtendCommand extends PaginationCommand {
	String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
