package com.bumu.arya.admin.soin.controller;

import com.bumu.arya.admin.misc.controller.InitBinderController;
import com.bumu.arya.admin.soin.controller.command.OrderBatchUpdateCommand;
import com.bumu.arya.admin.soin.service.OrderBatchService;
import com.bumu.arya.common.OperateConstants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.legacy.Log;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.command.ModelCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin/order/batch")
@Transactional // @Log 记录日志用
public class OrderBatchController extends InitBinderController {

	private static Logger logger = LoggerFactory.getLogger(OrderBatchController.class);

	@Autowired
	private OrderBatchService orderBatchService;

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	@ParamCheck
	@Log(model = OperateConstants.SOIN_ORDER_BATCH_UPDATE_ORDER, type = Log.Type.UPDATE)
	public HttpResponse batchUpdate(@RequestBody @Valid OrderBatchUpdateCommand batch, BindingResult bindingResult) throws Exception{
		logger.info("update ...");
		orderBatchService.batchUpdate(batch);
		return new HttpResponse(ErrorCode.CODE_OK);
	}

	@RequestMapping(value = "get/one/detail", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse getOneDetail(ModelCommand command) throws Exception {
		logger.info("getOneDetail ...");
		return new HttpResponse(ErrorCode.CODE_OK, orderBatchService.getOneDetail(command));
	}

}
