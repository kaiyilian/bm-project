package com.bumu.arya.admin.payroll.controller;

import com.bumu.arya.admin.payroll.controller.command.ESalaryCommand;
import com.bumu.arya.admin.payroll.result.PayrollDownloadUrlResult;
import com.bumu.arya.admin.payroll.result.PayrollManagerResult;
import com.bumu.arya.admin.payroll.result.PayrollDetailResult;
import com.bumu.arya.admin.payroll.service.PayrollService;
import com.bumu.arya.command.MybatisPagerCommand;
import com.bumu.arya.command.OrderCommand;
import com.bumu.arya.command.PagerCommand;
import com.bumu.arya.response.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by liangjun on 17-7-26.
 */
@Controller
@Api(tags = {"电子工资单管理ESalary"})
public class PayrollController {

    private static Logger logger = LoggerFactory.getLogger(PayrollController.class);

    @Autowired
    PayrollService payrollService;

    @RequestMapping(value = "admin/esalary/manager", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "电子工资单", value = "电子工资单")
    public HttpResponse<PayrollManagerResult> getSalaryInfo(
            @ApiParam @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam @RequestParam(value = "keyword", required = false) String keyword,
            @ApiParam @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "排序列 corpName,hrName,loginName,createTime,payrollNumber,useTimes") @RequestParam(value = "orderParam", required = false) String orderParam,
            @ApiParam(value = "0 倒序desc 1 升序 asc ") @RequestParam(value = "order", required = false, defaultValue = "0") Integer order) throws Exception{
        logger.info("进入电子工资单");
        ESalaryCommand eSalaryCommand = new ESalaryCommand();
        eSalaryCommand.setKeyword(keyword);
        PagerCommand pagerCommand = new MybatisPagerCommand(page, pageSize);
        logger.info("参数: ");
        logger.info("keyword: ");
        logger.info("page: " + pagerCommand.getPage());
        logger.info("pageSize: " + pagerCommand.getPage_size());
        return new HttpResponse<>(payrollService.getCorpUser(eSalaryCommand, pagerCommand, new OrderCommand(order,orderParam)));
    }


    @RequestMapping(value = "admin/esalary/info", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "查询全部电子工资单发送成功记录", value = "查询全部电子工资单发送成功记录")
    public HttpResponse<List<PayrollDetailResult>> getSalaryInfo(
            @ApiParam("查看该公司导入的全部工资单")
            @RequestParam(value = "corpId", required = true) String corpId) {
        return new HttpResponse<>(payrollService.getSalaryInfo(corpId));
    }


    @RequestMapping(value = "admin/esalary/download", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "电子工资单下载", value = "电子工资单下载")
    public HttpResponse<PayrollDownloadUrlResult> getSalaryInfoDownload(
            @ApiParam("现在对应的电子工资单")
            @RequestParam(value = "salaryId", required = false) String salaryId) {
        String s = payrollService.downloadFile(salaryId);
        PayrollDownloadUrlResult payrollDownloadUrlResult = new PayrollDownloadUrlResult();
        payrollDownloadUrlResult.setUrl(s);
        return new HttpResponse<>(payrollDownloadUrlResult);
    }

    @RequestMapping(value = "admin/esalary/download/file", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "电子工资单下载", value = "电子工资单下载")
    public void getSalaryInfoDownload(
            HttpServletResponse response,
            @ApiParam("现在对应的电子工资单")
            @RequestParam(value = "realFileName", required = false) String realFileName,
            @RequestParam(value = "fileName", required = false) String fileName) {
        payrollService.downloadFile(response, realFileName, fileName);
    }

}
