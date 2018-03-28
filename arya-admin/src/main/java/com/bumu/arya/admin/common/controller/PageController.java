package com.bumu.arya.admin.common.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author majun
 * @date 2017/3/9
 */
@Api(value = "PageController", tags = {"页面Page"})
@Controller
@RequestMapping(value = "/admin")
public class PageController {

    @ApiOperation(httpMethod = "GET", notes = "首页", value = "首页")
    @RequestMapping(value = "index/manage/page", method = RequestMethod.GET)
    public String indexPage() {
        return "index";
    }

    @ApiOperation(httpMethod = "GET", notes = "犯罪记录页面", value = "犯罪记录页面")
    @RequestMapping(value = "criminal/page", method = RequestMethod.GET)
    public String criminalPage() {
        return "criminal_record_query/criminal_record_query";
    }

    @ApiOperation(value = "客户商机页面")
    @RequestMapping(value = "business/page", method = RequestMethod.GET)
    public String businessPage() throws Exception {
        return "customer_salary/customer_salary";
    }

    @ApiOperation(value = "电子合同页面")
    @RequestMapping(value = "e_contract/page", method = RequestMethod.GET)
    public String eContractPage() throws Exception {
        return "template/contract_template";
    }

    @ApiOperation(value = "电子合同服务信息页面")
    @RequestMapping(value = "e_contract/service/info/page", method = RequestMethod.GET)
    public String eContractServicePage() {
        return "corporation/corp_service_contract";
    }

    @ApiOperation(value = "电子工资单薪酬页面")
    @RequestMapping(value = "esalary/page", method = RequestMethod.GET)
    public String eSalaryPage() {
        return "electronic_payroll/electronic_payroll";
    }

    @ApiOperation(value = "新闻管理页面")
    @RequestMapping(value = "news/page", method = RequestMethod.GET)
    public String newsPage() {
        return "operation/news_manage";
    }

    @ApiOperation(value = "领红包记录页面")
    @RequestMapping(value = "red/packet/record/page", method = RequestMethod.GET)
    public String redPackedRecordPage() {
        return "operation/red_packet_record";
    }

    @ApiOperation(value = "活动报名列表页面")
    @RequestMapping(value = "activity/enroll/register/page", method = RequestMethod.GET)
    public String activityRegisterPage() {
        return "operation/activity_register";
    }

    @ApiOperation(value = "在线日志")
    @RequestMapping(value = "online/log/page", method = RequestMethod.GET)
    public String onlineLogPage() {
        return "yunwei/log";
    }

    @ApiOperation(value = "用户信息查询")
    @RequestMapping(value = "user/info/index", method = RequestMethod.GET)
    public String userInfoPage() {
        return "user_manage/user_info";
    }

    @ApiOperation(value = "3.8活动")
    @RequestMapping(value = "activity/womens/page", method = RequestMethod.GET)
    public String womenDayActivityPage() {
        return "operation/women_day_activity";
    }

    @ApiOperation(value = "钱包用户查询")
    @RequestMapping(value = "wallet/cnt/page", method = RequestMethod.GET)
    public String walletUserManagePage() {
        return "user_manage/wallet_user_manage";
    }

    @ApiOperation(value = "访问统计")
    @RequestMapping(value = "stats/visit/index", method = RequestMethod.GET)
    public String statsVisitPage() {
        return "yunwei/access_statistics";
    }

    @ApiOperation(value = "访问明细")
    @RequestMapping(value = "detail/visit/index", method = RequestMethod.GET)
    public String detailVisitPage() {
        return "yunwei/access_detail";
    }

    @ApiOperation(value = "招聘管理")
    @RequestMapping(value = "officialwebsite/recruit/index", method = RequestMethod.GET)
    public String websiteRecruitManagePage() {
        return "bumu_website_manage/recruit_manage";
    }

    @ApiOperation(value = "新聞管理")
    @RequestMapping(value = "officialwebsite/news/index", method = RequestMethod.GET)
    public String websiteNewsManagePage() {
        return "bumu_website_manage/news_manage";
    }

    @ApiOperation(value = "组件列表")
    @RequestMapping(value = "assemblyManage/assembly/index", method = RequestMethod.GET)
    public String assemblyManagePage() {
        return "assembly_manage/assembly_list";
    }

}
