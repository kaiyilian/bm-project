package com.bumu.arya.admin.soin.result;

import com.bumu.arya.soin.model.entity.AryaSoinEntity;
import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * 增减员导出内容详情的实体类
 * @author liangjun
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmployeeExportOutDetail extends TxVersionResult implements ResultHandler<AryaSoinEntity> {
	//工作岗位
	//员工工作地点
	//入职日期YYYYMMDD

	//缴纳主体
	private String subject;

	//社保类型
	private String soinType;
	//服务年月YYYYMM
	private Integer serviceYearMonth;
	//缴纳年月YYYYMM
	private String payMonth;
	//补缴开始年月
	private String backStartYearMonth;
	//补缴结束年月
	private String backEndYearMonth;


	//增\减员
	private String modifyName;

	//社保基数
	private String soinBase;
	//公积金基数
	private String personalHouseFundBase;
	//公积金比例
	private String personalHouseFundPercentage;
	//社保编号
	private String soinCode;
	//公积金编号
	private String houseFundCode;
	//服务费-收账
	private String fees;
	//服务费-出账
	private String feeOut;
	//业务员
	private String salesmanName;
	//备注
	private String memo;

	//姓名
	private String insurancePersonName;
	//身份证号码
	private String idcardNo;
	//联系电话
	private String phoneNo;
	//参保省
	private String province;
	//参保地区
	private String district;
	//户口性质
	private String hukouTypeName;
	//户籍地址
	private String hukouDistrict;

	public String getSubject() {
		return subject;
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

	public Integer getServiceYearMonth() {
		return serviceYearMonth;
	}

	public void setServiceYearMonth(Integer serviceYearMonth) {
		this.serviceYearMonth = serviceYearMonth;
	}

	public String getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}

	public String getBackStartYearMonth() {
		return backStartYearMonth;
	}

	public void setBackStartYearMonth(String backStartYearMonth) {
		this.backStartYearMonth = backStartYearMonth;
	}

	public String getBackEndYearMonth() {
		return backEndYearMonth;
	}

	public void setBackEndYearMonth(String backEndYearMonth) {
		this.backEndYearMonth = backEndYearMonth;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public String getSoinBase() {
		return soinBase;
	}

	public void setSoinBase(String soinBase) {
		this.soinBase = soinBase;
	}

	public String getPersonalHouseFundBase() {
		return personalHouseFundBase;
	}

	public void setPersonalHouseFundBase(String personalHouseFundBase) {
		this.personalHouseFundBase = personalHouseFundBase;
	}

	public String getPersonalHouseFundPercentage() {
		return personalHouseFundPercentage;
	}

	public void setPersonalHouseFundPercentage(String personalHouseFundPercentage) {
		this.personalHouseFundPercentage = personalHouseFundPercentage;
	}

	public String getSoinCode() {
		return soinCode;
	}

	public void setSoinCode(String soinCode) {
		this.soinCode = soinCode;
	}

	public String getHouseFundCode() {
		return houseFundCode;
	}

	public void setHouseFundCode(String houseFundCode) {
		this.houseFundCode = houseFundCode;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getFeeOut() {
		return feeOut;
	}

	public void setFeeOut(String feeOut) {
		this.feeOut = feeOut;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getInsurancePersonName() {
		return insurancePersonName;
	}

	public void setInsurancePersonName(String insurancePersonName) {
		this.insurancePersonName = insurancePersonName;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getHukouTypeName() {
		return hukouTypeName;
	}

	public void setHukouTypeName(String hukouTypeName) {
		this.hukouTypeName = hukouTypeName;
	}

	public String getHukouDistrict() {
		return hukouDistrict;
	}

	public void setHukouDistrict(String hukouDistrict) {
		this.hukouDistrict = hukouDistrict;
	}

	@Override
	public void entityToResult(AryaSoinEntity aryaSoinEntity) {

		BigDecimal personalHouseFundBase = aryaSoinEntity.getPersonalHouseFundBase();
		if (personalHouseFundBase!=null) {
			//公积金基数
			setPersonalHouseFundBase(String.format("%.2f", personalHouseFundBase));
		}
		//公积金编号
		setHouseFundCode(aryaSoinEntity.getSoinCode());
		//社保编号
		setSoinCode(aryaSoinEntity.getSoinCode());

		BigDecimal soinBase = aryaSoinEntity.getSoinBase();
		if (soinBase!=null) {
			//社保基数
			setSoinBase(String.format("%.2f", soinBase));
		}



	}
}
