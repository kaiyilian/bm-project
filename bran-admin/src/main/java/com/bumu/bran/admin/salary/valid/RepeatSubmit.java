package com.bumu.bran.admin.salary.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author majun
 * @date 2016/9/21
 */
@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface RepeatSubmit {
    boolean verify() default false;
    String key();
}
