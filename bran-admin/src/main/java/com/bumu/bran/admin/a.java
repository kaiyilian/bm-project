package com.bumu.bran.admin;

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
 * Created by Administrator on 2017/11/9.
 */
@Controller
public class a {

    @Autowired
    WorkAttendanceRangeService workAttendanceRangeService;
//
//    @RequestMapping(value = "/admin/employee/roster/manage/index/new")
//    public String da() {
//        return "employee/emp_roster_new";
//    }



}
