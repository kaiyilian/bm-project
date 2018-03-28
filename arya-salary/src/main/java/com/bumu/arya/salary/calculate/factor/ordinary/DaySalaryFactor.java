package com.bumu.arya.salary.calculate.factor.ordinary;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static javafx.scene.input.KeyCode.Z;

/**
 * 日薪
 */
@Calculate
@Component
public class DaySalaryFactor implements Factor {

    @Formula(value = "DAY_SALARY", title = "日薪")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("BASE_SALARY") Value<Float> baseSalary,
            @CalculateParam("WORK_DAYS") Value<Float> workDays) {

        Float result;
        if (baseSalary.getValue() == null || workDays.getValue() == null || !(workDays.getValue() > 0)) {
            result = 0f;
        } else {
            result = new BigDecimal(baseSalary.getValue() / (workDays.getValue())).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }

        Value resultValue = new Value(result);
        return resultValue;
    }
}
