package com.bumu.arya.admin.welfare.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by CuiMengxin on 16/9/8.
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WelfareOrderListCommand {

    @ApiModelProperty("产品id，可以为空或null,")
    String goods_id;

    @ApiModelProperty("收货人姓名或者手机号，可以为空或null")
    String receiver_key_word;

    @ApiModelProperty("开始时间，可以为空或null'")
    Long begin_time;

    @ApiModelProperty("结束时间，可以为空或null")
    Long end_time;

    @ApiModelProperty("页码")
    int page;

    @ApiModelProperty("每页数量")
    int page_size;

    @ApiModelProperty("企业ID，可以为空或null")
    String corp_id;

    @ApiModelProperty("订单状态值，可以为空或null")
    Integer order_status;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getReceiver_key_word() {
        return receiver_key_word;
    }

    public void setReceiver_key_word(String receiver_key_word) {
        this.receiver_key_word = receiver_key_word;
    }

    public Long getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Long begin_time) {
        this.begin_time = begin_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public int getPage() {
        return page - 1;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public String getCorp_id() {return  corp_id;}

    public void  setCorp_id(String corp_id){this.corp_id = corp_id;}

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
    }
}
