package com.bumu.arya.salary.calculate.annotation;

import com.bumu.common.util.AnnotationUtils;
import com.bumu.arya.salary.calculate.factor.Factor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 *
 * Created by allen on 2017/7/6.
 */
public class SalaryAnnoHelper {

    /**
     * 从 Factor 中抽取出来执行方法的关键信息
     * @param factor
     * @return
     */
    public static FactorMethod extractFactorMethod(Factor factor) {

        FactorMethod factorMethod = new FactorMethod();

        List<Method> methods = AnnotationUtils.getMethodsContainsAnnotation(factor, Formula.class);

        if (methods== null || methods.size() > 1) {
            throw new RuntimeException("Factor 只能有一个计算方法");
        }

        Method method = methods.get(0);

        factorMethod.setMethod(method);

        // 处理返回值注解中的key和title
        Formula methodReturnAnno = method.getDeclaredAnnotation(Formula.class);
        factorMethod.setReturnAnnoKey(methodReturnAnno.value());
        factorMethod.setReturnTitle(methodReturnAnno.title());

        // 处理输入参数中含有注解的值
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            CalculateParam annotation = parameter.getAnnotation(CalculateParam.class);
            if (annotation == null) continue;
            factorMethod.getParamAnnoKeyList().add(annotation.value());
//            factorMethod.getParamAnnoKeys().put(annotation.value(), parameter);
        }
        for (Parameter parameter : parameters) {
            CalculateParams annotation = parameter.getAnnotation(CalculateParams.class);
            if (annotation == null) continue;
            factorMethod.getMultiParamAnnoKeyList().add(annotation.value());
//            factorMethod.getMultiParamAnnoKeys().put(annotation.value(), parameter);
        }

        return factorMethod;


//        try {
//            factorClass.getClass().getDeclaredMethod("calculate", Value[].class, SalaryContext.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }

    }

}
