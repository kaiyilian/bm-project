package com.bumu.bran.admin.examination.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.examination.service.BranExamService;
import com.bumu.bran.employee.command.ExamCommand;
import com.bumu.bran.employee.result.ExamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping()
public class BranExamController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BranExamService branExamService;

	@RequestMapping(value = "/admin/employee/prospective/examination/result/querybyid")
	@ResponseBody
	public HttpResponse<ExamResult> querybyId(ExamCommand examCommand) throws Exception {

		return new HttpResponse<>(ErrorCode.CODE_OK, branExamService.querybyId(examCommand));
	}

}
