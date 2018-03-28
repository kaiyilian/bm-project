package com.bumu.bran.admin.prospective.controller;

import com.bumu.SysUtils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.corporation.result.CreateProspectiveEmployeeResult;
import com.bumu.bran.admin.employee.result.EmployeeDetailResult;
import com.bumu.bran.admin.employee.result.EmployeeListPaginationResult;
import com.bumu.bran.admin.employee.result.ImportProspectiveResult;
import com.bumu.bran.admin.employee.result.UpdateProspectiveEmployeeResult;
import com.bumu.bran.admin.employee.service.EmployeeService;
import com.bumu.bran.admin.prospective.controller.command.AcceptProspectiveEmployeeCommand;
import com.bumu.bran.admin.prospective.controller.command.DeleteEmployeeCommand;
import com.bumu.bran.admin.prospective.result.ProspectiveCheckResult;
import com.bumu.bran.admin.prospective.service.ProspectiveService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.employee.command.EmployeePagedListCommand;
import com.bumu.bran.validated.AcceptProspectiveValidatedGroup;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.common.validator.annotation.AddValidatedGroup;
import com.bumu.common.validator.annotation.UpdateValidatedGroup;
import com.bumu.exception.Assert;
import com.bumu.exception.ExceptionModel;
import com.bumu.prospective.command.SaveProspectiveEmployeeCommand;
import com.bumu.prospective.validated.TypeValidationGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 重构
 * 待入职模块Controller
 *
 * @author majun
 * @Date 2016/9/18
 */
@Api(tags = "员工管理-入职Prospective")
@Controller
public class ProspectiveController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProspectiveService service;

    /**
     * 添加待入职员工
     *
     * @param command
     * @param bindingResult
     * @return
     * @Validated 验证参数
     * AddValidatedGroup 分组验证 职位、班组、工段、部门、手机号、入职时间
     * TypeValidationGroup 分组验证 待入职员工是否已经存在、花名册是否已经存在
     */
    @ApiOperation(value = "添加待入职员工")
    @RequestMapping(value = "/admin/employee/prospective/add/save", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<CreateProspectiveEmployeeResult> saveProspectiveEmployee(
            @RequestBody @Validated({AddValidatedGroup.class, TypeValidationGroup.class}) SaveProspectiveEmployeeCommand command,
            BindingResult bindingResult) throws Exception {
        logger.debug("start saveProspectiveEmployee ... ");
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        command.setUserId(currentUserId);
        command.setBranCorpId(branCorpId);
        return new HttpResponse<>(ErrorCode.CODE_OK, employeeService.addProspectiveEmployee(command, bindingResult));
    }

    /**
     * 修改待入职员工
     *
     * @param command
     * @return
     */
    @ApiOperation(value = "修改待入职员工")
    @RequestMapping(value = "/admin/employee/prospective/add/update", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<UpdateProspectiveEmployeeResult> updateProspectiveEmployee(
            @RequestBody @Validated(value = UpdateValidatedGroup.class) SaveProspectiveEmployeeCommand command, BindingResult bindingResult) throws Exception {
        logger.debug("start updateProspectiveEmployee ... ");
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse<>(ErrorCode.CODE_OK, employeeService.updateProspectiveEmployee(command, branCorpId,
                currentUserId));
    }

    /**
     * 删除待入职员工
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/prospective/add/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse deleteProspectiveEmployee(
            @RequestBody DeleteEmployeeCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        employeeService.deleteProspectiveEmployees(command.getEmployeeIds(), currentUserId, branCorpId);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    /**
     * 查询待入职员工名单
     *
     * @param positionId
     * @param workShiftId
     * @param workLineId
     * @param departmentId
     * @param checkinStartTime
     * @param checkinEndTime
     * @param keyword
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询待入职员工名单")
    @RequestMapping(value = {"/admin/employee/prospective/manage/list", "/admin/employee/prospective/index/manage/list"}, method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<EmployeeListPaginationResult> getProspectiveEmployeeList(
            @RequestParam(value = "position_id", required = false) String positionId,
            @RequestParam(value = "work_shift_id", required = false) String workShiftId,
            @RequestParam(value = "work_line_id", required = false) String workLineId,
            @RequestParam(value = "department_id", required = false) String departmentId,
            @RequestParam(value = "check_in_start_time", required = false) Long checkinStartTime,
            @RequestParam(value = "check_in_end_time", required = false) Long checkinEndTime,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "order", required = false) Integer order,
            @RequestParam(value = "order_param", required = false) String orderParam,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int pageSize)
            throws Exception {
        if (checkinStartTime != null) {
            checkinStartTime = SysUtils.getOneDayStartTime(checkinStartTime);
        }
        if (checkinEndTime != null) {
            checkinEndTime = DateTimeUtils.getOneDayLastTime(checkinEndTime);
        }
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        EmployeePagedListCommand command = new EmployeePagedListCommand();
        command.setBranCorpId(branCorpId);
        command.setWorkLineId(workLineId);
        command.setWorkShiftId(workShiftId);
        command.setPositionId(positionId);
        command.setDepartmentId(departmentId);
        command.setCheckinStartTime(checkinStartTime);
        command.setCheckinEndTime(checkinEndTime);
        if (StringUtils.isNotBlank(keyword)) {
            command.setKeyword(keyword.trim());
        }
        command.setPage(page - 1);
        command.setPageSize(pageSize);
        command.setOrder(order);
        command.setOrderParam(orderParam);
        command.setCreateUserId(currentUserId);
        return new HttpResponse<>(ErrorCode.CODE_OK, employeeService.getProspectiveEmployeePagedList(command));
    }

    /**
     * 获取待入职员工详情
     *
     * @param employeeId
     * @return
     */
    @ApiOperation(value = "获取待入职员工详情")
    @RequestMapping(value = "/admin/employee/prospective/manage/detail", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<EmployeeDetailResult> getProspectiveEmployeeDetail(
            @RequestParam("employee_id") String employeeId) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse<>(employeeService.getEmployeeDetail(employeeId, branCorpId));
    }

    /**
     * 待入职员工导入
     *
     * @param file 导入的文件
     * @return
     */
    @RequestMapping(value = "/admin/employee/prospective/import/check", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<ImportProspectiveResult> importEmployees(MultipartFile file) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        return new HttpResponse(employeeService.importEmployees(file, currentUserId, branCorpId));
    }


    /**
     * 导出待入职员工
     *
     * @param positionId
     * @param workShiftId
     * @param workLineId
     * @param departmentId
     * @param checkinStartTime
     * @param checkinEndTime
     * @param keyword
     * @param response
     */
    @RequestMapping(value = "/admin/employee/prospective/manage/export", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse prospectiveExport(@RequestParam(value = "position_id", required = false) String positionId,
                                          @RequestParam(value = "work_shift_id", required = false) String workShiftId,
                                          @RequestParam(value = "work_line_id", required = false) String workLineId,
                                          @RequestParam(value = "department_id", required = false) String departmentId,
                                          @RequestParam(value = "check_in_start_time", required = false) Long checkinStartTime,
                                          @RequestParam(value = "check_in_end_time", required = false) Long checkinEndTime,
                                          @RequestParam(value = "keyword", required = false) String keyword,
                                          HttpServletResponse response) throws Exception {

        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        if (branCorpId == null) {
            return new HttpResponse(ErrorCode.CODE_CORPORATION_ID_NOT_OFFER);
        }
        EmployeePagedListCommand employeePagedListCommand = new EmployeePagedListCommand();
        employeePagedListCommand.setPositionId(positionId);
        employeePagedListCommand.setWorkShiftId(workShiftId);
        employeePagedListCommand.setWorkLineId(workLineId);
        employeePagedListCommand.setDepartmentId(departmentId);
        employeePagedListCommand.setCheckinEndTime(checkinStartTime);
        employeePagedListCommand.setCheckinEndTime(checkinEndTime);
        employeePagedListCommand.setKeyword(keyword);
        employeePagedListCommand.setBranCorpId(branCorpId);
        employeePagedListCommand.setCreateUserId(currentUserId);
        employeeService.prospectiveExport(employeePagedListCommand, currentUserId, response);
        return new HttpResponse();
    }


    /**
     * 下载待入职附件
     *
     * @param phones
     * @param type
     * @param response
     * @return
     */
    @RequestMapping(value = "/admin/employee/prospective/manage/attachment/download", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse prospectEmployeeAttachmentDownload(@RequestParam(value = "employee_ids", required = false) String phones,
                                                           @RequestParam(value = "type", required = false) Integer type,
                                                           HttpServletResponse response,
                                                           HttpServletRequest httpServletRequest) throws Exception {
        if (StringUtils.isBlank(phones)) {
            return new HttpResponse(ErrorCode.CODE_PARAMS_ERROR);
        }

        String[] result = new String[100];
        if (phones != null) {

            result = phones.split(",");
        }
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        employeeService.attachmentDownload(result, currentUserId, response, type, 1, httpServletRequest);
        return new HttpResponse();
    }

    /**
     * 同意入职（签约）
     *
     * @param command
     * @return
     */
    @ApiOperation(value = "同意入职（签约）")
    @RequestMapping(value = "/admin/employee/prospective/manage/accept", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> acceptProspectiveEmployees(
            @RequestBody @Validated(value = AcceptProspectiveValidatedGroup.class) AcceptProspectiveEmployeeCommand command,
            BindingResult bindingResult) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();

        command.setBranCorpId(branCorpId);
        command.setUserId(currentUserId);
        Assert.paramsNotError(bindingResult, new ExceptionModel());
        employeeService.acceptProspectiveEmployees(command);
        return new HttpResponse<>();
    }

    @ApiOperation(value = "检测是否满足入职条件")
    @RequestMapping(value = "/admin/employee/prospective/manage/check", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    @GetSessionInfo
    public HttpResponse<List<ProspectiveCheckResult>> checkProspectiveEmployees(@ApiParam @RequestBody BaseCommand.BatchIds command,
                                                                                BindingResult bindingResult,
                                                                                SessionInfo sessionInfo) throws Exception {
        HttpResponse<List<ProspectiveCheckResult>> httpResponse = new HttpResponse<>(employeeService.checkProspectiveEmployees(command, sessionInfo));
        logger.debug("httpResponse: " + httpResponse);
        return httpResponse;
    }


    /**
     * 待入职模板下载
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/admin/employee/prospective/download", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse download(HttpServletResponse response) throws Exception {
        service.download(response);
        HttpResponse httpResponse = new HttpResponse(ErrorCode.CODE_OK);
        return httpResponse;
    }

}
