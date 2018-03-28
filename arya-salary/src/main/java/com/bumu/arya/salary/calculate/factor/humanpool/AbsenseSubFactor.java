package com.bumu.arya.salary.calculate.factor.humanpool;

import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.context.BumuSalaryConfig;
import com.bumu.arya.salary.calculate.context.HumanpoolSalaryConfig;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.apache.commons.math3.analysis.function.Divide;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 旷工计算（旷工时间单位是天数）
 * Created by allen on 2017/6/22.
 */
@Calculate
@Component
public class AbsenseSubFactor implements Factor {

    Divide divide = new Divide();


    @Formula(value = "ABSENCE_SUB", title = "旷工扣款")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("STAFF_STATUS") Value<String> staffStatus,
            @CalculateParam("BASE_SALARY") Value<Float> salaryBase,
            @CalculateParam("ABSENSE_DAYS") Value<Float> absenceDays) {

        Float result;

        HumanpoolSalaryConfig cfg = (HumanpoolSalaryConfig) salaryContext.getSalaryConfig();

        float daySalary = (float) divide.value(salaryBase.getValue(), STANDARD_MONTH_WORK_DAYS);

        // 非新进离职员工
        if ("非新进离职".equals(staffStatus.getValue())) {
            if (absenceDays.getValue() > 3) {
                result = daySalary * cfg.getAbsenceSubRatio() * 3f;
            }
            else {
                result = daySalary * cfg.getAbsenceSubRatio() * absenceDays.getValue();
            }
        }
        else { // 新进离职员工
            if (absenceDays.getValue() > 3) {
                result = daySalary * cfg.getNewLeaveAbsenceSubRatio() * 3f;
            }
            else {
                result = daySalary * cfg.getNewLeaveAbsenceSubRatio() * absenceDays.getValue();
            }
        }

        return new Value<>(result);
    }


    public static void main(String[] args) {
        try {
            Method calculate = AbsenseSubFactor.class.getDeclaredMethod("calculate",
                    SalaryContext.class, Value.class, Value.class, Value.class);
            Parameter[] parameters = calculate.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println(parameter);
                CalculateParam annotation = parameter.getAnnotation(CalculateParam.class);
                if (annotation == null) {
                    continue;
                }
                System.out.println(annotation.value());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
