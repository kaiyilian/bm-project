package com.bumu.arya.salary.calculate.suite;

import com.bumu.arya.salary.calculate.factor.general.SalaryAfterFactor;
import com.bumu.arya.salary.calculate.factor.general.TaxFactor;
import com.bumu.arya.salary.calculate.factor.humanpool.AbsenseSubFactor;
import com.bumu.arya.salary.calculate.factor.humanpool.SalaryBeforeFactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 默认的计算套件（只简单计算薪资和个税）
 * Created by allen on 2017/6/23.
 */
@Component
public class DefaultCalculateSuite extends BaseCalculateSuite {

    @Autowired
    AbsenseSubFactor absenseSubFactor;

    @Autowired
    SalaryBeforeFactor salaryBeforeFactor;

    @Autowired
    TaxFactor taxFactor;

    @Autowired
    SalaryAfterFactor salaryAfterFactor;

    @Override
    public void initBefore() {
        factorQueue.add(absenseSubFactor);
        factorQueue.add(salaryBeforeFactor);
        factorQueue.add(taxFactor);
        factorQueue.add(salaryAfterFactor);
    }

    public AbsenseSubFactor getAbsenseSubFactor() {
        return absenseSubFactor;
    }

    public void setAbsenseSubFactor(AbsenseSubFactor absenseSubFactor) {
        this.absenseSubFactor = absenseSubFactor;
    }

    public SalaryBeforeFactor getSalaryBeforeFactor() {
        return salaryBeforeFactor;
    }

    public void setSalaryBeforeFactor(SalaryBeforeFactor salaryBeforeFactor) {
        this.salaryBeforeFactor = salaryBeforeFactor;
    }

    public TaxFactor getTaxFactor() {
        return taxFactor;
    }

    public void setTaxFactor(TaxFactor taxFactor) {
        this.taxFactor = taxFactor;
    }

    public SalaryAfterFactor getSalaryAfterFactor() {
        return salaryAfterFactor;
    }

    public void setSalaryAfterFactor(SalaryAfterFactor salaryAfterFactor) {
        this.salaryAfterFactor = salaryAfterFactor;
    }
}
