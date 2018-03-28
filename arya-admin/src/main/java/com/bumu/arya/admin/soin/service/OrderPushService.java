package com.bumu.arya.admin.soin.service;

import com.bumu.exception.AryaServiceException;

/**
 * @author CuiMengxin
 * @date 2016/1/26
 */
public interface OrderPushService {

	/**
	 * 推送给用户订单已支付
	 *
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws AryaServiceException
	 */
	String pushOrderPayed(String orderId, String orderNo, String userId) throws AryaServiceException;

	/**
	 * 推送给用户某月社保已经缴纳
	 *
	 * @param soinId
	 * @param month
	 * @param userId
	 * @return
	 * @throws AryaServiceException
	 */
	String pushSoinPayed(String soinId, Integer month, String userId, String personName) throws AryaServiceException;

	/**
	 * 推送给用户订单已经完成
	 *
	 * @param orderId
	 * @param orderNo
	 * @param userId
	 * @param personName
	 * @return
	 */
	String pushOrderComplete(String orderId, String orderNo, String userId, String personName);

	/**
	 * 推送给用户订单完成退款
	 *
	 * @param orderId
	 * @param orderNo
	 * @param userId
	 * @param personName
	 * @return
	 */
	String pushOrderRefund(String orderId, String orderNo, String userId, String personName,String money);

	/**
	 * 推送给用户订单退款中
	 *
	 * @param orderId
	 * @param orderNo
	 * @param userId
	 * @param personName
	 * @return
	 */
	String pushOrderRefunding(String orderId, String orderNo, String userId, String personName);

	/**
	 * 推送给用户订单已取消
	 * @param orderId
	 * @param orderNo
	 * @param userId
	 * @param personName
	 * @return
	 */
	String pushOrderCancel(String orderId, String orderNo, String userId, String personName);

	/**
	 * 推送给用户订单已停缴
	 * @param orderId
	 * @param orderNo
	 * @param userId
	 * @param personName
	 * @return
	 */
	String pushOrderStop(String orderId, String orderNo, String userId, String personName);

	/**
	 * 推送给用户订单正在缴纳中
	 * @param orderId
	 * @param orderNo
	 * @param userId
	 * @param personName
	 * @return
	 */
	String pushOrderUnderway(String orderId, String orderNo, String userId, String personName);
}
