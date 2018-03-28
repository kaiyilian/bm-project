package com.bumu.arya.salary;

import com.bumu.arya.salary.calculate.SalaryCalculateEngine;
import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.context.BumuSalaryConfig;
import com.bumu.arya.salary.calculate.context.GlobalConfig;
import com.bumu.arya.salary.calculate.factor.bumu.BumuSalaryAfterFactor;
import com.bumu.arya.salary.calculate.factor.bumu.BumuServiceFactor;
import com.bumu.arya.salary.calculate.factor.bumu.BumuTaxFactor;
import com.bumu.arya.salary.calculate.model.InfoModel;
import com.bumu.arya.salary.calculate.model.SalaryModel;
import com.bumu.arya.salary.calculate.suite.BumuCalculateSuite;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 不木薪资计算测试 TODO
 * Created by allen on 2017/7/6.
 */
public class BumuSalaryCalculateEngineTest {

    GlobalConfig globalConfig = new GlobalConfig();

    @Test
    public void test() {
        SalaryCalculateEngine calculateEngine = SalaryCalculateEngine.getInstance();

        float taxToSub = 30f;

        List<SalaryModel> salaryModelList = new ArrayList<>();
        {
            SalaryModel salaryModel = new SalaryModel();
            salaryModel.addValue("taxableSalary", new Value(5000f, "工资"));
            salaryModel.addValue("tax_sub", new Value(0f, "上次发薪扣税"));
            salaryModelList.add(salaryModel);
        }
        {
            SalaryModel salaryModel = new SalaryModel();
            salaryModel.addValue("taxableSalary", new Value(8000f, "工资"));
            salaryModel.addValue("tax_sub", new Value(0f, "上次发薪扣税"));
            salaryModelList.add(salaryModel);
        }
        {
            SalaryModel salaryModel = new SalaryModel();
            salaryModel.addValue("taxableSalary", new Value(25000f, "工资"));
            salaryModel.addValue("tax_sub", new Value(taxToSub, "上次发薪扣税")); // 只是模拟扣减，不是真实计算所得
            salaryModelList.add(salaryModel);
        }

        BumuCalculateSuite calculateSuite = new BumuCalculateSuite();

        calculateSuite.setBumuTaxFactor(new BumuTaxFactor());
        calculateSuite.setBumuServiceFactor(new BumuServiceFactor());
        calculateSuite.setBumuSalaryAfterFactor(new BumuSalaryAfterFactor());

        calculateSuite.init();

        BumuSalaryConfig bumuSalaryConfig = new BumuSalaryConfig(globalConfig);
        bumuSalaryConfig.setBrokerageRate(0.02f);


        List<SalaryModel> result = calculateEngine.calculate(calculateSuite, bumuSalaryConfig, salaryModelList);


        InfoModel calculateInfo = calculateEngine.getCalculateInfo();

        for (int i = 0; i < calculateInfo.size(); i++) {
            List<InfoModel.Info> errors = calculateInfo.getInfos(i);
            for (InfoModel.Info error : errors) {
                System.out.println(error);
            }
        }

        Assert.assertEquals(3, result.size());

        for (SalaryModel model : result) {
            Assert.assertEquals(5, model.getValues().size());
            System.out.println();
            for (String k : model.getValues().keySet()) {
                System.out.printf("%s[%s]=%s%n", k, model.getValue(k).getTitle(), model.getValue(k).getValue());
            }
        }

    }
}
