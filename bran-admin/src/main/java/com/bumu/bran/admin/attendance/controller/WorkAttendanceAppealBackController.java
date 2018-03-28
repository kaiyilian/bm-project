package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.attendance.service.WorkAttendanceAppealFeedBackService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.attendance.command.WorkAttendanceAppealDealCommand;
import com.bumu.bran.attendance.result.WorkAttendanceAppealBackResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 申诉反馈
 * Created by DaiAoXiang on 2017/5/8.
 */
@Deprecated
@Api(value = "WorkAttendanceAppealBackController", tags = {"申诉反馈AttendanceAppealBack"})
@Controller
@RequestMapping(value = "/admin/work_attendance/appealback")
public class WorkAttendanceAppealBackController {

	@Autowired
	WorkAttendanceAppealFeedBackService workAttendanceAppealFeedBackService;

	@ApiOperation(httpMethod = "GET", value = "查询申述反馈列表")
	@ApiResponse(code = 200, message = "成功", response = Pager.class)
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	@GetSessionInfo
	public HttpResponse<WorkAttendanceAppealBackResult> getFeeBackList(
			@ApiParam("班组id") @RequestParam("shiftId")String workShiftId,
			@ApiParam("员工姓名")@RequestParam("empName") String empName,
			@ApiParam("申诉分类 0:迟到 1:缺卡 2:早退等") @RequestParam("appealType") Integer appealType,
			@ApiParam("申诉状态 0：未处理 1：通过 2：不通过")@RequestParam("dealState") Integer dealState,
			@ApiParam("申诉日期") @RequestParam("attendTime") Long attendTime,
			@ApiParam("当前页") @RequestParam("page")Integer page,
			@ApiParam("一页数量")@RequestParam("page_size") Integer pageSize,
			SessionInfo sessionInfo) throws Exception{
		return new HttpResponse(workAttendanceAppealFeedBackService.getFeeBackList(workShiftId,empName,appealType,dealState,attendTime,page,pageSize,sessionInfo));
	}

	@ApiOperation(httpMethod = "POST", value = "处理申诉反馈")
	@ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
	@RequestMapping(value = "deal", method = RequestMethod.POST)
	@ResponseBody
	@GetSessionInfo
	public HttpResponse<Object> dealAppealback(@ApiParam("申诉反馈id") @RequestBody WorkAttendanceAppealDealCommand command,
												  SessionInfo sessionInfo) throws Exception{
		workAttendanceAppealFeedBackService.dealAppealback(command,sessionInfo);
		return new HttpResponse();
	}
}
