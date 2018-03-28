package com.bumu.bran.admin.page_route.controller;

import com.bumu.bran.attendance.service.WorkAttendanceRangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.bumu.bran.admin.page_route.constants.BranPageConstants.*;

/**
 * @author majun
 * @date 2016/12/16
 * 重构,把所有的index页面都放入该controller
 */
@Api(tags = {"页面BranPage"})
@Controller
public class BranPageController {

    @Autowired
    WorkAttendanceRangeService workAttendanceRangeService;

    /**
     * 体检页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/prospective/examination/index")
    public String getExaminationPage() {
        return BRAN_EXAM_PAGE;
    }

    /**
     * 企业信息页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/corporation/info/index")
    public String getCorporationPage() {
        return BRAN_CORPORATION_PAGE;
    }

    /**
     * 厂车路线页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/corporation/bus/index")
    public String getCorporationBusPage() {
        return BRAN_CORPORATION_BUS_PAGE;
    }

    /**
     * 员工手册页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/corporation/handbook/index")
    public String getCorporationHandbookPage() {
        return BRAN_CORPORATION_HANDBOOK_PAGE;
    }

    /**
     * 待入职-员工名单页面-测试
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/prospective/list/index/test")
    public String getProspectiveEmployeeListPageTest() {
        return BRAN_PROSPECTIVE_TEST_PAGE;
    }

    /**
     * 待入职-员工名单页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/prospective/list/index")
    public String getProspectiveEmployeeListPage() {
        return BRAN_PROSPECTIVE_PAGE;
    }

    /**
     * 在职-员工花名册页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/roster/manage/index")
    public String getEmployeeListPage() {
        return BRAN_EMP_PAGE;
    }

    /**
     * 待入职-查看员工详情页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/prospective/manage/detail/index")
    public String getEmployeeProspectiveDetailPage() {
        return BRAN_PROSPECTIVE_DETAIL_PAGE;
    }

    /**
     * 在职-查看员工详情页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/roster/manage/detail/index")
    public String getEmployeeDetailPage() {
        return BRAN_EMP_DETAIL_PAGE;
    }

    /**
     * 离职-查看离职员工列表页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/leave/index")
    public String getEmployeeLeavePage() {
        return BRAN_LEAVE_PAGE;
    }

    /**
     * 离职-查看离职员工详情页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/leave/detail/index")
    public String getLeaveEmployeeDetailPage() {
        return BRAN_LEAVE_DETAIL_PAGE;
    }

    /**
     * 登录页面
     *
     * @return jsp
     */
    @RequestMapping(value = {"/", "/login"})
    public String getLoginPage() {
        return BRAN_LOGIN_PAGE;
    }

    /**
     * 主页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/main")
    public String getMainPage(ModelMap map) {
        String loginName = (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isBlank(loginName)) {
            return BRAN_LOGIN_PAGE;
        } else {
            Session session = SecurityUtils.getSubject().getSession();
            String branCorpId = session.getAttribute("bran_corp_id").toString();
            int isOpenPhoneClock = workAttendanceRangeService.isOpenPhoneClock(branCorpId);
            map.put("is_open_phone_clock", isOpenPhoneClock);
        }
        return BRAN_MAIN_PAGE;
    }

    /**
     * 合同到期详情页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/home/expiration/detail")
    public String getExpirationDetailPage() {
        return BRAN_EXPIRATION_DETAIL_PAGE;
    }

    /**
     * 试用期到期详情页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/home/probation/detail")
    public String getProbationDetailPage() {
        return BRAN_PROBATION_DETAIL_PAGE;
    }

    /**
     * 同意入职详情页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/home/acceptOffer/detail")
    public String getAcceptOfferDetailPage() {
        return BRAN_ACCEPT_OFFER_DETAIL_PAGE;
    }

    /**
     * 消息页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/corporation/notification/index")
    public String getNotificationPage() {
        return BRAN_ACCEPT_NOTIFICATION_PAGE;
    }

    /**
     * 获取薪资条页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/salary/index")
    public String getSalaryUnPublishedPage() {
        return BRAN_SALARY_PAGE;
    }

    /**
     * 薪资条已发布页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/salary/release/index")
    public String getSalaryPublishedPage() {
        return BRAN_SALARY_RELEASE_PAGE;
    }

    /**
     * 获取工资详情页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/salary/detail/index")
    public String getSalaryDetailPage() {
        return BRAN_SALARY_DETAIL_PAGE;
    }

    /**
     * 获取未导入薪资页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/salary/not/import/index")
    public String getSalaryFailImportPage() {
        return BRAN_SALARY_FAIL_IMPORT_PAGE;
    }

    /**
     * 部门管理页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/structure/department/index")
    public String getDepartmentSettingPage() {
        return BRAN_DEPARTMENT_PAGE;
    }

    /**
     * 工段管理页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/setting/work_line/index")
    public String getWorkLineSettingPage() {
        return BRAN_WORK_LINE_PAGE;
    }

    /**
     * 职位管理页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/setting/position/index")
    public String getPositionSettingPage() {
        return BRAN_POSITION_PAGE;
    }

    /**
     * 离职原因页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/setting/leave_reason/index")
    public String getLeaveReasonSettingPage() {
        return BRAN_LEAVE_REASON_PAGE;
    }

    /**
     * 班组管理页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/setting/work_shift/index")
    public String getWorkShiftSettingPage() {
        return BRAN_WORK_SHIFT_PAGE;
    }

    /**
     * 通用管理页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/setting/general/index")
    public String getGeneralSettingPage() {
        return BRAN_SETTING_PAGE;
    }

    /**
     * 操作日志页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/setting/log/operation/index")
    public String getCorpOpLogPage() {
        return BRAN_LOGGER_PAGE;
    }

    /**
     * 工号页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/employee/setting/work_sn_prefix/index")
    public String getWorkSnPrefixPage() {
        return BRAN_WORK_SN_PREFIX_PAGE;
    }

    /**
     * 获取页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/user/defined/page")
    public String getUserDefinedPage() {
        return BRAN_USER_DEFINED_PAGE;
    }

    /**
     * 获得用户管理页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/permission/account/index")
    public String getPermissionPage() {
        return BRAN_USER_PERMISSION_PAGE;
    }


    /**
     * 首页页面
     *
     * @return jsp
     */
    @RequestMapping(value = "/admin/index")
    public String getIndexPage() {
        return BRAN_INDEX_PAGE;
    }


    /**
     * 电子合同新建页面
     *
     * @return
     */
    @ApiOperation(value = "电子合同新建页面")
    @RequestMapping(value = "/admin/e_contract/create/index", method = RequestMethod.GET)
    public String getEContractCreatePage() {
        return "contract/contract_create";
    }

    /**
     * 电子合同详情页面
     *
     * @return
     */
    @ApiOperation(value = "电子合同详情页面")
    @RequestMapping(value = "/admin/e_contract/detail/index", method = RequestMethod.GET)
    public String getEContractDetailPage() {
        return "contract/contract_detail";
    }

    /**
     * 电子合同管理页面
     *
     * @return
     */
    @ApiOperation(value = "电子合同管理页面")
    @RequestMapping(value = "/admin/e_contract/manager/index", method = RequestMethod.GET)
    public String getEContractManagerPage() {
        return "contract/contract_manage";
    }

    /**
     * 消息中心
     *
     * @return
     */
    @ApiOperation(value = "消息中心管理页面")
    @RequestMapping(value = "/admin/msg/center/manager/index", method = RequestMethod.GET)
    public String getMsgCenterPage() {
        return "notification/notification";
    }

    /**
     * 薪资新建
     *
     * @return
     */
    @ApiOperation(value = "薪资新建页面")
    @RequestMapping(value = "/admin/salary/create/index", method = RequestMethod.GET)
    public String getSalaryCreatePage() {
        return "salary/salary_create";
    }

    /**
     * 薪资历史
     *
     * @return
     */
    @ApiOperation(value = "薪资历史页面")
    @RequestMapping(value = "/admin/salary/history/index", method = RequestMethod.GET)
    public String getSalaryHistoryPage() {
        return "salary/salary_history";
    }

    /**
     * 薪资详情
     *
     * @return
     */
    @ApiOperation(value = "薪资详情页面")
    @RequestMapping(value = "/admin/salary/history/detail/index", method = RequestMethod.GET)
    public String getSalaryHistoryDetailPage() {
        return "salary/salary_detail";
    }

    /**
     * 入职提醒
     *
     * @return
     */
    @ApiOperation(value = "入职提醒页面")
    @RequestMapping(value = "/admin/home/entry/remind/index", method = RequestMethod.GET)
    public String getEntryRemindPage() {
        return "expire_remind/entry_remind_manage";
    }

    /**
     * 生日提醒
     *
     * @return
     */
    @ApiOperation(value = "生日提醒页面")
    @RequestMapping(value = "/admin/home/employee/birthday/index", method = RequestMethod.GET)
    public String getBirthWarningPage() {
        return "expire_remind/birth_warning";
    }

    /**
     * 身份证到期提醒
     *
     * @return
     */
    @ApiOperation(value = "身份证到期提醒页面")
    @RequestMapping(value = "/admin/home/employee/idCardNo/index", method = RequestMethod.GET)
    public String getIdCardExpireManagePage() {
        return "expire_remind/idCard_expire_manage";
    }


    /**
     * 员工配置页面
     *
     * @return
     */
    @ApiOperation(value = "员工配置页面")
    @RequestMapping(value = "/admin/employee/setting/index", method = RequestMethod.GET)
    public String getEmpSettingPage() {
        return "employee/setting";
    }

    /**
     * 员工配置页面
     *
     * @return
     */
    @ApiOperation(value = "花名册新")
    @RequestMapping(value = "/admin/employee/roster/manage/index/new", method = RequestMethod.GET)
    public String getEmpRosterNewPage() {
        return "employee/emp_roster_new";
    }


}


