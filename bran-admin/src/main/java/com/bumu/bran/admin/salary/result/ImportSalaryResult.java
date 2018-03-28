package com.bumu.bran.admin.salary.result;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 导入薪资条
 *
 * @author majun
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ImportSalaryResult {

	private String file_id;
	private List<SalaryField> salaries;
	private Integer total_count;
	private boolean hasError;


	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public Integer getTotal_count() {
		return total_count;
	}

	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public List<SalaryField> getSalaries() {
		return salaries;
	}

	public void setSalaries(List<SalaryField> salaries) {
		this.salaries = salaries;
	}

	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	public class SalaryField {
		private int release;
		private Params name;
		private Params idCardNo;
		private Params tel;
		private Params month;
		private Params wage;
		private Params totalWorkdayHours;
		private Params workdayOvertimeSalary;
		private Params workdayOvertimeHours;
		private Params restdayOvertimeSalary;
		private Params restDayOvertimeHours;
		private Params legalHolidayOvertimeSalary;
		private Params legalHolidayOvertimeHours;
		private Params performanceBonus;
		private Params subsidy;
		private Params casualLeaveCut;
		private Params casualLeaveDays;
		private Params sickLeaveCut;
		private Params sickLeaveDays;
		private Params otherCut;
		private Params repayment;
		private Params soinPersonal;
		private Params houseFundPersonal;
		private Params taxableSalary;
		private Params personalTax;
		private Params grossSalary;
		private Params netSalary;

		public int getRelease() {
			return release;
		}

		public void setRelease(int release) {
			this.release = release;
		}

		public Params getName() {
			return name;
		}

		public void setName(Params name) {
			this.name = name;
		}

		public Params getIdCardNo() {
			return idCardNo;
		}

		public void setIdCardNo(Params idCardNo) {
			this.idCardNo = idCardNo;
		}

		public Params getTel() {
			return tel;
		}

		public void setTel(Params tel) {
			this.tel = tel;
		}

		public Params getMonth() {
			return month;
		}

		public void setMonth(Params month) {
			this.month = month;
		}

		public Params getWage() {
			return wage;
		}

		public void setWage(Params wage) {
			this.wage = wage;
		}

		public Params getTotalWorkdayHours() {
			return totalWorkdayHours;
		}

		public void setTotalWorkdayHours(Params totalWorkdayHours) {
			this.totalWorkdayHours = totalWorkdayHours;
		}

		public Params getWorkdayOvertimeSalary() {
			return workdayOvertimeSalary;
		}

		public void setWorkdayOvertimeSalary(Params workdayOvertimeSalary) {
			this.workdayOvertimeSalary = workdayOvertimeSalary;
		}

		public Params getWorkdayOvertimeHours() {
			return workdayOvertimeHours;
		}

		public void setWorkdayOvertimeHours(Params workdayOvertimeHours) {
			this.workdayOvertimeHours = workdayOvertimeHours;
		}

		public Params getRestdayOvertimeSalary() {
			return restdayOvertimeSalary;
		}

		public void setRestdayOvertimeSalary(Params restdayOvertimeSalary) {
			this.restdayOvertimeSalary = restdayOvertimeSalary;
		}

		public Params getRestDayOvertimeHours() {
			return restDayOvertimeHours;
		}

		public void setRestDayOvertimeHours(Params restDayOvertimeHours) {
			this.restDayOvertimeHours = restDayOvertimeHours;
		}

		public Params getLegalHolidayOvertimeSalary() {
			return legalHolidayOvertimeSalary;
		}

		public void setLegalHolidayOvertimeSalary(Params legalHolidayOvertimeSalary) {
			this.legalHolidayOvertimeSalary = legalHolidayOvertimeSalary;
		}

		public Params getLegalHolidayOvertimeHours() {
			return legalHolidayOvertimeHours;
		}

		public void setLegalHolidayOvertimeHours(Params legalHolidayOvertimeHours) {
			this.legalHolidayOvertimeHours = legalHolidayOvertimeHours;
		}

		public Params getPerformanceBonus() {
			return performanceBonus;
		}

		public void setPerformanceBonus(Params performanceBonus) {
			this.performanceBonus = performanceBonus;
		}

		public Params getSubsidy() {
			return subsidy;
		}

		public void setSubsidy(Params subsidy) {
			this.subsidy = subsidy;
		}

		public Params getCasualLeaveCut() {
			return casualLeaveCut;
		}

		public void setCasualLeaveCut(Params casualLeaveCut) {
			this.casualLeaveCut = casualLeaveCut;
		}

		public Params getCasualLeaveDays() {
			return casualLeaveDays;
		}

		public void setCasualLeaveDays(Params casualLeaveDays) {
			this.casualLeaveDays = casualLeaveDays;
		}

		public Params getSickLeaveCut() {
			return sickLeaveCut;
		}

		public void setSickLeaveCut(Params sickLeaveCut) {
			this.sickLeaveCut = sickLeaveCut;
		}

		public Params getSickLeaveDays() {
			return sickLeaveDays;
		}

		public void setSickLeaveDays(Params sickLeaveDays) {
			this.sickLeaveDays = sickLeaveDays;
		}

		public Params getOtherCut() {
			return otherCut;
		}

		public void setOtherCut(Params otherCut) {
			this.otherCut = otherCut;
		}

		public Params getRepayment() {
			return repayment;
		}

		public void setRepayment(Params repayment) {
			this.repayment = repayment;
		}

		public Params getSoinPersonal() {
			return soinPersonal;
		}

		public void setSoinPersonal(Params soinPersonal) {
			this.soinPersonal = soinPersonal;
		}

		public Params getHouseFundPersonal() {
			return houseFundPersonal;
		}

		public void setHouseFundPersonal(Params houseFundPersonal) {
			this.houseFundPersonal = houseFundPersonal;
		}

		public Params getTaxableSalary() {
			return taxableSalary;
		}

		public void setTaxableSalary(Params taxableSalary) {
			this.taxableSalary = taxableSalary;
		}

		public Params getPersonalTax() {
			return personalTax;
		}

		public void setPersonalTax(Params personalTax) {
			this.personalTax = personalTax;
		}

		public Params getGrossSalary() {
			return grossSalary;
		}

		public void setGrossSalary(Params grossSalary) {
			this.grossSalary = grossSalary;
		}

		public Params getNetSalary() {
			return netSalary;
		}

		public void setNetSalary(Params netSalary) {
			this.netSalary = netSalary;
		}
	}

	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	public class Params {

		private String value;
		private int flag;
		private String err;
		private String tipMsg;


		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
			this.flag = flag;
		}

		public String getErr() {
			return err;
		}

		public void setErr(String err) {
			this.err = err;
		}

		public String getTipMsg() {
			return tipMsg;
		}

		public void setTipMsg(String tipMsg) {
			this.tipMsg = tipMsg;
		}
	}
}
