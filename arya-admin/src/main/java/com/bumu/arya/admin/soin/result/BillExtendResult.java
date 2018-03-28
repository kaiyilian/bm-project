package com.bumu.arya.admin.soin.result;

import com.bumu.common.result.PaginationResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuiMengxin on 2017/1/6.
 * 批量顺延订单查询结果
 */
public class BillExtendResult extends PaginationResult {
	List<ExtendBillResult> orders;

	public BillExtendResult() {
		orders = new ArrayList<>();
	}

	public List<ExtendBillResult> getOrders() {
		return orders;
	}

	public void setOrders(List<ExtendBillResult> orders) {
		this.orders = orders;
	}

	public static class ExtendBillResult {
		String subject;

		String name;

		String idcard;

		@JsonProperty("soin_district")
		String soinDistrict;

		@JsonProperty("soin_type")
		String soinType;

		@JsonProperty("service_month")
		Integer serviceMonth;

		@JsonProperty("pay_month")
        StringStatusResult payMonth;

		//补缴年月
		@JsonProperty("back_month")
		List<StringStatusResult> backMonth;

		//总收账
		@JsonProperty("collection_subtotal")
		String collectionSubtotal;

		//总出账
		@JsonProperty("charge_subtotal")
		String chargeSubtotal;

		String salesman;

		String supplier;

		//增减员
		String modify;

		//顺延到期
		@JsonProperty("postpone_due")
		int postponeDue;

		public String getModify() {
			return modify;
		}

		public void setModify(String modify) {
			this.modify = modify;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

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

		public Integer getServiceMonth() {
			return serviceMonth;
		}

		public void setServiceMonth(Integer serviceMonth) {
			this.serviceMonth = serviceMonth;
		}

		public StringStatusResult getPayMonth() {
			return payMonth;
		}

		public void setPayMonth(StringStatusResult payMonth) {
			this.payMonth = payMonth;
		}

		public List<StringStatusResult> getBackMonth() {
			return backMonth;
		}

		public void setBackMonth(List<StringStatusResult> backMonth) {
			this.backMonth = backMonth;
		}

		public String getCollectionSubtotal() {
			return collectionSubtotal;
		}

		public void setCollectionSubtotal(String collectionSubtotal) {
			this.collectionSubtotal = collectionSubtotal;
		}

		public String getChargeSubtotal() {
			return chargeSubtotal;
		}

		public void setChargeSubtotal(String chargeSubtotal) {
			this.chargeSubtotal = chargeSubtotal;
		}

		public String getSalesman() {
			return salesman;
		}

		public void setSalesman(String salesman) {
			this.salesman = salesman;
		}

		public String getSupplier() {
			return supplier;
		}

		public void setSupplier(String supplier) {
			this.supplier = supplier;
		}

		public int getPostponeDue() {
			return postponeDue;
		}

		public void setPostponeDue(int postponeDue) {
			this.postponeDue = postponeDue;
		}
	}
}
