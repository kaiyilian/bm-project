package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by CuiMengxin on 16/8/7.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SoinOrderCustomerListResult {

    List<SoinOrderCustomerResult> customer;

    public SoinOrderCustomerListResult() {
    }

    public List<SoinOrderCustomerResult> getCustomer() {
        return customer;
    }

    public void setCustomer(List<SoinOrderCustomerResult> customer) {
        this.customer = customer;
    }

    public static class SoinOrderCustomerResult {
        String id;

        String name;

        public SoinOrderCustomerResult() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
