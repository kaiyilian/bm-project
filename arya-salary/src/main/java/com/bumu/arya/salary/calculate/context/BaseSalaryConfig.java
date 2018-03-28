package com.bumu.arya.salary.calculate.context;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.Range;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by allen on 2017/7/19.
 */
public abstract class BaseSalaryConfig {

    /**
     * 个税起征点
     */
    Float taxThrottle = 3500f;

    /**
     * 标准税率（不包含结束范围值）TODO 可能需要再封装
     */
    Map<Range<Float>, TaxLevel> taxRatios = new HashMap() {
        {
            put(Range.between(0f, 1500f), new TaxLevel(0.03f, 0f)); // <500
            put(Range.between(1501f, 4500f), new TaxLevel(0.10f, 105f)); // >=500 & <2000
            put(Range.between(4501f, 9000f), new TaxLevel(0.20f, 555f));
            put(Range.between(9001f, 35000f), new TaxLevel(0.25f, 1005f));
            put(Range.between(35001f, 55000f), new TaxLevel(0.30f, 2755f));
            put(Range.between(55001f, 80000f), new TaxLevel(0.35f, 5505f));
            put(Range.between(80001f, Float.MAX_VALUE), new TaxLevel(0.45f, 13505f));
        }
    };

    /**
     * 从全局配置中初始化当前的薪资配置
     * @param globalConfig
     */
    public BaseSalaryConfig(GlobalConfig globalConfig) {
        try {
            BeanUtils.copyProperties(this, globalConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Range<Float>, TaxLevel> getTaxRatios() {
        return taxRatios;
    }

    public void setTaxRatios(Map<Range<Float>, TaxLevel> taxRatios) {
        this.taxRatios = taxRatios;
    }

    public Float getTaxThrottle() {
        return taxThrottle;
    }

    public void setTaxThrottle(Float taxThrottle) {
        this.taxThrottle = taxThrottle;
    }

}
