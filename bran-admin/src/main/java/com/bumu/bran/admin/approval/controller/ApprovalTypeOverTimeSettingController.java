package com.bumu.bran.admin.approval.controller;

import com.bumu.approval.command.ApprovalTypeSettingCommand;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.approval.service.ApprovalTypeSettingService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
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
import java.util.List;
import java.util.Map;

/**
 * 出勤类型-加班设置
 *
 * @author majun
 * @date 2017/10/14
 */
@Api(tags = {"考勤管理-加班设置AttendanceSetting"})
@Controller
@RequestMapping(value = "/admin/attendance/setting/over_time")
public class ApprovalTypeOverTimeSettingController {

    @Autowired
    private ApprovalTypeSettingService approvalTypeSettingService;


    @ApiOperation(value = "提交加班设置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.POST)
    @ParamCheck
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Void> commit(
            @ApiParam(value = "ids") @Valid @RequestBody Map<String, List<ApprovalTypeSettingCommand>> ids,
            BindingResult bindingResult, SessionInfo sessionInfo) throws Exception {

        approvalTypeSettingService.commit(1, ids.get("ids"), sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

}
