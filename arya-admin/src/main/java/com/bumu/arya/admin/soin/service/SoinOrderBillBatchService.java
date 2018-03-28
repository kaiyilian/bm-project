package com.bumu.arya.admin.soin.service;

import com.bumu.arya.admin.soin.controller.command.BatchDeleteSoinOrderCommand;
import com.bumu.arya.admin.soin.controller.command.OrderBatchDeleteCommand;
import com.bumu.arya.admin.soin.controller.command.OrderBatchDeleteQueryCommand;
import com.bumu.arya.admin.soin.result.SalesmanListResult;
import com.bumu.arya.admin.soin.result.SoinOrderBillListResult;
import com.bumu.arya.admin.soin.result.SoinOrderImportBatchListResult;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by CuiMengxin on 16/8/17.
 */
@Transactional
public interface SoinOrderBillBatchService {

	/**
	 * 获取所有业务员列表
	 *
	 * @return
	 * @throws AryaServiceException
	 */
	SalesmanListResult getAllSalesman() throws AryaServiceException;


	/**
	 * 获取所有做过订单导入的用户
	 *
	 * @return
	 * @throws AryaServiceException
	 */
	SalesmanListResult getAllImportedUsers() throws AryaServiceException;

	/**
	 * 获取业务员的所有社保订单导入批次号
	 *
	 * @param salesmanId
	 * @return
	 */
	SoinOrderImportBatchListResult getSalesmanAllSoinOrderImportBatchs(String salesmanId);

	/**
	 * 按批次删除订单
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	void batchDeleteSoinOrder(BatchDeleteSoinOrderCommand command) throws AryaServiceException;

	/**
	 * 批次分页获取订单
	 *
	 * @param batchId
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws AryaServiceException
	 */
	HttpResponse getBatchPaginationOrderList(String batchId, int page, int pageSize) throws AryaServiceException;

	/**
	 * 订单批量删除
	 *
	 * @param command
	 */
	void batchDeleteV2(OrderBatchDeleteCommand command);

	/**
	 * 订单批量删除前查询
	 *
	 * @param command
	 * @return
	 */
	SoinOrderBillListResult batchDeleteV2Query(OrderBatchDeleteQueryCommand command);

	/**
	 * 订单批量删除
	 *
	 * @param ids
	 */
	void batchDeleteV2(List<String> ids);
}
