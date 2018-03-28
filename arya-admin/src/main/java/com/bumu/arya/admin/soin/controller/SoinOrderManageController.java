package com.bumu.arya.admin.soin.controller;


import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.soin.controller.command.ChangeOrderStatusCommand;
import com.bumu.arya.admin.soin.controller.command.OrderPartialCompleteCommand;
import com.bumu.arya.admin.soin.controller.command.OrderPaymentCommand;
import com.bumu.arya.admin.soin.controller.command.SoinOrderSetSalesmanAndSupplierCommand;
import com.bumu.arya.admin.soin.result.OrderResidualAmountResult;
import com.bumu.arya.admin.soin.result.OrderStatusChangeResult;
import com.bumu.arya.admin.soin.result.SoinOrderDetailResult;
import com.bumu.arya.admin.soin.result.SoinOrderListResult;
import com.bumu.arya.admin.soin.service.SoinOrderBillBatchService;
import com.bumu.arya.admin.soin.service.SoinOrderBillService;
import com.bumu.arya.admin.soin.service.SoinOrderService;
import com.bumu.arya.soin.constant.SoinOrderStatus;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社保订单相关接口
 */
@Controller
public class SoinOrderManageController extends BaseController {

	Logger log = LoggerFactory.getLogger(SoinOrderManageController.class);

    @Autowired
    SoinOrderService orderService;

    @Autowired
    SoinOrderBillBatchService orderBillBatchService;

    @Autowired
    SoinOrderBillService soinOrderBillService;

    /**
     * 获取社保订单管理页面
     */
    @RequestMapping(value = "admin/soin/order/manage/index", method = RequestMethod.GET)
    public String soinOrderManager(ModelMap map) {
        map.put("order_status", SoinOrderStatus.getOrderManageStatusMap());
        return "soin/soin_order_manager";
    }

    /**
     * 查询当前需要办理的订单
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/list/handle", method = RequestMethod.GET)
    public
    @ResponseBody
    JQDatatablesPaginationResult soinOrderList(@RequestParam Map<String, String> params) {

        Integer start = Integer.valueOf(params.get("start"));
        Integer pageSize = Integer.valueOf(params.get("length"));
        String districtId = params.get("district_id");
        String soinTypeIds = params.get("soin_type_id");
        String orderStatusCode = params.get("order_status_code");
        String keyword = params.get("keyword");
        JQDatatablesPaginationResult result = null;
        try {
            result = new JQDatatablesPaginationResult();
            SoinOrderListResult listResult = orderService.getOrderManageList(districtId, soinTypeIds, orderStatusCode, keyword != null ? keyword.trim() : null, start / pageSize, pageSize);
            result.setRecordsTotal(listResult.getTotalCount());
            result.setRecordsFiltered(listResult.getTotalCount());
            result.setData(listResult.getOrders());
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/detail", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<SoinOrderDetailResult> soinDetail(@RequestParam("order_id") String orderId) {
        try {
            return new HttpResponse<>(orderService.getOrderDetail(orderId));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 订单支付已完成
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/payment_complete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<OrderStatusChangeResult> orderPaymentComplete(@RequestBody OrderPaymentCommand command) {
        try {
            return new HttpResponse(orderService.orderPaymentComplete(command));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 部分缴纳（缴纳某月社保）
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/partial_complete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<OrderStatusChangeResult> orderPartialComplete(@RequestBody OrderPartialCompleteCommand command) {
        try {
            return new HttpResponse(orderService.orderPartialComplete(command.getSoinId(), command.getVersion()));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 缴纳中
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/underway", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<OrderStatusChangeResult> orderUnderway(@RequestBody ChangeOrderStatusCommand command) {
        try {
            return new HttpResponse(orderService.orderUnderway(command.getOrderId(), command.getVersion()));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 订单退款已完成
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/refund_complete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<OrderStatusChangeResult> orderRefundComplete(@RequestBody OrderPaymentCommand command) {
        try {
            return new HttpResponse(orderService.orderRefundComplete(command));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 订单退款中
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/refunding", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<OrderStatusChangeResult> orderRefunding(@RequestBody OrderPaymentCommand command) {
        try {
            return new HttpResponse(orderService.orderRefunding(command));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 已停缴
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/stop", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<OrderStatusChangeResult> orderStop(@RequestBody ChangeOrderStatusCommand command) {
        try {
            return new HttpResponse(orderService.orderStop(command.getOrderId(), command.getVersion()));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 补款
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/supply", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse orderSupply(@RequestBody ChangeOrderStatusCommand command) {
        try {
//			orderService.orderSimpleStatusChange(command);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

//	/**
//	 * 退款
//	 *
//	 * @param command
//	 * @return
//	 */
//	@RequestMapping(value = "/admin/soin/order/terminate", method = RequestMethod.POST)
//	public
//	@ResponseBody
//	HttpResponse orderTerminate(@RequestBody ChangeOrderStatusCommand command) {
//		try {
//			orderService.orderSimpleStatusChange(command);
//			return new HttpResponse();
//		} catch (AryaServiceException e) {
//			log.error(e.getMessage(), e);
//			return new HttpResponse(e.getErrorCode());
//		}
//	}

    /**
     * 订单完成
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/complete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<OrderStatusChangeResult> orderComplete(@RequestBody ChangeOrderStatusCommand command) {
        try {
            return new HttpResponse(orderService.orderComplete(command.getOrderId(), command.getVersion()));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 订单异常
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/exception", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<OrderStatusChangeResult> orderException(@RequestBody ChangeOrderStatusCommand command) {
        try {
            return new HttpResponse(orderService.orderException(command.getOrderId(), command.getVersion()));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 订单异常恢复
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/recover", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse orderRecover(@RequestBody ChangeOrderStatusCommand command) {
        try {
//			orderService.orderSimpleStatusChange(command);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 订单取消
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/cancel", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<OrderStatusChangeResult> orderCancel(@RequestBody ChangeOrderStatusCommand command) {
        try {
            return new HttpResponse(orderService.orderCancel(command.getOrderId(), command.getVersion()));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 查询订单未缴纳的剩余金额
     */
    @RequestMapping(value = "/admin/soin/order/manage/residual_amount", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<OrderResidualAmountResult> getResidualAmount(@RequestParam("order_id") String orderId) {
        try {
            return new HttpResponse(orderService.orderResidualAmount(orderId));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 为订单赋值业务员和供应商
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/set_salesman_supplier", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse setSalesmanAndSupplier(@RequestBody SoinOrderSetSalesmanAndSupplierCommand command) {
        try {
            orderService.setOrderSalesmanAndSupplier(command);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 获取所有业务员,包括客服
     *
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/salesman/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse queryAllSalesMan() {
        try {
            return new HttpResponse(orderBillBatchService.getAllSalesman());
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 供应商过滤条件查询
     *
     * @param district_id 如果district_id为空则返回所有供应商
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/manage/suppliers/list")
    @ResponseBody
    public HttpResponse suppliersList(String district_id) {
        return soinOrderBillService.suppliersList(district_id);
    }
}
