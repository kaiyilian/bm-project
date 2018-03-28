package com.bumu.bran.admin.work_shift_rule.controller;

import com.bumu.arya.command.MybatisPagerCommand;
import com.bumu.arya.command.PagerCommand;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.work_shift_rule.controller.command.BranOneDayWorkShiftRuleCommand;
import com.bumu.bran.admin.work_shift_rule.result.AvailableWorkShiftEmpResult;
import com.bumu.bran.admin.work_shift_rule.result.AvailableWorkShiftResult;
import com.bumu.bran.admin.work_shift_rule.service.BranNewScheduleService;
import com.bumu.bran.admin.work_shift_rule.service.BranScheduleService;
import com.bumu.bran.attendance.result.ScheduleDetailsResult;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.common.annotation.SetParams;
import com.bumu.bran.workshift.command.WorkShiftRuleCommand;
import com.bumu.bran.workshift.command.WorkShiftRuleQueryCommand;
import com.bumu.bran.workshift.result.ScheduleViewResult;
import com.bumu.bran.workshift.result.WorkShiftRuleDetailResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.result.BaseResult;
import com.bumu.common.result.ModelResult;
import com.bumu.common.result.Pager;
import com.bumu.exception.Assert;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Controller
@Api(tags = {"考勤管理-排班视图ScheduleView"})
public class ScheduleViewController {

    private Logger logger = LoggerFactory.getLogger(ScheduleViewController.class);

    @Autowired
    private BranScheduleService branScheduleService;

    @Autowired
    private BranNewScheduleService branNewScheduleService;

    /**
     * 获取所有的颜色
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {
            "/admin/attendance/schedule/workShiftType/colors",
            "/admin/attendance/attendance/schedule/rule/colors"}, method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<Void> getScheduleColors() throws Exception {
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branScheduleService.getScheduleColors());
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }


    @RequestMapping(value = "/admin/attendance/schedule/view/one/day", method = RequestMethod.PUT)
    @ResponseBody
    @ParamCheck
    @GetSessionInfo
    @ApiOperation(value = "个人排班按天修改")
    @ApiResponse(code = 200, message = "成功", response = BaseResult.IDNameResult.class)
    public HttpResponse<Pager<ScheduleViewResult>> setOneDayRule(
            @ApiParam(name = "最新的排班规律id name") @RequestBody @Valid BranOneDayWorkShiftRuleCommand branOneDayWorkShiftRuleCommand,
            BindingResult bindingResult, SessionInfo sessionInfo) throws Exception {
        Assert.notBlank(branOneDayWorkShiftRuleCommand.getWorkShiftTypeId(), "班次必填");
        String ruleId = branScheduleService.setOneDayRule(branOneDayWorkShiftRuleCommand, sessionInfo);
        HttpResponse<Pager<ScheduleViewResult>> httpResponse = new HttpResponse(ErrorCode.CODE_OK, getRules(
                branOneDayWorkShiftRuleCommand.getModifyDate(),
                null,
                null,
                null,
                null,
                null,
                branOneDayWorkShiftRuleCommand.getPage(),
                10
                , sessionInfo
        ));
        logger.info("个人排班修改: httpResponse: " + httpResponse.toJson());
        return httpResponse;
    }

    @RequestMapping(value = "/admin/attendance/schedule/view/revert", method = RequestMethod.DELETE)
    @ResponseBody
    @ParamCheck
    @GetSessionInfo
    @ApiOperation(value = "恢复初始排班")
    @ApiResponse(code = 200, message = "成功", response = ScheduleDetailsResult.class)
    public HttpResponse<Pager<ScheduleViewResult>> setRuleRevert(
            @ApiParam @RequestBody @Valid BranOneDayWorkShiftRuleCommand branOneDayWorkShiftRuleCommand,
            BindingResult bindingResult, SessionInfo sessionInfo) throws Exception {
        String ruleId = branScheduleService.setRuleRevert(branOneDayWorkShiftRuleCommand, sessionInfo);

        HttpResponse<Pager<ScheduleViewResult>> httpResponse = new HttpResponse(ErrorCode.CODE_OK, getRules(
                branOneDayWorkShiftRuleCommand.getModifyDate(),
                null,
                null,
                null,
                null,
                null,
                branOneDayWorkShiftRuleCommand.getPage(),
                10
                , sessionInfo
        ));

        logger.info("个人排班恢复初始化: " + httpResponse.toJson());
        return httpResponse;
    }

    //    admin/schedule/rule/views
    @RequestMapping(value = {"/admin/attendance/schedule/rule/views", "/admin/attendance/schedule/view/list"}, method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取排班视图")
    @ApiResponse(code = 200, message = "成功")
    @GetSessionInfo
    public HttpResponse<Pager<ScheduleViewResult>> getRules(
            @ApiParam(value = "查询月份 时间戳, 没有不要传,不能传入0") @RequestParam(value = "queryDate", required = false) Long queryDate,
            @ApiParam(value = "排班id") @RequestParam(value = "workShiftRuleId", required = false) String workShiftRuleId,
            @ApiParam(value = "部门id") @RequestParam(value = "department_id", required = false) String departmentId,
            @ApiParam(value = "班组id") @RequestParam(value = "work_shift_id", required = false) String workShiftId,
            @ApiParam(value = "发布状态 0:未发布 1:发布") @RequestParam(value = "publishedState", required = false) Integer publishedState,
            @ApiParam(value = "快速搜索 员工姓名/工号/工段 这里只要传员工id就行") @RequestParam(value = "empId", required = false) String empId,
            @ApiParam @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize,
            SessionInfo sessionInfo) throws Exception {

        logger.info("设置查询参数");
        WorkShiftRuleQueryCommand workShiftRuleQueryCommand = new WorkShiftRuleQueryCommand();
        workShiftRuleQueryCommand.setEmpId(empId);
        workShiftRuleQueryCommand.setQueryDate(queryDate);
        workShiftRuleQueryCommand.setDepartmentId(departmentId);
        workShiftRuleQueryCommand.setPublishedState(publishedState);
        workShiftRuleQueryCommand.setWorkShiftId(workShiftId);
        workShiftRuleQueryCommand.setWorkShiftRuleId(workShiftRuleId);
        workShiftRuleQueryCommand.setBranCorpId(sessionInfo.getCorpId());

        PagerCommand pagerCommand = new MybatisPagerCommand(page, pageSize);
        logger.info("查询参数为: " + workShiftRuleQueryCommand.toString());
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branScheduleService.getRules(workShiftRuleQueryCommand, pagerCommand, sessionInfo));
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }

    @RequestMapping(value = "/admin/attendance/schedule/rule/query/names", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取排班名字(作为排班查询条件)")
    @ApiResponse(code = 200, message = "成功", response = List.class)
    @GetSessionInfo
    public HttpResponse<List<ModelResult>> getRuleNames(SessionInfo sessionInfo) throws Exception {
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branScheduleService.getRuleNames(sessionInfo));
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }


    /**
     * 发布规则
     *
     * @param workShiftRuleCommand
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin/attendance/schedule/view/publish", method = RequestMethod.GET)
    @ResponseBody
    @SetParams
    public HttpResponse publishRules(WorkShiftRuleCommand workShiftRuleCommand, BindingResult bindingResult) throws Exception {
        branScheduleService.publishRules(workShiftRuleCommand);
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, "发布成功");
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }

    /**
     * 导出排班视图
     *
     * @param workShiftRuleCommand
     * @param bindingResult
     * @param httpServletResponse
     * @return
     * @throws Exception
     * @deprecated
     */
    @RequestMapping(value = "/admin/attendance/schedule/rule/export", method = RequestMethod.GET)
    @ResponseBody
    @SetParams
    public HttpResponse<Void> export(WorkShiftRuleQueryCommand workShiftRuleCommand, BindingResult bindingResult,
                                     HttpServletResponse httpServletResponse) throws Exception {
        branScheduleService.export(workShiftRuleCommand, httpServletResponse);
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK);
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }


    @RequestMapping(value = "/admin/attendance/schedule/rule/details", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    @ApiOperation(value = "获取排班规律详情")
    @ApiResponse(code = 200, message = "成功", response = WorkShiftRuleDetailResult.class)
    public HttpResponse<WorkShiftRuleDetailResult> getRuleDetails(@ApiParam("排班ID") @RequestParam(value = "id", required = false) String id,
                                                                  SessionInfo sessionInfo) throws Exception {
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branScheduleService.getRuleDetails(id, sessionInfo));
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }

    /**
     * 获取排班规律列表
     *
     * @return
     */
    @RequestMapping(value = "/admin/attendance/schedule/rule/list", method = RequestMethod.GET)
    @ResponseBody
    @SetParams
    public HttpResponse<Void> getRuleList(WorkShiftRuleCommand workShiftRuleCommand, BindingResult bindingResult) throws Exception {
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branScheduleService.getRuleList(workShiftRuleCommand));
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }

    /**
     * 获取可用的班组列表
     *
     * @return
     */
    @RequestMapping(value = "/admin/attendance/schedule/rule/available/workShift/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    @ApiOperation(value = "获取可用的班组列表")
    @ApiResponse(code = 200, message = "成功")
    public HttpResponse<List<AvailableWorkShiftResult>> getAvailableWorkShifts(
            @ApiParam(value = "排班ID") @RequestParam(value = "workShiftRuleId", required = false) String workShiftRuleId,
            @ApiParam(value = "排班开始时间") @RequestParam(value = "executeTime") Long executeTime,
            @ApiParam(value = "排班结束时间") @RequestParam(value = "executeEndsTime", required = false) Long executeEndTime,
            @ApiParam(value = "是否一直循环") @RequestParam(value = "isLoopAround", required = false) Boolean isLoopAround, SessionInfo sessionInfo) throws Exception {
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branScheduleService.getAvailableWorkShifts(workShiftRuleId, executeTime, executeEndTime, isLoopAround, sessionInfo));
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }

    @RequestMapping(value = "/admin/attendance/schedule/rule/available/workShift/emp/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    @ApiOperation(value = "获取班组员工列表")
    @ApiResponse(code = 200, message = "成功")
    public HttpResponse<List<AvailableWorkShiftEmpResult>> getAvailableWorkShiftEmps(
            @ApiParam(value = "班组ID") @RequestParam(value = "workShiftId") String workShiftId, SessionInfo sessionInfo) throws Exception {
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branScheduleService.getAvailableWorkShiftEmpList(sessionInfo, workShiftId));
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }


}
