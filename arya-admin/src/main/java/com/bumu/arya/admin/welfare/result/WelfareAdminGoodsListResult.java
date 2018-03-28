package com.bumu.arya.admin.welfare.result;

import com.bumu.common.result.PaginationResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by CuiMengxin on 16/9/13.
 */
@ApiModel
public class WelfareAdminGoodsListResult extends PaginationResult {

    @ApiModelProperty("商品列表")
    List<Goods> goods;

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    @ApiModelProperty("活动开始时间，时间戳")
    @JsonProperty("begin_time")
    long beginTime;

    @ApiModelProperty("活动结束时间，时间戳")
    @JsonProperty("end_time")
    long endTime;

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public static class Goods {

        @ApiModelProperty("商品id")
        String id;

        @ApiModelProperty("缩略图url")
        @JsonProperty("thumb_url")
        String thumbUrl;

        @ApiModelProperty("轮播图url")
        @JsonProperty("shuffling_img_url")
        String shufflingImgUrl;

        @ApiModelProperty("商品名")
        @JsonProperty("goods_name")
        String goodsName;

        @ApiModelProperty("活动价")
        @JsonProperty("deal_price")
        BigDecimal dealPrice;

        @ApiModelProperty("市场价")
        @JsonProperty("marked_price")
        BigDecimal markedPrice;

        @ApiModelProperty("商品详情")
        @JsonProperty("goods_detail")
        String goodsDetail;

        @ApiModelProperty("是否上架")
        @JsonProperty("on_sale")
        int onSale;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }

        public void setThumbUrl(String thumbUrl) {
            this.thumbUrl = thumbUrl;
        }

        public String getShufflingImgUrl() {
            return shufflingImgUrl;
        }

        public void setShufflingImgUrl(String shufflingImgUrl) {
            this.shufflingImgUrl = shufflingImgUrl;
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

        public String getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(String goodsDetail) {
            this.goodsDetail = goodsDetail;
        }

        public int getOnSale() {
            return onSale;
        }

        public void setOnSale(int onSale) {
            this.onSale = onSale;
        }
    }
}
