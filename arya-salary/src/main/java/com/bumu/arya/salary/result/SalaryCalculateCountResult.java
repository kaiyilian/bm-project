package com.bumu.arya.salary.result;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/17
 */
@ApiModel
public class SalaryCalculateCountResult {

    String corpName;

    String departmentName;

    String districtName;

    int staffCount;

    BigDecimal taxableSalaryTotal;

    BigDecimal personalTaxTotal;

    BigDecimal brokerageTotal;

    BigDecimal netSalaryTotal;

    public SalaryCalculateCountResult() {
        taxableSalaryTotal = new BigDecimal("0").setScale(2, BigDecimal.ROUND_DOWN);
        personalTaxTotal = new BigDecimal("0").setScale(2, BigDecimal.ROUND_DOWN);
        brokerageTotal = new BigDecimal("0").setScale(2, BigDecimal.ROUND_DOWN);
        netSalaryTotal = new BigDecimal("0").setScale(2, BigDecimal.ROUND_DOWN);
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getStaffCount() {
        return staffCount;
    }

    public void setStaffCount(int staffCount) {
        this.staffCount = staffCount;
    }

    public BigDecimal getTaxableSalaryTotal() {
        return taxableSalaryTotal;
    }

    public void setTaxableSalaryTotal(BigDecimal taxableSalaryTotal) {
        this.taxableSalaryTotal = taxableSalaryTotal;
    }

    public BigDecimal getPersonalTaxTotal() {
        return personalTaxTotal;
    }

    public void setPersonalTaxTotal(BigDecimal personalTaxTotal) {
        this.personalTaxTotal = personalTaxTotal;
    }

    public BigDecimal getBrokerageTotal() {
        return brokerageTotal;
    }

    public void setBrokerageTotal(BigDecimal brokerageTotal) {
        this.brokerageTotal = brokerageTotal;
    }

    public BigDecimal getNetSalaryTotal() {
        return netSalaryTotal;
    }

    public void setNetSalaryTotal(BigDecimal netSalaryTotal) {
        this.netSalaryTotal = netSalaryTotal;
    }

}
