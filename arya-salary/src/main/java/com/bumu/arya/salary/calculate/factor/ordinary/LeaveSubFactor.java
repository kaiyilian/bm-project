package com.bumu.arya.salary.calculate.factor.ordinary;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.HumanpoolSalaryConfig;
import com.bumu.arya.salary.calculate.context.OrdinarySalaryConfig;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

/**
 * 请假扣款
 */
@Calculate
@Component
public class LeaveSubFactor implements Factor {

    @Formula(value = "LEAVE_SUB", title = "请假扣款")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("DAY_SALARY") Value<Float> daySalary,
            @CalculateParam("AFFAIR_DAYS") Value<Float> affairDays,
            @CalculateParam("ILL_DAYS") Value<Float> illDays) {
        OrdinarySalaryConfig ordinarySalaryConfig = (OrdinarySalaryConfig) salaryContext.getSalaryConfig();

        Float result = 0f;
        if (null != affairDays.getValue() && null != ordinarySalaryConfig.getAffairSubRatio() && null != daySalary.getValue()) {
            result += daySalary.getValue() * affairDays.getValue() * ordinarySalaryConfig.getAffairSubRatio();
        }
        if (null != illDays.getValue() && null != ordinarySalaryConfig.getIllSubRatio() && null != daySalary.getValue()) {
            result += daySalary.getValue() * illDays.getValue() * ordinarySalaryConfig.getIllSubRatio();
        }
        Value resultValue = new Value(result);
        return resultValue;
    }
}
