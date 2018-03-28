package com.bumu.arya.salary.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.salary.command.*;
import com.bumu.arya.salary.result.CustomerFollowResult;
import com.bumu.arya.salary.result.ProjectApplyResult;
import com.bumu.arya.salary.service.ProjectApplyService;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


/**
 * 立项申请
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@Controller
@Api(tags = {"立项申请projectApply"})
@RequestMapping(value = "/salary/project")
public class ProjectApplyController {

    @Autowired
    private ProjectApplyService projectApplyService;

    @ApiOperation(value = "分页查询立项申请")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<Pager<ProjectApplyResult>>  getPageList(
            @ApiParam("销售人员或销售部门") @RequestParam(value = "condition", required = false) String condition,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) throws Exception  {
        return new HttpResponse<>(ErrorCode.CODE_OK, projectApplyService.getPage(condition, page, pageSize));
    }

    @ApiOperation(value = "查看立项申请详情")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<ProjectApplyResult> view(
            @ApiParam(value = "立项申请ID") @RequestParam(value = "id") String id) {
        return new HttpResponse<>(ErrorCode.CODE_OK, projectApplyService.view(id));
    }

    @ApiOperation(value = "新建立项申请")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.POST)
    @ParamCheck
    @ResponseBody
    public HttpResponse<Void> create(@ApiParam @Valid @RequestBody ProjectApplyCommand projectApplyCommand,
                                           BindingResult bindingResult) throws Exception {
        projectApplyService.create(projectApplyCommand);
        return new HttpResponse<>();
    }

    @ApiOperation(value = "更新立项申请")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.PUT)
    @ParamCheck
    @ResponseBody
    public HttpResponse<Void> update(@ApiParam @Valid @RequestBody ProjectApplyUpdateCommand projectApplyUpdateCommand,
                                     BindingResult bindingResult, SessionInfo sessionInfo) throws Exception {
        projectApplyService.update(projectApplyUpdateCommand);
        return new HttpResponse<>();
    }

    @ApiOperation(value = "获取客户跟进记录List")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customerFollows", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<CustomerFollowResult>> getCustomerFollows(
            @ApiParam(value = "立项申请ID") @RequestParam(value = "id") String id) {
        return new HttpResponse<>(projectApplyService.followList(id));
    }


    @ApiOperation(value = "增加跟进记录")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/addFollowRecord", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> addFollowRecord(
            @ApiParam @Valid @RequestBody ProjectFollowCommand projectFollowCommand,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception {
        projectApplyService.addFollow(projectFollowCommand);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "成为正式客户")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/toCustomer", method = RequestMethod.PATCH)
    @ParamCheck
    @ResponseBody
    public HttpResponse<Void> toCustomer(
            @ApiParam @Valid @RequestBody ProjectApplyCustomerCommand projectApplyCustomerCommand,
            BindingResult bindingResult, SessionInfo sessionInfo) throws Exception{
        projectApplyService.toCustomer(projectApplyCustomerCommand);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }
}
