package com.bumu.bran.admin.corporation.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.corporation.service.BranCorpService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公司管理制度（只读）
 * @author Allen 2018-01-12
 **/
@Controller
public class CorporationRegulationController {

    private Logger logger = LoggerFactory.getLogger(CorporationRegulationController.class);

    @Autowired
    BranCorpService branCorpService;

    /**
     * 获取所有部门（或者某部门下所有部门）
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = {"/admin/no_permission/employee/prospective/manage/department/list",
            "/admin/no_permission/employee/roster/manage/department/list",
            "/admin/no_permission/employee/leave/manage/department/list",
            "/admin/employee/structure/department/list",
            "/admin/schedule/view/department/list",
            "/admin/corporation/notification/department/list",
            "/admin/attendance/summary/department/list"}, method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getDepartments(@RequestParam(value = "parent_department_id", required = false) String parentId)
            throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        HttpResponse httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, branCorpService.getCorpDepartments(branCorpId));
        logger.debug("httpResponse: " + httpResponse.toJson());
        return httpResponse;
    }


    /**
     * 获取所有班组
     *
     * @return
     */
    @RequestMapping(value = {
            "/admin/no_permission/employee/prospective/manage/work_shift/list",
            "/admin/no_permission/employee/roster/manage/work_shift/list",
            "/admin/no_permission/employee/leave/manage/work_shift/list",
            "/admin/employee/setting/work_shift/list",
            "/admin/schedule/view/work_shift/list",
            "/admin/attendance/summary/work_shift/list",
            "/admin/attendance/setting/work_shift/list"}, method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getWorkShift() throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse<>(ErrorCode.CODE_OK, branCorpService.getCorpAllWorkShifts(branCorpId));
    }

    /**
     * 获取所有职位
     *
     * @return
     */
    @RequestMapping(value = {"/admin/no_permission/employee/prospective/manage/position/list",
            "/admin/no_permission/employee/roster/manage/position/list",
            "/admin/no_permission/employee/leave/manage/position/list",
            "/admin/employee/setting/position/list"}, method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getPosition() throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse<>(ErrorCode.CODE_OK, branCorpService.getCorpAllPositions(branCorpId));
    }

    /**
     * 获取所有工段
     *
     * @return
     */
    @RequestMapping(value = {
            "/admin/no_permission/employee/prospective/manage/work_line/list",
            "/admin/no_permission/employee/roster/manage/work_line/list",
            "/admin/no_permission/employee/leave/manage/work_line/list",
            "/admin/employee/setting/work_line/list"}, method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getWorkLine() throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        return new HttpResponse<>(ErrorCode.CODE_OK, branCorpService.getCorpAllWorkLines(branCorpId));

    }
}
