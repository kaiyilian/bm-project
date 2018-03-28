package com.bumu.arya.salary.calculate.factor.ordinary;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

/**
 * 应发薪资
 */
@Calculate
@Component
public class GrossSalaryFactor implements Factor {

    @Formula(value = "GROSS_SALARY", title = "应发薪资")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("BASE_SALARY") Value<Float> baseSalary,
            @CalculateParam("LEAVE_SUB") Value<Float> leaveSub,
            @CalculateParam("ABSENT_SUB") Value<Float> absentSub,
            @CalculateParam("OTHER_SUB") Value<Float> otherSub) {

        Float result = baseSalary.getValue() -
                (null == leaveSub.getValue() ? 0f : leaveSub.getValue()) -
                (null == absentSub.getValue() ? 0f : absentSub.getValue()) -
                (null == otherSub.getValue() ? 0f : otherSub.getValue());
        if (!(result > 0f)) {
            result = 0f;
        }

        Value resultValue = new Value(result);
        return resultValue;
    }
}
