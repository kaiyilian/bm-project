package com.bumu.arya.salary.calculate.model;

import com.bumu.arya.salary.calculate.Value;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.util.*;

/**
 * 薪资数据模型
 * 包含已知的信息：基本工资，加班，请假，排班，奖惩等薪资信息
 * 存储计算后的信息：例如加班工资，税前薪资，个税，税后薪资，服务费等等
 * Created by allen on 2017/6/22.
 */
public class SalaryModel {

    // key -> (value, title)
    protected Map<String, Value> values = new LinkedMap();

    //
//    MultiValueMap multiValues = new MultiValueMap();
    protected Map<String, Set<Value<Float>>> multiValues = new HashMap<>();

    public Value getValue(String key) {
        return values.get(key);
    }

    public void addValue(String key, Value value) {
        values.put(key, value);
    }

    public Set<Value<Float>> getMultiValue(String key) {
        return (Set<Value<Float>>) multiValues.get(key);
    }

    public void addMultiValue(String key, Set<Value<Float>> values) {
        multiValues.put(key, values);
    }

    public Map<String, Set<Value<Float>>> getMultiValues() {
        return multiValues;
    }

    public Map<String, Value> getValues() {
        return values;
    }
}
