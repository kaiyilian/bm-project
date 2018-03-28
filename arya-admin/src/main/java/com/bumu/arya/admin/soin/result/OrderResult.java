package com.bumu.arya.admin.soin.result;

import com.bumu.arya.common.Constants;
import com.bumu.arya.soin.constant.SoinPersonHukouType;
import com.bumu.arya.soin.model.entity.AryaSoinOrderEntity;
import com.bumu.arya.soin.model.entity.AryaSoinPersonEntity;
import com.bumu.common.result.TxVersionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * <p>接口订单查询
 * <p>
 * <p>[GET]/admin/soin/bill/manage/query
 * <p>
 * <p>相应数据的封装
 *
 * @author majun
 */
public class OrderResult extends TxVersionResult implements ResultHandler<AryaSoinOrderEntity> {

	private String id;
	// 缴纳状态，0是全部，1是成功，2是失败
	private Integer payed_status;
	// 缴纳失败原因
	private String failed_reason;
	// 缴纳主体
	private String subject;
	// 订单来源
	private Integer source;
	// 参保地区
	@JsonProperty(value = "soin_district")
	private String soinDistrict;
	// 参保类型
	@JsonProperty(value = "soin_type")
	private String soinType;
	// 服务月份
	private int service_month;
	// 缴纳月份
	private String pay_month;
	// 社保基数
	@JsonProperty(value = "soin_base")
	private BigDecimal soinBase;
	// 社保编号
	@JsonProperty(value = "soin_code")
	private String soinCode;
	// 公积金编号
	@JsonProperty(value = "housefund_code")
	private String housefundCode;
	// 公积金基数
	@JsonProperty(value = "housefund_base")
	private BigDecimal housefundBase;
	// 公积金比例
	@JsonProperty(value = "housefund_percent")
	private String housefundPercent;

	// 企业部分社保费用小计
	@JsonProperty(value = "corp_subtotal")
	private String corpSubtotal;
	// 个人部分社保费用小计
	@JsonProperty(value = "person_subtotal")
	private String personSubtotal;
	// 收账服务费
	@JsonProperty(value = "collection_service_fee")
	private String collectionServiceFee;
	// 出账服务费
	@JsonProperty(value = "charge_service_fee")
	private String chargeServiceFee;
	// 其他费用
	@JsonProperty(value = "other_payment")
	private String otherPayement;
	// 收账总计
	@JsonProperty(value = "collection_total")
	private String collectionTotal;
	// 出账总计
	@JsonProperty(value = "charge_total")
	private String chargeTotal;
	// 业务员名称
	private String salesman;
	// 供应商名称
	private String supplier;
	// 参保人信息
	private SionPersonResult personResult;

	String modify;

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public String getHousefundCode() {
		return housefundCode;
	}

	public void setHousefundCode(String housefundCode) {
		this.housefundCode = housefundCode;
	}

	public String getSoinCode() {
		return soinCode;
	}

	public void setSoinCode(String soinCode) {
		this.soinCode = soinCode;
	}

	public String getOtherPayement() {
		return otherPayement;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public SionPersonResult getPersonResult() {
		return personResult;
	}

	public void setPersonResult(SionPersonResult personResult) {
		this.personResult = personResult;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPayed_status() {
		return payed_status;
	}

	public void setPayed_status(Integer payed_status) {
		this.payed_status = payed_status;
	}

	public String getFailed_reason() {
		return failed_reason;
	}

	public void setFailed_reason(String failed_reason) {
		this.failed_reason = failed_reason;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(Integer subject) {
		if (subject != null) {
			this.subject = Constants.subjectMap.get(subject);
		}
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getSoinDistrict() {
		return soinDistrict;
	}

	public void setSoinDistrict(String soinDistrict) {
		this.soinDistrict = soinDistrict;
	}

	public String getSoinType() {
		return soinType;
	}

	public void setSoinType(String soinType) {
		this.soinType = soinType;
	}

	public int getService_month() {
		return service_month;
	}

	public void setService_month(int service_month) {
		this.service_month = service_month;
	}

	public String getPay_month() {
		return pay_month;
	}

	public void setPay_month(String pay_month) {
		this.pay_month = pay_month;
	}

	public BigDecimal getSoinBase() {
		return soinBase;
	}

	public void setSoinBase(BigDecimal soinBase) {
		this.soinBase = soinBase;
	}

	public BigDecimal getHousefundBase() {
		return housefundBase;
	}

	public void setHousefundBase(BigDecimal housefundBase) {
		this.housefundBase = housefundBase;
	}

	public String getHousefundPercent() {
		return housefundPercent;
	}

	public void setHousefundPercent(String housefundPercent) {
		this.housefundPercent = housefundPercent;
	}

	public String getCorpSubtotal() {
		return corpSubtotal;
	}

	public void setCorpSubtotal(String corpSubtotal) {
		this.corpSubtotal = corpSubtotal;
	}

	public String getPersonSubtotal() {
		return personSubtotal;
	}

	public void setPersonSubtotal(String personSubtotal) {
		this.personSubtotal = personSubtotal;
	}

	public String getCollectionServiceFee() {
		return collectionServiceFee;
	}

	public void setCollectionServiceFee(String collectionServiceFee) {
		this.collectionServiceFee = collectionServiceFee;
	}

	public String getChargeServiceFee() {
		return chargeServiceFee;
	}

	public void setChargeServiceFee(String chargeServiceFee) {
		this.chargeServiceFee = chargeServiceFee;
	}

	public void setOtherPayement(String otherPayement) {
		this.otherPayement = otherPayement;
	}

	public String getCollectionTotal() {
		return collectionTotal;
	}

	public void setCollectionTotal(String collectionTotal) {
		this.collectionTotal = collectionTotal;
	}

	public String getChargeTotal() {
		return chargeTotal;
	}

	public void setChargeTotal(String chargeTotal) {
		this.chargeTotal = chargeTotal;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	@Override
	public void entityToResult(AryaSoinOrderEntity entity) {
		this.id = entity.getId();
		setSubject(entity.getSubject());
		this.source = entity.getOrigin();
		this.soinDistrict = entity.getDistrict();
		this.soinType = entity.getSoinType();
		this.service_month = entity.getServiceYearMonth();
		this.soinBase = entity.getBasePension();
		this.housefundBase = entity.getBaseHouseFund();
		if (entity.getHousefundProportion() != null) {
			this.housefundPercent = entity.getHousefundProportion().intValue() + "";
		}
		this.salesman = entity.getSalesmanName();
		super.version = entity.getTxVersion();
	}

	public static class SionPersonResult implements ResultHandler<AryaSoinPersonEntity> {
		// 参保人姓名
		private String name;
		// 参保人身份证号码
		private String idcard;
		// 参保人手机号
		private String phoneNo;
		// 户口类型
		@JsonProperty(value = "hukou_type")
		private String hukouType;
		// 户籍地址
		@JsonProperty(value = "hukou_district")
		private String hukouDstrict;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIdcard() {
			return idcard;
		}

		public void setIdcard(String idcard) {
			this.idcard = idcard;
		}

		public String getPhoneNo() {
			return phoneNo;
		}

		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		}

		public String getHukouType() {
			return hukouType;
		}

		public void setHukouType(String hukouType) {
			this.hukouType = hukouType;
		}

		public String getHukouDstrict() {
			return hukouDstrict;
		}

		public void setHukouDstrict(String hukouDstrict) {
			this.hukouDstrict = hukouDstrict;
		}

		@Override
		public void entityToResult(AryaSoinPersonEntity entity) {
			this.name = entity.getInsurancePersonName();
			this.idcard = entity.getIdcardNo();
			this.hukouType = SoinPersonHukouType.getHuKouTypeName(entity.getHukouType());
			this.phoneNo = entity.getPhoneNo();
		}
	}
}
