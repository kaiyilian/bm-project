package com.bumu.arya.admin.soin.result;

import com.bumu.common.result.PaginationResult;

import java.util.List;

/**
 * Created by CuiMengxin on 2017/1/9.
 */
public class EmployTypeListResult extends PaginationResult {

	List<EmployTypeQueryResult> orders;

	public List<EmployTypeQueryResult> getOrders() {
		return orders;
	}

	public void setOrders(List<EmployTypeQueryResult> orders) {
		this.orders = orders;
	}
}
