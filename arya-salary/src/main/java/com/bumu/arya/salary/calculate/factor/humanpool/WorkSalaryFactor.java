package com.bumu.arya.salary.calculate.factor.humanpool;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.apache.commons.math3.analysis.function.Divide;
import org.springframework.stereotype.Component;

/**
 * 出勤工资
 */
@Calculate
@Component
public class WorkSalaryFactor implements Factor {

    @Formula(value = "WORK_SALARY", title = "出勤工资")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("HOUR_SALARY") Value<Float> hourSalary,
            @CalculateParam("WORK_HOURS") Value<Float> workHours,
            @CalculateParam("ANNUAL_DAYS") Value<Float> annualDays,
            @CalculateParam("PRECREATE_DAYS") Value<Float> precreateDays,
            @CalculateParam("MARRY_DAYS") Value<Float> marryDays) {

        Float result;

        Float hours = (workHours.getValue() + annualDays.getValue() * 8 + precreateDays.getValue() * 8 + marryDays.getValue() * 8);

        result = hourSalary.getValue() * hours;

        Value resultValue = new Value(result);
        return resultValue;
    }
}
