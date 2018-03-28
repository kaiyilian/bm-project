package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.admin.corporation.controller.command.IdsCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by CuiMengxin on 16/9/2.
 */
public class AdjustSoinOrderOtherPaymentCommand extends IdsCommand {

    @JsonProperty("other_payment")
    BigDecimal otherPayment;

    public BigDecimal getOtherPayment() {
        return otherPayment;
    }

    public void setOtherPayment(BigDecimal otherPayment) {
        this.otherPayment = otherPayment;
    }
}
