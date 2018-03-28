package com.bumu.arya.admin.welfare.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by DaiAoXiang on 2016/11/29.
 */
@ApiModel
public class CouponDefLayoutResult2 {

	@ApiModelProperty("券面宽高比")
	@JsonProperty("coupon_width_height")
	String couponWidthHeight;

	@ApiModelProperty("二维码左上角x坐标和券面宽度比")
	@JsonProperty("qrcode_x_coupon_width")
	String qrcodeXCouponWidth;

	@ApiModelProperty("二维码左上角y坐标和券面高度比")
	@JsonProperty("qrcode_y_coupon_height")
	String qrcodeYCouponHeight;

	@ApiModelProperty("二维码宽度和券面宽度比")
	@JsonProperty("qrcode_width_coupon_width")
	String qrcodeWidthCouponWidth;

	@ApiModelProperty("二维码高度和券面高度比")
	@JsonProperty("qrcode_height_coupon_height")
	String qrcodeHeightCouponHeight;

	public String getCouponWidthHeight() {
		return couponWidthHeight;
	}

	public void setCouponWidthHeight(String couponWidthHeight) {
		this.couponWidthHeight = couponWidthHeight;
	}

	public String getQrcodeXCouponWidth() {
		return qrcodeXCouponWidth;
	}

	public void setQrcodeXCouponWidth(String qrcodeXCouponWidth) {
		this.qrcodeXCouponWidth = qrcodeXCouponWidth;
	}

	public String getQrcodeYCouponHeight() {
		return qrcodeYCouponHeight;
	}

	public void setQrcodeYCouponHeight(String qrcodeYCouponHeight) {
		this.qrcodeYCouponHeight = qrcodeYCouponHeight;
	}

	public String getQrcodeWidthCouponWidth() {
		return qrcodeWidthCouponWidth;
	}

	public void setQrcodeWidthCouponWidth(String qrcodeWidthCouponWidth) {
		this.qrcodeWidthCouponWidth = qrcodeWidthCouponWidth;
	}

	public String getQrcodeHeightCouponHeight() {
		return qrcodeHeightCouponHeight;
	}

	public void setQrcodeHeightCouponHeight(String qrcodeHeightCouponHeight) {
		this.qrcodeHeightCouponHeight = qrcodeHeightCouponHeight;
	}
}
