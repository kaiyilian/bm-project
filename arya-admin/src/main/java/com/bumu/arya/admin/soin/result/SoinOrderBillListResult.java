package com.bumu.arya.admin.soin.result;

import com.bumu.common.result.PaginationResult;

import java.util.List;

/**
 * Created by CuiMengxin on 16/8/17.
 */
public class SoinOrderBillListResult extends PaginationResult {
    List<OrderResult> orders;

    public List<OrderResult> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderResult> orders) {
        this.orders = orders;
    }
}
