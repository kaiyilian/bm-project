package com.bumu.arya.admin.soin.result;

import com.bumu.arya.soin.util.SoinUtil;
import com.bumu.arya.common.Constants;
import com.bumu.arya.soin.model.entity.AryaSoinEntity;
import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * majun
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderExportResult extends TxVersionResult implements ResultHandler<AryaSoinEntity> {
	// 订单主体
	private String subject;
	// 参保人姓名
	private String soinPersonName;
	// 身份证号码
	private String idcardNo;
	// 服务年月
	private String serviceYearMonth;
	// 参保地区
	private String district;
	// 缴纳年月
	private String payMonth;
	// 缴纳年
	private Integer year;
	// 缴纳月
	private Integer month;
	//公积金编号
	private String houseFundCode;
	// 养老-总金额
	private BigDecimal pensionTotal;
	// 企业-养老-基数
	private BigDecimal companyPensionBase;
	// 企业-养老-比例
	private String companyPensionPercentage;
	// 企业-养老-金额
	private BigDecimal companyPension;
	// 个人-养老-基数
	private BigDecimal personalPensionBase;
	// 个人-养老-比例
	private String personalPensionPercentage;
	// 个人-养老-金额
	private BigDecimal personalPension;
	// 医疗-总金额
	private BigDecimal medicalTotal;
	// 企业-医疗-基数
	private BigDecimal companyMedicalBase;
	// 企业-医疗-比例
	private String companyMedicalPercentage;
	// 企业-医疗-金额
	private BigDecimal companyMedical;
	// 个人-医疗-基数
	private BigDecimal personalMedicalBase;
	// 个人-医疗-比例
	private String personalMedicalPercentage;
	// 个人-医疗-金额
	private BigDecimal personalMedical;
	// 失业-总金额
	private BigDecimal unemploymentTotal;
	// 企业-失业-基数
	private BigDecimal companyUnemploymentBase;
	// 企业-失业-比例
	private String companyUnemploymentPercentage;
	// 企业-失业-金额
	private BigDecimal companyUnemployment;
	// 个人-失业-基数
	private BigDecimal personalUnemploymentBase;
	// 个人-失业-比例
	private String personalUnemploymentPercentage;
	// 个人-失业-金额
	private BigDecimal personalUnemployment;
	// 工伤-总金额
	private BigDecimal injuryTotal;
	// 企业-工伤-基数
	private BigDecimal companyInjuryBase;
	// 企业-工伤-比例
	private String companyInjuryPercentage;
	// 企业-工伤-金额
	private BigDecimal companyInjury;
	// 生育-总金额
	private BigDecimal pregnancyTotal;
	// 企业-生育-基数
	private BigDecimal companyPregnancyBase;
	// 企业-生育-比例
	private String companyPregnancyPercentage;
	// 企业-生育-金额
	private BigDecimal companyPregnancy;
	// 大病医疗-总金额
	private BigDecimal serverIllnessTotal;
	// 企业-大病医疗-基数
	private BigDecimal companyServerIllnessBase;
	// 企业-大病医疗金额
	private BigDecimal companyServerIllness;
	// 个人-大病医疗-基数
	private BigDecimal personalServerIllnessBase;
	// 个人-大病医疗-比例
	private String personalServerIllnessPercentage;
	// 个人-大病医疗金额
	private BigDecimal personalServerIllness;
	// 残保-总金额
	private BigDecimal disabilityTotal;
	// 企业-残保-基数
	private BigDecimal companyDisabilityBase;
	// 企业-残保-比例
	private String companyDisabilityPercentage;
	// 企业-残保金额
	private BigDecimal companyDisability;
	// 工伤补充-总金额
	private BigDecimal injuryAdditionTotal;
	// 企业-工伤补充险-基数
	private BigDecimal companyInjuryAdditionBase;
	// 企业-工伤补充险-比例
	private String companyInjuryAdditionPercentage;
	// 企业-工伤补充险金额
	private BigDecimal companyInjuryAddition;
	// 采暖费-总金额
	private BigDecimal heatingTotal;
	// 企业-采暖费-基数
	private BigDecimal companyHeatingBase;
	// 企业-采暖费-比例
	private String companyHeatingPercentage;
	// 企业-采暖费金额
	private BigDecimal companyHeating;
	// 公积金-总金额
	private BigDecimal houseFundTotal;
	// 企业-住房-基数
	private BigDecimal companyHouseFundBase;
	// 企业-住房-比例
	private String companyHouseFundPercentage;
	// 企业-住房-金额
	private BigDecimal companyHouseFund;
	// 个人-住房-基数
	private BigDecimal personalHouseFundBase;
	// 个人-住房-比例
	private String personalHouseFundPercentage;
	// 个人-住房-金额
	private BigDecimal personalHouseFund;
	// 管理费(收账)
	private BigDecimal fees;
	// 管理费(出账)
	private BigDecimal feesOut;
	// 企业-小计 各险种金额+企业其他费用
	private BigDecimal companyTotal;
	//其他费用
	private BigDecimal otherPayment;
	// 个人-小计 各险种个人部分加上个人其他费用
	private BigDecimal personalTotal;
	// 收账总计 个人+企业+收账管理费
	private BigDecimal totalPayment;
	// 出账总计 个人+企业+出账管理费
	private BigDecimal totalOutPayment;
	// 业务员名称
	private String salesmanName;
	// 供应商名称
	private String supplierName;
	// 缴纳状态
	private Integer statusCode;
	// 缴纳状态名称
	private String statusCodeName;
	// 备注
	private String memo;
	// 社保类型
	private String soinType;

	public String getSubject() {
		return subject;
	}

	public void setSubject(Integer subject) {
		if (subject != null) {
			this.subject = Constants.subjectMap.get(subject);
		}
	}

	public String getHouseFundCode() {
		return houseFundCode;
	}

	public void setHouseFundCode(String houseFundCode) {
		this.houseFundCode = houseFundCode;
	}

	public BigDecimal getOtherPayment() {
		return otherPayment;
	}

	public void setOtherPayment(BigDecimal otherPayment) {
		this.otherPayment = otherPayment;
	}

	public String getSoinPersonName() {
		return soinPersonName;
	}

	public void setSoinPersonName(String soinPersonName) {
		this.soinPersonName = soinPersonName;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getServiceYearMonth() {
		return serviceYearMonth;
	}

	public void setServiceYearMonth(String serviceYearMonth) {
		this.serviceYearMonth = serviceYearMonth;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPayMonth() {
		return payMonth;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public BigDecimal getPensionTotal() {
		return pensionTotal;
	}

	public void setPensionTotal(BigDecimal pensionTotal) {
		this.pensionTotal = pensionTotal;
	}

	public BigDecimal getCompanyPensionBase() {
		return companyPensionBase;
	}

	public void setCompanyPensionBase(BigDecimal companyPensionBase) {
		this.companyPensionBase = companyPensionBase;
	}

	public BigDecimal getCompanyPension() {
		return companyPension;
	}

	public void setCompanyPension(BigDecimal companyPension) {
		this.companyPension = companyPension;
	}

	public BigDecimal getPersonalPensionBase() {
		return personalPensionBase;
	}

	public void setPersonalPensionBase(BigDecimal personalPensionBase) {
		this.personalPensionBase = personalPensionBase;
	}

	public BigDecimal getPersonalPension() {
		return personalPension;
	}

	public void setPersonalPension(BigDecimal personalPension) {
		this.personalPension = personalPension;
	}

	public BigDecimal getMedicalTotal() {
		return medicalTotal;
	}

	public void setMedicalTotal(BigDecimal medicalTotal) {
		this.medicalTotal = medicalTotal;
	}

	public BigDecimal getCompanyMedicalBase() {
		return companyMedicalBase;
	}

	public void setCompanyMedicalBase(BigDecimal companyMedicalBase) {
		this.companyMedicalBase = companyMedicalBase;
	}

	public BigDecimal getCompanyMedical() {
		return companyMedical;
	}

	public void setCompanyMedical(BigDecimal companyMedical) {
		this.companyMedical = companyMedical;
	}

	public BigDecimal getPersonalMedicalBase() {
		return personalMedicalBase;
	}

	public void setPersonalMedicalBase(BigDecimal personalMedicalBase) {
		this.personalMedicalBase = personalMedicalBase;
	}

	public BigDecimal getPersonalMedical() {
		return personalMedical;
	}

	public void setPersonalMedical(BigDecimal personalMedical) {
		this.personalMedical = personalMedical;
	}

	public BigDecimal getUnemploymentTotal() {
		return unemploymentTotal;
	}

	public void setUnemploymentTotal(BigDecimal unemploymentTotal) {
		this.unemploymentTotal = unemploymentTotal;
	}

	public BigDecimal getCompanyUnemploymentBase() {
		return companyUnemploymentBase;
	}

	public void setCompanyUnemploymentBase(BigDecimal companyUnemploymentBase) {
		this.companyUnemploymentBase = companyUnemploymentBase;
	}

	public BigDecimal getCompanyUnemployment() {
		return companyUnemployment;
	}

	public void setCompanyUnemployment(BigDecimal companyUnemployment) {
		this.companyUnemployment = companyUnemployment;
	}

	public BigDecimal getPersonalUnemploymentBase() {
		return personalUnemploymentBase;
	}

	public void setPersonalUnemploymentBase(BigDecimal personalUnemploymentBase) {
		this.personalUnemploymentBase = personalUnemploymentBase;
	}

	public BigDecimal getPersonalUnemployment() {
		return personalUnemployment;
	}

	public void setPersonalUnemployment(BigDecimal personalUnemployment) {
		this.personalUnemployment = personalUnemployment;
	}

	public BigDecimal getInjuryTotal() {
		return injuryTotal;
	}

	public void setInjuryTotal(BigDecimal injuryTotal) {
		this.injuryTotal = injuryTotal;
	}

	public BigDecimal getCompanyInjuryBase() {
		return companyInjuryBase;
	}

	public void setCompanyInjuryBase(BigDecimal companyInjuryBase) {
		this.companyInjuryBase = companyInjuryBase;
	}

	public BigDecimal getCompanyInjury() {
		return companyInjury;
	}

	public void setCompanyInjury(BigDecimal companyInjury) {
		this.companyInjury = companyInjury;
	}

	public BigDecimal getPregnancyTotal() {
		return pregnancyTotal;
	}

	public void setPregnancyTotal(BigDecimal pregnancyTotal) {
		this.pregnancyTotal = pregnancyTotal;
	}

	public BigDecimal getCompanyPregnancyBase() {
		return companyPregnancyBase;
	}

	public void setCompanyPregnancyBase(BigDecimal companyPregnancyBase) {
		this.companyPregnancyBase = companyPregnancyBase;
	}

	public BigDecimal getCompanyPregnancy() {
		return companyPregnancy;
	}

	public void setCompanyPregnancy(BigDecimal companyPregnancy) {
		this.companyPregnancy = companyPregnancy;
	}

	public BigDecimal getServerIllnessTotal() {
		return serverIllnessTotal;
	}

	public void setServerIllnessTotal(BigDecimal serverIllnessTotal) {
		this.serverIllnessTotal = serverIllnessTotal;
	}

	public BigDecimal getCompanyServerIllnessBase() {
		return companyServerIllnessBase;
	}

	public void setCompanyServerIllnessBase(BigDecimal companyServerIllnessBase) {
		this.companyServerIllnessBase = companyServerIllnessBase;
	}

	public BigDecimal getCompanyServerIllness() {
		return companyServerIllness;
	}

	public void setCompanyServerIllness(BigDecimal companyServerIllness) {
		this.companyServerIllness = companyServerIllness;
	}

	public BigDecimal getPersonalServerIllnessBase() {
		return personalServerIllnessBase;
	}

	public void setPersonalServerIllnessBase(BigDecimal personalServerIllnessBase) {
		this.personalServerIllnessBase = personalServerIllnessBase;
	}

	public BigDecimal getPersonalServerIllness() {
		return personalServerIllness;
	}

	public void setPersonalServerIllness(BigDecimal personalServerIllness) {
		this.personalServerIllness = personalServerIllness;
	}

	public BigDecimal getDisabilityTotal() {
		return disabilityTotal;
	}

	public void setDisabilityTotal(BigDecimal disabilityTotal) {
		this.disabilityTotal = disabilityTotal;
	}

	public BigDecimal getCompanyDisabilityBase() {
		return companyDisabilityBase;
	}

	public void setCompanyDisabilityBase(BigDecimal companyDisabilityBase) {
		this.companyDisabilityBase = companyDisabilityBase;
	}

	public BigDecimal getCompanyDisability() {
		return companyDisability;
	}

	public void setCompanyDisability(BigDecimal companyDisability) {
		this.companyDisability = companyDisability;
	}

	public BigDecimal getInjuryAdditionTotal() {
		return injuryAdditionTotal;
	}

	public void setInjuryAdditionTotal(BigDecimal injuryAdditionTotal) {
		this.injuryAdditionTotal = injuryAdditionTotal;
	}

	public BigDecimal getCompanyInjuryAdditionBase() {
		return companyInjuryAdditionBase;
	}

	public void setCompanyInjuryAdditionBase(BigDecimal companyInjuryAdditionBase) {
		this.companyInjuryAdditionBase = companyInjuryAdditionBase;
	}

	public BigDecimal getCompanyInjuryAddition() {
		return companyInjuryAddition;
	}

	public void setCompanyInjuryAddition(BigDecimal companyInjuryAddition) {
		this.companyInjuryAddition = companyInjuryAddition;
	}

	public BigDecimal getHouseFundTotal() {
		return houseFundTotal;
	}

	public void setHouseFundTotal(BigDecimal houseFundTotal) {
		this.houseFundTotal = houseFundTotal;
	}

	public BigDecimal getCompanyHouseFundBase() {
		return companyHouseFundBase;
	}

	public void setCompanyHouseFundBase(BigDecimal companyHouseFundBase) {
		this.companyHouseFundBase = companyHouseFundBase;
	}

	public BigDecimal getCompanyHouseFund() {
		return companyHouseFund;
	}

	public void setCompanyHouseFund(BigDecimal companyHouseFund) {
		this.companyHouseFund = companyHouseFund;
	}

	public BigDecimal getPersonalHouseFundBase() {
		return personalHouseFundBase;
	}

	public void setPersonalHouseFundBase(BigDecimal personalHouseFundBase) {
		this.personalHouseFundBase = personalHouseFundBase;
	}

	public BigDecimal getPersonalHouseFund() {
		return personalHouseFund;
	}

	public void setPersonalHouseFund(BigDecimal personalHouseFund) {
		this.personalHouseFund = personalHouseFund;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	public BigDecimal getFeesOut() {
		return feesOut;
	}

	public void setFeesOut(BigDecimal feesOut) {
		this.feesOut = feesOut;
	}

	public BigDecimal getCompanyTotal() {
		return companyTotal;
	}

	public void setCompanyTotal(BigDecimal companyTotal) {
		this.companyTotal = companyTotal;
	}

	public BigDecimal getPersonalTotal() {
		return personalTotal;
	}

	public void setPersonalTotal(BigDecimal personalTotal) {
		this.personalTotal = personalTotal;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

	public BigDecimal getTotalOutPayment() {
		return totalOutPayment;
	}

	public void setTotalOutPayment(BigDecimal totalOutPayment) {
		this.totalOutPayment = totalOutPayment;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSoinType() {
		return soinType;
	}

	public void setSoinType(String soinType) {
		this.soinType = soinType;
	}

	public String getStatusCodeName() {
		return statusCodeName;
	}

	public void setStatusCodeName(String statusCodeName) {
		this.statusCodeName = statusCodeName;
	}

	public String getCompanyPensionPercentage() {
		return companyPensionPercentage;
	}

	public BigDecimal getHeatingTotal() {
		return heatingTotal;
	}

	public BigDecimal getCompanyHeatingBase() {
		return companyHeatingBase;
	}

	public String getCompanyHeatingPercentage() {
		return companyHeatingPercentage;
	}

	public BigDecimal getCompanyHeating() {
		return companyHeating;
	}

	public void setCompanyPensionPercentage(BigDecimal companyPensionPercentage) {
		if (companyPensionPercentage != null) {
			this.companyPensionPercentage = SoinUtil.turnBigDecimalRoundUpToString(companyPensionPercentage, 2) + "%";
		}

	}

	public String getPersonalPensionPercentage() {
		return personalPensionPercentage;
	}

	public void setPersonalPensionPercentage(BigDecimal personalPensionPercentage) {
		if (personalPensionPercentage != null) {
			this.personalPensionPercentage = SoinUtil.turnBigDecimalRoundUpToString(personalPensionPercentage, 2) + "%";
		}

	}

	public String getCompanyMedicalPercentage() {
		return companyMedicalPercentage;
	}

	public void setCompanyMedicalPercentage(BigDecimal companyMedicalPercentage) {
		if (companyMedicalPercentage != null) {
			this.companyMedicalPercentage = SoinUtil.turnBigDecimalRoundUpToString(companyMedicalPercentage, 2) + "%";
		}

	}

	public String getPersonalMedicalPercentage() {
		return personalMedicalPercentage;
	}

	public void setPersonalMedicalPercentage(BigDecimal personalMedicalPercentage) {
		if (personalMedicalPercentage != null) {
			this.personalMedicalPercentage = SoinUtil.turnBigDecimalRoundUpToString(personalMedicalPercentage, 2) + "%";
		}

	}

	public String getCompanyUnemploymentPercentage() {
		return companyUnemploymentPercentage;
	}

	public void setCompanyUnemploymentPercentage(BigDecimal companyUnemploymentPercentage) {
		if (companyUnemploymentPercentage != null) {
			this.companyUnemploymentPercentage = SoinUtil.turnBigDecimalRoundUpToString(companyUnemploymentPercentage, 2) + "%";
		}

	}

	public String getPersonalUnemploymentPercentage() {
		return personalUnemploymentPercentage;
	}

	public void setPersonalUnemploymentPercentage(BigDecimal personalUnemploymentPercentage) {
		if (personalUnemploymentPercentage != null) {
			this.personalUnemploymentPercentage = SoinUtil.turnBigDecimalRoundUpToString(personalUnemploymentPercentage, 2) + "%";
		}

	}

	public String getCompanyInjuryPercentage() {
		return companyInjuryPercentage;
	}

	public void setCompanyInjuryPercentage(BigDecimal companyInjuryPercentage) {
		if (companyInjuryPercentage != null) {
			this.companyInjuryPercentage = SoinUtil.turnBigDecimalRoundUpToString(companyInjuryPercentage, 2) + "%";
		}

	}

	public String getCompanyPregnancyPercentage() {
		return companyPregnancyPercentage;
	}

	public void setCompanyPregnancyPercentage(BigDecimal companyPregnancyPercentage) {
		if (companyPregnancyPercentage != null) {
			this.companyPregnancyPercentage = SoinUtil.turnBigDecimalRoundUpToString(companyPregnancyPercentage, 2) + "%";
		}

	}

	public String getPersonalServerIllnessPercentage() {
		return personalServerIllnessPercentage;
	}

	public void setPersonalServerIllnessPercentage(BigDecimal personalServerIllnessPercentage) {
		if (personalServerIllnessPercentage != null) {
			this.personalServerIllnessPercentage = SoinUtil.turnBigDecimalRoundUpToString(personalServerIllnessPercentage, 2) + "%";
		}

	}

	public String getCompanyDisabilityPercentage() {
		return companyDisabilityPercentage;
	}

	public void setCompanyDisabilityPercentage(BigDecimal companyDisabilityPercentage) {
		if (companyDisabilityPercentage != null) {
			this.companyDisabilityPercentage = SoinUtil.turnBigDecimalRoundUpToString(companyDisabilityPercentage, 2) + "%";
		}

	}

	public String getCompanyInjuryAdditionPercentage() {
		return companyInjuryAdditionPercentage;
	}

	public void setCompanyInjuryAdditionPercentage(BigDecimal companyInjuryAdditionPercentage) {
		if (companyInjuryAdditionPercentage != null) {
			this.companyInjuryAdditionPercentage = SoinUtil.turnBigDecimalRoundUpToString(companyInjuryAdditionPercentage, 2) + "%";
		}

	}

	public String getCompanyHouseFundPercentage() {
		return companyHouseFundPercentage;
	}

	public void setCompanyHouseFundPercentage(BigDecimal companyHouseFundPercentage) {
		if (companyHouseFundPercentage != null) {
			this.companyHouseFundPercentage = SoinUtil.turnBigDecimalRoundUpToString(companyHouseFundPercentage, 2) + "%";
		}

	}

	public String getPersonalHouseFundPercentage() {
		return personalHouseFundPercentage;
	}

	public void setPersonalHouseFundPercentage(BigDecimal personalHouseFundPercentage) {
		if (personalHouseFundPercentage != null) {
			this.personalHouseFundPercentage = SoinUtil.turnBigDecimalRoundUpToString(personalHouseFundPercentage, 2) + "%";
		}
	}

	public void setCompanyHeatingBase(BigDecimal companyHeatingBase) {
		this.companyHeatingBase = companyHeatingBase;
	}

	public void setCompanyHeatingPercentage(BigDecimal companyHeatingPercentage) {
		if (companyHeatingPercentage != null) {
			this.companyHeatingPercentage = SoinUtil.turnBigDecimalRoundUpToString(companyHeatingPercentage, 2) + "%";
		}
	}

	public void setCompanyHeating(BigDecimal companyHeating) {
		this.companyHeating = companyHeating;
	}

	public void setHeatingTotal(BigDecimal heatingTotal) {
		this.heatingTotal = heatingTotal;
	}

	@Override
	public void entityToResult(AryaSoinEntity aryaSoinEntity) {
		setCompanyPensionPercentage(aryaSoinEntity.getCompanyPensionPercentage());
		setPersonalPensionPercentage(aryaSoinEntity.getPersonalPensionPercentage());
		setCompanyMedicalPercentage(aryaSoinEntity.getCompanyMedicalPercentage());
		setPersonalMedicalPercentage(aryaSoinEntity.getPersonalMedicalPercentage());
		setCompanyUnemploymentPercentage(aryaSoinEntity.getCompanyUnemploymentPercentage());
		setPersonalUnemploymentPercentage(aryaSoinEntity.getPersonalUnemploymentPercentage());
		setCompanyInjuryPercentage(aryaSoinEntity.getCompanyInjuryPercentage());
		setCompanyPregnancyPercentage(aryaSoinEntity.getCompanyPregnancyPercentage());
		setPersonalServerIllnessPercentage(aryaSoinEntity.getPersonalServerIllnessPercentage());
		setCompanyDisabilityPercentage(aryaSoinEntity.getCompanyDisabilityPercentage());
		setCompanyInjuryAdditionPercentage(aryaSoinEntity.getCompanyInjuryAdditionPercentage());
		setCompanyHouseFundPercentage(aryaSoinEntity.getCompanyHouseFundPercentage());
		setPersonalHouseFundPercentage(aryaSoinEntity.getPersonalHouseFundPercentage());
		setCompanyHeatingPercentage(aryaSoinEntity.getPersonalHeatingPercentage());//采暖费个人比例
	}
}
