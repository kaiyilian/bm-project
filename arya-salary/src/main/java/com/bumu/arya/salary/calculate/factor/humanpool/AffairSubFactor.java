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
 * 事假扣款
 */
@Calculate
@Component
public class AffairSubFactor implements Factor {

    @Formula(value = "AFFAIR_SUB", title = "事假扣款")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("BASE_SALARY") Value<Float> baseSalary,
            @CalculateParam("AFFAIR_DAYS") Value<Float> affairDays) {

        Float result;

        HumanpoolSalaryConfig salaryConfig = (HumanpoolSalaryConfig) salaryContext.getSalaryConfig();

        Float affairSubRatio = salaryConfig.getAffairSubRatio();

        result = new BigDecimal((baseSalary.getValue() / STANDARD_MONTH_WORK_DAYS) * affairDays.getValue() * affairSubRatio).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

        return new Value(result);
    }
}
