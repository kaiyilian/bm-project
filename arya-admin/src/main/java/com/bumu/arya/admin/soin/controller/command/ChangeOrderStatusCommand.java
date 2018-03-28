package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.admin.common.controller.ConcurrentAware;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2015/12/24
 */
public class ChangeOrderStatusCommand extends ConcurrentAware{

	@JsonProperty("order_id")
	String orderId;

	@JsonProperty("to_status")
	Integer toStatus;

	public ChangeOrderStatusCommand() {
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getToStatus() {
		return toStatus;
	}

	public void setToStatus(Integer toStatus) {
		this.toStatus = toStatus;
	}
}
