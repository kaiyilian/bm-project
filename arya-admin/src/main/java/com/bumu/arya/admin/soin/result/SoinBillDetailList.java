package com.bumu.arya.admin.soin.result;

import com.bumu.arya.admin.misc.result.SimpleResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuiMengxin on 2017/1/9.
 */
public class SoinBillDetailList {

	List<SimpleResult> details;

	public SoinBillDetailList() {
		this.details = new ArrayList<>();
	}

	public List<SimpleResult> getDetails() {
		return details;
	}

	public void setDetails(List<SimpleResult> details) {
		this.details = details;
	}
}
