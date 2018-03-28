package com.bumu.arya.admin.soin.service.impl;

import com.bumu.arya.admin.soin.controller.command.OrderBatchUpdateCommand;
import com.bumu.arya.admin.soin.result.OrderDetailResult;
import com.bumu.arya.admin.soin.service.OrderBatchService;
import com.bumu.arya.admin.soin.controller.command.OrderDetailCommand;
import com.bumu.arya.common.Constants;
import com.bumu.arya.soin.model.dao.SoinOrderDao;
import com.bumu.arya.soin.model.entity.AryaSoinEntity;
import com.bumu.arya.soin.model.entity.AryaSoinOrderEntity;
import com.bumu.common.command.ModelCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author majun
 * @date 2017/2/22
 */
@Service
public class OrderBatchServiceImpl implements OrderBatchService {

	@Autowired
	private SoinOrderDao orderDao;

	@Override
	public OrderDetailResult getOneDetail(ModelCommand command) {
		AryaSoinOrderEntity orderEntity = orderDao.billManageDetailList(command.getId(), Constants.SOIN_VERSION_TYPE_NORMAL);
		if(orderEntity == null){
			return null;
		}
		return OrderDetailResult.create(orderEntity.getOneDetail());
	}

	@Override
	public void batchUpdate(OrderBatchUpdateCommand batch) {
		for (ModelCommand command : batch.getBatch()) {
			updateOne(command, batch.getModel());
		}
	}

	private void updateOne(ModelCommand command, OrderDetailCommand detailCommand) {
		AryaSoinOrderEntity orderEntity = orderDao.billManageDetailList(command.getId(),
				Constants.SOIN_VERSION_TYPE_NORMAL);

		oneOrderAmonut(orderEntity, detailCommand);

		orderDao.update(orderEntity);
	}

    public void oneOrderAmonut(AryaSoinOrderEntity orderEntity, OrderDetailCommand detailCommand) {
        AryaSoinEntity detail = orderEntity.getOneDetail();
        setProperty(detail, detailCommand);
        // 收账
        detail.setTotalPayment(detail.oneOrderAmonut(detail.getFees()));
        // 出账
        detail.setTotalOutPayment(detail.oneOrderAmonut(detail.getFeesOut()));

        // 收账
        orderEntity.setPayment(detail.getTotalPayment());
        // 出账
        orderEntity.setTotalOutPayment(detail.getTotalOutPayment());
    }

    /**
     * 设置更新属性
     *
     * @param detailCommand
     */
    public void setProperty(AryaSoinEntity detail, OrderDetailCommand detailCommand) {
        detail.setDisabilityTotal(detailCommand.getDisabilityTotal());
        detail.setHeatingTotal(detailCommand.getHeatingTotal());
        detail.setHouseFundAdditionTotal(detailCommand.getHouseFundAdditionTotal());
        detail.setInjuryAdditionTotal(detailCommand.getInjuryAdditionTotal());
        detail.setServerIllnessTotal(detailCommand.getServerIllnessTotal());
    }


}
