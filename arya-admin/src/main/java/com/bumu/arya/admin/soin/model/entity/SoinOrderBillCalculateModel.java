package com.bumu.arya.admin.soin.model.entity;

import com.bumu.arya.soin.util.SoinUtil;
import com.bumu.arya.soin.model.SoinTypeCalculateModel;

import java.math.BigDecimal;

/**
 * Created by CuiMengxin on 16/8/3.
 */
public class SoinOrderBillCalculateModel {

	int no;//模型编号

	String tempId;

	String templateType;//模板类型

	Integer type;//计算类型，正常缴纳，补缴还是混合形式

	String orderId;//订单id

	String needUpdateOrderId;//需要修改的订单id

	String orderOperate;//应该对订单做的操作，新增还是删除还是不动

	int status;//订单状态

	int subjectType;//缴纳主体类型

	Corp corp;

	SoinPerson soinPerson;

	StringSubject serviceYearMonth;

	StringSubject payYearMonth;

	StringSubject backYearMonth;//补缴年月

	StringSubject modify;//增减员

	StringSubject postponeMonth;//顺延月

	Integer modifyType;//增减员类型

	String soinCode;

	String houseFundCode;

	int serviceYear;

	int serviceMonth;

	int payYear;

	int payMonth;

	int backYear;//补缴开始年

	int backMonth;//补缴开始月

	int backCount;//补缴期数

	SoinDistrict soinDistrict;

	SoinType soinType;

	Salesman salesman;

	Supplier supplier;

	SoinTypeCalculateModel normalCalculateModel;//正常缴纳计算模型

	SoinTypeCalculateModel backCalculateModel;//补缴计算模型

	BigDecimal totalIn;//总收账

	BigDecimal totalOut;//总出账

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public SoinOrderBillCalculateModel() {
		this.totalIn = SoinUtil.ZERO;
		this.totalOut = SoinUtil.ZERO;
	}

	public SoinTypeCalculateModel getBackCalculateModel() {
		return backCalculateModel;
	}

	public void setBackCalculateModel(SoinTypeCalculateModel backCalculateModel) {
		this.backCalculateModel = backCalculateModel;
	}

	public StringSubject getBackYearMonth() {
		return backYearMonth;
	}

	public void setBackYearMonth(StringSubject backYearMonth) {
		this.backYearMonth = backYearMonth;
	}

	public int getBackYear() {
		return backYear;
	}

	public void setBackYear(int backYear) {
		this.backYear = backYear;
	}

	public int getBackMonth() {
		return backMonth;
	}

	public void setBackMonth(int backMonth) {
		this.backMonth = backMonth;
	}

	public int getBackCount() {
		return backCount;
	}

	public void setBackCount(int backCount) {
		this.backCount = backCount;
	}

	public String getNeedUpdateOrderId() {
		return needUpdateOrderId;
	}

	public void setNeedUpdateOrderId(String needUpdateOrderId) {
		this.needUpdateOrderId = needUpdateOrderId;
	}

	public Integer getModifyType() {
		return modifyType;
	}

	public void setModifyType(Integer modifyType) {
		this.modifyType = modifyType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderOperate() {
		return orderOperate;
	}

	public void setOrderOperate(String orderOperate) {
		this.orderOperate = orderOperate;
	}

	public StringSubject getModify() {
		return modify;
	}

	public void setModify(StringSubject modify) {
		this.modify = modify;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getSoinCode() {
		return soinCode;
	}

	public void setSoinCode(String soinCode) {
		this.soinCode = soinCode;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getHouseFundCode() {
		return houseFundCode;
	}

	public void setHouseFundCode(String houseFundCode) {
		this.houseFundCode = houseFundCode;
	}

	public SoinDistrict getSoinDistrict() {
		return soinDistrict;
	}

	public void setSoinDistrict(SoinDistrict soinDistrict) {
		this.soinDistrict = soinDistrict;
	}

	public BigDecimal getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(BigDecimal totalIn) {
		this.totalIn = totalIn;
	}

	public BigDecimal getTotalOut() {
		return totalOut;
	}

	public void setTotalOut(BigDecimal totalOut) {
		this.totalOut = totalOut;
	}

	public SoinTypeCalculateModel getNormalCalculateModel() {
		return normalCalculateModel;
	}

	public void setNormalCalculateModel(SoinTypeCalculateModel normalCalculateModel) {
		this.normalCalculateModel = normalCalculateModel;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public SoinType getSoinType() {
		return soinType;
	}

	public void setSoinType(SoinType soinType) {
		this.soinType = soinType;
	}


	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public int getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(int subjectType) {
		this.subjectType = subjectType;
	}

	public Corp getCorp() {
		return corp;
	}

	public void setCorp(Corp corp) {
		this.corp = corp;
	}

	public SoinPerson getSoinPerson() {
		return soinPerson;
	}

	public void setSoinPerson(SoinPerson soinPerson) {
		this.soinPerson = soinPerson;
	}

	public StringSubject getServiceYearMonth() {
		return serviceYearMonth;
	}

	public void setServiceYearMonth(StringSubject serviceYearMonth) {
		this.serviceYearMonth = serviceYearMonth;
	}

	public StringSubject getPayYearMonth() {
		return payYearMonth;
	}

	public void setPayYearMonth(StringSubject payYearMonth) {
		this.payYearMonth = payYearMonth;
	}

	public int getServiceYear() {
		return serviceYear;
	}

	public void setServiceYear(int serviceYear) {
		this.serviceYear = serviceYear;
	}

	public int getServiceMonth() {
		return serviceMonth;
	}

	public void setServiceMonth(int serviceMonth) {
		this.serviceMonth = serviceMonth;
	}

	public int getPayYear() {
		return payYear;
	}

	public void setPayYear(int payYear) {
		this.payYear = payYear;
	}

	public int getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(int payMonth) {
		this.payMonth = payMonth;
	}


	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}

	public StringSubject getPostponeMonth() {
		return postponeMonth;
	}

	public void setPostponeMonth(StringSubject postponeMonth) {
		this.postponeMonth = postponeMonth;
	}

	public static class SoinPerson {

		String id;

		StringSubject name;

		StringSubject idcardNo;

		StringSubject phoneNo;

		String hukouTypeReadName;

		IntSubject hukouType;

		StringSubject hukouDistrict;

		String hukouDistrictIds;

		int status;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getHukouTypeReadName() {
			return hukouTypeReadName;
		}

		public void setHukouTypeReadName(String hukouTypeReadName) {
			this.hukouTypeReadName = hukouTypeReadName;
		}

		public String getHukouDistrictIds() {
			return hukouDistrictIds;
		}

		public void setHukouDistrictIds(String hukouDistrictIds) {
			this.hukouDistrictIds = hukouDistrictIds;
		}

		public StringSubject getName() {
			return name;
		}

		public void setName(StringSubject name) {
			this.name = name;
		}

		public StringSubject getIdcardNo() {
			return idcardNo;
		}

		public void setIdcardNo(StringSubject idcardNo) {
			this.idcardNo = idcardNo;
		}

		public StringSubject getPhoneNo() {
			return phoneNo;
		}

		public void setPhoneNo(StringSubject phoneNo) {
			this.phoneNo = phoneNo;
		}

		public IntSubject getHukouType() {
			return hukouType;
		}

		public void setHukouType(IntSubject hukouType) {
			this.hukouType = hukouType;
		}

		public StringSubject getHukouDistrict() {
			return hukouDistrict;
		}

		public void setHukouDistrict(StringSubject hukouDistrict) {
			this.hukouDistrict = hukouDistrict;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public static class Corp {
		String name;

		int status;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

	}

	public static class SoinDistrict {
		String ids;//地区id串,用":"分开

		String names;

		String name;//参保目标地区名称,只有一个

		String id;//参保目标地区,只有一个id

		int status;

		public String getIds() {
			return ids;
		}

		public void setIds(String ids) {
			this.ids = ids;
		}

		public String getNames() {
			return names;
		}

		public void setNames(String names) {
			this.names = names;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public static class SoinType {

		String id;

		String name;

		int status;

		BigDecimalSubject soinBase;

		BigDecimalSubject housefundBase;

		BigDecimalSubject housefundPercent;

		BigDecimalSubject collectionFee;

		BigDecimal personPercent;//公积金个人比例

		BigDecimal corpPercent;//公积金公司比例

		String percentStr;//公积金比例字符串

		BigDecimal backPersonPercent;//公积金补缴个人比例

		BigDecimal backCorpPercent;//公积金补缴公司比例

		String backPercentStr;//公积金补缴比例字符串

		public BigDecimal getBackPersonPercent() {
			return backPersonPercent;
		}

		public void setBackPersonPercent(BigDecimal backPersonPercent) {
			this.backPersonPercent = backPersonPercent;
		}

		public BigDecimal getBackCorpPercent() {
			return backCorpPercent;
		}

		public void setBackCorpPercent(BigDecimal backCorpPercent) {
			this.backCorpPercent = backCorpPercent;
		}

		public String getBackPercentStr() {
			return backPercentStr;
		}

		public void setBackPercentStr(String backPercentStr) {
			this.backPercentStr = backPercentStr;
		}

		public BigDecimal getPersonPercent() {
			return personPercent;
		}

		public void setPersonPercent(BigDecimal personPercent) {
			this.personPercent = personPercent;
		}

		public BigDecimal getCorpPercent() {
			return corpPercent;
		}

		public void setCorpPercent(BigDecimal corpPercent) {
			this.corpPercent = corpPercent;
		}

		public String getPercentStr() {
			return percentStr;
		}

		public void setPercentStr(String percentStr) {
			this.percentStr = percentStr;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public BigDecimalSubject getSoinBase() {
			return soinBase;
		}

		public void setSoinBase(BigDecimalSubject soinBase) {
			this.soinBase = soinBase;
		}

		public BigDecimalSubject getHousefundBase() {
			return housefundBase;
		}

		public void setHousefundBase(BigDecimalSubject housefundBase) {
			this.housefundBase = housefundBase;
		}

		public BigDecimalSubject getHousefundPercent() {
			return housefundPercent;
		}

		public void setHousefundPercent(BigDecimalSubject housefundPercent) {
			this.housefundPercent = housefundPercent;
		}

		public BigDecimalSubject getCollectionFee() {
			return collectionFee;
		}

		public void setCollectionFee(BigDecimalSubject collectionFee) {
			this.collectionFee = collectionFee;
		}
	}

	public static class Salesman {
		String id;

		String name;

		int status;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public static class Supplier {

		String id;

		String name;

		BigDecimalSubject fee;

		int status;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public BigDecimalSubject getFee() {
			return fee;
		}

		public void setFee(BigDecimalSubject fee) {
			this.fee = fee;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public static class StringSubject {
		String content;

		int status;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public static class BigDecimalSubject {

		BigDecimal content;

		int status;

		public BigDecimal getContent() {
			return content;
		}

		public void setContent(BigDecimal content) {
			this.content = content;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public static class IntSubject {
		int content;

		int status;

		String strContent;

		public String getStrContent() {
			return strContent;
		}

		public void setStrContent(String strContent) {
			this.strContent = strContent;
		}

		public Integer getContent() {
			return content;
		}

		public void setContent(Integer content) {
			this.content = content;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}
}
