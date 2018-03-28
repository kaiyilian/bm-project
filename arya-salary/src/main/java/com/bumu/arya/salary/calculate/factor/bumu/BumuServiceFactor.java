package com.bumu.arya.salary.calculate.factor.bumu;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.CalculateParam;
import com.bumu.arya.salary.calculate.annotation.Formula;
import com.bumu.arya.salary.calculate.context.BumuSalaryConfig;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.context.TaxLevel;
import com.bumu.arya.salary.calculate.factor.Factor;
import com.bumu.arya.salary.common.SalaryEnum;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

/**
 * 计算薪资服务费
 * Created by allen on 2017/7/19.
 */
@Component
public class BumuServiceFactor implements Factor {

    @Formula(value = "brokerage", title = "薪资服务费")
    public Value<Float> calculate(
            SalaryContext salaryContext,
            @CalculateParam("taxableSalary") Value<Float> salaryBefore) {

        BumuSalaryConfig salaryConfig = (BumuSalaryConfig) salaryContext.getSalaryConfig();

        System.out.println("brokerageRate: " + salaryConfig.getBrokerageRate());
        System.out.println("Salary before: " + salaryBefore.getValue());

        float brokerage = 0f;

        if (salaryConfig.getRuleType() == SalaryEnum.RuleType.defined) {
            brokerage = salaryConfig.getBrokerageRate() * salaryBefore.getValue();
        }
        if (salaryConfig.getRuleType() == SalaryEnum.RuleType.standard) {
            brokerage = salaryConfig.getBrokerageFee();
        }

        return new Value(brokerage);
    }
}
