package com.bumu.arya.salary.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User:LiuJie
 * Date:2017/6/28
 */
@Controller
@Api(tags = {"页面跳转FrameController"})
public class FrameLeftController {
    /**
     * 薪资计算导入页面
     *
     * @return
     */
    @ApiOperation(value = "薪资计算导入页面")
    @RequestMapping(value = "/salary/calculate/base/index", method = RequestMethod.GET)
    public String salaryCalcPage() {
        return "salary/salary_calc";
    }

    /**
     * 立项申请页面
     *
     * @return
     */
    @ApiOperation(value = "立项申请页面")
    @RequestMapping(value = "/salary/project/apply/index", method = RequestMethod.GET)
    public String projectApplyPage() {
        return "project_apply/project_apply_manage";
    }

    /**
     * 公司详情页面
     *
     * @return
     */
    @ApiOperation(value = "立项申请详情")
    @RequestMapping(value = "/salary/project/apply/detail/index", method = RequestMethod.GET)
    public String projectApplyCorpDetailPage() {
        return "project_apply/project_detail";
    }

    /**
     * 公司新建页面
     *
     * @return
     */
    @ApiOperation(value = "立项申请添加")
    @RequestMapping(value = "/salary/project/apply/create/index", method = RequestMethod.GET)
    public String projectApplyCorpCreatePage() {
        return "project_apply/project_create";
    }

    /**
     * 客户管理页面
     *
     * @return
     */
    @ApiOperation(value = "客户管理")
    @RequestMapping(value = "/salary/customer/index", method = RequestMethod.GET)
    public String customerManagePage() {
        return "customer_manage/customer_manage";
    }

    /**
     * 客户详情页面
     *
     * @return
     */
    @ApiOperation(value = "客户详情")
    @RequestMapping(value = "/salary/customer/detail/index", method = RequestMethod.GET)
    public String customerInfoPage() {
        return "customer_manage/customer_detail";
    }

    /**
     * 台账管理页面
     *
     * @return
     */
    @ApiOperation(value = "台账管理")
    @RequestMapping(value = "/salary/customer/account/index", method = RequestMethod.GET)
    public String ledgerManagePage() {
        return "ledger_manage/ledger_manage";
    }

    /**
     * 薪资操作记录反馈 页面
     *
     * @return
     */
    @ApiOperation(value = "薪资操作记录管理")
    @RequestMapping(value = "/salary/calculate/errLog/index", method = RequestMethod.GET)
    public String salaryOperateRecordPage() {
        return "salary_operate_record/salary_operate_record";
    }

    /**
     * 客户资料管理页面
     *
     * @return
     */
    @ApiOperation(value = "客户资料管理页面")
    @RequestMapping(value = "/salary/customerInfo/page/index", method = RequestMethod.GET)
    public String customerPage() {
        return "customer_manage/customer_info";
    }

    /**
     * 薪资操作记录反馈 页面
     *
     * @return
     */
    @ApiOperation(value = "薪资操作记录管理")
    @RequestMapping(value = "/salary/bill/index", method = RequestMethod.GET)
    public String salaryBillPage() {
        return "bill_record/bill_record";
    }

    @ApiOperation(value = "台账汇总")
    @RequestMapping(value = "/salary/customer/account/total/index", method = RequestMethod.GET)
    public String customerAccountTotalPage(){
        return "ledger_manage/ledger_summary";
    }

}
