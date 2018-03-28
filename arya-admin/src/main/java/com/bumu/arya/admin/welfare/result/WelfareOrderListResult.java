package com.bumu.arya.admin.welfare.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by CuiMengxin on 16/9/8.
 * 订单列表
 */
@ApiModel
public class WelfareOrderListResult {

    @ApiModelProperty("订单列表")
    List<WelfareOrderResult> orders;

    @ApiModelProperty("总页数")
    int pages;

    public List<WelfareOrderResult> getOrders() {
        return orders;
    }

    public void setOrders(List<WelfareOrderResult> orders) {
        this.orders = orders;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public static class WelfareOrderResult {

        @ApiModelProperty("订单id")
        String id;

        @ApiModelProperty("订单编号")
        @JsonProperty("order_no")
        String orderNo;

        @ApiModelProperty("订单状态，取值参考订单状态定义")
        @JsonProperty("order_status")
        int orderStatus;

        @ApiModelProperty("产品id")
        @JsonProperty("goods_id")
        String goodsId;

        @ApiModelProperty("收货人信息")
        @JsonProperty("receiver_name")
        String receiverName;

        @ApiModelProperty("收货人手机号")
        @JsonProperty("receiver_phone")
        String receiverPhone;

        @ApiModelProperty("收货人地址")
        @JsonProperty("receiver_address")
        String receiverAddress;

        @ApiModelProperty("收货人工作")
        @JsonProperty("receiver_corp")
        String receiverCorp;

        @ApiModelProperty("收货人部门")
        @JsonProperty("receiver_department")
        String receiverDepartment;

        @ApiModelProperty("收货人详细地址")
        @JsonProperty("receiver_address_detail")
        String receiverAddressDetail;

        @ApiModelProperty("产品名称")
        @JsonProperty("good_name")
        String goodName;

        @ApiModelProperty("产品规格名称")
        @JsonProperty("good_spec_name")
        String goodSpecName;

        @ApiModelProperty("品牌")
        String brand;

        @ApiModelProperty("购买数量")
        @JsonProperty("good_count")
        long goodCount;

        @ApiModelProperty("总金额")
        BigDecimal payment;

        @ApiModelProperty("账户余额支付部分")
        @JsonProperty("pay_balance")
        BigDecimal payBalance;

        @ApiModelProperty("支付平台 0:支付宝 1：福库劵 2：钱包余额 3：钱包银行卡")
        @JsonProperty("pay_platform_type")
        Integer payPlatformType;

        @ApiModelProperty("在线支付部分")
        @JsonProperty("pay_online")
        BigDecimal payOnline;

        @ApiModelProperty("创建时间")
        @JsonProperty("create_time")
        Long createTime;

        @ApiModelProperty("把创建时间戳转换成YYYY/MM/DD HH:mm:SS字符串")
        @JsonProperty("createTimeStr")
        String createTimeStr;

        public Integer getPayPlatformType() {
            return payPlatformType;
        }

        public void setPayPlatformType(Integer payPlatformType) {
            this.payPlatformType = payPlatformType;
        }

        public String getReceiverAddressDetail() {
            return receiverAddressDetail;
        }

        public void setReceiverAddressDetail(String receiverAddressDetail) {
            this.receiverAddressDetail = receiverAddressDetail;
        }

        public int getOrderStatus(){return orderStatus;}

        public void setOrderStatus(int orderStatus){this.orderStatus = orderStatus;}

        public String getGoodsId() {
            return goodsId;
        }

        public String getReceiverCorp() {
            return receiverCorp;
        }

        public void setReceiverCorp(String receiverCorp) {
            this.receiverCorp = receiverCorp;
        }

        public String getReceiverDepartment() {
            return receiverDepartment;
        }

        public void setReceiverDepartment(String receiverDepartment) {
            this.receiverDepartment = receiverDepartment;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public String getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public String getGoodSpecName() {
            return goodSpecName;
        }

        public void setGoodSpecName(String goodSpecName) {
            this.goodSpecName = goodSpecName;
        }

        public long getGoodCount() {
            return goodCount;
        }

        public void setGoodCount(long goodCount) {
            this.goodCount = goodCount;
        }

        public BigDecimal getPayment() {
            return payment;
        }

        public void setPayment(BigDecimal payment) {
            this.payment = payment;
        }

        public BigDecimal getPayBalance(){return payBalance;}

        public void setPayBalance(BigDecimal payBalance){this.payBalance = payBalance;}

        public BigDecimal getPayOnline(){return payOnline;}

        public void setPayOnline(BigDecimal payOnline){this.payOnline = payOnline;}

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }
    }
}
