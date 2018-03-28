package com.bumu.arya.salary.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.salary.command.SalaryBillDeleteCommand;
import com.bumu.arya.salary.command.SalaryBillUpdateCommand;
import com.bumu.arya.salary.result.SalaryBillResult;
import com.bumu.arya.salary.service.SalaryBillService;
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
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/9/4
 */
@Controller
@Api(tags = {"开票记录Controller"})
@RequestMapping("/salary/bill")
public class SalaryBillController {

    @Autowired
    private SalaryBillService salaryBillService;

    @Autowired
    private SalaryFileService salaryFileService;

    @ApiOperation(value = "查询所有开票记录")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/pager", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<SalaryBillResult>> getPageList(
            @ApiParam("查询条件-公司名字/开票总金额") @RequestParam(value = "condition", required = false) String condition/*,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize*/) {
        return new HttpResponse(salaryBillService.getList(condition));
    }

    @ApiOperation(value = "更新开票记录")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> update(
            @ApiParam @Valid @RequestBody SalaryBillUpdateCommand salaryBillUpdateCommand){
        salaryBillService.update(salaryBillUpdateCommand);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "导出开票记录")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> export() throws Exception{
        return new HttpResponse<>(salaryFileService.exportSalaryBill());
    }

    @ApiOperation(value = "删除开票记录")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> delete(@ApiParam @Valid @RequestBody SalaryBillDeleteCommand salaryBillDeleteCommand) {
        salaryBillService.delete(salaryBillDeleteCommand.getIds());
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

}
