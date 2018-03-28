package com.bumu.arya.admin.soin.result;

import java.io.Serializable;

/**
 * @author CuiMengxin
 * @date 2016/1/27
 */
public class OrderStatusChangeResult implements Serializable {

	String msg;

	public OrderStatusChangeResult() {
	}

	public OrderStatusChangeResult(String msg) {
		this.msg = msg;
	}

	public String getMsg() {

		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
