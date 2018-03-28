package com.bumu.arya.salary.calculate.model;

import com.bumu.common.util.AnnotationUtils;

import java.lang.reflect.Field;

/**
 * Created by allen on 2017/7/24.
 */
public class SalaryModelAdapter extends SalaryModel {

    public SalaryModelAdapter(Object modelObject) throws IllegalAccessException {

        Field[] declaredFields = modelObject.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            Object fieldValue = declaredField.get(modelObject);
            CalculateProperty fieldAnnotation = (CalculateProperty) AnnotationUtils.getFieldAnnotation(declaredField, CalculateProperty.class);
            if (fieldAnnotation != null) {
                String calcKey = fieldAnnotation.value();
                // TODO 标题信息
//                this.addValue(calcKey, new Value());
            }
        }


    }
}
