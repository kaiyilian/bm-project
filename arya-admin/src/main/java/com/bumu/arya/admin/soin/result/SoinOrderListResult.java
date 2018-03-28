package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2015/12/21
 */

public class SoinOrderListResult {

    @JsonProperty("total_count")
    int totalCount;

    @JsonProperty("filter_count")
    int filterCount;

    List<SoinOrderResult> orders;

    public List<SoinOrderResult> getOrders() {
        return orders;
    }

    public void setOrders(List<SoinOrderResult> orders) {
        this.orders = orders;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class SoinOrderResult {

        @JsonProperty("order_id")
        String orderId;

        @JsonProperty("order_no")
        String orderNo;

        @JsonProperty("person_name")
        String personName;

        String district;

        @JsonProperty("type_name")
        String typeName;

        @JsonProperty("start_year")
        Integer startYear;

        @JsonProperty("start_month")
        Integer startMonth;

        Integer count;

        @JsonProperty("status_code")
        Integer statusCode;

        String desc;

        @JsonProperty("create_time")
        String createTime;

        String payment;

        /**
         * 订单是否欠费
         */
        Integer arrearage;

        public SoinOrderResult() {
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getOrderId() {
            return orderId;
        }

        public Integer getArrearage() {
            return arrearage;
        }

        public void setArrearage(Integer arrearage) {
            this.arrearage = arrearage;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public Integer getStartYear() {
            return startYear;
        }

        public void setStartYear(Integer startYear) {
            this.startYear = startYear;
        }

        public Integer getStartMonth() {
            return startMonth;
        }

        public void setStartMonth(Integer startMonth) {
            this.startMonth = startMonth;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }
    }


    public int getTotalCount() {

        return totalCount;
    }

    public int getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(int filterCount) {
        this.filterCount = filterCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
