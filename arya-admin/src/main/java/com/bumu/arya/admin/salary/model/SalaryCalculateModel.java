package com.bumu.arya.admin.salary.model;

import java.math.BigDecimal;

/**
 * @author CuiMengxin
 * @date 2016/3/29
 */
public class SalaryCalculateModel {

	/**
	 * 税前薪资
	 */
	BigDecimal taxableSalary;

	/**
	 * 个税
	 */
	BigDecimal personalTax;

	/**
	 * 个税服务费
	 */
	BigDecimal serviceCharge;

	/**
	 * 薪资服务费
	 */
	BigDecimal brokerage;

	/**
	 * 税后薪资
	 */
	BigDecimal netSalary;

	public SalaryCalculateModel() {
	}

	public String getTaxableSalaryStr() {
		return getTaxableSalary().toString();
	}

	public String getPersonalTaxStr() {
		return getPersonalTax().toString();
	}

	public String getServiceChargeStr() {
		return getServiceCharge().toString();
	}

	public String getBrokerageStr() {
		return getBrokerage().toString();
	}

	public String getNetSalaryStr() {
		return getNetSalary().toString();
	}

	public BigDecimal getTaxableSalary() {
		return taxableSalary;
	}

	public void setTaxableSalary(BigDecimal taxableSalary) {
		this.taxableSalary = taxableSalary;
	}

	public BigDecimal getPersonalTax() {
		return personalTax;
	}

	public void setPersonalTax(BigDecimal personalTax) {
		this.personalTax = personalTax;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public BigDecimal getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
	}
}
