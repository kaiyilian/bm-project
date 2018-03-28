package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.attendance.service.WorkAttendanceManagerService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.attendance.command.WorkAttendanceManagerCommand;
import com.bumu.bran.attendance.result.WorkAttendanceManagerResult;
import com.bumu.common.SessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author majun
 * @date 2017/3/22
 */
@Api(tags = {"考勤管理-考勤管理员接口AttendanceManager"})
@Controller
@RequestMapping(value = "/admin/attendance/setting/cycle/manager")
public class WorkAttendanceManagerController {

    @Autowired
    WorkAttendanceManagerService workAttendanceManagerService;

    @ApiOperation(value = "查询考勤反馈绑定的管理员")
    @ApiResponse(code = 200, message = "查询成功", response = WorkAttendanceManagerResult.class)
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<WorkAttendanceManagerResult> get() throws Exception {
        return new HttpResponse<>(workAttendanceManagerService.getManagerInfo());
    }

    @ApiOperation(value = "绑定考勤反馈管理员", notes = "如果没有就创建,有就更新")
    @ApiResponse(code = 200, message = "成功")
    @RequestMapping(value = "bind", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttendanceManagerResult> bind(
            @ApiParam("绑定考勤反馈管理员请求参数")@RequestBody WorkAttendanceManagerCommand command,
            SessionInfo sessionInfo,
            BindingResult bindingResult) throws Exception {
        return new HttpResponse<>(workAttendanceManagerService.bindManager(command, sessionInfo));
    }
}
