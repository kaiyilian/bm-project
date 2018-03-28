package com.bumu.arya.salary.calculate.factor.bumu;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.BumuSalaryConfig;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import com.bumu.arya.salary.common.SalaryEnum;
import org.springframework.stereotype.Component;

/**
 * 计算税后薪资
 * Created by allen on 2017/7/5.
 */
@Calculate
@Component
public class BumuSalaryAfterFactor implements Factor {

    @Formula(value = "netSalary", title = "税后薪资")
    public Value<Float> calculateSalaryAfter(
            SalaryContext salaryContext,
            @CalculateParam("taxableSalary") Value<Float> taxableSalary,
            @CalculateParam("tax") Value<Float> tax,
            @CalculateParam("brokerage") Value<Float> brokerage) {
        BumuSalaryConfig salaryConfig = (BumuSalaryConfig) salaryContext.getSalaryConfig();

        float result = 0f;

        if (salaryConfig.getRuleType() == SalaryEnum.RuleType.defined && salaryConfig.getPersonBeatServiceFee()) {
            result = taxableSalary.getValue() - tax.getValue() - brokerage.getValue();
        } else {
            result = taxableSalary.getValue() - tax.getValue();
        }
        return new Value(result);
    }
}
