package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.admin.common.controller.ConcurrentAware;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/1/21
 */
public class OrderPartialCompleteCommand extends ConcurrentAware {

	@JsonProperty("order_id")
	String orderId;

	@JsonProperty("soin_id")
	String soinId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSoinId() {
		return soinId;
	}

	public void setSoinId(String soinId) {
		this.soinId = soinId;
	}

	public OrderPartialCompleteCommand() {

	}
}
