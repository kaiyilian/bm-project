package com.bumu.arya.admin.soin.service.impl;

import com.bumu.arya.admin.soin.service.OrderPushService;
import com.bumu.common.service.PushService;
import com.bumu.common.model.PushMessage;
import com.bumu.arya.common.Constants;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.exception.AryaServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bumu.common.model.PushTo.PushToOne;

/**
 * @author CuiMengxin
 * @date 2016/1/26
 */
@Service
public class OrderPushServiceImpl implements OrderPushService {

	Logger log = LoggerFactory.getLogger(OrderPushServiceImpl.class);

	@Autowired
	AryaUserDao userDao;

	@Autowired
	PushService pushService;

	@Override
	public String pushOrderPayed(String orderId, String orderNo, String userId) throws AryaServiceException {
		return pushOrderStatusChangeMsg(orderId, "订单支付成功", "您的订单" + orderNo + "已经支付成功", PushMessage.ICON_SOIN_ORDER, PushMessage.TYPE_SOIN_ORDER, userId);
	}

	@Override
	public String pushSoinPayed(String soinId, Integer month, String userId, String personName) throws AryaServiceException {
		return pushOrderStatusChangeMsg(soinId, "社保缴纳进度", "参保人" + personName + "的" + month + "月社保已经缴纳", PushMessage.ICON_SOIN_ORDER, PushMessage.TYPE_SOIN_ORDER, userId);
	}

	@Override
	public String pushOrderComplete(String orderId, String orderNo, String userId, String personName) {
		return pushOrderStatusChangeMsg(orderId, "订单完成", "参保人" + personName + "的" + orderNo + "社保订单已经全部完成", PushMessage.ICON_SOIN_ORDER, PushMessage.TYPE_SOIN_ORDER, userId);
	}

	@Override
	public String pushOrderRefund(String orderId, String orderNo, String userId, String personName,String money) {
		return pushOrderStatusChangeMsg(orderId, "订单退款成功", "参保人" + personName + "的" + orderNo + "社保订单已经完成退款"+money+"元", PushMessage.ICON_SOIN_ORDER, PushMessage.TYPE_SOIN_ORDER, userId);
	}

	@Override
	public String pushOrderRefunding(String orderId, String orderNo, String userId, String personName) {
		return pushOrderStatusChangeMsg(orderId, "订单正在退款中", "参保人" + personName + "的" + orderNo + "社保订单正在退款中", PushMessage.ICON_SOIN_ORDER, PushMessage.TYPE_SOIN_ORDER, userId);
	}

	@Override
	public String pushOrderCancel(String orderId, String orderNo, String userId, String personName) {
		return pushOrderStatusChangeMsg(orderId, "订单取消成功", "参保人" + personName + "的" + orderNo + "社保订单已经取消", PushMessage.ICON_SOIN_ORDER, PushMessage.TYPE_SOIN_ORDER, userId);
	}

	@Override
	public String pushOrderStop(String orderId, String orderNo, String userId, String personName) {
		return pushOrderStatusChangeMsg(orderId, "订单已停缴", "参保人" + personName + "的" + orderNo + "社保订单已经停止缴纳", PushMessage.ICON_SOIN_ORDER, PushMessage.TYPE_SOIN_ORDER, userId);
	}

	@Override
	public String pushOrderUnderway(String orderId, String orderNo, String userId, String personName) {
		return pushOrderStatusChangeMsg(orderId, "订单正在缴纳中", "参保人" + personName + "的" + orderNo + "社保订单正在缴纳中", PushMessage.ICON_SOIN_ORDER, PushMessage.TYPE_SOIN_ORDER, userId);
	}

	private String pushOrderStatusChangeMsg(String bzid, String title, String content, int icon, int type, String userId) {
		AryaUserEntity userEntity = userDao.findUserByIdThrow(userId);
		PushMessage pushMessage = new PushMessage();
		pushMessage.setTitle(title);
		pushMessage.setContent(content);
		pushMessage.setIcon(icon);
		pushMessage.setType(type);
		pushMessage.setUserId(userId);
		PushToOne pushTo = new PushToOne(bzid, userId, Constants.ClientType.getClientType(userEntity.getLastClientType()));
		String pushResult = pushService.push(pushMessage, pushTo);
		log.info(pushResult);
		return pushResult;
	}
}
