package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.admin.soin.result.SoinTypeVersionDetailResut;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author CuiMengxin
 * @date 2016/3/9
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateSoinTypeVersionDetailCommand extends SoinTypeVersionDetailResut {
	public UpdateSoinTypeVersionDetailCommand() {
	}
}
