package com.bumu.arya.salary.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CuiMengxin
 * @date 2016/3/29
 */
public class SalaryCalculateModel {

	/**
	 * 税前薪资
	 */
	BigDecimal taxableSalary = new BigDecimal(0);

	BigDecimal taxableWeekSalary = new BigDecimal(0);

	/**
	 * 个税
	 */
	BigDecimal personalTax = new BigDecimal(0);

	/**
	 * 薪资服务费
	 */
	BigDecimal brokerage = new BigDecimal(0);

	/**
	 * 税后薪资
	 */
	BigDecimal netSalary = new BigDecimal(0);

	/**
	 * 存放其他计算项（如蓝领一堆要算的，到时候直接转成JSON导出的时候直接显示）
	 */
	List<Map<String, String>> extraValueMaps = new ArrayList<>();

	public BigDecimal getTaxableWeekSalary() {
		return taxableWeekSalary;
	}

	public void setTaxableWeekSalary(BigDecimal taxableWeekSalary) {
		this.taxableWeekSalary = taxableWeekSalary;
	}

	public List<Map<String, String>> getExtraValueMaps() {
		return extraValueMaps;
	}

	public void setExtraValueMaps(List<Map<String, String>> extraValueMaps) {
		this.extraValueMaps = extraValueMaps;
	}

	public SalaryCalculateModel() {
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
