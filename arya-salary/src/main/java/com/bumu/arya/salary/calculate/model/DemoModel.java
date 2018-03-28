package com.bumu.arya.salary.calculate.model;

/**
 * Created by allen on 2017/7/24.
 */

public class DemoModel {

    @CalculateProperty("REAL_NAME")
    String realName;

    @CalculateProperty("SALARY_BEFORE")
    Float salaryBefore;

    @CalculateProperty("SALARY_BEFORE")
    Float tax;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Float getSalaryBefore() {
        return salaryBefore;
    }

    public void setSalaryBefore(Float salaryBefore) {
        this.salaryBefore = salaryBefore;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }
}
