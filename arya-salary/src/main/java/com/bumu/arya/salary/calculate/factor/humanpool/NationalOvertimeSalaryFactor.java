package com.bumu.arya.salary.calculate.factor.humanpool;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 国假加班工资
 */
@Calculate
@Component
public class NationalOvertimeSalaryFactor implements Factor {

    @Formula(value = "NATIONAL_OVERTIME_SALARY", title = "国假加班工资")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("BASE_SALARY_OVERTIME") Value<Float> baseSalaryOvertime,
            @CalculateParam("NATIONAL_OVERTIME") Value<Float> nationalOvertime) {

        Float result;

        result = new BigDecimal((baseSalaryOvertime.getValue() / (STANDARD_MONTH_WORK_DAYS * 8)) * 3f * nationalOvertime.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

        return new Value(result);
    }
}
