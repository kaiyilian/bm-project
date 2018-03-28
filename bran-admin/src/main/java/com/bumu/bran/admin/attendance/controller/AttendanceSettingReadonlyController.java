package com.bumu.bran.admin.attendance.controller;

import com.bumu.approval.result.ApprovalTypeSettingResult;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.approval.service.ApprovalTypeOverTimeSettingService;
import com.bumu.bran.admin.approval.service.ApprovalTypeSettingService;
import com.bumu.bran.admin.attendance.result.WorkAttendanceDeviceResult;
import com.bumu.bran.admin.attendance.service.WorkAttendanceSettingService;
import com.bumu.bran.admin.attendance.service.WorkAttendanceTotalService;
import com.bumu.bran.admin.corporation.result.WorkShiftListResult;
import com.bumu.bran.attendance.command.setting.WorkAttendanceDeviceWorkShiftCommand;
import com.bumu.bran.attendance.command.setting.WorkAttendanceGetSettingCommand;
import com.bumu.bran.attendance.result.WorkAttendanceSettingPageResult;
import com.bumu.bran.attendance.result.WorkAttendanceSettingResult;
import com.bumu.bran.attendance.result.WorkAttendanceTotalGetSettingResult;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.ModelResult;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤管理-设置AttendanceSetting
 *
 * @author majun
 * @date 2017/10/14
 */
@Api(tags = {"考勤管理-设置AttendanceSettingReadonly"})
@Controller
@RequestMapping()
public class AttendanceSettingReadonlyController {

    @Autowired
    private ApprovalTypeSettingService approvalTypeSettingService;

    @Autowired
    private ApprovalTypeOverTimeSettingService approvalTypeOverTimeSettingService;

    @Autowired
    private WorkAttendanceSettingService workAttendanceSettingService;

    @Autowired
    private WorkAttendanceTotalService workAttendanceTotalService;

    @ApiOperation(value = "查询加班设置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/admin/no_permission/attendance/setting/over_time/get", method = RequestMethod.GET)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<List<ApprovalTypeSettingResult>> get(SessionInfo sessionInfo) throws Exception {

        return new HttpResponse<>(ErrorCode.CODE_OK, approvalTypeSettingService.get(1, sessionInfo));
    }

    @ApiOperation(value = "获取所有加班设置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/admin/no_permission/attendance/setting/over_time/all", method = RequestMethod.GET)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<List<ApprovalTypeSettingResult>> all(SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(ErrorCode.CODE_OK, approvalTypeOverTimeSettingService.all());
    }

    @ApiOperation(httpMethod = "GET", value = "配置查询")
    @ApiResponse(code = 200, message = "成功", response = Pager.class)
    @RequestMapping(value = {
            "admin/attendance/summary/get/config",
            "admin/attendance/detail/get/config"},
            method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttendanceTotalGetSettingResult> getConfig(SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(workAttendanceTotalService.getSettingList(sessionInfo));
    }

    @ApiOperation(httpMethod = "GET", value = "查询出勤配置")
    @ApiResponse(code = 200, message = "成功", response = WorkAttendanceSettingResult.class)
    @RequestMapping(value = "/admin/attendance/setting/cycle/get/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttendanceSettingPageResult> getAvailableWorkShiftList(
            @ApiParam(value = "查询出勤配置请求参数") WorkAttendanceGetSettingCommand command,
            SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(workAttendanceSettingService.getCorpAllSetting(command.getWorkShiftId(),sessionInfo,command.getPage(), command.getPage_size()));
    }


    @ApiOperation(httpMethod = "GET", value = "出勤配置明细")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/admin/attendance/setting/cycle/get/detail", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttendanceSettingResult> getAttendanceSettingDetail(
            @ApiParam(value = "出勤配置id") @RequestParam("work_attendance_setting_id") String workAttendanceSettingId,
            SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(workAttendanceSettingService.getDetail(workAttendanceSettingId,sessionInfo));
    }

    @ApiOperation(httpMethod = "GET", value = "查询可用班组")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/admin/attendance/setting/cycle/get/available/work_shift/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<List<ModelResult>> getAvailableWorkShift(SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(workAttendanceSettingService.getAvailableWorkShiftList(sessionInfo));
    }

    @ApiOperation(httpMethod = "GET", value = "获取所有的考勤机")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/admin/attendance/setting/device/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<List<WorkAttendanceDeviceResult>> deviceList(SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(workAttendanceSettingService.deviceList(sessionInfo));
    }

    @ApiOperation(httpMethod = "GET", value = "获取可用的班组")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/admin/attendance/setting/device/work_shift/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkShiftListResult> deviceWorkShiftList(SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(workAttendanceSettingService.getDeviceWorkShifts(sessionInfo));
    }

    @ApiOperation(httpMethod = "POST", value = "保存考勤机班组")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/admin/attendance/setting/device/workShift/save", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<Void> deviceWorkShiftSave(@RequestBody WorkAttendanceDeviceWorkShiftCommand command, SessionInfo sessionInfo) throws Exception {
        workAttendanceSettingService.deviceWorkShiftSave(command, sessionInfo);
        return new HttpResponse(ErrorCode.CODE_OK);
    }
}