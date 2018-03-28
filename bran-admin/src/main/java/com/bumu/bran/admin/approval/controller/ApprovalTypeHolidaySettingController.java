package com.bumu.bran.admin.approval.controller;

import com.bumu.approval.command.ApprovalTypeSettingCommand;
import com.bumu.approval.result.ApprovalTypeSettingResult;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.approval.service.ApprovalTypeHolidaySettingService;
import com.bumu.bran.admin.approval.service.ApprovalTypeSettingService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.common.SessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 审批类型假期设置
 *
 * @author majun
 * @date 2017/10/14T
 */
@Api(tags = {"考勤管理-审批假期设置ApprovalHoliday"})
@Controller
@RequestMapping(value = "/admin/attendance/setting/holiday")
public class ApprovalTypeHolidaySettingController {

    @Autowired
    private ApprovalTypeSettingService approvalTypeSettingService;

    @Autowired
    private ApprovalTypeHolidaySettingService approvalTypeHolidaySettingService;

    @ApiOperation(value = "提交假期设置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.POST)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Void> commit(@ApiParam(value = "ids") @RequestBody Map<String, List<ApprovalTypeSettingCommand>> ids, SessionInfo sessionInfo) throws Exception {
        approvalTypeSettingService.commit(0, ids.get("ids"), sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "查询假期设置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.GET)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<List<ApprovalTypeSettingResult>> get(SessionInfo sessionInfo) throws Exception {

        return new HttpResponse<>(ErrorCode.CODE_OK, approvalTypeSettingService.get(0, sessionInfo));
    }

    @ApiOperation(value = "获取所有假期设置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "all", method = RequestMethod.GET)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<List<ApprovalTypeSettingResult>> all(SessionInfo sessionInfo) throws Exception {

        return new HttpResponse(ErrorCode.CODE_OK, approvalTypeHolidaySettingService.all());
    }

}
