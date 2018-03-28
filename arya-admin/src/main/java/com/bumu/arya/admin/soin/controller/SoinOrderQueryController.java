package com.bumu.arya.admin.soin.controller;

import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.soin.result.SoinOrderDetailResult;
import com.bumu.arya.admin.soin.result.SoinOrderListResult;
import com.bumu.arya.admin.soin.service.SoinOrderService;
import com.bumu.arya.soin.constant.SoinOrderStatus;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author CuiMengxin
 * @date 2016/2/1
 */
@Controller
public class SoinOrderQueryController extends BaseController {

	Logger log = LoggerFactory.getLogger(SoinOrderQueryController.class);

    @Autowired
    SoinOrderService orderService;

    /**
     * 获取社保订单查询页面
     */
    @RequestMapping(value = "admin/soin/order/query/index", method = RequestMethod.GET)
    public String soinOrderQuerier(ModelMap map) {
        map.put("order_status", SoinOrderStatus.getStatusMap());
        return "soin/soin_order_querier";
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/query/detail", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<SoinOrderDetailResult> soinDetail(@RequestParam("order_id") String orderId) {
        try {
            return new HttpResponse<>(orderService.getOrderDetail(orderId));
        } catch (AryaServiceException e) {
            e.printStackTrace();
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 查询订单列表
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/admin/soin/order/query/list", method = RequestMethod.GET)
    public
    @ResponseBody
    JQDatatablesPaginationResult getOrderQueryList(@RequestParam Map<String, String> params) {

        Integer start = Integer.valueOf(params.get("start"));
        Integer pageSize = Integer.valueOf(params.get("length"));
        String districtId = params.get("district_id");
        String soinTypeIds = params.get("soin_type_id");
        String orderStatusCode = params.get("order_status_code");
        String orderNo = params.get("order_no");
        String userIdcardOrPhoneOrName = params.get("user");
        String personIdcardOrPhoneOrName = params.get("person");
        JQDatatablesPaginationResult result = null;
        try {
            result = new JQDatatablesPaginationResult();
            SoinOrderListResult listResult = null;
            if (StringUtils.isAnyBlank(orderNo) && StringUtils.isAnyBlank(userIdcardOrPhoneOrName) && StringUtils.isAnyBlank(personIdcardOrPhoneOrName))
                listResult = orderService.getOrderQueryList(districtId, soinTypeIds, orderStatusCode, start / pageSize, pageSize);
            else if (!StringUtils.isAnyBlank(orderNo))
                listResult = orderService.getOrderByOrderNo(orderNo);
            else if (!StringUtils.isAnyBlank(userIdcardOrPhoneOrName))
                listResult = orderService.getOrderQueryListByUserIdcardOrPhoneOrName(userIdcardOrPhoneOrName, start / pageSize, pageSize);
            else if (!StringUtils.isAnyBlank(personIdcardOrPhoneOrName))
                listResult = orderService.getOrderQueryListByPersonIdcardOrPhoneOrName(personIdcardOrPhoneOrName, start / pageSize, pageSize);
            result.setRecordsTotal(listResult.getTotalCount());
            result.setRecordsFiltered(listResult.getFilterCount());
            result.setData(listResult.getOrders());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
