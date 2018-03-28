package com.bumu.arya.admin.devops.controller;

import com.bumu.arya.admin.devops.model.entity.ApiLogDocument;
import com.bumu.arya.admin.devops.result.UserActivityResult;
import com.bumu.arya.admin.devops.service.UserActivityService;
import com.bumu.arya.response.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Allen 2018-02-27
 **/
@Api(tags = {"接口访问日志接口 ApiLog"})
@Controller
@RequestMapping("admin/devops/apilog/")
public class ApiLogController {

    @Autowired
    UserActivityService userActivityService;

    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ApiOperation(value = "获取所有最近访问的用户")
    @RequestMapping(value = "/lastvisit", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<ApiLogDocument>> findLastVisit() {
        return new HttpResponse<>(userActivityService.findLastVisit());
    }

    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ApiOperation(value = "获取最近访问的用户相关信息")
    @RequestMapping(value = "/lastvisituser", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<UserActivityResult> findUserActivity() {
        return new HttpResponse<>(userActivityService.getActivitis());
    }

    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ApiOperation(value = "获取最近发薪的用户数据")
    @RequestMapping(value = "/last/payroll", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<UserActivityResult> findLastPayroll() {
        return new HttpResponse<>(userActivityService.getPayrollActivities());
    }
}
