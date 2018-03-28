package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.attendance.service.WorkAttendanceService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.attendance.command.WorkAttendanceCommand;
import com.bumu.bran.attendance.command.WorkAttendanceQueryCommand;
import com.bumu.bran.attendance.result.WorkAttendanceOneMonthResult;
import com.bumu.bran.attendance.result.WorkAttendanceStateResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author majun
 * @date 2017/3/22
 */
@Api(value = "WorkAttendanceController", tags = {"考勤记录接口Attendance"})
@Controller
@RequestMapping(value = "/admin/attendance")
@Deprecated
public class WorkAttendanceController {

    private static Logger logger = LoggerFactory.getLogger(WorkAttendanceController.class);

    @Autowired
    private WorkAttendanceService workAttendanceService;

    @ApiOperation(httpMethod = "GET", value = "获取考勤状态: 更新状态: 清空(0),请假(1),正常打卡(2),迟到(3),早退(4),缺卡(5), 所有状态" +
            "打卡开始时间状态(缺卡(0)、休息(1)、正常(2)、无效(3)、请假(4)、清空(5)、早退(6)、迟到(7)、加班(8)、休息加班(9)、上班请假(10)、下班请假(11))")
    @ApiResponse(code = 200, message = "成功", response = WorkAttendanceStateResult.class)
    @RequestMapping(value = "get/state", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<WorkAttendanceStateResult> getState() {
        logger.info("getState ...");
        return new HttpResponse(ErrorCode.CODE_OK, workAttendanceService.getState());
    }

    @ApiOperation(httpMethod = "POST", value = "查询某个日期的考勤记录")
    @ApiResponse(code = 200, message = "成功", response = WorkAttendanceOneMonthResult.class)
    @RequestMapping(value = "get/one/dayOrMonth/list", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse<WorkAttendanceOneMonthResult> getOneDayOrMonthList(
            @Valid @ApiParam("考勤查询请求对象") @RequestBody WorkAttendanceQueryCommand command,
            BindingResult bindingResult, SessionInfo sessionInfo) throws IOException {
        return new HttpResponse(ErrorCode.CODE_OK, workAttendanceService.getOneDayOrMonthList(command, sessionInfo));
    }

    @ApiOperation(httpMethod = "POST", value = "编辑考勤记录")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "update/one", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse updateOne(@Valid @ApiParam("编辑考勤对象") @RequestBody WorkAttendanceCommand command,
                                  BindingResult bindingResult,
                                  SessionInfo sessionInfo) {
        workAttendanceService.updateOne(command, sessionInfo);
        return new HttpResponse(ErrorCode.CODE_OK);
    }
}
