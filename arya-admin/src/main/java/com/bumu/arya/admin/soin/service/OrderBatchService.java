package com.bumu.arya.admin.soin.service;

import com.bumu.arya.admin.soin.controller.command.OrderBatchUpdateCommand;
import com.bumu.arya.admin.soin.result.OrderDetailResult;
import com.bumu.common.command.ModelCommand;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author majun
 * @date 2017/2/22
 */
@Transactional
public interface OrderBatchService {

	void batchUpdate(OrderBatchUpdateCommand batch);

	OrderDetailResult getOneDetail(ModelCommand command);
}
