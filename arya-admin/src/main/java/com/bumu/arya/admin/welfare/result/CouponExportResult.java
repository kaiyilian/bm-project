package com.bumu.arya.admin.welfare.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by DaiAoXiang on 2016/12/1.
 */
@ApiModel
public class CouponExportResult {

	@ApiModelProperty("导出的的福库劵数量，如果没有则为0")
	int count;

	@ApiModelProperty("导出的福库劵压缩包url")
	String coupon_url;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCoupon_url() {
		return coupon_url;
	}

	public void setCoupon_url(String coupon_url) {
		this.coupon_url = coupon_url;
	}
}
