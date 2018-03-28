package com.bumu.arya.salary.calculate.suite;

import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.annotation.FactorMethod;
import com.bumu.arya.salary.calculate.annotation.SalaryAnnoHelper;
import com.bumu.arya.salary.calculate.context.SalaryContext;
import com.bumu.arya.salary.calculate.factor.Factor;
import com.bumu.arya.salary.calculate.model.SalaryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * init() 方法必须在调用 doCalculate 之前执行进行初始化
 * Created by allen on 2017/7/11.
 */
public abstract class BaseCalculateSuite implements CalculateSuite {
    Logger log = LoggerFactory.getLogger(BaseCalculateSuite.class);

    // Factor 队列，按照队列的顺序执行
    protected Queue<Factor> factorQueue = new LinkedBlockingQueue<>();

    // 顺序和 factorQueue 一一对应
    protected Queue<FactorMethod> factorMethods = new LinkedBlockingQueue<>();

    /**
     * 初始化计算套件之前执行
     */
    public abstract void initBefore();

    @PostConstruct
    @Override
    public void init() {
        initBefore();
        // TODO 获取方法中所有带有 @CalculateParam 注解的参数，并从模型中获取参数值并赋值调用方法（所有项都为必填，如果参数值为空，则抛出异常）
        for (Factor factor : factorQueue) {
            FactorMethod factorMethod = SalaryAnnoHelper.extractFactorMethod(factor);
            factorMethod.setFactor(factor); // 保留反向引用便于执行计算
            factorMethods.add(factorMethod);
        }
    }

    @Override
    public SalaryModel doCalculate(SalaryContext salaryContext, SalaryModel salaryModel) throws Exception {
        // 检查套件初始化状态
        if (factorQueue.isEmpty() || factorMethods.isEmpty()) {
            throw new IllegalStateException("计算套件没有正常初始化");
        }

        // 遍历所有计算公式（按照既定的顺序）从原始薪资模型中取出参数进行计算
        for (FactorMethod factorMethod : factorMethods) {
            // 实际执行的所有参数值（按照定义顺序排列）
            List<Object> exeParams = new ArrayList<>();
            exeParams.add(salaryContext);

            // 单值参数处理
//            for (String key : factorMethod.getParamAnnoKeys().keySet()) {
            for (String key : factorMethod.getParamAnnoKeyList()) {
                Parameter parameter = factorMethod.getParamAnnoKeys().get(key); // 待定使用
                Value value = salaryModel.getValue(key);
                if (value == null) {
                    salaryContext.getInfoModel().addInfo(salaryContext.getCurrentRow(),
                            String.format("%s[%s] 缺少单值参数: %s", factorMethod.getFactor().getClass().getSimpleName(), factorMethod.getReturnTitle(), key));
                }
                exeParams.add(value);
            }

            // 多值参数处理
//            for (String key : factorMethod.getMultiParamAnnoKeys().keySet()) {
            for(String key: factorMethod.getMultiParamAnnoKeyList()) {
                Parameter parameter = factorMethod.getParamAnnoKeys().get(key); // 待定使用
                Set<Value<Float>> values = salaryModel.getMultiValue(key);
                if (values == null) {
                    salaryContext.getInfoModel().addInfo(salaryContext.getCurrentRow(),
                            String.format("%s[%s] 缺少多值参数: %s", factorMethod.getFactor().getClass().getSimpleName(), factorMethod.getReturnTitle(), key));
                }
                exeParams.add(values);
            }

            // 如果存在错误，则终止计算
            if (salaryContext.getInfoModel().hasErrors(salaryContext.getCurrentRow())) {
                log.warn(String.format("第 %d 行存在错误，略过计算", salaryContext.getCurrentRow()));
                return null;
            }

            // 最终执行 Factor 的计算过程，取得返回值并按照 @Formula 注解定义的关键字保存在薪资模型对象中
            log.info("执行计算：" + factorMethod.getMethod());
            Object calcResult = factorMethod.getMethod().invoke(factorMethod.getFactor(), exeParams.toArray());
            Value calcResultValue = (Value) calcResult;
            calcResultValue.setTitle(factorMethod.getReturnTitle());
            salaryModel.addValue(factorMethod.getReturnAnnoKey(), calcResultValue);
        }
        return salaryModel;
    }
}
