package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.attendance.service.WorkAttendanceFeedBackService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.attendance.command.WorkAttendanceFeeBackReconfirmCommand;
import com.bumu.bran.attendance.command.WorkAttendanceFeedBackQueryCommand;
import com.bumu.bran.attendance.result.WorkAttendanceFeedBackResult;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


/**
 * @author majun
 * @date 2017/3/22
 */
@Deprecated
@Api(value = "WorkAttendanceFeedBackController", tags = {"考勤反馈AttendanceFeedBack"})
@Controller
@RequestMapping(value = "/admin/work_attendance/feedback")
public class WorkAttendanceFeedBackController {

    @Autowired
    private WorkAttendanceFeedBackService workAttendanceFeedBackService;

    @ApiOperation(httpMethod = "GET", value = "查询反馈列表")
    @ApiResponse(code = 200, message = "成功", response = Pager.class)
    @RequestMapping(value = "get/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse<WorkAttendanceFeedBackResult> getFeeBackList(
            @Valid @ApiParam("考勤反馈查询请求参数") WorkAttendanceFeedBackQueryCommand command,
            BindingResult bindingResult,
            SessionInfo sessionInfo
           ) throws Exception {
        return new HttpResponse(workAttendanceFeedBackService.getFeeBackList(sessionInfo, command));
    }

    @ApiOperation(httpMethod = "POST", value = "考勤反馈重新确认")
    @RequestMapping(value = "reconfirm", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
   public HttpResponse<Object> reconfirmFeedBack(@ApiParam("考勤反馈id")@RequestBody WorkAttendanceFeeBackReconfirmCommand command,
                                                 SessionInfo sessionInfo) throws Exception {
        workAttendanceFeedBackService.reconfirmFeedBack(command.getFeedbackId(),sessionInfo);
        return new HttpResponse();
    }

}
