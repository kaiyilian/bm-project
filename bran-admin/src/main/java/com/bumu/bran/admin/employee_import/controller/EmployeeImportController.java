package com.bumu.bran.admin.employee_import.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.employee.result.ImportEmployeeResult;
import com.bumu.bran.admin.employee_import.service.EmployeeImportService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.FileCommand;
import com.bumu.common.command.FileStrCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author majun
 * @date 2017/11/9
 * @email 351264830@qq.com
 */
@Api(tags = {"员工管理-花名册导入RosterImport"})
@Controller
@RequestMapping()
public class EmployeeImportController {

    private static Logger logger = LoggerFactory.getLogger(EmployeeImportController.class);

    @Autowired
    private EmployeeImportService employeeImportService;

    @ApiOperation(value = "下载模板")
    @RequestMapping(value = "/admin/employee/roster/import/download", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<String> download(SessionInfo sessionInfo, HttpServletResponse response) throws Exception {
        return new HttpResponse(ErrorCode.CODE_OK, employeeImportService.download(sessionInfo, response));
    }

    @ApiOperation(value = "验证模板内容")
    @RequestMapping(value = "/admin/employee/roster/import/verify", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<List<ImportEmployeeResult>> verify(@ApiParam FileCommand file, SessionInfo sessionInfo) throws Exception {
        return new HttpResponse(employeeImportService.verify(file.getFile(), sessionInfo));
    }

    @ApiOperation(value = "确认导入")
    @RequestMapping(value = "/admin/employee/roster/import/confirm", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<Void> confirm(@ApiParam @RequestBody FileStrCommand fileStrCommand, SessionInfo sessionInfo) throws Exception {
        employeeImportService.confirm(fileStrCommand, sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }
}
