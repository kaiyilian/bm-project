package com.bumu.arya.admin.welfare.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by CuiMengxin on 16/9/18.
 */
@ApiModel
public class WelfareOrderCategoryDetailResult {

    @ApiModelProperty("订单id")
    @JsonProperty("order_id")
    String orderId;

    @ApiModelProperty("{id:'分类id',spec_ids:['规格id',...]},...")
    List<WelfareGoodsDetailResult.Category> categories;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<WelfareGoodsDetailResult.Category> getCategories() {
        return categories;
    }

    public void setCategories(List<WelfareGoodsDetailResult.Category> categories) {
        this.categories = categories;
    }
}
