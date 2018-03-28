package com.bumu.arya.salary.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.salary.command.*;
import com.bumu.arya.salary.result.*;
import com.bumu.arya.salary.service.CustomerAccountService;
import com.bumu.arya.salary.service.CustomerService;
import com.bumu.arya.salary.service.SalaryFileService;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import com.bumu.common.result.PagerResult;
import com.bumu.common.service.FileUploadService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 客户管理
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@Controller
@Api(tags = {"客户台账管理account"})
@RequestMapping(value = "/salary/customer/account")
public class CustomerAccountController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private SalaryFileService salaryFileService;

    @ApiOperation(value = "台账列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<PagerResult<CustomerAccountResult>> accountPage(
            @ApiParam("客户ID") @RequestParam(value = "customerId") String customerId,
            @ApiParam("查询条件-时间（年-月）") @RequestParam(value = "yearMonth") String yearMonth,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) throws Exception{
        return new HttpResponse<>(customerAccountService.pageAccount(customerId, yearMonth, page, pageSize));
    }

    @ApiOperation(value = "导出台账")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> accountExport(
            @ApiParam @Valid @RequestBody CustomerAccountExportCommand customerAccountExportCommand) throws Exception{
        return new HttpResponse(salaryFileService.exportAccount(customerAccountExportCommand.getCustomerId(), customerAccountExportCommand.getYearMonth()));
    }


    @ApiOperation(value = "更新台账信息")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> updateAccount(
            @ApiParam @Valid @RequestBody CustomerAccountUpdateCommand customerAccountUpdateCommand,
            BindingResult bindingResult,
            SessionInfo sessionInfo) {
        customerAccountService.updateAccount(customerAccountUpdateCommand);
        return new HttpResponse<>();
    }

    @ApiOperation(value = "台账汇总")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/total", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<CustomerAccountTotalResult> accountTotal(
            @ApiParam("开始时间") @RequestParam(value = "startTime") Long startTime,
            @ApiParam("结束时间") @RequestParam(value = "endTime") Long endTime,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        //customerAccountService.updateAccount(startTime, endTime);
        return new HttpResponse<>(customerAccountService.customerAccountTotalList(startTime, endTime, page, pageSize));
    }

    @ApiOperation(value = "导出台账汇总")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/total/export", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> accountTotalExport(
            @ApiParam @Valid @RequestBody CustomerAccountTotalCommand customerAccountTotalCommand,
            BindingResult bindingResult) throws Exception{
        CustomerAccountTotalResult customerAccountTotalResult = customerAccountService.customerAccountTotalList(customerAccountTotalCommand.getStartTime(), customerAccountTotalCommand.getEndTime(), null, null);
        FileUploadFileResult fileUploadFileResult = salaryFileService.exportCustomerAccountTotal(customerAccountTotalResult);
        return new HttpResponse<>(fileUploadFileResult);
    }
}
