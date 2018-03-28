package com.bumu.arya.salary.calculate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示一个计算公式
 * Created by allen on 2017/6/22.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Formula {

    /**
     * 计算之后的返回值的 key，返回值会按照这个 key 自动被填充到数据模型中
     * @return
     */
    String value() default "";

    /**
     * 返回值标题
     * @return
     */
    String title() default "";
}
