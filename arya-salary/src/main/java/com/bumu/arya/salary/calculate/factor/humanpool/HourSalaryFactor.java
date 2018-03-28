package com.bumu.arya.salary.calculate.factor.humanpool;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.apache.commons.math3.analysis.function.Divide;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 小时薪资
 */
@Calculate
@Component
public class HourSalaryFactor implements Factor {

    @Formula(value = "HOUR_SALARY", title = "小时薪资")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("BASE_SALARY") Value<Float> baseSalary,
            @CalculateParam("SCHEDULE_DAYS") Value<Float> scheduleDays) {

        Float result;
        if (scheduleDays.getValue() > STANDARD_MONTH_WORK_DAYS) {
            result = new BigDecimal(baseSalary.getValue() / (scheduleDays.getValue() * 8)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }
        else {
            result = new BigDecimal(baseSalary.getValue() / (STANDARD_MONTH_WORK_DAYS * 8)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }

        Value resultValue = new Value(result);
        return resultValue;
    }
}
