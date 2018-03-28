package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.attendance.service.WorkAttendanceService;
import com.bumu.bran.attendance.command.NewWorkAttendanceCleanCommand;
import com.bumu.bran.attendance.command.NewWorkAttendanceUpdateCommand;
import com.bumu.bran.attendance.result.NewWorkAttendanceViewResult;
import com.bumu.bran.attendance.result.WorkAttendanceEmpMonthResult;
import com.bumu.bran.attendance.result.WorkAttendanceOneMonthResult;
import com.bumu.bran.attendance.result.WorkAttendanceTotalExportUrlResult;
import com.bumu.bran.common.annotation.GetSessionInfo;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author majun
 * @date 2017/3/22
 */
@Api(tags = {"考勤管理-出勤明细 AttendanceDetail"})
@Controller
@RequestMapping(value = "/admin/attendance/detail")
public class NewWorkAttendanceController {

    private static Logger logger = LoggerFactory.getLogger(NewWorkAttendanceController.class);

    @Autowired
    private WorkAttendanceService workAttendanceService;


    @ApiOperation(httpMethod = "GET", value = "查询员工某月考勤数据")
    @ApiResponse(code = 200, message = "成功", response = WorkAttendanceOneMonthResult.class)
    @RequestMapping(value = "employ/month/data/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttendanceEmpMonthResult> empMonthDataList(
            @ApiParam("时间（年-月）") @RequestParam(value = "yearMonth") Long yearMonth,
            @ApiParam("出勤周期") @RequestParam(value = "settingId") String settingId,
            @ApiParam("员工ID") @RequestParam(value = "empId") String empId,
            @ApiParam("在职离职 0离职 1在职") @RequestParam(value = "isOnJob") Integer isOnJob,
            SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(ErrorCode.CODE_OK, workAttendanceService.getEmpMonthDataList(yearMonth, settingId, empId, isOnJob, sessionInfo));
    }

    @ApiOperation(httpMethod = "POST", value = "编辑考勤记录")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse update(@Valid @ApiParam("编辑考勤对象") @RequestBody NewWorkAttendanceUpdateCommand command,
                               BindingResult bindingResult,
                               SessionInfo sessionInfo) throws Exception {
        workAttendanceService.update(command, sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(httpMethod = "GET", value = "考勤记录详情（用于修改考勤）")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "view", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<NewWorkAttendanceViewResult> view(
            @ApiParam("编辑考勤对象") @RequestParam(value = "workAttendId") String workAttendId,
            SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(ErrorCode.CODE_OK, workAttendanceService.view(workAttendId));
    }

    @ApiOperation(httpMethod = "POST", value = "清空考勤")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "clean", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse clean(@Valid @ApiParam("清空考勤对象") @RequestBody NewWorkAttendanceCleanCommand command,
                              BindingResult bindingResult,
                              SessionInfo sessionInfo) {
        workAttendanceService.clean(command, sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    /*@ApiOperation(httpMethod = "GET", value = "查询修改记录")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "modify/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse<NewWorkAttendanceModifyResult> modifyList(@ApiParam("考勤ID") @RequestParam(value = "id") String id) {
        workAttendanceService.modifyList(id);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }*/


    @ApiOperation(httpMethod = "GET", value = "导出")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "export", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttendanceTotalExportUrlResult> export(
            @ApiParam("员工ID") @RequestParam(value = "empId") String empId,
            @ApiParam("周期ID") @RequestParam(value = "settingId") String settingId,
            @ApiParam("时间（年-月）") @RequestParam(value = "yearMonth") Long yearMonth,
            @ApiParam("是否在职 0离职， 1在职") @RequestParam(value = "isOnJob") Integer isOnJob,
            SessionInfo sessionInfo) throws Exception {
        return new HttpResponse(workAttendanceService.getExporUrl(empId, yearMonth, settingId, isOnJob));
    }

    @RequestMapping(value = "export/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<Void> exportList(
            @ApiParam("员工ID") @RequestParam(value = "empId") String empId,
            @ApiParam("周期ID") @RequestParam(value = "settingId") String settingId,
            @ApiParam("时间（年-月）") @RequestParam(value = "yearMonth") Long yearMonth,
            @ApiParam("是否在职 0离职， 1在职") @RequestParam(value = "isOnJob") Integer isOnJob,
            SessionInfo sessionInfo, HttpServletResponse response) throws Exception {
        workAttendanceService.exportList(empId, yearMonth, settingId, isOnJob, sessionInfo, response);
        return new HttpResponse<>();
    }

}
