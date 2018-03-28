package com.bumu.arya.admin.welfare.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by DaiAoXiang on 2016/12/29.
 */
@ApiModel
public class WelfareGoodsChangePositionCommand {

	@ApiModelProperty("商品id")
	@JsonProperty("goods_id")
	String goodsId;

	@ApiModelProperty("移动位置 上或者下")
	int direction;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
