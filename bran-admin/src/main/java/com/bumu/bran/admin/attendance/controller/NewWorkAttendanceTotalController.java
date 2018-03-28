package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.attendance.service.WorkAttendanceTotalService;
import com.bumu.bran.admin.work_shift_rule.service.BranScheduleService;
import com.bumu.bran.attendance.command.WorkAttendanceTotalPublishCommand;
import com.bumu.bran.attendance.command.WorkAttendanceTotalQueryCommand;
import com.bumu.bran.attendance.result.*;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author majun
 * @date 2017/3/22
 */
@Api(tags = {"考勤管理-新考勤汇总接口AttendanceSummary"})
@Controller
@RequestMapping(value = "/admin/attendance/summary")
public class NewWorkAttendanceTotalController {

    @Autowired
    private WorkAttendanceTotalService workAttendanceTotalService;

    @Autowired
    private BranScheduleService branScheduleService;

    @ApiOperation(httpMethod = "GET", value = "考勤配置（请假、加班分类）")
    @ApiResponse(code = 200, message = "成功", response = Pager.class)
    @RequestMapping(value = "get/approval/config", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<AttendanceTotalApprovalTypeResult> getApprovalType(SessionInfo sessionInfo) throws Exception{
        return new HttpResponse<>(workAttendanceTotalService.getApprovalType(sessionInfo));
    }


    @ApiOperation(httpMethod = "GET", value = "汇总查询")
    @ApiResponse(code = 200, message = "成功", response = Pager.class)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse<WorkAttendanceTotalResult> getTotalPageList(
            @Valid @ApiParam("考勤汇总查询请求参数") WorkAttendanceTotalQueryCommand command,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception{
        return new HttpResponse<>(workAttendanceTotalService.getTotalPageList(command,sessionInfo));
    }

    @ApiOperation(httpMethod = "POST", value = "发布")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "new/publish", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse publish(
            @Valid @ApiParam("考勤汇总发布请求参数") @RequestBody WorkAttendanceTotalPublishCommand command,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception{
        workAttendanceTotalService.publish(command,sessionInfo);
        return new HttpResponse<>();
    }

    @ApiOperation(httpMethod = "GET", value = "导出")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "new/export", method = RequestMethod.GET)
    @ResponseBody
    @ParamCheck
    public HttpResponse<WorkAttendanceTotalExportUrlResult> export(
            @Valid @ApiParam("考勤汇总导出请求参数") WorkAttendanceTotalQueryCommand command,
            BindingResult bindingResult)throws Exception{
        return new HttpResponse<>(workAttendanceTotalService.getExportTotalUrl(command));
    }

    @ApiOperation(httpMethod = "GET", value = "周期请假列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "leave/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<WorkAttendanceLeaveResult>> leaveList(
            @ApiParam("员工ID") @RequestParam(value = "empId") String empId,
            @ApiParam("配置ID ") @RequestParam(value = "settingId") String settingId,
            @ApiParam("考勤时间(年月)") @RequestParam(value = "yearMonth") Long yearMonth)throws Exception{
        return new HttpResponse<>(workAttendanceTotalService.leaveList(empId, settingId, yearMonth));
    }

    @ApiOperation(httpMethod = "GET", value = "周期迟到列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "late/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<WorkAttendanceLateResult>> lateList(
            @ApiParam("员工ID") @RequestParam(value = "empId") String empId,
            @ApiParam("配置ID ") @RequestParam(value = "settingId") String settingId,
            @ApiParam("考勤时间(年月)") @RequestParam(value = "yearMonth") Long yearMonth)throws Exception{
        return new HttpResponse<>(workAttendanceTotalService.lateList(empId, settingId, yearMonth));
    }

    @ApiOperation(httpMethod = "GET", value = "周期早退列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "no_full/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<WorkAttendanceNoFullResult>> noFullList(
            @ApiParam("员工ID") @RequestParam(value = "empId") String empId,
            @ApiParam("配置ID ") @RequestParam(value = "settingId") String settingId,
            @ApiParam("考勤时间(年月)") @RequestParam(value = "yearMonth") Long yearMonth)throws Exception{
        return new HttpResponse<>(workAttendanceTotalService.noFullList(empId, settingId, yearMonth));
    }

    @ApiOperation(httpMethod = "GET", value = "周期缺卡列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "lack/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<WorkAttendanceLackResult>> lackList(
            @ApiParam("员工ID") @RequestParam(value = "empId") String empId,
            @ApiParam("配置ID ") @RequestParam(value = "settingId") String settingId,
            @ApiParam("考勤时间(年月)") @RequestParam(value = "yearMonth") Long yearMonth)throws Exception{
        return new HttpResponse<>(workAttendanceTotalService.lackList(empId, settingId, yearMonth));
    }

    @ApiOperation(httpMethod = "GET", value = "周期旷工列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "absent/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<WorkAttendanceAbsentResult>> absentList(
            @ApiParam("员工ID") @RequestParam(value = "empId") String empId,
            @ApiParam("配置ID ") @RequestParam(value = "settingId") String settingId,
            @ApiParam("考勤时间(年月)") @RequestParam(value = "yearMonth") Long yearMonth)throws Exception{
        return new HttpResponse<>(workAttendanceTotalService.absentList(empId, settingId, yearMonth));
    }

    /**
     * @deprecated
     * @param queryDate
     * @param workShiftId
     * @param settingId
     * @param empId
     * @param isOnJob
     * @param departmentId
     * @param isPublish
     * @param sureState
     * @param sessionInfo
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "export/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<Void> exportList(Long queryDate,
										   String workShiftId,
                                           String settingId,
                                           String empId,
										   Integer isOnJob,
                                           String departmentId,
                                           Integer isPublish,
                                           Integer sureState,
                                           SessionInfo sessionInfo,
                                           HttpServletResponse response) throws Exception{
        workAttendanceTotalService.exportWorkAttendanceTotal(queryDate, workShiftId, settingId, empId, isOnJob, departmentId, isPublish, sureState, sessionInfo, response);
        return new HttpResponse<>();
    }
}
