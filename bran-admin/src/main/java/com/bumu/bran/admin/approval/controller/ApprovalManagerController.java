package com.bumu.bran.admin.approval.controller;

import com.bumu.approval.command.ApprovalManagerQueryCommand;
import com.bumu.arya.command.PagerCommand;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.approval.result.ApprovalManagerResult;
import com.bumu.bran.admin.approval.service.ApprovalService;
import com.bumu.bran.admin.approval.service.ApprovalTypeSettingService;
import com.bumu.bran.admin.system.result.KeyValueResult;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BatchCommand;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 审批管理
 *
 * @author majun
 * @date 2017/10/16
 */
@Api(tags = {"考勤管理-审批管理AttendanceApproval"})
@Controller
@RequestMapping(value = "/admin/attendance/approval/")
public class ApprovalManagerController {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ApprovalTypeSettingService approvalTypeSettingService;

    @ApiOperation(value = "查询")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Pager<ApprovalManagerResult>> get(
            @ApiParam(value = "查询开始时间") @RequestParam(value = "query_startTime", required = false) Long queryStartTime,
            @ApiParam(value = "查询结束时间") @RequestParam(value = "query_endTime", required = false) Long queryEndTime,
            @ApiParam(value = "正式员工id") @RequestParam(value = "empId", required = false) String empId,
            @ApiParam(value = "审批类型 0:请假 1:加班 2:补卡") @RequestParam(value = "approval_type", required = false) Integer approvalType,
            @ApiParam(value = "审批状态 0:待处理 1:未通过 2:通过 3:撤销") @RequestParam(value = "approval_state", required = false) Integer approvalState,
            @ApiParam @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam @RequestParam(value = "page_size", defaultValue = "10") int pageSize,
            SessionInfo sessionInfo) throws Exception {

        // 设置查询参数
        ApprovalManagerQueryCommand approvalManagerQueryCommand = new ApprovalManagerQueryCommand();
        approvalManagerQueryCommand.setApprovalState(approvalState);
        approvalManagerQueryCommand.setApprovalType(approvalType);
        approvalManagerQueryCommand.setEmpId(empId);
        approvalManagerQueryCommand.setQueryStartTime(queryStartTime);
        approvalManagerQueryCommand.setQueryEndTime(queryEndTime);
        approvalManagerQueryCommand.setBranCorpId(sessionInfo.getCorpId());

        // 设置分页参数
        PagerCommand pagerCommand = new PagerCommand(page, pageSize);


        return new HttpResponse<>(ErrorCode.CODE_OK, approvalService.get(approvalManagerQueryCommand, pagerCommand));
    }

    @ApiOperation(value = "详情")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<ApprovalManagerResult> detail(
            @ApiParam(value = "审批id 必填") @RequestParam(value = "approval_id", required = false) String approvalId,
            SessionInfo sessionInfo) throws Exception {

        return new HttpResponse<>(ErrorCode.CODE_OK, approvalService.detail(approvalId, sessionInfo));
    }

    @ApiOperation(value = "通过")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "batch/pass", method = RequestMethod.PUT)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Void> pass(@RequestBody BatchCommand batch,
                                   SessionInfo sessionInfo) throws Exception {

        approvalService.pass(batch, sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "不通过")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "batch/fail", method = RequestMethod.PUT)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Void> fail(@RequestBody BatchCommand batch,
                                   SessionInfo sessionInfo) throws Exception {

        approvalService.fail(batch, sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "导出")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "export", method = RequestMethod.GET)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Void> export(
            @ApiParam(value = "查询开始时间") @RequestParam(value = "query_startTime", required = false) Long queryStartTime,
            @ApiParam(value = "查询结束时间") @RequestParam(value = "query_endTime", required = false) Long queryEndTime,
            @ApiParam(value = "正式员工id") @RequestParam(value = "emp_id", required = false) String empId,
            @ApiParam(value = "审批类型 0:请假 1:加班 2:补卡") @RequestParam(value = "approval_type", required = false) Integer approvalType,
            @ApiParam(value = "审批状态 0:待处理 1:未通过 2:通过 3:撤销") @RequestParam(value = "approval_state", required = false) Integer approvalState,
            SessionInfo sessionInfo,
            HttpServletResponse httpServletResponse) throws Exception {

        // 设置查询参数
        ApprovalManagerQueryCommand approvalManagerQueryCommand = new ApprovalManagerQueryCommand();
        approvalManagerQueryCommand.setApprovalState(approvalState);
        approvalManagerQueryCommand.setApprovalType(approvalType);
        approvalManagerQueryCommand.setEmpId(empId);
        approvalManagerQueryCommand.setQueryStartTime(queryStartTime);
        approvalManagerQueryCommand.setQueryEndTime(queryEndTime);
        approvalManagerQueryCommand.setBranCorpId(sessionInfo.getCorpId());

        approvalService.export(approvalManagerQueryCommand, sessionInfo, httpServletResponse);

        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "获取类型设置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "type", method = RequestMethod.GET)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<List<KeyValueResult>> allType(SessionInfo sessionInfo) throws Exception {

        return new HttpResponse<>(ErrorCode.CODE_OK, approvalTypeSettingService.allType());
    }

    @ApiOperation(value = "获取详情设置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "detail/type", method = RequestMethod.GET)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<List<KeyValueResult>> allDetailType(SessionInfo sessionInfo) throws Exception {

        return new HttpResponse<>(ErrorCode.CODE_OK, approvalTypeSettingService.allDetailType());
    }
}
