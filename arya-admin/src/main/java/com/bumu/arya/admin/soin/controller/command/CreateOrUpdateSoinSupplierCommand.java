package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * Created by CuiMengxin on 16/8/8.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateOrUpdateSoinSupplierCommand {

    String id;

    String name;

    BigDecimal fee;

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

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
