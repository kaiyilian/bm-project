package com.bumu.arya.admin.soin.controller;

import com.bumu.arya.admin.soin.controller.command.*;
import com.bumu.arya.admin.soin.service.SoinOrderBillBatchService;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by CuiMengxin on 16/8/17.
 */
@Controller
public class SoinOrderBillBatchController {

	@Autowired
	SoinOrderBillBatchService orderBillBatchService;

	/**
	 * 订单批量删除页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/batch/manage/delete/v2/index", method = RequestMethod.GET)
	public String soinBillManagePage() {
		return "order/order_batch_del";
	}

	/**
	 * 获取所有做过导入操作的人
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/order/batch/manage/salesman/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse queryAllSalesMan() {
		try {
			return new HttpResponse(orderBillBatchService.getAllImportedUsers());
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 获取业务员的导入批次,包括客服
	 *
	 * @param salesmanId
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/order/batch/manage/salesman/batch_query", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse querySalesManAllBatch(@RequestParam("salesman_id") String salesmanId) {
		try {
			return new HttpResponse(orderBillBatchService.getSalesmanAllSoinOrderImportBatchs(salesmanId));
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 批次删除订单
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/order/batch/manage/delete", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse deleteBatchSoinOrder(@RequestBody BatchDeleteSoinOrderCommand command) {
		try {
			orderBillBatchService.batchDeleteSoinOrder(command);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 订单批次查询
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/order/batch/manage/salesman/order_list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse queryBatchSoinOrderList(OrderBillBatchListCommand command) {
		try {
			return orderBillBatchService.getBatchPaginationOrderList(command.getBatch_id(), command.getPage(), command.getPage_size());
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 批量删除
	 */


	/**
	 * 订单批量删除查询
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/order/batch/manage/delete/v2/query", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse orderBatchDeleteQuery(OrderBatchDeleteQueryCommand command) throws Exception {
		return new HttpResponse(orderBillBatchService.batchDeleteV2Query(command));
	}

	/**
	 * 订单参照查询条件批量删除
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/order/batch/manage/delete/v2/bycommand", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse orderBatchDeleteByCommand(@RequestBody OrderBatchDeleteCommand command) throws Exception {
		orderBillBatchService.batchDeleteV2(command);
		return new HttpResponse();
	}

	/**
	 * 订单批量删除
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/order/batch/manage/delete/v2/byids", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse orderBatchDeleteByCommand(@RequestBody IdStrListCommand command) throws Exception {
		orderBillBatchService.batchDeleteV2(command.getIds());
		return new HttpResponse();
	}
}
