package com.bumu.arya.salary.calculate.suite;

import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.model.SalaryModel;

/**
 * 薪资计算套件，根据不同的导入模板配置不同的计算套件。
 * Created by allen on 2017/7/10.
 */
public interface CalculateSuite {

    /**
     * 初始化
     */
    void init();

    /**
     * 执行一次（一人）薪资计算
     * @param salaryContext
     * @param salaryModel 一行数据
     * @return
     * @throws Exception
     */
    SalaryModel doCalculate(SalaryContext salaryContext, SalaryModel salaryModel) throws Exception;
}
