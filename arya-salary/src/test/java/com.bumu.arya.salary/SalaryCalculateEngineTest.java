package com.bumu.arya.salary;

import com.bumu.arya.salary.calculate.SalaryCalculateEngine;
import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.context.GlobalConfig;
import com.bumu.arya.salary.calculate.context.StandardSalaryConfig;
import com.bumu.arya.salary.calculate.factor.general.SalaryAfterFactor;
import com.bumu.arya.salary.calculate.factor.general.TaxFactor;
import com.bumu.arya.salary.calculate.factor.humanpool.AbsenseSubFactor;
import com.bumu.arya.salary.calculate.factor.humanpool.SalaryBeforeFactor;
import com.bumu.arya.salary.calculate.model.SalaryModel;
import com.bumu.arya.salary.calculate.suite.DefaultCalculateSuite;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准薪资计算测试
 * Created by allen on 2017/7/6.
 */
public class SalaryCalculateEngineTest {

    GlobalConfig globalConfig = new GlobalConfig();

    @Test
    public void test() {
        SalaryCalculateEngine calculateEngine = SalaryCalculateEngine.getInstance();

        List<SalaryModel> salaryModelList = new ArrayList<>();
        {
            SalaryModel salaryModel = new SalaryModel();
            salaryModel.addValue("SALARY_BASE", new Value(5000f, "基本工资"));
            salaryModel.addValue("WORK_DAYS", new Value(22f, "工作天数"));
            salaryModel.addValue("ABSENSE_DAYS", new Value(2f, "旷工天数"));
            salaryModelList.add(salaryModel);
        }
        {
            SalaryModel salaryModel = new SalaryModel();
            salaryModel.addValue("SALARY_BASE", new Value(8000f, "基本工资"));
            salaryModel.addValue("WORK_DAYS", new Value(21f, "工作天数"));
            salaryModel.addValue("ABSENSE_DAYS", new Value(1f, "旷工天数"));
            salaryModelList.add(salaryModel);
        }

        DefaultCalculateSuite calculateSuite = new DefaultCalculateSuite();

        calculateSuite.setAbsenseSubFactor(new AbsenseSubFactor());
        calculateSuite.setSalaryBeforeFactor(new SalaryBeforeFactor());
        calculateSuite.setTaxFactor(new TaxFactor());
        calculateSuite.setSalaryAfterFactor(new SalaryAfterFactor());

        calculateSuite.init();

        StandardSalaryConfig standardSalaryConfig = new StandardSalaryConfig(globalConfig);


        System.out.println("起征点: " + standardSalaryConfig.getTaxThrottle());

        List<SalaryModel> result = calculateEngine.calculate(calculateSuite, standardSalaryConfig, salaryModelList);

        Assert.assertEquals(2, result.size());

        for (SalaryModel model : result) {
            Assert.assertEquals(7, model.getValues().size());
            System.out.println();
            for (String k : model.getValues().keySet()) {
                System.out.printf("%s[%s]=%s%n", k, model.getValue(k).getTitle(), model.getValue(k).getValue());
            }
        }

    }
}
