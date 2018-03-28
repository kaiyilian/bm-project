package com.bumu.arya.salary.calculate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示一组相似的计算参数
 * Created by allen on 2017/6/22.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CalculateParams {

    String value() default "";
}
