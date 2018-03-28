package com.bumu.bran.admin.setting.controller;

import com.bumu.SysUtils;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.corporation.controller.command.UpdateCheckinMessageCommand;
import com.bumu.bran.admin.corporation.result.LeaveReasonListResult;
import com.bumu.bran.admin.corporation.service.BranCorpService;
import com.bumu.bran.admin.setting.controller.command.*;
import com.bumu.bran.common.command.CorpOpLogQueryCommand;
import com.bumu.common.util.DateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author CuiMengxin
 * @date 2016/5/11
 */
@Api(tags = {"员工管理-员工设置EmployeeSetting"})
@Controller
public class SettingController {
    @Autowired
    BranCorpService branCorpService;

    /**
     * 新增部门
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/structure/department/add", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse addDepartment(@RequestBody CreateDepartmentCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.addCorpDepartment(command.getParentId(), command.getDepartmentName(),
                branCorpId, currentUserId));

    }

    /**
     * 修改部门
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/structure/department/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse updateDepartment(@RequestBody UpdateDepartment2Command command) throws Exception {

        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.updateDepartment(command.getDepartmentId(), command.getDepartmentName(),
                branCorpId, currentUserId, command.getVersion()));
    }

    /**
     * 删除部门
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/structure/department/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse deleteDepartment(@RequestBody DeleteDepartmentCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        branCorpService.deleteCorpDepartment(command.getDepartmentId(), branCorpId, currentUserId, command.getVersion());
        return new HttpResponse();
    }

    /**
     * 新增工段
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/work_line/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse addWorkLine(@RequestBody CreateWorkLineCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.addCorpWorkLine(command.getWorkLineName(), branCorpId, currentUserId));
    }

    /**
     * 修改工段
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/work_line/update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse updateWorkLine(@RequestBody UpdateWorkLineCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.updateWorkLine(command.getWorkLineId(), command.getWorkLineName(),
                branCorpId, currentUserId, command.getVersion()));

    }

    /**
     * 删除工段
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/work_line/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse deleteWorkLine(@RequestBody DeleteWorkLineCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        branCorpService.deleteCorpWorkLine(command.getWorkLineId(), branCorpId, currentUserId, command.getVersion());
        return new HttpResponse();

    }

    /**
     * 新增班组
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/work_shift/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse addWorkShift(@RequestBody CreateWorkShiftCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.addCorpWorkShift(command.getWorkShiftName(), branCorpId, currentUserId));
    }

    /**
     * 修改班组
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/work_shift/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse updateWorkShift(@RequestBody UpdateWorkShiftCommand command) throws Exception {

        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.updateWorkShift(command.getWorkShiftId(), command.getWorkShiftName(),
                branCorpId, currentUserId, command.getVersion()));
    }


    /**
     * 删除班组
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/work_shift/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse deleteWorkShift(@RequestBody DeleteWorkShiftCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        branCorpService.deleteCorpWorkShift(command.getWorkShiftId(), branCorpId, currentUserId, command.getVersion());
        return new HttpResponse();
    }

    /**
     * 新增职位
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/position/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse addPosition(@RequestBody CreatePositionCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.addCorpPosition(command.getPositionName(), branCorpId, currentUserId));
    }

    /**
     * 修改职位
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/position/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse updatePostition(@RequestBody UpdatePositionCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.updatePosition(command.getPositionId(), command.getPositionName(),
                branCorpId, currentUserId, command.getVersion()));
    }

    /**
     * 删除职位
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/position/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse deletePosition(@RequestBody DeletePositionCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        branCorpService.deleteCorpPosition(command.getPositionId(), branCorpId, currentUserId, command.getVersion());
        return new HttpResponse();
    }

    /**
     * 获取所有离职原因
     *
     * @return
     */
    @ApiOperation(value = "获取所有离职原因")
    @RequestMapping(value = {
            "/admin/employee/setting/leave_reason/list",
            "/admin/employee/roster/setting/leave_reason/list"}, method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<LeaveReasonListResult> getLeaveReasonList() throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.getCorpAllLeaveReason(branCorpId));
    }

    /**
     * 添加离职原因
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/leave_reason/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse addLeaveReason(@RequestBody CreateLeaveReasonCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.addCorpLeaveReason(command.getLeaveReasonName(), command.getIsNotGood(),
                branCorpId, currentUserId));
    }

    /**
     * 修改离职原因
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/leave_reason/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse updateLeaveReason(@RequestBody UpdateLeaveReasonCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.updateLeaveReason(command.getLeaveReasonId(), command.getLeaveReasonName(),
                command.getIsNotGood(), branCorpId, currentUserId, command.getVersion()));
    }

    /**
     * 删除离职原因
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/leave_reason/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse deleteLeaveReason(@RequestBody DeleteLeaveReasonCommand command) {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        branCorpService.deleteCorpLeaveReason(command.getLeaveReasonId(), branCorpId, currentUserId, command.getVersion());
        return new HttpResponse();
    }

    /**
     * 获取入职消息配置
     *
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/general/message/check_in", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getCheckinMessage() throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.getCorpCheckinMessage(branCorpId));
    }

    /**
     * 修改入职消息配置
     *
     * @return
     */
    @RequestMapping(value = "/admin/employee/setting/general/message/check_in/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse updateCheckinMessage(@RequestBody UpdateCheckinMessageCommand command) throws Exception {

        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.updateCorpCheckinMessage(command, currentUserId, branCorpId));
    }

    /**
     * 获取操作模块列表
     *
     * @return
     */
    @RequestMapping(value = "/admin/setting/log/operation/module/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getCorpOperationModuleList() throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.getCorpOpModuleList(branCorpId));
    }


    /**
     * 获取操作类型列表
     *
     * @param moduleId
     * @return
     */
    @RequestMapping(value = "/admin/setting/log/operation/type/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getCorpOperationTypeList(@RequestParam("module_id") String moduleId) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.getCorpOpTypeList(moduleId, branCorpId));
    }

    /**
     * 获取企业的所有操作记录
     *
     * @return
     */
    @RequestMapping(value = "/admin/setting/log/operation", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse getCorpOperationLog(@RequestParam(value = "module_id", required = false) String moduleId,
                                     @RequestParam(value = "type_id", required = false) String typeId,
                                     @RequestParam(value = "start_time", required = false) Long startTime,
                                     @RequestParam(value = "end_time", required = false) Long endTime,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize)
            throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        CorpOpLogQueryCommand command = new CorpOpLogQueryCommand();
        command.setBranCorpId(branCorpId);
        command.setModuleId(moduleId);
        command.setOpTypeId(typeId);
        if (startTime != null) {
            command.setStartTime(SysUtils.getOneDayStartTime(startTime));
        }
        if (endTime != null) {
            command.setEndTime(DateTimeUtils.getOneDayLastTime(endTime));
        }
        command.setPage(page - 1);
        command.setPageSize(pageSize);
        return new HttpResponse(branCorpService.getCorpOperateLog(command));
    }

    /**
     * 导出公司部门的组织架构
     *
     * @return
     */
    @RequestMapping(value = "/admin/employee/structure/department/export", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse export() throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse(branCorpService.export(branCorpId));
    }
}
