package com.bumu.arya.admin.welfare.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by DaiAoXiang on 2016/11/29.
 */
@ApiModel()
public class CouponDefSaveCommand {

	@ApiModelProperty("券定义ID，为空则表示新建的券定义")
	@JsonProperty("coupon_def_id")
	String couponDefId;

	@ApiModelProperty("券背景图文件ID’，从通用文件上传接口获得（编辑的情况下如果为空表示图片不改变)")
	@JsonProperty("coupon_background_file_id")
	String couponBackgroundFileId;

	@ApiModelProperty("公司ID")
	@JsonProperty("corp_id")
	String corpId;

	@ApiModelProperty("券总数")
	Integer count;

	@ApiModelProperty("开始时间")
	@JsonProperty("active_time")
	long activeTime;

	@ApiModelProperty("结束时间")
	@JsonProperty("expire_time")
	long expireTime;

	@ApiModelProperty("券金额")
	BigDecimal price;

	public String getCouponDefId() {
		return couponDefId;
	}

	public void setCouponDefId(String couponDefId) {
		this.couponDefId = couponDefId;
	}

	public String getCouponBackgroundFileId() {
		return couponBackgroundFileId;
	}

	public void setCouponBackgroundFileId(String couponBackgroundFileId) {
		this.couponBackgroundFileId = couponBackgroundFileId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
