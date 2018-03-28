package com.bumu.arya.admin.salary.model;

import java.math.BigDecimal;

/**
 * @author CuiMengxin
 * @date 2016/4/12
 */
public class CorpSalaryStatisticsStructure {

	String corpName;

	String departmentName;

	String districtName;

	int staffCount;

	BigDecimal taxableSalaryTotal;

	BigDecimal personalTaxTotal;

	BigDecimal serviceChargeTotal;

	BigDecimal brokerageTotal;

	BigDecimal netSalaryTotal;

	public CorpSalaryStatisticsStructure() {
		taxableSalaryTotal = new BigDecimal("0").setScale(2, BigDecimal.ROUND_DOWN);
		personalTaxTotal = new BigDecimal("0").setScale(2, BigDecimal.ROUND_DOWN);
		serviceChargeTotal = new BigDecimal("0").setScale(2, BigDecimal.ROUND_DOWN);
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

	public BigDecimal getServiceChargeTotal() {
		return serviceChargeTotal;
	}

	public void setServiceChargeTotal(BigDecimal serviceChargeTotal) {
		this.serviceChargeTotal = serviceChargeTotal;
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
