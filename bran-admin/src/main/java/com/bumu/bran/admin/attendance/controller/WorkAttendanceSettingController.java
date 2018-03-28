package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.attendance.service.WorkAttendanceSettingService;
import com.bumu.bran.attendance.command.setting.WorkAttendanceAddSettingCommand;
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

import javax.validation.Valid;

/**
 * @author majun
 * @date 2017/3/22
 */
@Api(tags = {"考勤管理-考勤配置AttendanceSetting"})
@Controller
@RequestMapping(value = "/admin/attendance/setting/cycle/")
public class WorkAttendanceSettingController {

    private Logger logger = LoggerFactory.getLogger(WorkAttendanceSettingController.class);

    @Autowired
    private WorkAttendanceSettingService workAttendanceSettingService;


//    @ApiOperation(httpMethod = "GET", value = "根据班组查询出勤配置信息")
//    @ApiResponse(code = 200, message = "成功", response = List.class)
//    @RequestMapping(value = "get/specified/list", method = RequestMethod.GET)
//    @ResponseBody
//    @GetSessionInfo
//    public HttpResponse<WorkAttendanceSettingResult> getList(
//            @ApiParam(value = "考勤查询的请求对象") @RequestParam("work_shift_id") String workShiftId,
//            SessionInfo sessionInfo) throws Exception {
//        return new HttpResponse<>(workAttendanceSettingService.getSetting(workShiftId, sessionInfo));
//    }

    @ApiOperation(httpMethod = "POST", value = "出勤配置保存")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse<Object> save(
            @Valid @ApiParam(value = "出勤配置保存请求参数") @RequestBody WorkAttendanceAddSettingCommand command,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception {
        workAttendanceSettingService.saveSetting(command, sessionInfo);
        return new HttpResponse<>();
    }


    @ApiOperation(httpMethod = "GET", value = "删除出勤配置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<Object> delete(
            @ApiParam("出勤配置Id") @RequestParam("work_attendance_setting_id") String workAttendanceSettingId,
            SessionInfo sessionInfo) throws Exception {

        workAttendanceSettingService.deleteSetting(workAttendanceSettingId, sessionInfo);
        return new HttpResponse<>();
    }

}
