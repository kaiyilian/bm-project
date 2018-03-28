package com.bumu.arya.admin.soin.controller;

import com.bumu.arya.admin.soin.controller.command.*;
import com.bumu.arya.admin.soin.result.OrderResult;
import com.bumu.arya.admin.soin.service.SoinFileService;
import com.bumu.arya.admin.soin.service.SoinOrderBillService;
import com.bumu.arya.command.OrderBillListQueryCommand;
import com.bumu.arya.response.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CuiMengxin on 16/8/2.
 */
@Controller
public class SoinOrderBillController {

	@Autowired
	SoinOrderBillService soinOrderBillService;

	@Autowired
    SoinFileService fileService;

	/**
	 * 订单计算导入页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/order/import/index", method = RequestMethod.GET)
	public String soinOrderImportPage() {
		return "order/order_import";
	}

	/**
	 * 订单对账管理页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/index", method = RequestMethod.GET)
	public String soinBillManagePage() {
		return "order/order_manage";
	}

	/**
	 * 订单批次删除页面
	 *
	 * @return
	 * @deprecated
	 */
	@RequestMapping(value = "/admin/soin/order/batch/manage/index", method = RequestMethod.GET)
	public String soinBillBatchDeletePage() {
		return "order/order_batch_manage";
	}

	/**
	 * 订单批量顺延页面
	 *
	 * @return
	 * @deprecated
	 */
	@RequestMapping(value = "/admin/soin/order/bill/extend/index", method = RequestMethod.GET)
	public String soinBillExtendPage() {
		return "order/order_batch_extend";
	}

	/**
	 * 增减员导出页面
	 *
	 * @return
	 * @deprecated
	 */
	@RequestMapping(value = "/admin/soin/in_or_decrease/index", method = RequestMethod.GET)
	public String soinInOrDecreasePage() {
		return "order/in_or_decrease";
	}

	/**
	 * 获取已开通社保地区树
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/soin_district/tree", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse getSoinDistricts() throws Exception {
		return new HttpResponse(soinOrderBillService.getAllSoinDistricts());
	}

	/**
	 * 上传文件并计算
	 *
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/order/import/calculate", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse uploadAndCalculateOrder(@RequestParam("file") MultipartFile file) throws Exception {
		String fileName = fileService.saveOrderCalculateExcelFile(file);
		return new HttpResponse(soinOrderBillService.uploadAndCalculateOrderBill(fileName));
	}

	/**
	 * 确认导入
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/order/import/confirm", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse uploadAndCalculateOrder(@RequestBody SoinOrderBillImportConfirmCommand command) throws Exception {
		Map map = new HashMap();
		map.put("msg", soinOrderBillService.soinOrderBillImportConfirm(command));
		return new HttpResponse(map);
	}

	/**
	 * 查询所有客户
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/customer/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse queryAllCustomers() throws Exception {
		return new HttpResponse(soinOrderBillService.getAllSoinOrderCustomers());
	}

	/**
	 * 供应商过滤条件查询
	 *
	 * @param districtId 如果district_id为空则返回所有供应商
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/suppliers/list")
	@ResponseBody
	public HttpResponse suppliersList(@RequestParam(value = "district_id", required = false) String districtId) throws Exception {
		return soinOrderBillService.suppliersList(districtId);
	}

	/**
	 * 对账单列表查询
	 *
	 * @param order 封装了查询参数
	 * @return 相应信息
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/query", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse<OrderResult> billManageQuery(OrderBillListQueryCommand order) throws Exception {
		return new HttpResponse(soinOrderBillService.billManageQuery(order));
	}

	/**
	 * 对账单详情列表
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/detail/list", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse billManageDetailList(@RequestParam String id) throws Exception {
		return new HttpResponse(soinOrderBillService.billManageDetailList(id));
	}
	/**
	 * 增减员查询
	 *
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/in_or_decrease/query", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse employQuery(OrderBillListQueryCommand order, HttpServletResponse response) throws Exception {
		return new HttpResponse(soinOrderBillService.employeeTypeDetailQuery(order, response));
	}
	/**
	 * 增减员导出
	 *
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/in_or_decrease/export", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse exportAddEmp(OrderBillListQueryCommand order, HttpServletResponse response) throws Exception {
		return soinOrderBillService.employeeModifyExport(order, response);
	}


	/**
	 * 对账单详情
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/detail/query", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse billManageDetailQuery(@RequestParam String id) throws Exception {
		return new HttpResponse(soinOrderBillService.billManageDetail(id));
	}

	/**
	 * 对账单列表导出
	 *
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/export", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse export(OrderBillListQueryCommand order, HttpServletResponse response) throws Exception {
		return soinOrderBillService.export(order, response);
	}

	/**
	 * 删除订单
	 *
	 * @param deleteListCommand
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/delete", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse deleteSoinOrderBill(@RequestBody IdListCommand deleteListCommand) throws Exception {
		soinOrderBillService.deleteSoinOrders(deleteListCommand);
		return new HttpResponse();
	}

	/**
	 * 订单缴纳成功
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/pay_succeed", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse payFailedSoinOrderBill(@RequestBody IdListCommand command) throws Exception {
		soinOrderBillService.paySuccessSoinOrders(command);
		return new HttpResponse();
	}


	/**
	 * 订单缴纳失败
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/pay_failed", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse payFailedSoinOrderBill(@RequestBody SoinOrderBillPayFailedCommand command) throws Exception {
		soinOrderBillService.payFailedSoinOrders(command);
		return new HttpResponse();
	}

	/**
	 * 调整其他费用
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/other_payment/adjust", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse adjustOtherPayment(@RequestBody AdjustSoinOrderOtherPaymentCommand command) throws Exception {
		soinOrderBillService.adjustOrderBillsOtherPayment(command);
		return new HttpResponse();
	}

	/**
	 * 按批次导出
	 *
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/export/batch", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse batchExport(@RequestParam("batch_id") String batchId, HttpServletResponse response) throws Exception {
		return soinOrderBillService.batchExport(batchId, response);
	}

	/**
	 * 按照查询条件的订单全部缴纳成功
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/pay_succeed/all", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse allPaySuccessSoinOrderBill(@RequestBody OrderBillListQueryCommand command) throws Exception {
		soinOrderBillService.paySuccessAllSoinOrders(command);
		return new HttpResponse();
	}


	/**
	 * 按照查询条件的订单缴纳失败
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/pay_failed/all", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse allPayFailedSoinOrderBill(@RequestBody OrderBillListQueryCommand command) throws Exception {
		soinOrderBillService.payFailedAllSoinOrders(command);
		return new HttpResponse();
	}

	/**
	 * 下载导入模板
	 *
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/bill/manage/export/template/download", method = RequestMethod.GET)
	@ResponseBody
	public void exportTemplateDownload(@RequestParam(value = "template_type") String templateType, HttpServletResponse response) throws IOException {
		soinOrderBillService.exportTemplateDownload(response,templateType);
	}

	/**
	 * 顺延订单查询
	 */
	@RequestMapping(value = "/admin/soin/bill/extend/query", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse queryCanExtendBills(BillExtendCommand command) throws Exception {
		return new HttpResponse(soinOrderBillService.extendQuery(command));
	}

	/**
	 * 顺延订单
	 *
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/soin/bill/extend/exec", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse extendBills(@RequestBody BillExtendCommand command) throws Exception {
		Map result = new HashMap();
		result.put("msg", soinOrderBillService.extend(command.getId()));
		return new HttpResponse(result);
	}
}