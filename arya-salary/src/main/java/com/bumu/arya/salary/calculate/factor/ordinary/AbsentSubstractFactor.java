package com.bumu.arya.salary.calculate.factor.ordinary;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

/**
 * 缺勤扣款
 */
@Calculate
@Component
public class AbsentSubstractFactor implements Factor {

    @Formula(value = "ABSENT_SUB", title = "缺勤扣款")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("DAY_SALARY") Value<Float> daySalary,
            @CalculateParam("ABSENSE_DAYS") Value<Float> absenseDays) {

        Float result;
        if (null == daySalary.getValue() || null == absenseDays.getValue()) {
            Value resultValue = new Value(0f);
            return resultValue;
        }

        result = daySalary.getValue() * absenseDays.getValue();

        Value resultValue = new Value(result);
        return resultValue;
    }
}
