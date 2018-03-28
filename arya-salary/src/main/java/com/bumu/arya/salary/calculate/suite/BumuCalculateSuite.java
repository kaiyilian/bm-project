package com.bumu.arya.salary.calculate.suite;

import com.bumu.arya.salary.calculate.factor.bumu.BumuSalaryAfterFactor;
import com.bumu.arya.salary.calculate.factor.bumu.BumuServiceFactor;
import com.bumu.arya.salary.calculate.factor.bumu.BumuTaxFactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 不木自定义薪资（累计薪资计算）计算套件
 * 注：计算套件不处理累计，每次计算有调用方处理好累计薪资和个税
 * Created by allen on 2017/7/11.
 */
@Component
public class BumuCalculateSuite extends BaseCalculateSuite {

    @Autowired
    BumuTaxFactor bumuTaxFactor;

    @Autowired
    BumuServiceFactor bumuServiceFactor;

    @Autowired
    BumuSalaryAfterFactor bumuSalaryAfterFactor;

    @Override
    public void initBefore() {
        super.factorQueue.add(bumuTaxFactor);
        super.factorQueue.add(bumuServiceFactor);
        super.factorQueue.add(bumuSalaryAfterFactor);
    }

    public BumuTaxFactor getBumuTaxFactor() {
        return bumuTaxFactor;
    }

    public void setBumuTaxFactor(BumuTaxFactor bumuTaxFactor) {
        this.bumuTaxFactor = bumuTaxFactor;
    }

    public BumuServiceFactor getBumuServiceFactor() {
        return bumuServiceFactor;
    }

    public void setBumuServiceFactor(BumuServiceFactor bumuServiceFactor) {
        this.bumuServiceFactor = bumuServiceFactor;
    }

    public BumuSalaryAfterFactor getBumuSalaryAfterFactor() {
        return bumuSalaryAfterFactor;
    }

    public void setBumuSalaryAfterFactor(BumuSalaryAfterFactor bumuSalaryAfterFactor) {
        this.bumuSalaryAfterFactor = bumuSalaryAfterFactor;
    }

    public void initSuit(){
        setBumuSalaryAfterFactor(new BumuSalaryAfterFactor());
        setBumuServiceFactor(new BumuServiceFactor());
        setBumuTaxFactor(new BumuTaxFactor());
    }
}
