package com.bumu.arya.admin.soin.service;

import com.bumu.arya.admin.soin.controller.command.OrderPaymentCommand;
import com.bumu.arya.admin.soin.controller.command.SoinOrderSetSalesmanAndSupplierCommand;
import com.bumu.arya.admin.soin.result.OrderResidualAmountResult;
import com.bumu.arya.admin.soin.result.OrderStatusChangeResult;
import com.bumu.arya.admin.soin.result.SoinOrderDetailResult;
import com.bumu.arya.admin.soin.result.SoinOrderListResult;
import com.bumu.arya.soin.model.entity.AryaSoinOrderEntity;
import com.bumu.exception.AryaServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author CuiMengxin
 * @date 2015/12/21
 */
@Transactional
public interface SoinOrderService {

    AryaSoinOrderEntity loadSoinOrder(String orderId) throws AryaServiceException;

    /**
     * 获取订单管理的订单列表
     *
     * @param districtId      地区编号，用于全选社保类型的情况
     * @param soinTypeIds     社保类型id组合，用于非全选情况，如aaaa:bbbbb:cccc
     * @param orderStatusCode 订单状态位或，如1|2|4
     * @param page
     * @param pageSize
     * @return
     */
    SoinOrderListResult getOrderManageList(String districtId, String soinTypeIds, String orderStatusCode, String keyWord, int page, int pageSize);


    /**
     * 获取订单列表
     *
     * @param districtId
     * @param soinTypeIds
     * @param orderStatusCode
     * @param page
     * @param pageSize
     * @return
     */
    SoinOrderListResult getOrderQueryList(String districtId, String soinTypeIds, String orderStatusCode, int page, int pageSize);

    /**
     * 根据订单编号查订单
     *
     * @param orderNo
     * @return
     */
    SoinOrderListResult getOrderByOrderNo(String orderNo);

    /**
     * 根据用户身份证号或者手机号查询订单
     *
     * @param userIdcardOrPhoneOrName
     * @param page
     * @param pageSize
     * @return
     */
    SoinOrderListResult getOrderQueryListByUserIdcardOrPhoneOrName(String userIdcardOrPhoneOrName, int page, int pageSize);

    /**
     * 根据参保人身份证号或者手机号查询订单
     *
     * @param personIdcardOrPhoneOrName
     * @param page
     * @param pageSize
     * @return
     */
    SoinOrderListResult getOrderQueryListByPersonIdcardOrPhoneOrName(String personIdcardOrPhoneOrName, int page, int pageSize);

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     * @throws AryaServiceException
     */
    SoinOrderDetailResult getOrderDetail(String orderId) throws AryaServiceException;

    /**
     * 得到订单总数
     *
     * @return
     */
    Long getOrderTotalCount();

    /**
     * 订单付款
     *
     * @param command
     * @throws AryaServiceException
     */
    OrderStatusChangeResult orderPaymentComplete(OrderPaymentCommand command) throws AryaServiceException;

    /**
     * 订单已退款
     *
     * @param command
     * @throws AryaServiceException
     */
    OrderStatusChangeResult orderRefundComplete(OrderPaymentCommand command) throws AryaServiceException;

    /**
     * 订单退款中
     *
     * @param command
     * @return
     * @throws AryaServiceException
     */
    OrderStatusChangeResult orderRefunding(OrderPaymentCommand command) throws AryaServiceException;

    /**
     * 查询订单剩余金额
     *
     * @param orderId
     * @return
     * @throws AryaServiceException
     */
    OrderResidualAmountResult orderResidualAmount(String orderId) throws AryaServiceException;

    /**
     * 部分缴纳（确认缴纳某月社保）
     *
     * @param soinId
     * @param version
     * @return
     * @throws AryaServiceException
     */
    OrderStatusChangeResult orderPartialComplete(String soinId, long version) throws AryaServiceException;

    /**
     * 完成订单
     *
     * @param orderId
     * @param version
     */
    OrderStatusChangeResult orderComplete(String orderId, long version) throws AryaServiceException;

    /**
     * 取消订单
     *
     * @param orderId
     * @param version
     * @return
     */
    OrderStatusChangeResult orderCancel(String orderId, long version) throws AryaServiceException;

    /**
     * 停缴订单
     *
     * @param orderId
     * @param version
     * @return
     */
    OrderStatusChangeResult orderStop(String orderId, long version) throws AryaServiceException;

    /**
     * 订单异常
     *
     * @param orderId
     * @param version
     * @return
     */
    OrderStatusChangeResult orderException(String orderId, long version) throws AryaServiceException;

    /**
     * 订单缴纳中
     *
     * @param orderId
     * @param version
     * @return
     */
    OrderStatusChangeResult orderUnderway(String orderId, long version) throws AryaServiceException;

    /**
     * 为订单设置供应商和业务员
     *
     * @param command
     * @throws AryaServiceException
     */
    void setOrderSalesmanAndSupplier(SoinOrderSetSalesmanAndSupplierCommand command) throws AryaServiceException;

    /**
     * 修改订单的社保缴纳详情的出账费用
     *
     * @param orderId
     * @param feeOut
     * @throws AryaServiceException
     */
    void changeOrderSoinOutFee(String orderId, BigDecimal feeOut) throws AryaServiceException;
}
