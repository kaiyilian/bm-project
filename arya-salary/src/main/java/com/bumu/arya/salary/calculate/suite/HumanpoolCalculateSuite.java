package com.bumu.arya.salary.calculate.suite;

import com.bumu.arya.salary.calculate.factor.general.SalaryAfterFactor;
import com.bumu.arya.salary.calculate.factor.general.TaxFactor;
import com.bumu.arya.salary.calculate.factor.humanpool.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 汇思蓝领工资计算套件（标准）
 * Created by allen on 2017/7/6.
 */
public class HumanpoolCalculateSuite extends BaseCalculateSuite {

    @Autowired
    HourSalaryFactor hourSalaryFactor;

    @Autowired
    WorkSalaryFactor workSalaryFactor;

    @Autowired
    NewLeaveHoursFactor newLeaveHoursFactor;

    @Autowired
    WorkdayOvertimeSalaryFactor workdayOvertimeSalaryFactor;

    @Autowired
    WeekendOvertimeSalaryFactor weekendOvertimeSalaryFactor;

    @Autowired
    NationalOvertimeSalaryFactor nationalOvertimeSalaryFactor;

    @Autowired
    FulltimeBonusFactor fulltimeBonusFactor;

    @Autowired
    AffairSubFactor affairSubFactor;

    @Autowired
    IllSubFactor illSubFactor;

    @Autowired
    AbsenseSubFactor absenseSubFactor;

    @Autowired
    NewLeaveSubFactor newLeaveSubFactor;

    @Autowired
    SalaryBeforeFactor salaryBeforeFactor;

    @Autowired
    TaxFactor taxFactor;

    @Autowired
    HumanpoolSalaryAfterFactor humanpoolSalaryAfterFactor;

    @Override
    public void initBefore() {
        factorQueue.add(hourSalaryFactor);
        factorQueue.add(workSalaryFactor);
        factorQueue.add(newLeaveHoursFactor);
        factorQueue.add(workdayOvertimeSalaryFactor);
        factorQueue.add(weekendOvertimeSalaryFactor);
        factorQueue.add(nationalOvertimeSalaryFactor);
        factorQueue.add(fulltimeBonusFactor);
        factorQueue.add(affairSubFactor);
        factorQueue.add(illSubFactor);
        factorQueue.add(absenseSubFactor);
        factorQueue.add(newLeaveSubFactor);
        factorQueue.add(salaryBeforeFactor);
        factorQueue.add(taxFactor);
        factorQueue.add(humanpoolSalaryAfterFactor);
    }
//
//    @Override
//    public SalaryModel doCalculate(SalaryContext salaryContext, SalaryModel salaryModel) throws Exception {
//        // TODO
//        return null;
//    }


    public HourSalaryFactor getHourSalaryFactor() {
        return hourSalaryFactor;
    }

    public void setHourSalaryFactor(HourSalaryFactor hourSalaryFactor) {
        this.hourSalaryFactor = hourSalaryFactor;
    }

    public WorkSalaryFactor getWorkSalaryFactor() {
        return workSalaryFactor;
    }

    public void setWorkSalaryFactor(WorkSalaryFactor workSalaryFactor) {
        this.workSalaryFactor = workSalaryFactor;
    }

    public NewLeaveHoursFactor getNewLeaveHoursFactor() {
        return newLeaveHoursFactor;
    }

    public void setNewLeaveHoursFactor(NewLeaveHoursFactor newLeaveHoursFactor) {
        this.newLeaveHoursFactor = newLeaveHoursFactor;
    }

    public WorkdayOvertimeSalaryFactor getWorkdayOvertimeSalaryFactor() {
        return workdayOvertimeSalaryFactor;
    }

    public void setWorkdayOvertimeSalaryFactor(WorkdayOvertimeSalaryFactor workdayOvertimeSalaryFactor) {
        this.workdayOvertimeSalaryFactor = workdayOvertimeSalaryFactor;
    }

    public WeekendOvertimeSalaryFactor getWeekendOvertimeSalaryFactor() {
        return weekendOvertimeSalaryFactor;
    }

    public void setWeekendOvertimeSalaryFactor(WeekendOvertimeSalaryFactor weekendOvertimeSalaryFactor) {
        this.weekendOvertimeSalaryFactor = weekendOvertimeSalaryFactor;
    }

    public NationalOvertimeSalaryFactor getNationalOvertimeSalaryFactor() {
        return nationalOvertimeSalaryFactor;
    }

    public void setNationalOvertimeSalaryFactor(NationalOvertimeSalaryFactor nationalOvertimeSalaryFactor) {
        this.nationalOvertimeSalaryFactor = nationalOvertimeSalaryFactor;
    }

    public FulltimeBonusFactor getFulltimeBonusFactor() {
        return fulltimeBonusFactor;
    }

    public void setFulltimeBonusFactor(FulltimeBonusFactor fulltimeBonusFactor) {
        this.fulltimeBonusFactor = fulltimeBonusFactor;
    }

    public AffairSubFactor getAffairSubFactor() {
        return affairSubFactor;
    }

    public void setAffairSubFactor(AffairSubFactor affairSubFactor) {
        this.affairSubFactor = affairSubFactor;
    }

    public IllSubFactor getIllSubFactor() {
        return illSubFactor;
    }

    public void setIllSubFactor(IllSubFactor illSubFactor) {
        this.illSubFactor = illSubFactor;
    }

    public AbsenseSubFactor getAbsenseSubFactor() {
        return absenseSubFactor;
    }

    public void setAbsenseSubFactor(AbsenseSubFactor absenseSubFactor) {
        this.absenseSubFactor = absenseSubFactor;
    }

    public NewLeaveSubFactor getNewLeaveSubFactor() {
        return newLeaveSubFactor;
    }

    public void setNewLeaveSubFactor(NewLeaveSubFactor newLeaveSubFactor) {
        this.newLeaveSubFactor = newLeaveSubFactor;
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

    public HumanpoolSalaryAfterFactor getHumanpoolSalaryAfterFactor() {
        return humanpoolSalaryAfterFactor;
    }

    public void setHumanpoolSalaryAfterFactor(HumanpoolSalaryAfterFactor humanpoolSalaryAfterFactor) {
        this.humanpoolSalaryAfterFactor = humanpoolSalaryAfterFactor;
    }
}
