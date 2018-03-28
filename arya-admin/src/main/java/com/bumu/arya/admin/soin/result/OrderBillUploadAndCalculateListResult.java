package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by CuiMengxin on 16/8/3.
 */
public class OrderBillUploadAndCalculateListResult {

	@JsonProperty("file_name")
	String fileName;

	String batch;

	@JsonProperty("template_type")
	String templateType;

	@JsonProperty("batch_id")
	String batchId;

	@JsonProperty("wrong_rows_count")
	int wrongRowsCount;

	@JsonProperty("duplicate_rows_count")
	int duplicateRowsCount;

	@JsonProperty("total_rows_count")
	int totalRowsCount;

	String msg;

	List<OrderBillUploadAndCalculateResult> orders;


	OrderBillStatisticsResult statistics;


	public OrderBillUploadAndCalculateListResult() {
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public int getTotalRowsCount() {
		return totalRowsCount;
	}

	public void setTotalRowsCount(int totalRowsCount) {
		this.totalRowsCount = totalRowsCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public int getWrongRowsCount() {
		return wrongRowsCount;
	}

	public void setWrongRowsCount(int wrongRowsCount) {
		this.wrongRowsCount = wrongRowsCount;
	}

	public int getDuplicateRowsCount() {
		return duplicateRowsCount;
	}

	public void setDuplicateRowsCount(int duplicateRowsCount) {
		this.duplicateRowsCount = duplicateRowsCount;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<OrderBillUploadAndCalculateResult> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderBillUploadAndCalculateResult> orders) {
		this.orders = orders;
	}

	public OrderBillStatisticsResult getStatistics() {
		return statistics;
	}

	public void setStatistics(OrderBillStatisticsResult statistics) {
		this.statistics = statistics;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	/**
	 * 行类
	 */
	public static class OrderBillUploadAndCalculateResult {
		String id;

		int status;

		/**
		 * 增减员状态
		 */
		@JsonProperty("in_or_decrease")
		ColumnResult inOrDecreaseStatus;

		ColumnResult subject;

		ColumnResult name;

		ColumnResult idcard;

		@JsonProperty("soin_district")
		ColumnResult soinDistrict;

		@JsonProperty("soin_type")
		ColumnResult soinType;

		@JsonProperty("service_month")
		ColumnResult serviceMonth;

		@JsonProperty("pay_month")
		ColumnResult payMonth;

		//补缴年月
		@JsonProperty("back_month")
		ColumnResult backMonth;

		@JsonProperty("soin_base")
		ColumnResult soinBase;

		@JsonProperty("soin_code")
		ColumnResult soinCode;

		@JsonProperty("housefund_code")
		ColumnResult houseFundCode;

		@JsonProperty("housefund_base")
		ColumnResult housefundBase;

		@JsonProperty("housefund_percent")
		ColumnResult housefundPercent;

		@JsonProperty("hukou_type")
		ColumnResult hukouType;

		@JsonProperty("hukou_district")
		ColumnResult hukouDistrict;

		@JsonProperty("collection_service_fee")
		ColumnResult collectionServiceFee;

		@JsonProperty("charge_service_fee")
		ColumnResult chargeServiceFee;

		@JsonProperty("collection_subtotal")
		ColumnResult collectionSubtotal;

		@JsonProperty("charge_subtotal")
		ColumnResult chargeSubtotal;

		ColumnResult salesman;

		ColumnResult supplier;

		@JsonProperty("postpone_month")
		ColumnResult postponeMonth;

		public OrderBillUploadAndCalculateResult() {

		}

		/**
		 * 单元格类
		 */
		public static class ColumnResult {
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

			public ColumnResult() {

			}
		}

		public ColumnResult getBackMonth() {
			return backMonth;
		}

		public void setBackMonth(ColumnResult backMonth) {
			this.backMonth = backMonth;
		}

		public ColumnResult getInOrDecreaseStatus() {
			return inOrDecreaseStatus;
		}

		public void setInOrDecreaseStatus(ColumnResult inOrDecreaseStatus) {
			this.inOrDecreaseStatus = inOrDecreaseStatus;
		}

		public ColumnResult getSoinCode() {
			return soinCode;
		}

		public void setSoinCode(ColumnResult soinCode) {
			this.soinCode = soinCode;
		}

		public ColumnResult getHouseFundCode() {
			return houseFundCode;
		}

		public void setHouseFundCode(ColumnResult houseFundCode) {
			this.houseFundCode = houseFundCode;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public ColumnResult getSubject() {
			return subject;
		}

		public void setSubject(ColumnResult subject) {
			this.subject = subject;
		}

		public ColumnResult getName() {
			return name;
		}

		public void setName(ColumnResult name) {
			this.name = name;
		}

		public ColumnResult getIdcard() {
			return idcard;
		}

		public void setIdcard(ColumnResult idcard) {
			this.idcard = idcard;
		}

		public ColumnResult getSoinDistrict() {
			return soinDistrict;
		}

		public void setSoinDistrict(ColumnResult soinDistrict) {
			this.soinDistrict = soinDistrict;
		}

		public ColumnResult getSoinType() {
			return soinType;
		}

		public void setSoinType(ColumnResult soinType) {
			this.soinType = soinType;
		}

		public ColumnResult getServiceMonth() {
			return serviceMonth;
		}

		public void setServiceMonth(ColumnResult serviceMonth) {
			this.serviceMonth = serviceMonth;
		}

		public ColumnResult getPayMonth() {
			return payMonth;
		}

		public void setPayMonth(ColumnResult payMonth) {
			this.payMonth = payMonth;
		}

		public ColumnResult getSoinBase() {
			return soinBase;
		}

		public void setSoinBase(ColumnResult soinBase) {
			this.soinBase = soinBase;
		}

		public ColumnResult getHousefundBase() {
			return housefundBase;
		}

		public void setHousefundBase(ColumnResult housefundBase) {
			this.housefundBase = housefundBase;
		}

		public ColumnResult getHousefundPercent() {
			return housefundPercent;
		}

		public void setHousefundPercent(ColumnResult housefundPercent) {
			this.housefundPercent = housefundPercent;
		}

		public ColumnResult getHukouType() {
			return hukouType;
		}

		public void setHukouType(ColumnResult hukouType) {
			this.hukouType = hukouType;
		}

		public ColumnResult getHukouDistrict() {
			return hukouDistrict;
		}

		public void setHukouDistrict(ColumnResult hukouDistrict) {
			this.hukouDistrict = hukouDistrict;
		}

		public ColumnResult getCollectionServiceFee() {
			return collectionServiceFee;
		}

		public void setCollectionServiceFee(ColumnResult collectionServiceFee) {
			this.collectionServiceFee = collectionServiceFee;
		}

		public ColumnResult getChargeServiceFee() {
			return chargeServiceFee;
		}

		public void setChargeServiceFee(ColumnResult chargeServiceFee) {
			this.chargeServiceFee = chargeServiceFee;
		}

		public ColumnResult getCollectionSubtotal() {
			return collectionSubtotal;
		}

		public void setCollectionSubtotal(ColumnResult collectionSubtotal) {
			this.collectionSubtotal = collectionSubtotal;
		}

		public ColumnResult getChargeSubtotal() {
			return chargeSubtotal;
		}

		public void setChargeSubtotal(ColumnResult chargeSubtotal) {
			this.chargeSubtotal = chargeSubtotal;
		}

		public ColumnResult getSalesman() {
			return salesman;
		}

		public void setSalesman(ColumnResult salesman) {
			this.salesman = salesman;
		}

		public ColumnResult getSupplier() {
			return supplier;
		}

		public void setSupplier(ColumnResult supplier) {
			this.supplier = supplier;
		}

		public ColumnResult getPostponeMonth() {
			return postponeMonth;
		}

		public void setPostponeMonth(ColumnResult postponeMonth) {
			this.postponeMonth = postponeMonth;
		}
	}
}
