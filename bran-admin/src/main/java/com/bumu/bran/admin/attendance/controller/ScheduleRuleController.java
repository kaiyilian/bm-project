package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.work_shift_rule.controller.command.BranWorkShiftRuleCommand;
import com.bumu.bran.admin.work_shift_rule.service.BranNewScheduleService;
import com.bumu.bran.admin.work_shift_rule.service.BranScheduleService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.common.annotation.SetParams;
import com.bumu.bran.workshift.command.NewWorkShiftRuleCommand;
import com.bumu.bran.workshift.command.WorkShiftRuleCommand;
import com.bumu.bran.workshift.result.WorkShiftRuleDetailResult;
import com.bumu.common.SessionInfo;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;

/**
 * @author Allen 2018-01-10
 **/
@Controller
@Api(tags = {"考勤管理-排班规律ScheduleRule"})
public class ScheduleRuleController {
    private Logger logger = LoggerFactory.getLogger(ScheduleRuleController.class);

    @Autowired
    private BranScheduleService branScheduleService;

    @Autowired
    private BranNewScheduleService branNewScheduleService;

    /**
     * 规则设置
     *
     * @param workShiftRuleCommand
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin/attendance/schedule/rule/set", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    @ApiOperation(value = "规则设置")
    @ApiResponse(code = 200, message = "成功", response = Void.class)
    public HttpResponse<Void> setRules(
            @ApiParam @RequestBody @Valid BranWorkShiftRuleCommand workShiftRuleCommand,
            BindingResult bindingResult, SessionInfo sessionInfo) throws Exception {
        logger.debug("params: " + workShiftRuleCommand.toString());
        branScheduleService.setRules(workShiftRuleCommand, sessionInfo);
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK);
        httpResponse.setMsg("排班规则设置完毕, 赶快去发布吧");
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }

    /**
     * 获取排版规律
     *
     * @return
     */
    @RequestMapping(value = "/admin/attendance/schedule/rule/query/list", method = RequestMethod.GET)
    @ResponseBody
    @SetParams
    @ApiOperation(value = "获取排版规律")
    @ApiResponse(code = 200, message = "成功")
    public HttpResponse<List<WorkShiftRuleDetailResult>> getQueryWorkShifts(
            @ApiParam(value = "公司的id,必填")
                    WorkShiftRuleCommand workShiftRuleCommand,
            BindingResult bindingResult) throws Exception {
        String corpId = workShiftRuleCommand.getBranCorpId();
        if (StringUtils.isBlank(corpId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "查询异常，缺少必要的参数");
        }
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branNewScheduleService.getNewRuleDetails(corpId));
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }

    /**
     * 获取排版规律对应的全部班次信息
     *
     * @return
     */
    @RequestMapping(value = "/admin/attendance/schedule/rule/query/list/type", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取排版规律对应全部班次信息")
    @ApiResponse(code = 200, message = "成功")
    public HttpResponse<List<WorkShiftRuleDetailResult>> getQueryWorkShiftType(
            @ApiParam(value = "上传排版规律的id  ")
            @RequestBody NewWorkShiftRuleCommand newWorkShiftRuleCommand) throws Exception {
        if (StringUtils.isBlank(newWorkShiftRuleCommand.getRuleId())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "查询异常，缺少必要的参数");
        }
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branNewScheduleService.getNewRuleType(newWorkShiftRuleCommand));
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }

    /**
     * 启用排版规律
     *
     * @return
     */
    @RequestMapping(value = "/admin/attendance/schedule/rule/set/use", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "设置是否启用排版规律")
    @ApiResponse(code = 200, message = "成功")
    public HttpResponse setScheduleRule(
            @ApiParam(value = "排版规律的id 和 是否启用的状态  ")
            @RequestBody NewWorkShiftRuleCommand newWorkShiftRuleCommand) throws Exception {

        if (StringUtils.isBlank(newWorkShiftRuleCommand.getRuleId())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "排班规律id必填");
        }
        Boolean aBoolean = branNewScheduleService.setUseRule(newWorkShiftRuleCommand.getRuleId(), newWorkShiftRuleCommand.getIsUser());
        if (aBoolean) {
            return new HttpResponse<>(ErrorCode.CODE_OK);
        }
        else {
            throw new AryaServiceException(ErrorCode.CODE_CODE__WORK_SHIFT_RULE_FAILED);
        }

    }

    /**
     * 删除排版规律
     *
     * @return
     */
    @RequestMapping(value = "/admin/attendance/schedule/rule/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除排版规律")
    @ApiResponse(code = 200, message = "成功")
    public HttpResponse<Void> deleteScheduleRule(
            @ApiParam(value = "上传排版规律的id  ")
            @RequestBody NewWorkShiftRuleCommand newWorkShiftRuleCommand) throws Exception {

        if (StringUtils.isBlank(newWorkShiftRuleCommand.getRuleId())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "排班规律id必填");
        }
        Boolean aBoolean = branNewScheduleService.closeRule(newWorkShiftRuleCommand.getRuleId());
        if (aBoolean) {
            return new HttpResponse<>(ErrorCode.CODE_OK);
        }
        else {
            throw new AryaServiceException(ErrorCode.CODE_CODE__WORK_SHIFT_RULE_FAILED);
        }

    }
}
