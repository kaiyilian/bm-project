package com.bumu.arya.admin.service;

import com.bumu.arya.admin.soin.result.SoinOrderListResult;
import com.bumu.arya.admin.soin.service.SoinOrderService;
import com.bumu.arya.common.Constants;
import com.bumu.arya.soin.constant.SoinOrderStatus;
import com.bumu.arya.soin.model.dao.SoinOrderDao;
import com.bumu.arya.soin.model.entity.AryaSoinOrderEntity;
import com.bumu.exception.AryaServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * Created by allen on 16/1/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/ac_datasource.xml"})
public class ConcurrentOrderTest {

	Logger log = LoggerFactory.getLogger(ConcurrentOrderTest.class);

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
    SoinOrderService soinOrderService;

	@Autowired
	SoinOrderDao soinOrderDao;

	@Test(expected = HibernateOptimisticLockingFailureException.class)
	public void test() throws AryaServiceException {
		SoinOrderListResult orderList =
				soinOrderService.getOrderManageList(Constants.CHN_ID, null, String.valueOf(SoinOrderStatus.ORDER_PAYED), null, 0, 10);

		log.info("订单：" + orderList.getOrders().size());

		if (orderList == null || orderList.getOrders().size() == 0) {
			log.info("无订单");
			return;
		}

		String orderId = "ebbe89c4987c4963b07f3168799fe45d";

//		orderId = orderList.get(0).getOrderId();

		AryaSoinOrderEntity aryaSoinOrderEntity1 = soinOrderService.loadSoinOrder(orderId);
		log.info("订单事务版本: " + aryaSoinOrderEntity1.getTxVersion());
		aryaSoinOrderEntity1.setStatusCode(SoinOrderStatus.ORDER_PAYED);

		AryaSoinOrderEntity aryaSoinOrderEntity2 = soinOrderService.loadSoinOrder(orderId);
		log.info("订单事务版本: " + aryaSoinOrderEntity1.getTxVersion());
		aryaSoinOrderEntity2.setStatusCode(SoinOrderStatus.ORDER_ABNORMAL);

		soinOrderDao.update(aryaSoinOrderEntity1);

		/** 此处应抛出 HibernateOptimisticLockingFailureException 异常 */
		soinOrderDao.update(aryaSoinOrderEntity2);

	}

}
