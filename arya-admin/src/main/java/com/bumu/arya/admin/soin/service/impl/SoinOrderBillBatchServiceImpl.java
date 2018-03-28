package com.bumu.arya.admin.soin.service.impl;

import com.bumu.arya.admin.soin.controller.command.BatchDeleteSoinOrderCommand;
import com.bumu.arya.admin.soin.controller.command.OrderBatchDeleteCommand;
import com.bumu.arya.admin.soin.controller.command.OrderBatchDeleteQueryCommand;
import com.bumu.arya.admin.soin.result.SalesmanListResult;
import com.bumu.arya.admin.soin.result.SoinOrderBillListResult;
import com.bumu.arya.admin.soin.result.SoinOrderImportBatchListResult;
import com.bumu.arya.admin.soin.model.dao.SoinImportBatchDao;
import com.bumu.arya.admin.soin.model.dao.SoinSupplierDao;
import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.soin.model.entity.SoinImportBatchEntity;
import com.bumu.arya.admin.soin.model.entity.SoinSupplierEntity;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.soin.result.OrderResult;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.soin.service.SoinOrderBillBatchService;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.admin.system.service.SystemRoleService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.*;
import com.bumu.arya.soin.model.entity.AryaSoinOrderEntity;
import com.bumu.arya.soin.model.entity.AryaSoinPersonEntity;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.soin.model.dao.AryaSoinDao;
import com.bumu.arya.soin.model.dao.InsurancePersonDao;
import com.bumu.arya.soin.model.dao.SoinInOrDecreaseDao;
import com.bumu.arya.soin.model.dao.SoinOrderDao;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.result.Pager;
import com.bumu.common.service.impl.BaseBumuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.bumu.arya.soin.util.SoinUtil.turnBigDecimalHalfRoundUpToString;
import static com.bumu.arya.common.Constants.PERSON_CUSTOMER;
import static com.bumu.arya.common.OperateConstants.SOIN_ORDER_BILL_BATCH_DELETE;
import static com.bumu.arya.soin.constant.SoinOrderBillConstants.NAME_LIST;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by CuiMengxin on 16/8/17.
 */
@Service
public class SoinOrderBillBatchServiceImpl extends BaseBumuService implements SoinOrderBillBatchService {

	Logger log = LoggerFactory.getLogger(SoinOrderBillBatchServiceImpl.class);

	@Autowired
	SysUserDao sysUserDao;

	@Autowired
	SoinImportBatchDao batchDao;

	@Autowired
	SysUserService sysUserService;

	@Autowired
	SoinImportBatchDao soinImportBatchDao;

	@Autowired
    SoinOrderDao soinOrderDao;

	@Autowired
    InsurancePersonDao soinPersonDao;

	@Autowired
	SoinSupplierDao soinSupplierDao;

	@Autowired
    AryaSoinDao soinDao;

	@Autowired
	SystemRoleService systemRoleService;

	@Autowired
    SoinInOrDecreaseDao inOrDecreaseDao;

	@Autowired
	CorporationDao corporationDao;

	@Autowired
    OpLogService opLogService;

	@Override
	public SalesmanListResult getAllSalesman() throws AryaServiceException {
		SalesmanListResult listResult = new SalesmanListResult();
		List<SalesmanListResult.SalesmanResult> list = new ArrayList<>();
		listResult.setSalesman(list);
		List<SysUserEntity> sysUserEntities = sysUserDao.findAll();//暂时找全部的系统用户
		for (SysUserEntity sysUserEntity : sysUserEntities) {
			if (systemRoleService.isSalesmanRole(sysUserEntity)) {
				SalesmanListResult.SalesmanResult result = new SalesmanListResult.SalesmanResult();
				result.setId(sysUserEntity.getId());
				result.setName(sysUserEntity.getRealName());
				list.add(result);
			}
		}
		return listResult;
	}

	@Override
	public SalesmanListResult getAllImportedUsers() throws AryaServiceException {
		SalesmanListResult listResult = new SalesmanListResult();
		List<SalesmanListResult.SalesmanResult> list = new ArrayList<>();
		listResult.setSalesman(list);
		SysUserModel currentUser = sysUserService.getCurrentSysUser();
		if (systemRoleService.isSalesmanRole(currentUser.getId())) {
			SalesmanListResult.SalesmanResult result = new SalesmanListResult.SalesmanResult();
			result.setId(currentUser.getId());
			result.setName(currentUser.getRealName());
			list.add(result);
			return listResult;
		}
		List<String> userIds = batchDao.findAllSoinImportBatchGroupByUser();
		if (userIds.size() == 0) {
			return listResult;
		}
		List<SysUserEntity> sysUserEntities = sysUserDao.findByIds(userIds);
		for (SysUserEntity sysUserEntity : sysUserEntities) {
			SalesmanListResult.SalesmanResult result = new SalesmanListResult.SalesmanResult();
			result.setId(sysUserEntity.getId());
			result.setName(sysUserEntity.getRealName());
			list.add(result);
		}
		return listResult;
	}

	@Override
	public SoinOrderImportBatchListResult getSalesmanAllSoinOrderImportBatchs(String salesmanId) {
		SoinOrderImportBatchListResult listResult = new SoinOrderImportBatchListResult();
		List<SoinImportBatchEntity> soinImportBatchEntities = soinImportBatchDao.findSalesmanAllSoinImportBatch(salesmanId);
		for (SoinImportBatchEntity importBatchEntity : soinImportBatchEntities) {
			SoinOrderImportBatchListResult.SoinOrderImportBatchResult result = new SoinOrderImportBatchListResult.SoinOrderImportBatchResult();
			result.setId(importBatchEntity.getId());
			result.setName(importBatchEntity.getBatchNo());
			listResult.getBatchs().add(result);
		}
		return listResult;
	}

	@Override
	public void batchDeleteSoinOrder(BatchDeleteSoinOrderCommand command) throws AryaServiceException {
		SysUserModel currentUser = sysUserService.getCurrentSysUser();
		SoinImportBatchEntity importBatchEntity = soinImportBatchDao.findSoinImportBatch(command.getBatchId());
		if (importBatchEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_BATCH_NOT_FOUND);
		}

		//如果是业务员角色,则特殊处理。判断该批次数据是否是该业务员导入的,如果是则可以操作
		if (systemRoleService.isSalesmanRole(importBatchEntity.getOperatorId())) {
			if (!importBatchEntity.getOperatorId().equals(importBatchEntity.getOperatorId()))
				throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_BATCH_DELETE_NO_AUTH);
		}

		List<AryaSoinOrderEntity> orderEntities = soinOrderDao.findBatchAllOrders(importBatchEntity.getId());
		if (orderEntities.size() == 0) {
			return;
		}
		List<String> orderIds = new ArrayList<>();
		for (AryaSoinOrderEntity orderEntity : orderEntities) {
			orderEntity.setDelete(true);
			orderIds.add(orderEntity.getId());
		}

		try {
			batchDao.delete(importBatchEntity);
			soinOrderDao.update(orderEntities);
			soinDao.deleteSoinsByOrderIds(orderIds);
			inOrDecreaseDao.deleteByOrders(orderIds);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_BATCH_DELETE_FAILED);
		}

	}

	@Override
	public HttpResponse getBatchPaginationOrderList(String batchId, int page, int pageSize) throws AryaServiceException {
		if (StringUtils.isBlank(batchId)) {
			return new HttpResponse<>(new SoinOrderBillListResult());
		}
		// 查询订单
		Pager<AryaSoinOrderEntity> pager = soinOrderDao.billBatchQueryPagination(batchId, page, pageSize);
		SoinOrderBillListResult listResult = generateOrderListResult(pager.getResult());
		// 拼接相应数据
		listResult.setPages(pager.getPage());
		listResult.setTotalRows(pager.getRowCount());
		return new HttpResponse<>(listResult);
	}

	@Override
	public void batchDeleteV2(OrderBatchDeleteCommand command) {
		boolean personSubject = false;
		if (String.valueOf(PERSON_CUSTOMER).equals(command.getId())) {
			personSubject = true;
		}
		AryaSoinOrderEntity latestCanDeleteOrder = null;
		if (personSubject) {
			latestCanDeleteOrder = soinOrderDao.findPersonSubjectLatestOrder();
		} else {
			latestCanDeleteOrder = soinOrderDao.findCorpSubjectLatestOrder(command.getId());
		}
		List<String> needDeleteOrderIds = soinOrderDao.findBatchDeleteOrdersIds(command.getKeyword(), command.getId(), personSubject, latestCanDeleteOrder.getServiceYearMonth());
		batchDeleteV2(needDeleteOrderIds);
	}

	@Override
	public SoinOrderBillListResult batchDeleteV2Query(OrderBatchDeleteQueryCommand command) {

		boolean personSubject = false;
		if (String.valueOf(PERSON_CUSTOMER).equals(command.getId())) {
			personSubject = true;
		}
		AryaSoinOrderEntity latestCanDeleteOrder = null;
		if (personSubject) {
			latestCanDeleteOrder = soinOrderDao.findPersonSubjectLatestOrder();
		} else {
			latestCanDeleteOrder = soinOrderDao.findCorpSubjectLatestOrder(command.getId());
		}
		if (latestCanDeleteOrder == null) {
			return new SoinOrderBillListResult();
		}

		Pager pager = soinOrderDao.findBatchDeleteOrders(command.getKeyword(), command.getId(), personSubject, latestCanDeleteOrder.getServiceYearMonth(), command.getPage(), command.getPage_size());
		if (pager.getResult() == null) {
			return new SoinOrderBillListResult();
		}
		List<AryaSoinOrderEntity> orderEntities = soinOrderDao.findOrderByIds(pager.getResult());
		SoinOrderBillListResult listResult = generateOrderListResult(orderEntities);
		// 拼接相应数据
		listResult.setPages(pager.getPage());
		listResult.setTotalRows(pager.getRowCount());
		return listResult;
	}

	@Override
	public void batchDeleteV2(List<String> ids) {
		StringBuffer msg = new StringBuffer("【对账单批量删除】ids:" + StringUtils.join(ids, ",") + "。");
		try {
			if (ids != null && !ids.isEmpty()) {
				List<AryaSoinOrderEntity> orderEntities = soinOrderDao.findOrderByIds(ids);
				for (AryaSoinOrderEntity orderEntity : orderEntities) {
					orderEntity.setDelete(true);
				}
				soinOrderDao.update(orderEntities);
				soinDao.deleteSoinsByOrderIds(ids);
				inOrDecreaseDao.deleteByOrders(ids);//单月订单的所有增减员记录全删除
			}
			opLogService.successLog(SOIN_ORDER_BILL_BATCH_DELETE, msg, log);
		} catch (Exception e) {
			opLogService.failedLog(SOIN_ORDER_BILL_BATCH_DELETE, msg, log);
			throw e;
		}
	}

	/**
	 * 组装订单返回结果
	 *
	 * @param orderEntities
	 * @return
	 */
	private SoinOrderBillListResult generateOrderListResult(List<AryaSoinOrderEntity> orderEntities) {
		SoinOrderBillListResult orderBillListResult = new SoinOrderBillListResult();
		List<OrderResult> orderResults = new ArrayList<>();
		orderBillListResult.setOrders(orderResults);
		for (AryaSoinOrderEntity orderEntity : orderEntities) {
			OrderResult orderResult = new OrderResult();
			orderResult.entityToResult(orderEntity);
			if (isNotBlank(orderEntity.getCorpId())) {
				CorporationEntity corporationEntity = corporationDao.find(orderEntity.getCorpId());
				if (corporationEntity != null) {
					orderResult.setSubject(corporationEntity.getName());
				}
			}
			orderResult.setService_month(orderEntity.getServiceYearMonth());
			// 缴纳月份
			String paymonth = "";
			if (orderEntity.getYear() > 0) {
				paymonth = orderEntity.getYear() + String.format("%02d", orderEntity.getStartMonth());
			}
			if (orderEntity.getBackYear() != null) {
				if (isNotBlank(paymonth)) {
					paymonth += "和";
				}
				paymonth += orderEntity.getBackYear() + String.format("%02d", orderEntity.getBackStartMonth()) + "开始补缴" + orderEntity.getBackCount() + "个月";
			}
			orderResult.setPay_month(paymonth);
			// 收账总计
			orderResult.setCollectionTotal(turnBigDecimalHalfRoundUpToString(orderEntity.getPayment(), 3));
			// 出账总计
			orderResult.setChargeTotal(turnBigDecimalHalfRoundUpToString(orderEntity.getTotalOutPayment(), 3));

			// 参保人查询
			AryaSoinPersonEntity personEntity = soinPersonDao.findSoinPersonByIdThrow(orderEntity.getSoinPersonId());
			if (personEntity != null) {
				OrderResult.SionPersonResult personResult = new OrderResult.SionPersonResult();
				personResult.entityToResult(personEntity);
				orderResult.setPersonResult(personResult);
			}
			SoinSupplierEntity supplier = soinSupplierDao.findSoinSupplier(orderEntity.getSupplierId());
			// 供应商名称
			if (supplier != null) {
				orderResult.setSupplier(supplier.getSupplierName());
			}
			if (orderEntity.getModifyType() != null) {
				orderResult.setModify(NAME_LIST.get(orderEntity.getModifyType()));
			}
			orderResults.add(orderResult);
		}
		return orderBillListResult;
	}
}
