package com.bumu.arya.salary.calculate.factor.humanpool;

import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.CalculateParams;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.context.BumuSalaryConfig;
import com.bumu.arya.salary.calculate.context.HumanpoolSalaryConfig;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 税前薪资计算
 * Created by allen on 2017/6/23.
 */
@Calculate
@Component
public class SalaryBeforeFactor implements Factor {

    @Formula(value = "TAXABLE_SALARY", title = "税前薪资")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("BASE_SALARY") Value<Float> baseSalary,
            @CalculateParam("WORKDAY_OVERTIME_SALARY") Value<Float> workdayOvertimeSalary,
            @CalculateParam("WEEKEND_OVERTIME_SALARY") Value<Float> weekendOvertimeSalary,
            @CalculateParam("NATIONAL_OVERTIME_SALARY") Value<Float> nationalOvertimeSalary,
            @CalculateParam("FULLTIME_BONUS") Value<Float> fulltimeBonus,
            @CalculateParam("ILL_SUB") Value<Float> illSub,
            @CalculateParam("ABSENCE_SUB") Value<Float> absenceSub,
            @CalculateParam("NEW_LEAVE_SUB") Value<Float> newLeaveSub,
            @CalculateParam("RECEIVE_SALARY") Value<Float> receiveSalary,
            @CalculateParams("PLUS") Set<Value<Float>> plus,
            @CalculateParams("SUBSTRACT") Set<Value<Float>> substract) {
        HumanpoolSalaryConfig humanpoolSalaryConfig = (HumanpoolSalaryConfig) salaryContext.getSalaryConfig();
        Float result;

        result = baseSalary.getValue() + workdayOvertimeSalary.getValue() + weekendOvertimeSalary.getValue() + nationalOvertimeSalary.getValue() + receiveSalary.getValue()
                + fulltimeBonus.getValue() - illSub.getValue() - absenceSub.getValue() - newLeaveSub.getValue();

        for (Value<Float> floatValue : plus) {
            result += floatValue.getValue();
        }

        for (Value<Float> floatValue : substract) {
            result -= floatValue.getValue();
        }
        result += humanpoolSalaryConfig.getTaxableSalaryTotal();

        return new Value(result);
    }
}
