package com.bumu.arya.salary.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.salary.command.*;
import com.bumu.arya.salary.result.*;
import com.bumu.arya.salary.service.CustomerService;
import com.bumu.arya.salary.service.SalaryFileService;
import com.bumu.common.result.FileUploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 客户管理
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@Controller
@Api(tags = {"客户资料管理customerInfo"})
@RequestMapping(value = "/salary/customerInfo")
public class CustomerInfoController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SalaryFileService salaryFileService;

    @ApiOperation(value = "客户列表（分页）")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<CustomerResult>> customerPage(
            @ApiParam("查询条件-公司名称") @RequestParam(value = "condition", required = false) String condition/*,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize*/) {
        return new HttpResponse<>(customerService.getList(condition));
    }

    @ApiOperation(value = "更新客户资料")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> updateCustomerDetail(
            @ApiParam @Valid @RequestBody CustomerDetailUpdateCommand customerDetailUpdateCommand){
        customerService.updateDetail(customerDetailUpdateCommand);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "导出")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> export() throws Exception{
        return new HttpResponse(salaryFileService.exportCustomer());
    }

}
