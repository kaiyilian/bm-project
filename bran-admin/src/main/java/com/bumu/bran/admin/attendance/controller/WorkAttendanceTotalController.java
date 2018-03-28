package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.attendance.service.WorkAttendanceTotalService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.attendance.command.WorkAttendanceTotalQueryCommand;
import com.bumu.bran.attendance.result.WorkAttendanceTotalExportUrlResult;
import com.bumu.bran.attendance.result.WorkAttendanceTotalGetSettingResult;
import com.bumu.bran.attendance.result.WorkAttendanceTotalResult;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author majun
 * @date 2017/3/22
 * @deprecated
 */
@Api(value = "WorkAttendanceTotalController", tags = {"考勤汇总接口AttendanceTotal"})
@Controller
@RequestMapping(value = "/admin/work_attendance/total")
@Deprecated
public class WorkAttendanceTotalController {

    @Autowired
    private WorkAttendanceTotalService workAttendanceTotalService;

    @ApiOperation(httpMethod = "GET", value = "配置查询")
    @ApiResponse(code = 200, message = "成功", response = Pager.class)
    @RequestMapping(value = "get/setting", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttendanceTotalGetSettingResult> getSettingList(SessionInfo sessionInfo) throws Exception{
        return new HttpResponse(workAttendanceTotalService.getSettingList(sessionInfo));
    }

    @ApiOperation(httpMethod = "GET", value = "汇总查询")
    @ApiResponse(code = 200, message = "成功", response = Pager.class)
    @RequestMapping(value = "get/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse<WorkAttendanceTotalResult> getTotalPageList(
            @Valid @ApiParam("考勤汇总查询请求参数") WorkAttendanceTotalQueryCommand command,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception{
        return new HttpResponse(workAttendanceTotalService.getTotalPageList(command,sessionInfo));
    }

   /* @ApiOperation(httpMethod = "POST", value = "发布")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "publish", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse publish(
            @Valid @ApiParam("考勤汇总发布请求参数") @RequestBody WorkAttendanceTotalQueryCommand command,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception{
        workAttendanceTotalService.publish(command, sessionInfo);
        return new HttpResponse();
    }*/

    @ApiOperation(httpMethod = "GET", value = "汇总导出")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "export", method = RequestMethod.GET)
    @ResponseBody
    @ParamCheck
    public HttpResponse<WorkAttendanceTotalExportUrlResult> export(
            @Valid @ApiParam("考勤汇总导出请求参数") WorkAttendanceTotalQueryCommand command,
            BindingResult bindingResult)throws Exception{
        return new HttpResponse<>(workAttendanceTotalService.getExportTotalUrl(command));
    }


    /*@RequestMapping(value = "export/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<Object> exportList(Long queryDate,
										   String workShiftId,
                                           String settingId,
                                           String empId,
										   Integer isOnJob,
                                           SessionInfo sessionInfo,
                                           HttpServletResponse response) throws Exception{
        workAttendanceTotalService.exportWorkAttendanceTotal(queryDate,workShiftId,settingId,empId,isOnJob,sessionInfo,response);
        return new HttpResponse();
    }*/
}
