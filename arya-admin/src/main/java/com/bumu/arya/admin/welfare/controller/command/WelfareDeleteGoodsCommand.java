package com.bumu.arya.admin.welfare.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by CuiMengxin on 16/9/19.
 */
@ApiModel
public class WelfareDeleteGoodsCommand {

    @ApiModelProperty("商品id")
    @JsonProperty("goods_id")
    String goodsId;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
