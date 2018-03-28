package com.bumu.arya.admin.misc.service.impl;

import com.bumu.arya.admin.soin.model.dao.SoinImportBatchDao;
import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.soin.model.entity.SoinImportBatchEntity;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.misc.service.DevToolService;
import com.bumu.arya.soin.model.dao.*;
import com.bumu.arya.soin.model.entity.*;
import com.bumu.common.service.impl.BaseBumuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by allen on 2017/1/18.
 */
@Service
public class DevToolServiceImpl extends BaseBumuService implements DevToolService {

	@Autowired
	private SoinOrderDao soinOrderDao;

	@Autowired
	private AryaSoinDao aryaSoinDao;

	@Autowired
	private SoinImportBatchDao soinImportBatchDao;

	@Autowired
	private InsurancePersonDao insurancePersonDao;

	@Autowired
	private SysUserDao sysUserDao;

//	@Autowired
//	private DistrictDao districtDao;

	@Autowired
	private AryaSoinDistrictDao soinDistrictDao;

	@Autowired
	private AryaSoinTypeDao aryaSoinTypeDao;

	@Autowired
	private SoinTypeVersionDao soinTypeVersionDao;

	@Autowired
	private SoinRuleDao soinRuleDao;

	@Override
	public DelSoinTypeResult findAllUnusedSoinTypes() {
		List<AryaSoinDistrictEntity> provinceSoinDistrict = soinDistrictDao.findProvinceSoinDistrict();

		DelSoinTypeResult result = new DelSoinTypeResult();
		for (AryaSoinDistrictEntity aryaSoinDistrictEntity : provinceSoinDistrict) {
			List<SoinTypeEntity> notDeleteSoinTypeByDistrict = aryaSoinTypeDao.findNotDeleteSoinTypeByDistrict(aryaSoinDistrictEntity.getId());
			DelDistrictType districtType = new DelDistrictType();
			districtType.setDistrictName(aryaSoinDistrictEntity.getDistrictName());
			districtType.setSoinTypeEntities(notDeleteSoinTypeByDistrict);
			result.add(districtType);
		}

		return result;
	}

	@Override
	public void deleteAllUnusedSoinTypes() {
		DelSoinTypeResult allUnusedSoinTypes = findAllUnusedSoinTypes();
		for (DelDistrictType allUnusedSoinType : allUnusedSoinTypes) {
			if (allUnusedSoinType.getSoinTypeEntities() == null
					|| allUnusedSoinType.getSoinTypeEntities().size() == 0) {
				continue;
			}
			System.out.printf("删除 %s 的社保规则%n", allUnusedSoinType.getDistrictName());
			for (SoinTypeEntity soinTypeEntity : allUnusedSoinType.getSoinTypeEntities()) {
				// 先删除社保规则版本
				List<SoinTypeVersionEntity> versions = soinTypeVersionDao.findByParam("soinTypeId", soinTypeEntity.getId());
				for (SoinTypeVersionEntity versionEntity : versions) {
					System.out.printf("  删除 %s 版本 %d - %d%n", (versionEntity.getDisable() ? "无效" : "有效"), versionEntity.getEffectYear(), versionEntity.getEffectMonth());

					// 先删除社保类型版本的规则
					if (versionEntity.getRuleDisability() != null) {
						soinRuleDao.delete(versionEntity.getRuleDisability());
					}
					if (versionEntity.getRuleHeating() != null) {
						soinRuleDao.delete(versionEntity.getRuleHeating());
					}
					if (versionEntity.getRuleHouseFund() != null) {
						soinRuleDao.delete(versionEntity.getRuleHouseFund());
					}
					if (versionEntity.getRuleHouseFundAddition() != null) {
						soinRuleDao.delete(versionEntity.getRuleHouseFundAddition());
					}
					if (versionEntity.getRuleInjury() != null) {
						soinRuleDao.delete(versionEntity.getRuleInjury());
					}
					if (versionEntity.getRuleInjuryAddition() != null) {
						soinRuleDao.delete(versionEntity.getRuleInjuryAddition());
					}
					if (versionEntity.getRuleMedical() != null) {
						soinRuleDao.delete(versionEntity.getRuleMedical());
					}
					if (versionEntity.getRulePension() != null) {
						soinRuleDao.delete(versionEntity.getRulePension());
					}
					if (versionEntity.getRulePregnancy() != null) {
						soinRuleDao.delete(versionEntity.getRulePregnancy());
					}
					if (versionEntity.getRuleSevereIllness() != null) {
						soinRuleDao.delete(versionEntity.getRuleSevereIllness());
					}
					if (versionEntity.getRuleUnemployment() != null) {
						soinRuleDao.delete(versionEntity.getRuleUnemployment());
					}

					// 删除版本
					soinTypeVersionDao.delete(versionEntity);
				}
				// 删除社保类型
				System.out.println("  删除社保类型");
				aryaSoinTypeDao.delete(soinTypeEntity);
			}
		}
	}

	@Override
	public DelSoinOrderResult findAllImportedOrders(long cutoffTime) {

		DelSoinOrderResult result = new DelSoinOrderResult();
		List<String> orderIds = new ArrayList<>();

		List<AryaSoinOrderEntity> cutoffOrders = soinOrderDao.findAllImportedOrders(cutoffTime);

		System.out.println("符合条件的订单：" + cutoffOrders.size());

		for (AryaSoinOrderEntity soinOrderEntity : cutoffOrders) {
			orderIds.add(soinOrderEntity.getId());
			DelSoinOrder delSoinOrder = new DelSoinOrder();
			delSoinOrder.setOrderId(StringUtils.abbreviateMiddle(soinOrderEntity.getId(), "...", 8));
			delSoinOrder.setOrderNo(soinOrderEntity.getOrderNo());
			delSoinOrder.setSalesMan(soinOrderEntity.getSalesmanName());
			delSoinOrder.setServiceYearMonth(String.valueOf(soinOrderEntity.getServiceYearMonth()));
			delSoinOrder.setCalculateType(String.valueOf(soinOrderEntity.getCalculateType()));
			delSoinOrder.setDistrictName(soinOrderEntity.getDistrict());

			long count = aryaSoinDao.countSoinEntitiesByOrderId(soinOrderEntity.getId());
			delSoinOrder.setSoinCount(String.valueOf(count));

			AryaSoinPersonEntity soinPersonEntity = insurancePersonDao.findSoinPersonById(soinOrderEntity.getSoinPersonId());
			if (soinPersonEntity == null) {
				System.out.printf("没有找到参保人: %s-%s%n", soinOrderEntity.getSoinPersonId(), soinOrderEntity.getSoinPersonName());
			}
			else {
				delSoinOrder.setIdcardName(soinPersonEntity.getIdcardNo());
				delSoinOrder.setRealName(soinPersonEntity.getInsurancePersonName());
				delSoinOrder.setIncreaseOrDecrease(String.valueOf(soinOrderEntity.getModifyType()));
			}

			SysUserEntity sysUserEntity = sysUserDao.findSysUserById(soinOrderEntity.getOperatorId());
			delSoinOrder.setOperator(String.format("%s-%s", sysUserEntity.getLoginName(), sysUserEntity.getRealName()));

			result.add(delSoinOrder);
		}
		return result;

	}

	@Override
	public DelResult deleteAllImportedOrders(long cutoffTime) {

		List<AryaSoinOrderEntity> cutoffOrders = soinOrderDao.findAllImportedOrders(cutoffTime);

		System.out.println("符合条件的订单：" + cutoffOrders.size());

		DelResult delResult = new DelResult();

		Map<String, Object> batchIds = new HashMap();

		// 删除所有符合条件的订单和缴纳记录
		for (AryaSoinOrderEntity cutoffOrder : cutoffOrders) {
			System.out.println("订单: " + cutoffOrder.getOrderNo());
			List<AryaSoinEntity> soinEntities = aryaSoinDao.findSoinEntitiesByOrderId(cutoffOrder.getId());

			for (AryaSoinEntity soinEntity : soinEntities) {
				System.out.printf("  删除缴纳记录: %s - 身份证:%s%n", soinEntity.getSoinCode(), soinEntity.getIdcardNo());
				aryaSoinDao.delete(soinEntity);
				delResult.setSoinCount(delResult.getSoinCount() + 1);
			}

			System.out.printf("  删除订单：%s%n", cutoffOrder.getOrderNo());
			soinOrderDao.delete(cutoffOrder);

			delResult.setOrderCount(delResult.getOrderCount() + 1);

			if (StringUtils.isBlank(cutoffOrder.getBatchId())) {
				System.out.println("  [WARN]此订单没有批次");
			}
			else {
				batchIds.put(cutoffOrder.getBatchId(), "");
			}
		}

		// 删除所有找到订单的批次
		for (String batchId : batchIds.keySet()) {
			if (StringUtils.isBlank(batchId)) {
				continue;
			}
			System.out.println("  删除批次" + batchId);
			SoinImportBatchEntity soinImportBatch = soinImportBatchDao.findSoinImportBatch(batchId);
			if (soinImportBatch == null) {
				System.out.println("批次不存在：" + batchId);
				continue;
			}

			soinImportBatchDao.delete(soinImportBatch);
			delResult.setBatchCount(delResult.getBatchCount() + 1);
		}

		return delResult;
	}


}
