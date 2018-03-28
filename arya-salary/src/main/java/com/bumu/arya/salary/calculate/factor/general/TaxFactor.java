package com.bumu.arya.salary.calculate.factor.general;

import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.context.*;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 个税计算
 * TODO
 * Created by allen on 2017/6/23.
 */
@Calculate
@Component
public class TaxFactor implements Factor {

    @Formula(value = "TAX", title = "个税")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("TAXABLE_SALARY") Value<Float> salaryBefore) {

        BaseSalaryConfig salaryConfig = (BaseSalaryConfig) salaryContext.getSalaryConfig();

        Float result = 0f;

        float s1 = (salaryBefore.getValue() - salaryConfig.getTaxThrottle());

        Map<Range<Float>, TaxLevel> taxRatios = salaryConfig.getTaxRatios();

        for (Range<Float> floatRange : taxRatios.keySet()) {

            if (floatRange.contains(s1)) {

                TaxLevel taxLevel = taxRatios.get(floatRange);

                result = (s1 * taxLevel.getRatio()) - taxLevel.getSubstract();
                break;
            }
        }

        if (result == null) {
            salaryContext.getInfoModel().addInfo(salaryContext.getCurrentRow(), "没有计算出个税，可能是个税参数配置错误");
        }

        return new Value(new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
    }

}
