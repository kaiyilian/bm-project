package com.bumu.arya.salary.calculate.factor.ordinary;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

/**
 * 实发薪资
 * Created by allen on 2017/7/5.
 */
@Calculate
@Component
public class OrdinarySalaryAfterFactor implements Factor {

    @Formula(value = "NET_SALARY", title = "实发薪资")
    public Value<Float> calculateSalaryAfter(
            SalaryContext salaryContext,
            @CalculateParam("TAXABLE_SALARY") Value<Float> salaryBefore,
            @CalculateParam("TAX") Value<Float> tax,
            @CalculateParam("ORDINARY_BROKERAGE") Value<Float> ordinaryBrokerage) {

        float result = salaryBefore.getValue() - tax.getValue() - (null == ordinaryBrokerage.getValue() ? 0 : ordinaryBrokerage.getValue());

        return new Value(result);
    }
}
