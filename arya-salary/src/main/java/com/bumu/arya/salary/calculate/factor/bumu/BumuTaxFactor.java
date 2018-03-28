package com.bumu.arya.salary.calculate.factor.bumu;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.BumuSalaryConfig;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.context.TaxLevel;
import com.bumu.arya.salary.calculate.factor.Factor;
import com.bumu.arya.salary.common.SalaryEnum;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 按照不木计算规则计算个税（处理费），含扣减前几次计算所得个税
 * Created by allen on 2017/7/19.
 */
@Calculate
@Component
public class BumuTaxFactor implements Factor {

    /**
     * @param salaryContext
     * @param taxableSalary  税前薪资
     * @param taxSub        个税计算后扣减的个税
     * @return
     */
    @Formula(value = "tax", title = "个税处理费")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("taxableSalary") Value<Float> taxableSalary,
            @CalculateParam("tax_sub") Value<Float> taxSub) {

        // TODO 此处实现需要改进，这样实现会导致Factor类很难复用
        BumuSalaryConfig salaryConfig = (BumuSalaryConfig) salaryContext.getSalaryConfig();

        salaryConfig.getBrokerageRate();
        Map<Range<Float>, Float> bumuTaxRatios = salaryConfig.getBumuTaxRatios();

        float taxBeforeSub = 0f;

        if (salaryConfig.getRuleType() == SalaryEnum.RuleType.defined) {
            for (Range<Float> salaryRange : bumuTaxRatios.keySet()) {
                if (taxableSalary.getValue().floatValue() >= salaryRange.getMinimum() &&
                        taxableSalary.getValue().floatValue() < salaryRange.getMaximum()) {
                    Float taxRatio = bumuTaxRatios.get(salaryRange);

                    // 计算本次个税
                    taxBeforeSub = taxableSalary.getValue() * taxRatio;
                }
            }
        }
        if (salaryConfig.getRuleType() == SalaryEnum.RuleType.standard) {
            float taxAfterThrottle = taxableSalary.getValue() - salaryConfig.getTaxThrottle();
            for (Range<Float> salaryRange : salaryConfig.getTaxRatios().keySet()) {
                if (taxAfterThrottle >= salaryRange.getMinimum() && taxAfterThrottle < salaryRange.getMaximum()) {
                    TaxLevel taxLevel = salaryConfig.getTaxRatios().get(salaryRange);

                    // 计算本次个税
                    taxBeforeSub = taxAfterThrottle * taxLevel.getRatio() - taxLevel.getSubstract();
                }
            }
        }


        // 扣减前几次计算所得个税
        float taxAfterSub = taxBeforeSub;
        if (taxBeforeSub > 0) {
            if (taxSub != null && taxSub.getValue() != null) {
                taxAfterSub = taxBeforeSub - taxSub.getValue();
            }
        }

        // 没有合适的税率就是没有个税（服务费）
        return new Value(new BigDecimal(taxAfterSub).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
//        InfoModel infoModel = salaryContext.getInfoModel();
//        infoModel.addInfo(salaryContext.getCurrentRow(), "个税服务费计算错误", "没有找到对应的");

    }


}
