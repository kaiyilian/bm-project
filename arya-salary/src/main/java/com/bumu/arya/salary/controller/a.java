package com.bumu.arya.salary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User:LiuJie
 * Date:2017/3/9
 */

@Controller
public class a {


    /**
     * 客户管理 详情 页面
     *
     * @return
     */
    @RequestMapping(value = "/salary/customer/info/index", method = RequestMethod.GET)
    public String electronicPayrollPage() {
        return "customer_manage/customer_info";
    }

    /**
     * 开票记录 页面
     *
     * @return
     */
    @RequestMapping(value = "/salary/bill/record/index", method = RequestMethod.GET)
    public String bPage() {
        return "bill_record/bill_record";
    }

//    /**
//     * 开票记录 页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/salary/customer/account/summary/index", method = RequestMethod.GET)
//    public String cPage() {
//        return "ledger_manage/ledger_summary";
//    }


}
