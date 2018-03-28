package com.bumu.arya.salary.calculate.context;

import com.bumu.arya.salary.model.SalaryCalculateRuleBounModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 汇思蓝领员工计算配置
 * （从数据库加载）
 * Created by allen on 2017/7/26.
 */
public class OrdinarySalaryConfig extends BaseSalaryConfig implements SalaryConfig {

    /**
     * 事假扣款比例
     */
    Float affairSubRatio = 1f;

    /**
     * 病假扣款比例
     */
    Float illSubRatio = 0.4f;

    /**
     * 之前累计税前薪资之和
     */
    Float taxableSalaryTotal = 0f;

    /**
     * 从全局配置中初始化当前的薪资配置
     *
     * @param globalConfig
     */
    public OrdinarySalaryConfig(GlobalConfig globalConfig) {
        super(globalConfig);
    }

    public Float getIllSubRatio() {
        return illSubRatio;
    }

    public void setIllSubRatio(Float illSubRatio) {
        this.illSubRatio = illSubRatio;
    }

    public Float getAffairSubRatio() {
        return affairSubRatio;
    }

    public void setAffairSubRatio(Float affairSubRatio) {
        this.affairSubRatio = affairSubRatio;
    }

    public Float getTaxableSalaryTotal() {
        return taxableSalaryTotal;
    }

    public void setTaxableSalaryTotal(Float taxableSalaryTotal) {
        this.taxableSalaryTotal = taxableSalaryTotal;
    }
}
