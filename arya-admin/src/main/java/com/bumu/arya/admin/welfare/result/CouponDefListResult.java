package com.bumu.arya.admin.welfare.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by DaiAoXiang on 2016/11/30.
 */
@ApiModel
public class CouponDefListResult {
	@ApiModelProperty("福库劵定义列表")
	List<CouponDefResult> coupon_defs;

	@ApiModelProperty("总页数")
	int pages;

	public List<CouponDefResult> getCoupon_defs() {
		return coupon_defs;
	}

	public void setCoupon_defs(List<CouponDefResult> coupon_defs) {
		this.coupon_defs = coupon_defs;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public static class CouponDefResult{

		@ApiModelProperty("劵定义id")
		@JsonProperty("coupon_def_id")
		String couponDefId;

		@ApiModelProperty("劵底图url")
		@JsonProperty("thumbnail_url")
		String thumbnailUrl;

		@ApiModelProperty("公司名称")
		@JsonProperty("corp_name")
		String corpName;

		@ApiModelProperty("数量")
		int count;

		@ApiModelProperty("生效时间")
		@JsonProperty("active_time")
		long activeTime;

		@ApiModelProperty("失效时间")
		@JsonProperty("expire_time")
		long expireTime;

		@ApiModelProperty("劵面值")
		BigDecimal price;

		@ApiModelProperty("是否导出")
		@JsonProperty("is_exported")
		int isExported;

		@ApiModelProperty("创建时间")
		@JsonProperty("create_time")
		long createTime;

		public String getCouponDefId() {
			return couponDefId;
		}

		public void setCouponDefId(String couponDefId) {
			this.couponDefId = couponDefId;
		}

		public String getThumbnailUrl() {
			return thumbnailUrl;
		}

		public void setThumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
		}

		public String getCorpName() {
			return corpName;
		}

		public void setCorpName(String corpName) {
			this.corpName = corpName;
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

		public int getIsExported() {
			return isExported;
		}

		public void setIsExported(int isExported) {
			this.isExported = isExported;
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}
	}
}
