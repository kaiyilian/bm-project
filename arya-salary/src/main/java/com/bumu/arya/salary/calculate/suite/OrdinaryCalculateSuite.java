package com.bumu.arya.salary.calculate.suite;

import com.bumu.arya.salary.calculate.factor.general.SalaryAfterFactor;
import com.bumu.arya.salary.calculate.factor.general.TaxFactor;
import com.bumu.arya.salary.calculate.factor.ordinary.*;
import com.bumu.arya.salary.calculate.factor.ordinary.OrdinarySalaryBeforeFactor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 汇思蓝领工资计算套件（标准）
 * Created by allen on 2017/7/6.
 */
public class OrdinaryCalculateSuite extends BaseCalculateSuite {

    @Autowired
    private AbsentSubstractFactor absentSubstractFactor;

    @Autowired
    private DaySalaryFactor daySalaryFactor;

    /*@Autowired
    private GrossSalaryFactor grossSalaryFactor;*/

    @Autowired
    private LeaveSubFactor leaveSubFactor;

    @Autowired
    private OrdinarySalaryBeforeFactor ordinarySalaryBeforeFactor;

    @Autowired
    private WorkAttendanceDaysFactor workAttendanceDaysFactor;

    @Autowired
    private TaxFactor taxFactor;

    @Autowired
    private OrdinarySalaryAfterFactor ordinarySalaryAfterFactor;

    @Override
    public void initBefore() {
        factorQueue.add(daySalaryFactor);
        factorQueue.add(workAttendanceDaysFactor);
        factorQueue.add(absentSubstractFactor);
        factorQueue.add(leaveSubFactor);
        factorQueue.add(ordinarySalaryBeforeFactor);
        factorQueue.add(taxFactor);
        factorQueue.add(ordinarySalaryAfterFactor);
    }
//
//    @Override
//    public SalaryModel doCalculate(SalaryContext salaryContext, SalaryModel salaryModel) throws Exception {
//        // TODO
//        return null;
//    }

    public AbsentSubstractFactor getAbsentSubstractFactor() {
        return absentSubstractFactor;
    }

    public void setAbsentSubstractFactor(AbsentSubstractFactor absentSubstractFactor) {
        this.absentSubstractFactor = absentSubstractFactor;
    }

    public DaySalaryFactor getDaySalaryFactor() {
        return daySalaryFactor;
    }

    public void setDaySalaryFactor(DaySalaryFactor daySalaryFactor) {
        this.daySalaryFactor = daySalaryFactor;
    }

    public LeaveSubFactor getLeaveSubFactor() {
        return leaveSubFactor;
    }

    public void setLeaveSubFactor(LeaveSubFactor leaveSubFactor) {
        this.leaveSubFactor = leaveSubFactor;
    }

    public OrdinarySalaryBeforeFactor getSalaryBeforeFactor() {
        return ordinarySalaryBeforeFactor;
    }


    public WorkAttendanceDaysFactor getWorkAttendanceDaysFactor() {
        return workAttendanceDaysFactor;
    }

    public void setWorkAttendanceDaysFactor(WorkAttendanceDaysFactor workAttendanceDaysFactor) {
        this.workAttendanceDaysFactor = workAttendanceDaysFactor;
    }

    public TaxFactor getTaxFactor() {
        return taxFactor;
    }

    public void setTaxFactor(TaxFactor taxFactor) {
        this.taxFactor = taxFactor;
    }

    public OrdinarySalaryBeforeFactor getOrdinarySalaryBeforeFactor() {
        return ordinarySalaryBeforeFactor;
    }

    public void setOrdinarySalaryBeforeFactor(OrdinarySalaryBeforeFactor ordinarySalaryBeforeFactor) {
        this.ordinarySalaryBeforeFactor = ordinarySalaryBeforeFactor;
    }

    public OrdinarySalaryAfterFactor getOrdinarySalaryAfterFactor() {
        return ordinarySalaryAfterFactor;
    }

    public void setOrdinarySalaryAfterFactor(OrdinarySalaryAfterFactor ordinarySalaryAfterFactor) {
        this.ordinarySalaryAfterFactor = ordinarySalaryAfterFactor;
    }
}
