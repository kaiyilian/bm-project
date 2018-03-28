package com.bumu.arya.salary.service.impl;

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
import com.bumu.arya.salary.service.CalculateEngineDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 2017/7/25.
 */
@Service
public class CalculateEngineDemoServiceImpl implements CalculateEngineDemoService {


    @Autowired
    SalaryCalculateEngine salaryCalculateEngine;

    @Autowired
    GlobalConfig globalConfig;

    @Override
    public void doit() {

        float taxToSub = 30f;

        List<SalaryModel> salaryModelList = new ArrayList<>();
        {
            SalaryModel salaryModel = new SalaryModel();
            salaryModel.addValue("SALARY_BEFORE", new Value(5000f, "工资"));
            salaryModel.addValue("TAX_SUB", new Value(0f, "上次发薪扣税"));
            salaryModelList.add(salaryModel);
        }
        {
            SalaryModel salaryModel = new SalaryModel();
            salaryModel.addValue("SALARY_BEFORE", new Value(8000f, "工资"));
            salaryModel.addValue("TAX_SUB", new Value(0f, "上次发薪扣税"));
            salaryModelList.add(salaryModel);
        }
        {
            SalaryModel salaryModel = new SalaryModel();
            salaryModel.addValue("SALARY_BEFORE", new Value(25000f, "工资"));
            salaryModel.addValue("TAX_SUB", new Value(taxToSub, "上次发薪扣税")); // 只是模拟扣减，不是真实计算所得
            salaryModelList.add(salaryModel);
        }

        BumuCalculateSuite calculateSuite = new BumuCalculateSuite();

        calculateSuite.setBumuTaxFactor(new BumuTaxFactor());
        calculateSuite.setBumuServiceFactor(new BumuServiceFactor());
        calculateSuite.setBumuSalaryAfterFactor(new BumuSalaryAfterFactor());

        calculateSuite.init();

        BumuSalaryConfig bumuSalaryConfig = new BumuSalaryConfig(globalConfig);
        bumuSalaryConfig.setBrokerageRate(0.02f);


        List<SalaryModel> result = salaryCalculateEngine.calculate(calculateSuite, bumuSalaryConfig, salaryModelList);


        InfoModel calculateInfo = salaryCalculateEngine.getCalculateInfo();

        for (int i = 0; i < calculateInfo.size(); i++) {
            List<InfoModel.Info> errors = calculateInfo.getInfos(i);
            for (InfoModel.Info error : errors) {
                System.out.println(error);
            }
        }

        for (SalaryModel model : result) {
            System.out.println();
            for (String k : model.getValues().keySet()) {
                System.out.printf("%s[%s]=%s%n", k, model.getValue(k).getTitle(), model.getValue(k).getValue());
            }
        }
    }
}
