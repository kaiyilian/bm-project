package com.bumu.arya.salary.calculate.factor.humanpool;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

/**
 * 新进离职/缺勤小时数
 */
@Calculate
@Component
public class NewLeaveHoursFactor implements Factor {

    @Formula(value = "NEW_LEAVE_HOURS", title = "新进离职/缺勤小时数")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("SCHEDULE_DAYS") Value<Float> scheduleDays,
            @CalculateParam("WORK_HOURS") Value<Float> workHours,
            @CalculateParam("ILL_DAYS") Value<Float> illDays,
            @CalculateParam("ABSENSE_DAYS") Value<Float> absenceDays,
            @CalculateParam("ANNUAL_DAYS") Value<Float> annualDays,
            @CalculateParam("MARRY_DAYS") Value<Float> marryDays) {

        Float result;

        Float day2hours = (illDays.getValue() + absenceDays.getValue() + annualDays.getValue() + marryDays.getValue()) * 8;

        result = scheduleDays.getValue() * 8 - workHours.getValue() - day2hours;

        if (result < 0) {
            result = 0f;
        }

        Value resultValue = new Value(result);
        return resultValue;
    }
}
