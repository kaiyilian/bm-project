package com.bumu.arya.salary.calculate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置 TBD
 * Created by allen on 2017/7/5.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    /**
     *
     * @return
     */
    String value() default "";
}
