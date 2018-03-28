package com.bumu.arya.salary.calculate;

import com.bumu.arya.salary.calculate.context.SalaryConfig;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.model.InfoModel;
import com.bumu.arya.salary.calculate.model.SalaryModel;
import com.bumu.arya.salary.calculate.suite.CalculateSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 薪资计算引擎 v1.0。
 * 通过向calculate方法传入计算套件，一个计算引擎实例可以计算不同规则的薪资。
 * 注意引擎的计算不是线程安全的
 * <p>
 * Created by allen on 2017/6/22.
 */
@Component
public class SalaryCalculateEngine {

    Logger log = LoggerFactory.getLogger(SalaryCalculateEngine.class);

    SalaryContext salaryContext;

    private static SalaryCalculateEngine salaryCalculateEngine = new SalaryCalculateEngine();

    public static SalaryCalculateEngine getInstance() {
        return salaryCalculateEngine;
    }

    /**
     * 一次批量计算薪资
     *
     * @param calculateSuite       计算套件
     * @param standardSalaryConfig 计算配置
     * @param salaryModelList      导入的数据模型
     * @return
     */
    public List<SalaryModel> calculate(CalculateSuite calculateSuite,
                                       SalaryConfig standardSalaryConfig,
                                       List<SalaryModel> salaryModelList) {

        // 薪资计算上下文初始化（生命周期从每一次计算开始）
        salaryContext = new SalaryContext();
        salaryContext.setSalaryConfig(standardSalaryConfig);

        log.info(String.format("开始计算，共 %d 行", salaryModelList.size()));

        // 遍历所有记录逐行进行计算
        for (int i = 0; i < salaryModelList.size(); i++) {
            SalaryModel salaryModel = salaryModelList.get(i);
            try {
                salaryContext.setCurrentRow(i);
                calculateSuite.doCalculate(salaryContext, salaryModel);
            } catch (Exception e) {
                e.printStackTrace();
                salaryContext.getInfoModel();
                // TODO 记录异常至Context
            }
        }

        log.info(String.format("计算完成"));

        // 每个计算公式计算后的结果放入薪资模型中
        return salaryModelList;
    }

    /**
     * 获取引擎最近一次计算的信息
     *
     * @return
     */
    public InfoModel getCalculateInfo() {
        return salaryContext.getInfoModel();
    }

}
