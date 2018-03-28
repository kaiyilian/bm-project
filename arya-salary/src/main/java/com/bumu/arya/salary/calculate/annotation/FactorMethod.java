package com.bumu.arya.salary.calculate.annotation;

import com.bumu.arya.salary.calculate.factor.Factor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * 表示一个计算方法，从 Factor 中抽取出来，包含实际执行的方法，和输入输出参数的注解信息
 * Created by allen on 2017/7/6.
 */
public class FactorMethod {

    /**
     * 保留对 Factor 的引用，便于操作
     */
    Factor factor;

    /**
     * 实际计算调用的方法
     */
    Method method;

    /**
     * 返回值注解上标注的关键字
     */
    String returnAnnoKey;

    /**
     * 返回值主街上标注的标题
     */
    String returnTitle;

    /**
     * 单值参数注解Key对应的Parameter对象
     */
    LinkedList<String> paramAnnoKeyList = new LinkedList<>();

    /**
     * 多值参数注解 key 对应的 Parameter 对象
     */
    LinkedList<String> multiParamAnnoKeyList = new LinkedList<>();

    /**
     * 单值参数注解Key对应的Parameter对象
     * @deprecated
     */
    Map<String, Parameter> paramAnnoKeys = new TreeMap<>();

    /**
     * 多值参数注解 key 对应的 Parameter 对象
     * @deprecated
     */
    Map<String, Parameter> multiParamAnnoKeys = new TreeMap<>();

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getReturnAnnoKey() {
        return returnAnnoKey;
    }

    public void setReturnAnnoKey(String returnAnnoKey) {
        this.returnAnnoKey = returnAnnoKey;
    }

    public String getReturnTitle() {
        return returnTitle;
    }

    public void setReturnTitle(String returnTitle) {
        this.returnTitle = returnTitle;
    }

    public Map<String, Parameter> getParamAnnoKeys() {
        return paramAnnoKeys;
    }

    public void setParamAnnoKeys(Map<String, Parameter> paramAnnoKeys) {
        this.paramAnnoKeys = paramAnnoKeys;
    }

    public Factor getFactor() {
        return factor;
    }

    public void setFactor(Factor factor) {
        this.factor = factor;
    }

    public Map<String, Parameter> getMultiParamAnnoKeys() {
        return multiParamAnnoKeys;
    }

    public void setMultiParamAnnoKeys(Map<String, Parameter> multiParamAnnoKeys) {
        this.multiParamAnnoKeys = multiParamAnnoKeys;
    }

    public LinkedList<String> getParamAnnoKeyList() {
        return paramAnnoKeyList;
    }

    public void setParamAnnoKeyList(LinkedList<String> paramAnnoKeyList) {
        this.paramAnnoKeyList = paramAnnoKeyList;
    }

    public LinkedList<String> getMultiParamAnnoKeyList() {
        return multiParamAnnoKeyList;
    }

    public void setMultiParamAnnoKeyList(LinkedList<String> multiParamAnnoKeyList) {
        this.multiParamAnnoKeyList = multiParamAnnoKeyList;
    }
}
