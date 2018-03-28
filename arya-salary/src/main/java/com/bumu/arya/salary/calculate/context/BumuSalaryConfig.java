package com.bumu.arya.salary.calculate.context;

import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.model.SalaryCalculateRuleGearModel;
import org.apache.commons.lang3.Range;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by allen on 2017/7/19.
 */
public class BumuSalaryConfig extends BaseSalaryConfig implements SalaryConfig {

    /**
     * 薪资服务费率（用于计算薪资业务服务费），例如0.02表示2%的费率
     */
    Float brokerageRate;

    /**
     * 薪资服务费
     */
    Float brokerageFee;

    /**
     * 是否个人承担服务费
     */
    Boolean isPersonBeatServiceFee = false;

    /**
     * 不木计税档（TODO 需要改为从用户设置中初始化）
     */
    Map<Range<Float>, Float> bumuTaxRatios = new HashMap();

    /**
     * 薪资计算规则
     */
    SalaryEnum.RuleType ruleType;

    /**
     * 从全局配置中初始化当前的薪资配置
     *
     * @param globalConfig
     */
    public BumuSalaryConfig(GlobalConfig globalConfig) {
        super(globalConfig);
    }

    public Float getBrokerageRate() {
        return brokerageRate;
    }

    public void setBrokerageRate(Float brokerageRate) {
        this.brokerageRate = brokerageRate;
    }

    public Float getBrokerageFee() {
        return brokerageFee;
    }

    public void setBrokerageFee(Float brokerageFee) {
        this.brokerageFee = brokerageFee;
    }

    public Map<Range<Float>, Float> getBumuTaxRatios() {
        return bumuTaxRatios;
    }

    public void setBumuTaxRatios(Map<Range<Float>, Float> bumuTaxRatios) {
        this.bumuTaxRatios = bumuTaxRatios;
    }

    public Boolean getPersonBeatServiceFee() {
        return isPersonBeatServiceFee;
    }

    public void setPersonBeatServiceFee(Boolean personBeatServiceFee) {
        isPersonBeatServiceFee = personBeatServiceFee;
    }

    public SalaryEnum.RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(SalaryEnum.RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public void initTaxRatios(List<SalaryCalculateRuleGearModel> gearModels) {
        Float lastGear = Float.MAX_VALUE;
        for (SalaryCalculateRuleGearModel model : getDescTaxGears(gearModels)) {
            bumuTaxRatios.put(Range.between(model.getGear().floatValue(),lastGear ), model.getTaxRate().floatValue());
            lastGear = model.getGear().floatValue();
        }
    }

    private List<SalaryCalculateRuleGearModel> getDescTaxGears(List<SalaryCalculateRuleGearModel> taxGears) {
        if (taxGears.size() == 0) {
            return taxGears;
        }

        //倒叙排序
        for (int i = 0; i < taxGears.size() - 1; i++) {
            BigDecimal greaterTaxGear = taxGears.get(i).getGear();
            int greaterPosition = i;
            for (int j = i + 1; j < taxGears.size(); j++) {
                SalaryCalculateRuleGearModel tempTaxGear = taxGears.get(j);
                if (tempTaxGear.getGear().compareTo(greaterTaxGear) == 1) {

                    greaterPosition = j;
                    greaterTaxGear = tempTaxGear.getGear();
                }
            }
            //交换位置
            if (greaterPosition != i) {
                SalaryCalculateRuleGearModel tempTaxGear = taxGears.get(i);
                taxGears.set(i, taxGears.get(greaterPosition));
                taxGears.set(greaterPosition, tempTaxGear);
            }
        }

        return taxGears;
    }
}
