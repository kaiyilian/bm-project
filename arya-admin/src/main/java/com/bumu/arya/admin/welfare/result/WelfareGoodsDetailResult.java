package com.bumu.arya.admin.welfare.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by CuiMengxin on 16/9/13.
 * 福库商品详情
 */
@ApiModel
public class WelfareGoodsDetailResult {

    @ApiModelProperty("轮播图")
    @JsonProperty("thumb")
    Image thumb;

    @ApiModelProperty("商品名称")
    @JsonProperty("goods_name")
    String goodsName;

    @ApiModelProperty("活动价")
    @JsonProperty("deal_price")
    BigDecimal dealPrice;

    @ApiModelProperty("市场价")
    @JsonProperty("marked_price")
    BigDecimal markedPrice;

    @ApiModelProperty("品牌")
    String brand;

    @ApiModelProperty("详情")
    String desc;

    @ApiModelProperty("是否上架, 1是，0不是")
    @JsonProperty("on_sale")
    int onSale;

    @ApiModelProperty("设置的库存")
    @JsonProperty("inventory_count")
    long inventoryCount;

    @ApiModelProperty("每单最大购买数量")
    @JsonProperty("buy_limit")
    int buyLimit;

    @ApiModelProperty("三天内//商品发货时间描述")
    @JsonProperty("delivery_time_desc")
    String deliveryTimeDesc;

    @ApiModelProperty("{id:'轮播图片id',url:'轮播图片url'}")
    List<Image> images;

    @ApiModelProperty("id:'已有分类id',spec_ids:['已有规格id',...]},…")
    @JsonProperty("exist_categories")
    List<Category> existCategories;

    public Image getThumb() {
        return thumb;
    }

    public void setThumb(Image thumb) {
        this.thumb = thumb;
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

    public int getOnSale() {
        return onSale;
    }

    public void setOnSale(int onSale) {
        this.onSale = onSale;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Category> getExistCategories() {
        return existCategories;
    }

    public void setExistCategories(List<Category> existCategories) {
        this.existCategories = existCategories;
    }

    public static class Category {

        @ApiModelProperty("已有分类id")
        String id;

        @ApiModelProperty("已有规格id")
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

    public static class Image {

        @ApiModelProperty("轮播图片id")
        String id;

        @ApiModelProperty("轮播图片url")
        String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
