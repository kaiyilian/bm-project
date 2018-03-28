package com.bumu.bran.admin.page_route.controller;

import com.bumu.bran.attendance.service.WorkAttendanceRangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.bumu.bran.admin.page_route.constants.BranPageConstants.*;

/**
 * 太多了，拆分一下好管理
 */
@Api(tags = {"页面-考勤管理PageAttendance"})
@Controller()
@RequestMapping(value = "/admin/attendance/")
public class AttendancePageController {

    @Autowired
    WorkAttendanceRangeService workAttendanceRangeService;

    /**
     * 排班管理页面
     *
     * @return jsp
     */
    @RequestMapping(value = "schedule/main/index")
    public String getScheduleMainPage() {
        return BRAN_SCHEDULE_MAIN_PAGE;
    }

    /**
     * 排班视图页面
     *
     * @return jsp
     */
    @RequestMapping(value = "schedule/view/index")
    public String getScheduleViewPage() {
        return BRAN_SCHEDULE_VIEW_PAGE;
    }

    /**
     * 新排班视图页面
     *
     * @return jsp
     */
    @RequestMapping(value = "schedule/view/new")
    public String getScheduleViewNewPage() {
        return BRAN_SCHEDULE_VIEW_NEW_PAGE;
    }


    /**
     * 排班规律页面
     *
     * @return jsp
     */
    @RequestMapping(value = "schedule/rule/index")
    public String getScheduleRulePage() {
        return BRAN_SCHEDULE_RULE_PAGE;
    }


    /**
     * 考勤设备管理页面
     *
     * @return jsp
     */
    @ApiOperation(value = "考勤设备管理页面")
    @RequestMapping(value = "setting/device/manager/index", method = RequestMethod.GET)
    public String getDeviceManagerPage() {
        return BRAN_DEVICE_MANAGER_PAGE;
    }

    /**
     * 考勤配置管理页面
     *
     * @return
     */
    @ApiOperation(value = "考勤配置管理页面")
    @RequestMapping(value = "setting/index", method = RequestMethod.GET)
    public String getSettingManagerPage() {
        return BRAN_WORK_ATTENDANCE_SETTING_PAGE;
    }

    /**
     * 考勤反馈管理页面
     *
     * @return
     */
    @ApiOperation(value = "考勤反馈管理页面")
    @RequestMapping(value = "feeback/manager/index", method = RequestMethod.GET)
    public String getFeeBackManagerPage() {
        return BRAN_WORK_ATTENDANCE_FEEBACK_PAGE;
    }

    /**
     * 考勤汇总管理页面
     *
     * @return
     */
    @ApiOperation(value = "考勤汇总管理页面")
    @RequestMapping(value = "total/manager/index", method = RequestMethod.GET)
    public String getTotalManagerPage() {
        return BRAN_WORK_ATTENDANCE_TOTAL_PAGE;
    }

    /**
     * 考勤页面
     *
     * @return
     */
    @ApiOperation(value = "考勤页面")
    @RequestMapping(value = "attend/manager/index", method = RequestMethod.GET)
    public String getWorkAttendPage() {
        return BRAN_WORK_ATTEND_PAGE;
    }

    /**
     * 考勤范围
     *
     * @return
     */
    @ApiOperation(value = "考勤范围")
    @RequestMapping(value = "setting/range/manager/index", method = RequestMethod.GET)
    public String getWorkAttendRangePage() {
        return BRAN_WORK_ATTEND_RANGE;
    }

    /**
     * 申诉反馈
     *
     * @return
     */
    @ApiOperation(value = "申诉反馈")
    @RequestMapping(value = "appeal/manager/index", method = RequestMethod.GET)
    public String getWorkAttendAppealPage() {
        return BRAN_WORK_ATTEND_APPEAL;
    }


    /**
     * 排班规律
     *
     * @return
     */
    @ApiOperation(value = "排班规律")
    @RequestMapping(value = "schedule/rule/index/new", method = RequestMethod.GET)
    public String getMsgCenterPageNew() {
        return "schedule/schedule_rule";
    }

    /**
     * 考勤-排班规律 信息
     *
     * @return
     */
    @RequestMapping(value = "schedule/rule/info/index", method = RequestMethod.GET)
    public String bPage() {
        return "schedule/schedule_rule_info";
    }

    /**
     * 班次管理
     *
     * @return
     */
    @RequestMapping(value = "schedule/workShiftType/main/index/new", method = RequestMethod.GET)
    public String aPage() {
        return "schedule/schedule_manage";
    }

    /**
     * 班次管理 详情
     *
     * @return
     */
    @RequestMapping(value = "schedule/workShiftType/main/detail/index", method = RequestMethod.GET)
    public String getShiftTypeMsg() {
        return "schedule/schedule_detail";
    }


    /**
     * 考勤汇总管理 详情
     *
     * @return
     */
    @RequestMapping(value = "summary/manager/index/new", method = RequestMethod.GET)
    public String attendanceTotalPage() {
        return "schedule/attendance_summary";
    }

    /**
     * 出勤明细
     * @return
     */
    @RequestMapping(value = "detail/index", method = RequestMethod.GET)
    public String attendancePage() {
        return "schedule/attendance_detail";
    }

    /**
     * 审批类型设置
     *
     * @return
     */
    @ApiOperation(value = "审批类型设置页面")
    @RequestMapping(value = "approval/type/setting/index", method = RequestMethod.GET)
    public String getApprovalTypeSettingTypePage() {
        return "schedule/attendance_setting";
    }

    /**
     * 审批管理
     *
     * @return
     */
    @ApiOperation(value = "审批管理页面")
    @RequestMapping(value = "approval/index", method = RequestMethod.GET)
    public String getApprovalManagerPage() {
        return "schedule/approval_manage";
    }


}


