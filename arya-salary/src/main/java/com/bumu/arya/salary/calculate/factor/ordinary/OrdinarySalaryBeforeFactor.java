package com.bumu.arya.salary.calculate.factor.ordinary;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.Calculate;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.CalculateParams;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.HumanpoolSalaryConfig;
import com.bumu.arya.salary.calculate.context.OrdinarySalaryConfig;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 税前薪资计算
 * Created by allen on 2017/6/23.
 */
@Calculate
@Component
public class OrdinarySalaryBeforeFactor implements Factor {

    @Formula(value = "TAXABLE_SALARY", title = "税前薪资")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("BASE_SALARY") Value<Float> baseSalary,
            @CalculateParam("LEAVE_SUB") Value<Float> leaveSub,
            @CalculateParam("ABSENT_SUB") Value<Float> absentSub,
            @CalculateParams("PLUS") Set<Value<Float>> plus,
            @CalculateParams("SUBSTRACT") Set<Value<Float>> substract) {
        OrdinarySalaryConfig ordinarySalaryConfig = (OrdinarySalaryConfig) salaryContext.getSalaryConfig();
        Float result = 0f;

        result = baseSalary.getValue()
                - (null == leaveSub.getValue() ? 0 : leaveSub.getValue())
                - (null == absentSub.getValue() ? 0 : absentSub.getValue());

        for (Value<Float> floatValue : plus) {
            result += floatValue.getValue();
        }

        for (Value<Float> floatValue : substract) {
            result -= floatValue.getValue();
        }
        result += ordinarySalaryConfig.getTaxableSalaryTotal();
        return new Value(result);
    }
}
