package com.bumu.arya.admin.salary.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2016/4/12
 */
public class SalaryCalculateStatisticsResultList extends ArrayList<SalaryCalculateStatisticsResultList.SalaryCalculateStatisticsResult> {

	public static class SalaryCalculateStatisticsResult {
		String district;

		String corp;

		@JsonProperty("department_name")
		String departmentName;

		@JsonProperty("staff_count")
		int staffCount;

		@JsonProperty("taxable_salary_total")
		String taxableSalaryTotal;

		/**
		 * 个税
		 */
		@JsonProperty("personal_tax_total")
		String personalTaxTotal;

		/**
		 * 个税服务费
		 */
		@JsonProperty("service_charge_total")
		String serviceChargeTotal;

		/**
		 * 薪资服务费
		 */
		@JsonProperty("brokerage_total")
		String brokerageTotal;

		/**
		 * 税后薪资
		 */
		@JsonProperty("net_salary_total")
		String netSalaryTotal;

		public SalaryCalculateStatisticsResult() {
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		public String getDistrict() {
			return district;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

		public String getCorp() {
			return corp;
		}

		public void setCorp(String corp) {
			this.corp = corp;
		}

		public int getStaffCount() {
			return staffCount;
		}

		public void setStaffCount(int staffCount) {
			this.staffCount = staffCount;
		}

		public String getTaxableSalaryTotal() {
			return taxableSalaryTotal;
		}

		public void setTaxableSalaryTotal(String taxableSalaryTotal) {
			this.taxableSalaryTotal = taxableSalaryTotal;
		}

		public String getPersonalTaxTotal() {
			return personalTaxTotal;
		}

		public void setPersonalTaxTotal(String personalTaxTotal) {
			this.personalTaxTotal = personalTaxTotal;
		}

		public String getServiceChargeTotal() {
			return serviceChargeTotal;
		}

		public void setServiceChargeTotal(String serviceChargeTotal) {
			this.serviceChargeTotal = serviceChargeTotal;
		}

		public String getBrokerageTotal() {
			return brokerageTotal;
		}

		public void setBrokerageTotal(String brokerageTotal) {
			this.brokerageTotal = brokerageTotal;
		}

		public String getNetSalaryTotal() {
			return netSalaryTotal;
		}

		public void setNetSalaryTotal(String netSalaryTotal) {
			this.netSalaryTotal = netSalaryTotal;
		}
	}
}
