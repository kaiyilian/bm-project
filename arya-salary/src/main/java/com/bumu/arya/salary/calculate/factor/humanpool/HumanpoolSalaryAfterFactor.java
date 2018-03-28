package com.bumu.arya.salary.calculate.factor.humanpool;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.CalculateParams;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 实发薪资
 * Created by allen on 2017/7/5.
 */
@Calculate
@Component
public class HumanpoolSalaryAfterFactor implements Factor {

    @Formula(value = "NET_SALARY", title = "实发薪资")
    public Value<Float> calculateSalaryAfter(
            SalaryContext salaryContext,
            @CalculateParam("TAXABLE_SALARY") Value<Float> salaryBefore,
            @CalculateParam("TAX") Value<Float> tax,
            @CalculateParam("RECEIVE_SALARY") Value<Float> receiveSalary,
            @CalculateParams("AT_PLUS") Set<Value<Float>> atPlus,
            @CalculateParams("AT_SUBSTRACT") Set<Value<Float>> atSubstract) {

        float result = salaryBefore.getValue() - tax.getValue() - receiveSalary.getValue();

        for (Value<Float> floatValue : atPlus) {
            result += floatValue.getValue();
        }

        for (Value<Float> floatValue : atSubstract) {
            result -= floatValue.getValue();
        }

        return new Value(result);
    }
}
