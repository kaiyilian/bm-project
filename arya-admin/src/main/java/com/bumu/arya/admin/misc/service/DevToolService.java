package com.bumu.arya.admin.misc.service;

import com.bumu.arya.soin.model.entity.SoinTypeEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by allen on 2017/1/18.
 */
@Transactional
public interface DevToolService {

	/**
	 * 查找未使用的社保类型
	 * @return
	 */
	DelSoinTypeResult findAllUnusedSoinTypes();

	/**
	 * 删除未使用的社保类型
	 */
	void deleteAllUnusedSoinTypes();


	/**
	 * 查询所有导入的订单
	 * @param cutoffTime
	 * @return
	 */
	DelSoinOrderResult findAllImportedOrders(long cutoffTime);

	/**
	 * 删除导入的订单
	 * @param cutoffTime
	 */
	DelResult deleteAllImportedOrders(long cutoffTime);


	/**
	 *
	 */
	class DelResult {
		int orderCount;
		int soinCount;
		int batchCount;

		public int getOrderCount() {
			return orderCount;
		}

		public void setOrderCount(int orderCount) {
			this.orderCount = orderCount;
		}

		public int getSoinCount() {
			return soinCount;
		}

		public void setSoinCount(int soinCount) {
			this.soinCount = soinCount;
		}

		public int getBatchCount() {
			return batchCount;
		}

		public void setBatchCount(int batchCount) {
			this.batchCount = batchCount;
		}
	}

	/**
	 * 删除社保类型列表
	 */
	class DelSoinTypeResult extends ArrayList<DelDistrictType>{



//		Map<String, List<SoinTypeEntity>> data = new HashMap();
//
//		public void put(String districtName, List<SoinTypeEntity> types) {
//			data.put(districtName, types);
//		}
//
//		public int size() {
//			return data.size();
//		}

	}

	/**
	 * 删除社保类型
	 */
	class DelDistrictType {
		String districtId;
		String districtName;
		List<SoinTypeEntity> soinTypeEntities;

		public String getDistrictId() {
			return districtId;
		}

		public void setDistrictId(String districtId) {
			this.districtId = districtId;
		}

		public String getDistrictName() {
			return districtName;
		}

		public void setDistrictName(String districtName) {
			this.districtName = districtName;
		}

		public List<SoinTypeEntity> getSoinTypeEntities() {
			return soinTypeEntities;
		}

		public void setSoinTypeEntities(List<SoinTypeEntity> soinTypeEntities) {
			this.soinTypeEntities = soinTypeEntities;
		}
	}



	/**
	 * 可删除订单列表
	 */
	class DelSoinOrderResult extends ArrayList<DelSoinOrder> {

	}

	/**
	 * 可删除订单
	 */
	class DelSoinOrder {
		String orderId;
		String orderNo;
		String realName;
		String idcardName;
		String districtName;
		String serviceYearMonth;
		String salesMan;
		String increaseOrDecrease;
		String soinCount;// 社保缴纳记录数量
		String operator;// 操作人姓名
		String calculateType;// 计算类型

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		public String getIdcardName() {
			return idcardName;
		}

		public void setIdcardName(String idcardName) {
			this.idcardName = idcardName;
		}

		public String getDistrictName() {
			return districtName;
		}

		public void setDistrictName(String districtName) {
			this.districtName = districtName;
		}

		public String getServiceYearMonth() {
			return serviceYearMonth;
		}

		public void setServiceYearMonth(String serviceYearMonth) {
			this.serviceYearMonth = serviceYearMonth;
		}

		public String getSalesMan() {
			return salesMan;
		}

		public void setSalesMan(String salesMan) {
			this.salesMan = salesMan;
		}

		public String getIncreaseOrDecrease() {
			return increaseOrDecrease;
		}

		public void setIncreaseOrDecrease(String increaseOrDecrease) {
			this.increaseOrDecrease = increaseOrDecrease;
		}

		public String getSoinCount() {
			return soinCount;
		}

		public void setSoinCount(String soinCount) {
			this.soinCount = soinCount;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getCalculateType() {
			return calculateType;
		}

		public void setCalculateType(String calculateType) {
			this.calculateType = calculateType;
		}
	}
}
