package com.bumu.arya.admin.welfare.controller.command;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by DaiAoXiang on 2016/12/2.
 */
@ApiModel
public class CouponDefDeleteCommand {

	@ApiModelProperty("劵定义id  Map<String, String>")
	List<Map<String,String>> ids;

	public List<Map<String, String>> getIds() {
		return ids;
	}

	public void setIds(List<Map<String, String>> ids) {
		this.ids = ids;
	}
}
