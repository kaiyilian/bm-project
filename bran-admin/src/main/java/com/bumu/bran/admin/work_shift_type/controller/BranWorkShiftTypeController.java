package com.bumu.bran.admin.work_shift_type.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.work_shift_type.service.BranWorkShiftTypeService;
import com.bumu.bran.common.annotation.SetParams;
import com.bumu.bran.workshift.command.WorkShiftTypeCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author majun
 * @date 2016/12/19
 */
@Controller
@Transactional // AOP中记录日志有用
public class BranWorkShiftTypeController {

	private Logger logger = LoggerFactory.getLogger(BranWorkShiftTypeController.class);

	@Autowired
	private BranWorkShiftTypeService branWorkShiftTypeService;

	/**
	 * 查询班次包含默认项
	 *
	 * @param workShiftTypeCommand
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {
            "/admin/attendance/summary/workShiftType/get/default",
            "/admin/attendance/detail/workShiftType/get/default",
	        "/admin/attendance/schedule/rule/workShiftType/get/default",
            "/admin/attendance/schedule/view/workShiftType/get/default"}, method = RequestMethod.GET)
	@ResponseBody
	@SetParams
	public HttpResponse getDefault(WorkShiftTypeCommand workShiftTypeCommand, BindingResult bindingResult) throws Exception {
		logger.debug("/admin/workShiftType/get/default start ....");
		logger.debug("params: " + workShiftTypeCommand.toString());
		HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branWorkShiftTypeService.getDefault(workShiftTypeCommand));
		logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
		return httpResponse;
	}

}
