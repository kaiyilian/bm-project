package com.bumu.bran.admin.employee.controller;

import com.bumu.SysUtils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.corporation.service.BranCorpService;
import com.bumu.bran.admin.corporation.service.FileService;
import com.bumu.bran.admin.employee.controller.command.ContractExtensionCommand;
import com.bumu.bran.admin.employee.controller.command.EmployeeLeaveCommand;
import com.bumu.bran.admin.employee.controller.command.ImportEmploeesConfirm;
import com.bumu.bran.admin.employee.controller.command.RosterCommand;
import com.bumu.bran.admin.employee.result.DistractResult;
import com.bumu.bran.admin.employee.result.EmployeeListPaginationResult;
import com.bumu.bran.admin.employee.result.ImportEmpConfirmResult;
import com.bumu.bran.admin.employee.service.EmployeeService;
import com.bumu.bran.admin.system.command.IdVersionsCommand;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.employee.command.EmployeePagedListCommand;
import com.bumu.bran.employee.result.EmployeeResult;
import com.bumu.bran.employee.result.QueryEmpResult;
import com.bumu.bran.employee.result.QueryLeaveEmpResult;
import com.bumu.bran.validated.AcceptProspectiveValidatedGroup;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.employee.constant.EmployeeConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/5/11
 */
@Api(tags = {"员工管理-正式员工花名册EmployeeRoster"})
@Controller
public class EmployeeController {

    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @Autowired
    BranCorpService branCorpService;

    @Autowired
    FileService fileService;

    /**
     * 获取在职员工详情
     *
     * @param employeeId
     * @return
     */
    @RequestMapping(value = "/admin/employee/roster/manage/detail", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getEmployeeDetail(@RequestParam("employee_id") String employeeId) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse<>(employeeService.getEmployeeDetailForEmp(employeeId, branCorpId,
                EmployeeConstants.EMPLOYEE_OFFICIAL));
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/admin/no_permission/location", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<DistractResult> location(String id, @RequestParam(value = "type", defaultValue = "0") int type)
            throws Exception {
        return new HttpResponse<>(employeeService.location(id, type));
    }

    /**
     * 获取离职员工详情
     *
     * @param employeeId
     * @return
     */
    @RequestMapping(value = "/admin/employee/leave/detail", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getLeaveEmployeeDetail(@RequestParam("employee_id") String employeeId) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        return new HttpResponse<>(employeeService.getEmployeeDetailForLeave(currentUserId, employeeId, branCorpId,
                EmployeeConstants.EMPLOYEE_LEAVE));
    }

    /**
     * 员工花名册
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
    @ApiOperation(value = "查询员工花名册")
    @RequestMapping(value = "/admin/employee/roster/manage/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<EmployeeListPaginationResult> getEmployeeList(
            @RequestParam(value = "emp_id", required = false) String empId,
            @RequestParam(value = "position_id", required = false) String positionId,
            @RequestParam(value = "work_shift_id", required = false) String workShiftId,
            @RequestParam(value = "work_line_id", required = false) String workLineId,
            @RequestParam(value = "department_id", required = false) String departmentId,
            @RequestParam(value = "check_in_start_time", required = false) Long checkinStartTime,
            @RequestParam(value = "check_in_end_time", required = false) Long checkinEndTime,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "order", required = false) Integer order,
            @RequestParam(value = "orderParam", required = false) String orderParam,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int pageSize)
            throws Exception {

        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        if (checkinStartTime != null) {
            checkinStartTime = SysUtils.getOneDayStartTime(checkinStartTime);
        }
        if (checkinEndTime != null) {
            checkinEndTime = DateTimeUtils.getOneDayLastTime(checkinEndTime);
        }
        EmployeePagedListCommand command = new EmployeePagedListCommand();
        command.setBranCorpId(branCorpId);
        command.setWorkLineId(workLineId);
        command.setWorkShiftId(workShiftId);
        command.setPositionId(positionId);
        command.setDepartmentId(departmentId);
        command.setEmpId(empId);
        if (checkinStartTime != null) {
            command.setCheckinStartTime(checkinStartTime);
        }
        if (checkinEndTime != null) {
            command.setCheckinEndTime(checkinEndTime);
        }
        if (StringUtils.isNotBlank(keyword)) {
            command.setKeyword(keyword.trim());
        }
        command.setPage(page - 1);
        command.setPageSize(pageSize);
        command.setOrder(order);
        command.setOrderParam(orderParam);
        return new HttpResponse<>(employeeService.getEmployeePagedSortList(command));
    }

    /**
     * 读取员工身份证照片
     *
     * @param fileName
     * @param branUserId
     * @param response
     */
    @RequestMapping(value = {"/admin/employee/prospective/detail/idcard",
            "/admin/employee/detail/idcard",
            "/admin/employee/leave/detail/idcard"}, method = RequestMethod.GET)
    @ResponseBody
    public void getEmployeeIdcardImage(
            @RequestParam("file_name") String fileName,
            @RequestParam("bran_user_id") String branUserId,
            HttpServletResponse response) throws Exception {
        fileService.readEmployeeIdcardImageFile(fileName, branUserId, response);
    }

    /**
     * 读取学历照片
     *
     * @param fileName
     * @param branUserId
     * @param response
     */
    @RequestMapping(value = {"/admin/employee/prospective/detail/education",
            "/admin/employee/detail/education",
            "/admin/employee/leave/detail/education"}, method = RequestMethod.GET)
    @ResponseBody
    public void getEmployeeEducationImage(
            @RequestParam("file_name") String fileName,
            @RequestParam("bran_user_id") String branUserId,
            HttpServletResponse response) throws Exception {
        fileService.readEmployeeEducationImageFile(fileName, branUserId, response);
    }

    /**
     * 读取员工离职证明照片
     *
     * @param fileName
     * @param branUserId
     * @param response
     */
    @RequestMapping(value = {"/admin/employee/prospective/detail/leave",
            "/admin/employee/detail/leave",
            "/admin/employee/leave/detail/leave"}, method = RequestMethod.GET)
    @ResponseBody
    public void getEmployeeLeaveResionImage(
            @RequestParam("file_name") String fileName,
            @RequestParam("bran_user_id") String branUserId,
            HttpServletResponse response) throws Exception {
        fileService.readEmployeeLeaveImageFile(fileName, branUserId, response);
    }


    @RequestMapping(value = {"/admin/employee/detail/all"}, method = RequestMethod.GET)
    @ResponseBody
    public void getEmpAllImage(String fileName,
                               String empId,
                               String corpId,
                               HttpServletResponse response) throws Exception {
        fileService.getEmpAllImage(fileName, empId, corpId, response);
    }

    /**
     * 获取员工证件照片
     *
     * @param fileName
     * @param branUserId
     * @param response
     */
    @RequestMapping(value = {
            "/admin/employee/prospective/detail/face",
            "/admin/employee/detail/face",
            "/admin/employee/leave/detail/face"}, method = RequestMethod.GET)
    @ResponseBody
    public void getEmployeeFaceImage(@RequestParam("file_name") String fileName,
                                     @RequestParam("bran_user_id") String branUserId,
                                     HttpServletResponse response) throws Exception {
        fileService.readEmployeeFaceImageFile(fileName, branUserId, response);
    }

    /**
     * 离职员工列表
     *
     * @param positionId
     * @param workShiftId
     * @param workLineId
     * @param departmentId
     * @param leaveStartTime
     * @param leaveEndTime
     * @param keyword
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/admin/employee/leave/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getLeaveEmployeeList(
            @RequestParam(value = "position_id", required = false) String positionId,
            @RequestParam(value = "work_shift_id", required = false) String workShiftId,
            @RequestParam(value = "work_line_id", required = false) String workLineId,
            @RequestParam(value = "department_id", required = false) String departmentId,
            @RequestParam(value = "leave_start_time", required = false) Long leaveStartTime,
            @RequestParam(value = "leave_end_time", required = false) Long leaveEndTime,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "order", required = false) Integer order,
            @RequestParam(value = "order_param", required = false) String orderParam,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int pageSize)
            throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        if (leaveStartTime != null) {
            leaveStartTime = SysUtils.getOneDayStartTime(leaveStartTime);
        }
        if (leaveEndTime != null) {
            leaveEndTime = DateTimeUtils.getOneDayLastTime(leaveEndTime);
        }
        EmployeePagedListCommand command = new EmployeePagedListCommand();
        command.setBranCorpId(branCorpId);
        command.setWorkLineId(workLineId);
        command.setWorkShiftId(workShiftId);
        command.setPositionId(positionId);
        command.setDepartmentId(departmentId);
        command.setLeaveStartTime(leaveStartTime);
        command.setLeaveEndTime(leaveEndTime);
        if (StringUtils.isNotBlank(keyword)) {
            command.setKeyword(keyword.trim());
        }
        command.setPage(page - 1);
        command.setPageSize(pageSize);
        command.setOrder(order);
        command.setOrderParam(orderParam);
        return new HttpResponse<>(employeeService.getLeaveEmployeePagedSortListNew(command));
    }

    /**
     * 退工
     *
     * @param command
     * @return
     */
    @ApiOperation(value = "退工")
    @RequestMapping(value = "/admin/employee/roster/manage/leave", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> employeesLeave(@RequestBody EmployeeLeaveCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        employeeService.employeesLeave(command.getEmployeeIds(), command.getLeaveReasonId(),
                command.getLeaveTime(), command.getRemarks(), branCorpId, currentUserId);
        return new HttpResponse<>();
    }

    /**
     * 续签
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/roster/manage/contract/extension", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse contractExtension(@RequestBody ContractExtensionCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        employeeService.contractExtension(command.getMap(), command.getContractStartTime(), command.getContractEndTime(),
                branCorpId, currentUserId);
        return new HttpResponse<>();
    }


    /**
     * 确认导入待入职员工
     *
     * @param importEmploeesConfirm
     * @return
     */
    @RequestMapping(value = "/admin/employee/prospective/import/confirm", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<ImportEmpConfirmResult> importEmployeesConfirm(@RequestBody @Valid ImportEmploeesConfirm
                                                                               importEmploeesConfirm,
                                                                       BindingResult bindingResult) throws Exception {
        logger.debug("进入确认导入待入职员工");
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        logger.debug("file_id: " + importEmploeesConfirm.getFileId());
        logger.debug("fileTypeStr: " + importEmploeesConfirm.getFileTypeStr());
        logger.debug("bran_corp_id: " + branCorpId);

        return new HttpResponse<>(employeeService.importEmployeesConfirm(importEmploeesConfirm.getFileId(),
                importEmploeesConfirm.getFileTypeStr(),
                currentUserId, branCorpId, bindingResult));

    }


    /**
     * 导出花名册员工
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
    @RequestMapping(value = "/admin/employee/roster/manage/export", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> employeeExport(
            @RequestParam(value = "position_id", required = false) String positionId,
            @RequestParam(value = "work_shift_id", required = false) String workShiftId,
            @RequestParam(value = "work_line_id", required = false) String workLineId,
            @RequestParam(value = "department_id", required = false) String departmentId,
            @RequestParam(value = "check_in_start_time", required = false) Long checkinStartTime,
            @RequestParam(value = "check_in_end_time", required = false) Long checkinEndTime,
            @RequestParam(value = "keyword", required = false) String keyword,
            HttpServletResponse response) throws Exception {

        EmployeePagedListCommand employeePagedListCommand = new EmployeePagedListCommand();
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        logger.info("bran_corp_id: " + branCorpId);
        if (branCorpId == null) {
            return new HttpResponse<>(ErrorCode.CODE_CORPORATION_ID_NOT_OFFER);
        }
        employeePagedListCommand.setPositionId(positionId);
        employeePagedListCommand.setWorkShiftId(workShiftId);
        employeePagedListCommand.setWorkLineId(workLineId);
        employeePagedListCommand.setDepartmentId(departmentId);
        // 开始时间 取最小值 时分秒 00:00:00
        if (checkinStartTime != null) {
            checkinStartTime = DateTimeUtils.getTodayMin(new DateTime(checkinStartTime)).getMillis();
        }
        // 开始时间 取最大值 时分秒 23:59:59
        if (checkinEndTime != null) {
            checkinEndTime = DateTimeUtils.getTodayMax(new DateTime(checkinEndTime)).getMillis();
        }
        employeePagedListCommand.setCheckinStartTime(checkinStartTime);
        employeePagedListCommand.setCheckinEndTime(checkinEndTime);
        employeePagedListCommand.setKeyword(keyword);
        employeePagedListCommand.setBranCorpId(branCorpId);

        return new HttpResponse<>(ErrorCode.CODE_OK, employeeService.employeeExport(employeePagedListCommand, currentUserId, response));
    }

    /**
     * 离职员工导出
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
    @RequestMapping(value = "/admin/employee/leave/export", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse leaveExport(
            @RequestParam(value = "position_id", required = false) String positionId,
            @RequestParam(value = "work_shift_id", required = false) String workShiftId,
            @RequestParam(value = "work_line_id", required = false) String workLineId,
            @RequestParam(value = "department_id", required = false) String departmentId,
            @RequestParam(value = "check_in_start_time", required = false) Long checkinStartTime,
            @RequestParam(value = "check_in_end_time", required = false) Long checkinEndTime,
            @RequestParam(value = "keyword", required = false) String keyword,
            HttpServletResponse response) throws Exception {

        EmployeePagedListCommand employeePagedListCommand = new EmployeePagedListCommand();
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        logger.debug("bran_corp_id: " + branCorpId);
        if (branCorpId == null) {
            return new HttpResponse<>(ErrorCode.CODE_CORPORATION_ID_NOT_OFFER);
        }
        employeePagedListCommand.setPositionId(positionId);
        employeePagedListCommand.setWorkShiftId(workShiftId);
        employeePagedListCommand.setWorkLineId(workLineId);
        employeePagedListCommand.setDepartmentId(departmentId);
        // 开始时间 取最小值 时分秒 00:00:00
        if (checkinStartTime != null) {
            checkinStartTime = DateTimeUtils.getTodayMin(new DateTime(checkinStartTime)).getMillis();
        }
        employeePagedListCommand.setLeaveStartTime(checkinStartTime);
        // 开始时间 取最大值 时分秒 23:59:59
        if (checkinEndTime != null) {
            checkinEndTime = DateTimeUtils.getTodayMax(new DateTime(checkinEndTime)).getMillis();
        }
        employeePagedListCommand.setLeaveEndTime(checkinEndTime);
        employeePagedListCommand.setKeyword(keyword);
        employeePagedListCommand.setBranCorpId(branCorpId);
        employeeService.leaveExport(employeePagedListCommand, currentUserId, response);
        return new HttpResponse<>();
    }


    /**
     * 下载在职员工附件
     *
     * @param phones
     * @param type
     * @param response
     * @return
     */
    @RequestMapping(value = "/admin/employee/roster/manage/attachment/download", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse employeeAttachmentDownload(@RequestParam(value = "employee_ids", required = false) String phones,
                                                   @RequestParam(value = "type", required = false) Integer type,
                                                   HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        if (StringUtils.isBlank(phones)) {
            return new HttpResponse<>(ErrorCode.CODE_PARAMS_ERROR);
        }

        String[] result = new String[100];
        if (phones != null) {
            result = phones.split(",");
        }
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        employeeService.attachmentDownload(result, currentUserId, response, type, 2, request);
        return new HttpResponse<>();
    }

    /**
     * 删除离职员工
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/leave/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse deleteLeaveEmployee(@RequestBody IdVersionsCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        command.setId(currentUserId);
        employeeService.deleteLeaveEmployee(command);
        return new HttpResponse<>();
    }

    /**
     * 修改花名册
     *
     * @param command
     * @return
     */
    @ApiOperation(value = "修改花名册")
    @RequestMapping(value = "/admin/employee/roster/manage/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse update(@ApiParam @RequestBody @Validated(value = AcceptProspectiveValidatedGroup.class)
                                       RosterCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        command.setBranCorpId(branCorpId);
        command.setUserId(currentUserId);
        return new HttpResponse<>(ErrorCode.CODE_OK, employeeService.update(command));
    }

    /**
     * 合同到期
     *
     * @param command
     * @return
     */
    @RequestMapping(value = {
            "/admin/employee/roster/manage/expiration",
            "/admin/home/employee/roster/manage/expiration"}, method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse expiration(@RequestBody RosterCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        command.setBranCorpId(branCorpId);
        command.setUserId(currentUserId);
        logger.debug("page: " + command.getPage());
        return new HttpResponse<>(employeeService.expiration(command));
    }

    /**
     * 试用期到期
     *
     * @param command
     * @return
     */
    @RequestMapping(value = {
            "/admin/employee/roster/manage/probation",
            "/admin/home/employee/roster/manage/probation"}, method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse probation(@RequestBody RosterCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        command.setBranCorpId(branCorpId);
        command.setUserId(currentUserId);
        return new HttpResponse<>(employeeService.probation(command));
    }

    /**
     * 首页显示已经完成入职流程的待入职员工
     *
     * @param command
     * @return
     */
    @RequestMapping(value = {
            "/admin/employee/roster/manage/acceptOfferUsers",
            "/admin/home/employee/roster/manage/acceptOfferUsers"}, method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse acceptOfferUsers(@RequestBody RosterCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        command.setBranCorpId(branCorpId);
        command.setUserId(currentUserId);
        return new HttpResponse<>(employeeService.acceptOfferUsers(command));
    }

    /**
     * 试用期受理
     *
     * @param command 参数
     * @return 响应
     */
    @RequestMapping(value = "/admin/employee/roster/manage/probation/process", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse probationProcess(@RequestBody IdVersionsCommand command)
            throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        command.setUserId(currentUserId);
        command.setBranCorpId(branCorpId);
        return new HttpResponse<>(employeeService.probationProcess(command));
    }

    @ApiOperation(value = "根据id获取花名册详情")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/admin/employee/roster/manage/get/id", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<EmployeeResult> getId(@ApiParam @RequestParam(value = "id", required = false) String id, SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(employeeService.getId(id, sessionInfo));
    }

    /**
     * 读取员工照片
     *
     * @param fileName
     * @param branUserId
     * @param response
     */
    @RequestMapping(value = {"/admin/employee/prospective/detail/image/all"}, method = RequestMethod.GET)
    @ResponseBody
    public void getEmployeeImage(String fileName,
                                 String branUserId,
                                 String type,
                                 HttpServletResponse response) throws Exception {
        fileService.getEmployeeImage(fileName, branUserId, type, response);
    }

    /**
     * 查询某公司所有未删除的在职员工
     *
     * @throws Exception
     */
    @ApiOperation(value = "查询某公司所有未删除的员工")
    @ApiResponse(code = 200, message = "成功", response = List.class)
    @RequestMapping(value = {
            "/admin/attendance/approval/emp_list",
            "/admin/attendance/summary/emp_list",
            "/admin/attendance/detail/emp_list",
            "/admin/attendance/schedule/view/emp_list",
            "/admin/employee/roster/branCorp/list"}, method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<List<QueryEmpResult>> getAllEmpListByBranCorpId(SessionInfo sessionInfo) throws Exception {
        logger.info("getAllEmpListByBranCorpId ...");
        return new HttpResponse<>(ErrorCode.CODE_OK, employeeService.getAllEmpListByBranCorpId(sessionInfo));
    }

    /**
     * 查询某公司所有未删除的离职员工
     *
     * @throws Exception
     */
    @ApiOperation(value = "查询某公司所有未删除的离职员工")
    @ApiResponse(code = 200, message = "成功", response = List.class)
    @RequestMapping(value = {
            "/admin/attendance/approval/leave_emp_list",
            "/admin/attendance/summary/leave_emp_list",
            "/admin/attendance/detail/leave_emp_list",
            "/admin/attendance/schedule/view/leave_emp_list"}, method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<List<QueryLeaveEmpResult>> getAllLeaveEmpListByBranCorpId(SessionInfo sessionInfo) throws Exception {
        logger.info("getAllLeaveEmpListByBranCorpId ...");
        return new HttpResponse<>(ErrorCode.CODE_OK, employeeService.getAllLeaveEmpListByBranCorpId(sessionInfo));
    }


}
