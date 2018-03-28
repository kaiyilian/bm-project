package com.bumu.arya.admin.welfare.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by DaiAoXiang on 2016/11/30.
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CouponDefListCommand {

	@ApiModelProperty("公司id，为空表示所有公司")
	String corp_id;

	@ApiModelProperty("创建日期，为空表示不限定日期")
	Long create_date;

	@ApiModelProperty("页码")
	int page;

	@ApiModelProperty("每页数量")
	int page_size;

	public String getCorp_id() {
		return corp_id;
	}

	public void setCorp_id(String corp_id) {
		this.corp_id = corp_id;
	}

	public Long getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Long create_date) {
		this.create_date = create_date;
	}

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
