package com.bumu.arya.salary.calculate.context;

/**
 *
 * Created by allen on 2017/7/6.
 */
public class TaxLevel {

    /**
     * 税率
     */
    Float ratio;

    /**
     * 速算扣除数
     */
    Float substract;

    public TaxLevel(Float ratio, Float substract) {
        this.ratio = ratio;
        this.substract = substract;
    }

    public Float getRatio() {
        return ratio;
    }

    public void setRatio(Float ratio) {
        this.ratio = ratio;
    }

    public Float getSubstract() {
        return substract;
    }

    public void setSubstract(Float substract) {
        this.substract = substract;
    }
}
