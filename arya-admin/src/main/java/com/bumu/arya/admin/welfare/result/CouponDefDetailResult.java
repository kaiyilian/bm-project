package com.bumu.arya.admin.welfare.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by DaiAoXiang on 2016/11/30.
 */
@ApiModel
public class CouponDefDetailResult {

	@ApiModelProperty("0或1，表示是否已经导出，如果已经导出，则不能公司，数量和金额不能编辑")
	@JsonProperty("is_exported")
	int isExported;

	@ApiModelProperty("券背景图URL")
	@JsonProperty("coupon_background_url")
	String couponBackgroundUrl;

	@ApiModelProperty("公司ID")
	@JsonProperty("corp_id")
	String corpId;

	@ApiModelProperty("券总数")
	int count;

	@ApiModelProperty("开始时间戳，没有则为0")
	@JsonProperty("active_time")
	long activeTime;

	@ApiModelProperty("结束时间戳，没有则为0")
	@JsonProperty("expire_time")
	long expireTime;

	@ApiModelProperty("券金额")
	BigDecimal price;

	@ApiModelProperty("创建时间戳")
	@JsonProperty("create_time")
	long createTime;

	@ApiModelProperty("是否有时间限制（0表示否，1表示是）")
	@JsonProperty("is_time_limit")
	int isTimeLimit;

	public int getIsExported() {
		return isExported;
	}

	public void setIsExported(int isExported) {
		this.isExported = isExported;
	}

	public String getCouponBackgroundUrl() {
		return couponBackgroundUrl;
	}

	public void setCouponBackgroundUrl(String couponBackgroundUrl) {
		this.couponBackgroundUrl = couponBackgroundUrl;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getIsTimeLimit() {
		return isTimeLimit;
	}

	public void setIsTimeLimit(int isTimeLimit) {
		this.isTimeLimit = isTimeLimit;
	}
}
