package com.bumu.arya.salary.calculate.factor.humanpool;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.HumanpoolSalaryConfig;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 病假扣款
 */
@Calculate
@Component
public class IllSubFactor implements Factor {

    @Formula(value = "ILL_SUB", title = "病假扣款")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("BASE_SALARY") Value<Float> baseSalary,
            @CalculateParam("SCHEDULE_DAYS") Value<Float> scheduleDays,
            @CalculateParam("ILL_DAYS") Value<Float> illDays) {

        Float illSubRatio = ((HumanpoolSalaryConfig) salaryContext.getSalaryConfig()).getIllSubRatio();
        Float result;
        result = new BigDecimal((baseSalary.getValue() / scheduleDays.getValue()) * illDays.getValue() * illSubRatio).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return new Value(result);
    }
}
