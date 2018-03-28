package com.bumu.arya.admin.welfare.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by DaiAoXiang on 2016/12/1.
 */
@ApiModel
public class CouponExportCommand {

	@ApiModelProperty("福库券定义ID列表")
	List<String>  coupon_def_ids;

	public List<String> getCoupon_def_ids() {
		return coupon_def_ids;
	}

	public void setCoupon_def_ids(List<String> coupon_def_ids) {
		this.coupon_def_ids = coupon_def_ids;
	}
}
