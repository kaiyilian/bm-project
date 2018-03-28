package com.bumu.arya.salary.calculate.factor.ordinary;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

import static com.bumu.arya.salary.common.SalaryEnum.BillProject.work;

/**
 * 实际出勤天数
 */
@Calculate
@Component
public class WorkAttendanceDaysFactor implements Factor {

    @Formula(value = "WORK_ATTENDANCE_DAYS", title = "实际出勤天数")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("WORK_DAYS") Value<Float> workDays,
            @CalculateParam("AFFAIR_DAYS") Value<Float> affairDays,
            @CalculateParam("ILL_DAYS") Value<Float> illDays,
            @CalculateParam("ABSENSE_DAYS") Value<Float> absenceDays,
            @CalculateParam("ANNUAL_DAYS") Value<Float> annualDays,
            @CalculateParam("MARRY_DAYS") Value<Float> marryDays,
            @CalculateParam("PRECREATE_DAYS") Value<Float> precreateDays,
            @CalculateParam("FUNERAL_DAYS") Value<Float> funeralDays) {

        Float result;
        if (null == workDays || !(0 < workDays.getValue())) {
            Value resultValue = new Value(0f);
            return resultValue;
        }
        result = workDays.getValue() -
                (null == affairDays.getValue() ? 0 : affairDays.getValue()) -
                (null == illDays.getValue() ? 0 : illDays.getValue()) -
                (null == absenceDays.getValue() ? 0 : absenceDays.getValue()) -
                (null == annualDays.getValue() ? 0 : annualDays.getValue()) -
                (null == marryDays.getValue() ? 0 : marryDays.getValue()) -
                (null == precreateDays.getValue() ? 0 : precreateDays.getValue()) -
                (null == funeralDays.getValue() ? 0 : funeralDays.getValue());

        if (!(result > 0f)) {
            result = 0f;
        }

        Value resultValue = new Value(result);
        return resultValue;
    }
}
