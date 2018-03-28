package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.admin.common.controller.ConcurrentAware;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2015/12/23
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderPaymentCommand extends ConcurrentAware{

	/**
	 * 订单编号
	 */
	@JsonProperty("order_id")
	String orderId;

	/**
	 * 已付款或者已退款需要提交金额
	 */
	String money;

	public String getMoney() {
		return money;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public OrderPaymentCommand() {

	}
}
