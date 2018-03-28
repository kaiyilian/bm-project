package com.bumu.arya.salary.calculate.factor.general;

import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.CalculateParams;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.apache.commons.jexl2.parser.ParserConstants.plus;

/**
 * 实发薪资
 * Created by allen on 2017/7/5.
 */
@Calculate
@Component
public class SalaryAfterFactor implements Factor {

    @Formula(value = "NET_SALARY", title = "实发薪资")
    public Value<Float> calculateSalaryAfter(
            SalaryContext salaryContext,
            @CalculateParam("TAXABLE_SALARY") Value<Float> salaryBefore,
            @CalculateParam("TAX") Value<Float> tax) {

        float result = salaryBefore.getValue() - tax.getValue();

        return new Value(result);
    }
}
