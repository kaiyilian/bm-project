package com.bumu.arya.admin.soin.model;

import java.math.BigDecimal;

/**
 * Created by CuiMengxin on 2016/9/26.
 */
public class SoinTypeMinMaxBaseModel {
    BigDecimal minBase;

    BigDecimal maxBase;

    public SoinTypeMinMaxBaseModel() {
        minBase = new BigDecimal("9999999");
        maxBase = BigDecimal.ZERO;
    }

    public BigDecimal getMinBase() {
        return minBase;
    }

    public void setMinBase(BigDecimal minBase) {
        this.minBase = minBase;
    }

    public BigDecimal getMaxBase() {
        return maxBase;
    }

    public void setMaxBase(BigDecimal maxBase) {
        this.maxBase = maxBase;
    }
}
