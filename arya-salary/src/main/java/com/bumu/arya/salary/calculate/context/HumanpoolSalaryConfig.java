package com.bumu.arya.salary.calculate.context;

import com.bumu.arya.salary.model.SalaryCalculateRuleBounModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 汇思蓝领员工计算配置
 * （从数据库加载）
 * Created by allen on 2017/7/26.
 */
public class HumanpoolSalaryConfig extends BaseSalaryConfig implements SalaryConfig {

    /**
     * 病假扣款比例
     */
    Float illSubRatio = 0.4f;

    /**
     * 事假扣款比例
     */
    Float affairSubRatio = 1f;

    /**
     * (非新进/离职员工)旷工扣款比例
     */
    Float absenceSubRatio = 4f;

    /**
     * 新进/离职员工旷工扣款比例
     */
    Float newLeaveAbsenceSubRatio = 3f;

    /**
     * 之前累计税前薪资之和
     */
    Float taxableSalaryTotal = 0f;

    /**
     * 全勤奖金（分三档）
     */
    List<Float> fulltimeBonus = new ArrayList<Float>() {
        {
            add(0f);
            add(100f);
            add(200f);
        }
    };

    /**
     * 从全局配置中初始化当前的薪资配置
     *
     * @param globalConfig
     */
    public HumanpoolSalaryConfig(GlobalConfig globalConfig) {
        super(globalConfig);
    }

    public Float getIllSubRatio() {
        return illSubRatio;
    }

    public void setIllSubRatio(Float illSubRatio) {
        this.illSubRatio = illSubRatio;
    }

    public Float getAbsenceSubRatio() {
        return absenceSubRatio;
    }

    public void setAbsenceSubRatio(Float absenceSubRatio) {
        this.absenceSubRatio = absenceSubRatio;
    }

    public Float getAffairSubRatio() {
        return affairSubRatio;
    }

    public void setAffairSubRatio(Float affairSubRatio) {
        this.affairSubRatio = affairSubRatio;
    }

    public Float getNewLeaveAbsenceSubRatio() {
        return newLeaveAbsenceSubRatio;
    }

    public void setNewLeaveAbsenceSubRatio(Float newLeaveAbsenceSubRatio) {
        this.newLeaveAbsenceSubRatio = newLeaveAbsenceSubRatio;
    }

    public Float getTaxableSalaryTotal() {
        return taxableSalaryTotal;
    }

    public void setTaxableSalaryTotal(Float taxableSalaryTotal) {
        this.taxableSalaryTotal = taxableSalaryTotal;
    }

    public List<Float> getFulltimeBonus() {
        return fulltimeBonus;
    }

    public void setFulltimeBonus(List<Float> fulltimeBonus) {
        this.fulltimeBonus = fulltimeBonus;
    }

    public void initFullTimeBouns(List<SalaryCalculateRuleBounModel> fulltimeBonuList) {
        fulltimeBonus.clear();
        SalaryCalculateRuleBounModel temp;
        for (int i = 0; i < fulltimeBonuList.size(); i++) {
            for (int j = i + 1; j < fulltimeBonuList.size(); j++) {
                if (fulltimeBonuList.get(i).getLeval() > fulltimeBonuList.get(j).getLeval()) {
                    temp = fulltimeBonuList.get(i);
                    fulltimeBonuList.set(i, fulltimeBonuList.get(j));
                    fulltimeBonuList.set(j, temp);
                }
            }
            fulltimeBonus.add(fulltimeBonuList.get(i).getBonu().floatValue());
        }
    }
}
