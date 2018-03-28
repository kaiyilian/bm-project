package com.bumu.arya.admin.soin.result;

import com.bumu.common.result.PaginationResult;

import java.util.List;

/**
 * Created by CuiMengxin on 2017/1/9.
 */
public class SoinBillListResult extends PaginationResult {

	List<SoinBillQueryResult> orders;

	public List<SoinBillQueryResult> getOrders() {
		return orders;
	}

	public void setOrders(List<SoinBillQueryResult> orders) {
		this.orders = orders;
	}
}
