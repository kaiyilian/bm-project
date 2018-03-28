package com.bumu.arya.admin.welfare.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by CuiMengxin on 16/9/8.
 * 编辑商品信息请求参数
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WelfareGoodsEditCommand {

    @ApiModelProperty("商品id,有则修改无则新增")
    @Size(min = 32, max = 32)
    String id;

    @ApiModelProperty("缩略图id")
    @JsonProperty("thumb_id")
    String thumbId;

    @ApiModelProperty("'轮播图片id'")
    @JsonProperty("image_ids")
    List<String> imageIds;

    @ApiModelProperty("商品名称")
    @JsonProperty("goods_name")
    @Size(max = 128)
    @NotNull
    String goodsName;

    @ApiModelProperty("活动价")
    @JsonProperty("deal_price")
    @NotNull
    BigDecimal dealPrice;

    @ApiModelProperty("市场价")
    @JsonProperty("marked_price")
    @NotNull
    BigDecimal markedPrice;

    @ApiModelProperty("设置的库存（活动开始后不能编辑）")
    @JsonProperty("inventory_count")
    @NotNull
    long inventoryCount;

    @ApiModelProperty("每单最大购买数量")
    @JsonProperty("buy_limit")
    @NotNull
    int buyLimit;

    @ApiModelProperty("三天内//商品发货时间描述")
    @JsonProperty("delivery_time_desc")
    @Size(max = 128)
    String deliveryTimeDesc;

    @ApiModelProperty("品牌")
    @Size(max = 128)
    String brand;

    @ApiModelProperty("详情")
    @Size(max = 512)
    String desc;

    @ApiModelProperty("是否上架, 1是，0不是")
    @JsonProperty("on_sale")
    int onSale;

    @ApiModelProperty("分类列表")
    List<Category> categories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbId() {
        return thumbId;
    }

    public void setThumbId(String thumbId) {
        this.thumbId = thumbId;
    }

    public List<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<String> imageIds) {
        this.imageIds = imageIds;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public BigDecimal getMarkedPrice() {
        return markedPrice;
    }

    public void setMarkedPrice(BigDecimal markedPrice) {
        this.markedPrice = markedPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(long inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(int buyLimit) {
        this.buyLimit = buyLimit;
    }

    public String getDeliveryTimeDesc() {
        return deliveryTimeDesc;
    }

    public void setDeliveryTimeDesc(String deliveryTimeDesc) {
        this.deliveryTimeDesc = deliveryTimeDesc;
    }

    public int getOnSale() {
        return onSale;
    }

    public void setOnSale(int onSale) {
        this.onSale = onSale;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * 分类和规格
     */
    public static class Category {
        @ApiModelProperty("分类id")
        @Size(min = 32, max = 32)
        String id;

        @ApiModelProperty("规格id")
        @JsonProperty("spec_ids")
        List<String> specIds;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getSpecIds() {
            return specIds;
        }

        public void setSpecIds(List<String> specIds) {
            this.specIds = specIds;
        }
    }
}
