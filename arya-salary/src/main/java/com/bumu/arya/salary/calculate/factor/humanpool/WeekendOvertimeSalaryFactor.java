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
 * 休日加班工资
 */
@Calculate
@Component
public class WeekendOvertimeSalaryFactor implements Factor {

    @Formula(value = "WEEKEND_OVERTIME_SALARY", title = "休日加班工资")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("BASE_SALARY_OVERTIME") Value<Float> baseSalaryOvertime,
            @CalculateParam("WEEKEND_OVERTIME") Value<Float> weekendOvertime) {

        Float result;

        result = new BigDecimal((baseSalaryOvertime.getValue() / (STANDARD_MONTH_WORK_DAYS * 8)) * 2f * weekendOvertime.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

        return new Value(result);
    }
}
