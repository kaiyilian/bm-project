package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IdStrListCommand {

	List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
