package com.bumu.arya.admin.operation.controller;

import com.bumu.arya.activity.command.EnrollRecordCommand;
import com.bumu.arya.activity.result.EnrollRecordListResult;
import com.bumu.arya.admin.operation.service.EnrollRecordService;
import com.bumu.arya.admin.welfare.service.WelfareService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.service.ConfigService;
import com.bumu.common.util.StringUtil;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 报名活动
 *
 * 2017年12月27日11:15:19
 *
 * farmeryou
 */
@Api(value = "activity",tags = "报名活动")
@Controller
@RequestMapping(value = "/admin")
public class EnrollRecordController {

	Logger log = LoggerFactory.getLogger(EnrollRecordController.class);

	@Autowired
	WelfareService welfareService;

	@Autowired
	EnrollRecordService enrollRecordService;

	@Autowired
	private ConfigService configService;

	private static String acName="arya.admin.activity.enroll.name";


	/**
	 * 获得活动名称
	 *
	 *
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "活动名称",value = "活动名称")
	@RequestMapping(value = "/activity/enroll/name/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<String> getEnrollRecordList( ) {
		 String str=configService.getConfigByKey(acName);
		 String strs []={};
		 if(!StringUtil.isEmptyStr(str)){
			 strs= str.split(",");
		 }
		return new HttpResponse(strs);
	}



	/**
	 * 报名列表
	 *
	 *
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "报名列表",value = "报名列表")
	@RequestMapping(value = "/activity/enroll/record/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<EnrollRecordListResult> getEnrollRecordList(
			@ApiParam(value = "开始时间") @RequestParam(value = "begin_time", required = false) Long begin_time,
			@ApiParam(value = "结束时间") @RequestParam(value = "end_time", required = false) Long end_time,
			@ApiParam(value = "手机号") @RequestParam(value = "mobile", required = false) String mobile,
			@ApiParam(value = "页码") @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "每页数量") @RequestParam(value = "page_size", required = false) Integer page_size,
			@ApiParam(value = "活动名称") @RequestParam(value = "ac_name", required = false) String activity_name
			 ) {
		try {
			if(page==null){
				page=1;
			}
			page=page-1;

			if(page_size==null){
				page_size=10;
			}

			EnrollRecordCommand command =  new EnrollRecordCommand(mobile,begin_time,end_time,page,page_size,activity_name,null,null);
			return new HttpResponse(enrollRecordService.queryRecordList(command));
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

    /**
     * 报名列表导出
     *
     * @return
     */
    @ApiOperation(httpMethod = "GET",notes = "报名列表导出",value = "报名列表导出")
    @RequestMapping(value = "/activity/enroll/record/list/export", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse EnrollEnrollRecordList(
            @ApiParam(value = "开始时间") @RequestParam(value = "begin_time", required = false) Long begin_time,
            @ApiParam(value = "结束时间") @RequestParam(value = "end_time", required = false) Long end_time,
            @ApiParam(value = "手机号") @RequestParam(value = "mobile", required = false) String mobile,
			@ApiParam(value = "活动名称") @RequestParam(value = "ac_name", required = false) String activity_name
            , HttpServletResponse response) {
        try {
            EnrollRecordCommand command =  new EnrollRecordCommand(mobile,begin_time,end_time,null,null,activity_name,null,null);
			return new HttpResponse(ErrorCode.CODE_OK, enrollRecordService.exportOrders(command, response));

        } catch (Exception e) {
            return new HttpResponse();
        }
    }

}
