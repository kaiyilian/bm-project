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
 * 新进离职/缺勤扣款
 */
@Calculate
@Component
public class NewLeaveSubFactor implements Factor {

    @Formula(value = "NEW_LEAVE_SUB", title = "新进离职/缺勤扣款")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("BASE_SALARY") Value<Float> baseSalary,
            @CalculateParam("SCHEDULE_DAYS") Value<Float> scheduleDays,
            @CalculateParam("NEW_LEAVE_HOURS") Value<Float> newLeaveHours) {

        Float result;
        result = new BigDecimal((baseSalary.getValue() / scheduleDays.getValue()) * newLeaveHours.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return new Value(result);
    }
}
