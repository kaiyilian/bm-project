package com.bumu.arya.salary.calculate.factor.humanpool;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.HumanpoolSalaryConfig;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.apache.commons.math3.analysis.function.Divide;
import org.springframework.stereotype.Component;

/**
 * 全勤奖
 */
@Calculate
@Component
public class FulltimeBonusFactor implements Factor {

    Divide divide = new Divide();

    @Formula(value = "FULLTIME_BONUS", title = "全勤奖")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("ABSENSE_DAYS") Value<Float> absenceDays,
            @CalculateParam("NEW_LEAVE_DAYS") Value<Float> newLeaveDays,
            @CalculateParam("ILL_DAYS") Value<Float> illDays,
            @CalculateParam("AFFAIR_DAYS") Value<Float> affairDays) {

        Float result;

        HumanpoolSalaryConfig salaryConfig = (HumanpoolSalaryConfig) salaryContext.getSalaryConfig();

        if (absenceDays.getValue() <= 0.5f
                && newLeaveDays.getValue() == 0f
                && illDays.getValue() < 1f
                && affairDays.getValue() < 0.5f) {
            result = salaryConfig.getFulltimeBonus().get(2);
        }
        else if (absenceDays.getValue() <= 0.5f
                && newLeaveDays.getValue() == 0f
                && (illDays.getValue() >= 1f && illDays.getValue() < 2f)
                && affairDays.getValue() < 0.5f) {
            result = salaryConfig.getFulltimeBonus().get(1);
        }
        else {
            result = salaryConfig.getFulltimeBonus().get(0);
        }

        return new Value<>(result);
    }
}
