package com.bumu.arya.admin.soin.model.entity;

import com.bumu.arya.admin.soin.model.OrderBillImportMessage;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.model.entity.*;
import com.bumu.arya.soin.model.entity.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CuiMengxin on 16/8/4.
 */
public class SoinOrderBillCalculateStructure {

	List<SoinOrderBillCalculateModel> calculateModels;

	Map<String, Corp> corporationStructureMap;//key: corpName

	Map<String, SoinPerson> soinPersonStructureMap;//key:idcardNo

	Map<String, SoinTypeEntity> soinTypeEntityMap;//key:districtId+soinTypeName

	Map<String, SoinTypeVersionEntity> soinTypeVersionEntityMap;//key:typeId   正常缴纳社保类型版本map

	Map<String, SoinTypeVersionEntity> soinBackTypeVersionEntityMap;//key:typeId 补缴社保类型版本map

	Map<String, SoinSupplierEntity> soinSupplierEntityMap;//key:soinDistrictId

	Map<String, SysUserEntity> sysUserEntityMap;

	Map<String, SoinInOrDecreaseEntity> soinInEntityMap;//需要新增的增员记录 key 身份证号码+缴纳年月 value 增减员记录

	Map<String, SoinInOrDecreaseEntity> soinDecreaseEntityMap;//需要新增的减员记录 key 身份证号码+缴纳年月 value 增减员记录

	Map<String, SoinInOrDecreaseEntity> soinExtendEntityMap;//需要顺延的记录 key 身份证号码+缴纳年月 value 增减员记录

	Map<String, SoinInOrDecreaseEntity> soinDeleteEntityMap;//需要删除的的增减员记录 key value 增减员记录

	List<AryaSoinOrderEntity> needDeleteOrders;//需要删除的订单

	List<String> needDeleteOrderIds;//需要删除订单的ids

	List<String> needIncreaseUpdateOrderIds;//需要增员修改的订单ids
	List<String> needDecreaseUpdateOrderIds;//需要减员修改的订单ids
	List<String> needOrderIds;//需要删除的订单ids
	List<String> needDeleteModifyIds;//需要删除的增减员记录ids
	OrderBillImportMessage message;

	public SoinOrderBillCalculateStructure() {
		this.calculateModels = new ArrayList<>();
		this.corporationStructureMap = new HashMap<>();
		this.soinPersonStructureMap = new HashMap<>();
		this.soinTypeEntityMap = new HashMap<>();
		this.soinTypeVersionEntityMap = new HashMap<>();
		this.soinSupplierEntityMap = new HashMap<>();
		this.sysUserEntityMap = new HashMap<>();
		soinInEntityMap = new HashMap<>();
		soinDecreaseEntityMap = new HashMap<>();
		needDeleteOrders = new ArrayList<>();
		needDeleteOrderIds = new ArrayList<>();
		message = new OrderBillImportMessage();
		soinExtendEntityMap = new HashMap<>();
		soinDeleteEntityMap = new HashMap<>();
		needIncreaseUpdateOrderIds = new ArrayList<>();
		needDecreaseUpdateOrderIds = new ArrayList<>();
		soinBackTypeVersionEntityMap = new HashMap<>();
		needOrderIds = new ArrayList<>();
		needDeleteModifyIds = new ArrayList<>();
	}

	public List<String> getNeedOrderIds() {
		return needOrderIds;
	}

	public void setNeedOrderIds(List<String> needOrderIds) {
		this.needOrderIds = needOrderIds;
	}

	public List<String> getNeedDeleteModifyIds() {
		return needDeleteModifyIds;
	}

	public void setNeedDeleteModifyIds(List<String> needDeleteModifyIds) {
		this.needDeleteModifyIds = needDeleteModifyIds;
	}

	public Map<String, SoinTypeVersionEntity> getSoinBackTypeVersionEntityMap() {
		return soinBackTypeVersionEntityMap;
	}

	public void setSoinBackTypeVersionEntityMap(Map<String, SoinTypeVersionEntity> soinBackTypeVersionEntityMap) {
		this.soinBackTypeVersionEntityMap = soinBackTypeVersionEntityMap;
	}

	public List<String> getNeedIncreaseUpdateOrderIds() {
		return needIncreaseUpdateOrderIds;
	}

	public void setNeedIncreaseUpdateOrderIds(List<String> needIncreaseUpdateOrderIds) {
		this.needIncreaseUpdateOrderIds = needIncreaseUpdateOrderIds;
	}

	public List<String> getNeedDecreaseUpdateOrderIds() {
		return needDecreaseUpdateOrderIds;
	}

	public void setNeedDecreaseUpdateOrderIds(List<String> needDecreaseUpdateOrderIds) {
		this.needDecreaseUpdateOrderIds = needDecreaseUpdateOrderIds;
	}

	public Map<String, SoinInOrDecreaseEntity> getSoinExtendEntityMap() {
		return soinExtendEntityMap;
	}

	public void setSoinExtendEntityMap(Map<String, SoinInOrDecreaseEntity> soinExtendEntityMap) {
		this.soinExtendEntityMap = soinExtendEntityMap;
	}

	public Map<String, SoinInOrDecreaseEntity> getSoinDeleteEntityMap() {
		return soinDeleteEntityMap;
	}

	public void setSoinDeleteEntityMap(Map<String, SoinInOrDecreaseEntity> soinDeleteEntityMap) {
		this.soinDeleteEntityMap = soinDeleteEntityMap;
	}

	public List<AryaSoinOrderEntity> getNeedDeleteOrders() {
		return needDeleteOrders;
	}

	public void setNeedDeleteOrders(List<AryaSoinOrderEntity> needDeleteOrders) {
		this.needDeleteOrders = needDeleteOrders;
	}

	public List<String> getNeedDeleteOrderIds() {
		return needDeleteOrderIds;
	}

	public void setNeedDeleteOrderIds(List<String> needDeleteOrderIds) {
		this.needDeleteOrderIds = needDeleteOrderIds;
	}

	public OrderBillImportMessage getMessage() {
		return message;
	}

	public void setMessage(OrderBillImportMessage message) {
		this.message = message;
	}

	public Map<String, SoinSupplierEntity> getSoinSupplierEntityMap() {
		return soinSupplierEntityMap;
	}

	public void setSoinSupplierEntityMap(Map<String, SoinSupplierEntity> soinSupplierEntityMap) {
		this.soinSupplierEntityMap = soinSupplierEntityMap;
	}

	public Map<String, SysUserEntity> getSysUserEntityMap() {
		return sysUserEntityMap;
	}

	public void setSysUserEntityMap(Map<String, SysUserEntity> sysUserEntityMap) {
		this.sysUserEntityMap = sysUserEntityMap;
	}

	public List<SoinOrderBillCalculateModel> getCalculateModels() {
		return calculateModels;
	}

	public void setCalculateModels(List<SoinOrderBillCalculateModel> calculateModels) {
		this.calculateModels = calculateModels;
	}

	public Map<String, Corp> getCorporationStructureMap() {
		return corporationStructureMap;
	}

	public void setCorporationStructureMap(Map<String, Corp> corporationStructureMap) {
		this.corporationStructureMap = corporationStructureMap;
	}

	public Map<String, SoinPerson> getSoinPersonStructureMap() {
		return soinPersonStructureMap;
	}

	public void setSoinPersonStructureMap(Map<String, SoinPerson> soinPersonStructureMap) {
		this.soinPersonStructureMap = soinPersonStructureMap;
	}

	public Map<String, SoinTypeEntity> getSoinTypeEntityMap() {
		return soinTypeEntityMap;
	}

	public void setSoinTypeEntityMap(Map<String, SoinTypeEntity> soinTypeEntityMap) {
		this.soinTypeEntityMap = soinTypeEntityMap;
	}

	public Map<String, SoinTypeVersionEntity> getSoinTypeVersionEntityMap() {
		return soinTypeVersionEntityMap;
	}

	public void setSoinTypeVersionEntityMap(Map<String, SoinTypeVersionEntity> soinTypeVersionEntityMap) {
		this.soinTypeVersionEntityMap = soinTypeVersionEntityMap;
	}

	public Map<String, SoinInOrDecreaseEntity> getSoinInEntityMap() {
		return soinInEntityMap;
	}

	public void setSoinInEntityMap(Map<String, SoinInOrDecreaseEntity> soinInEntityMap) {
		this.soinInEntityMap = soinInEntityMap;
	}

	public Map<String, SoinInOrDecreaseEntity> getSoinDecreaseEntityMap() {
		return soinDecreaseEntityMap;
	}

	public void setSoinDecreaseEntityMap(Map<String, SoinInOrDecreaseEntity> soinDecreaseEntityMap) {
		this.soinDecreaseEntityMap = soinDecreaseEntityMap;
	}

	public static class SoinPerson {
		AryaSoinPersonEntity personEntity;
		int status;

		public AryaSoinPersonEntity getPersonEntity() {
			return personEntity;
		}

		public void setPersonEntity(AryaSoinPersonEntity personEntity) {
			this.personEntity = personEntity;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public static class Corp {
		CorporationEntity corporationEntity;
		int status;

		public CorporationEntity getCorporationEntity() {
			return corporationEntity;
		}

		public void setCorporationEntity(CorporationEntity corporationEntity) {
			this.corporationEntity = corporationEntity;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public StringBuffer getBillMsg() {
		StringBuffer msg = new StringBuffer();
		if (message == null) {
			return null;
		}

		if (!message.getErrorMsgs().isEmpty()) {
			msg.append("/n有如下错误，请改正：/n");
			msg.append(StringUtils.join(message.getErrorMsgs().values(), "/n"));
			msg.append("/n-----------------------------------------/n");
		}

		if (!message.getWarnMsgs().isEmpty()) {
			msg.append("/n有如下警告，可忽略：/n");
			msg.append(StringUtils.join(message.getWarnMsgs().values(), "/n"));
			msg.append("/n-----------------------------------------/n");
		}

		if (!message.getNormalMsgs().isEmpty()) {
			msg.append("/n系统提示：/n");
			msg.append(StringUtils.join(message.getNormalMsgs().values(), "/n"));
			msg.append("/n-----------------------------------------/n");
		}
		return msg;
	}

	/**
	 * 获取对账单计算的反馈消息
	 *
	 * @return
	 */
	public String getBillCalculateMsg() {
		if (message == null) {
			return null;
		}
		return getBillMsg().append("总条数：" + message.getTotalRows() + " 错误数：" + message.getErrorMsgs().size() + " 警告数：" + message.getWarnMsgs().size()).toString();
	}

	/**
	 * 获取对账单计算导入的反馈消息
	 *
	 * @return
	 */
	public String getBillImportMsg() {
		if (message == null) {
			return null;
		}
		return getBillMsg().append("总条数：" + message.getTotalRows()).toString();
	}
}
