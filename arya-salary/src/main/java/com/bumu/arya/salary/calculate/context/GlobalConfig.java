package com.bumu.arya.salary.calculate.context;

/**
 * 全局配置，会被企业配置覆盖
 * Created by allen on 2017/7/10.
 */
public class GlobalConfig {
    /**
     * 个税起征点
     */
    Float taxThrottle = 3500f;

    public Float getTaxThrottle() {
        return taxThrottle;
    }

    public void setTaxThrottle(Float taxThrottle) {
        this.taxThrottle = taxThrottle;
    }
}
