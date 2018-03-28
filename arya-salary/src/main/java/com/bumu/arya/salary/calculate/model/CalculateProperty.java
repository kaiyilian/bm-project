package com.bumu.arya.salary.calculate.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO
 * Created by allen on 2017/7/24.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CalculateProperty {

    /**
     * 对应的计算引擎的Key
     * @return
     */
    String value() default "";
}
