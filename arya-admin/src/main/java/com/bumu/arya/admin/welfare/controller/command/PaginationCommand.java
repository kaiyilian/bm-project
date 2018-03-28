package com.bumu.arya.admin.welfare.controller.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by CuiMengxin on 16/9/13.
 */
@ApiModel
public class PaginationCommand {

	@ApiModelProperty("每页数量")
	int page;

	@ApiModelProperty("页码")
	int page_size;

	public int getPage() {
		return page - 1;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
}
