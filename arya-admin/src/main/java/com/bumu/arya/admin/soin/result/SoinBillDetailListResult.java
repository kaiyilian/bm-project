package com.bumu.arya.admin.soin.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuiMengxin on 2017/1/9.
 */
public class SoinBillDetailListResult {
	List<SoinBillDetailResult> details;

	public SoinBillDetailListResult() {
		this.details = new ArrayList<>();
	}

	public List<SoinBillDetailResult> getDetails() {
		return details;
	}

	public void setDetails(List<SoinBillDetailResult> details) {
		this.details = details;
	}
}
