package com.bumu.arya.admin.welfare.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by DaiAoXiang on 2016/11/25.
 */
@ApiModel
public class OrderRefundCommand {

	@ApiModelProperty("订单id")
	@JsonProperty("order_id")
	String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
