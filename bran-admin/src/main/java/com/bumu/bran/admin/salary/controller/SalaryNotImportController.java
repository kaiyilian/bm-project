package com.bumu.bran.admin.salary.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.salary.service.SalaryNotImportService;
import com.bumu.bran.admin.system.command.CorpModel;
import com.bumu.bran.esalary.command.SalaryNotImportCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author majun
 * @Deprecated 废弃, 使用web端的薪资
 * @date 2016/11/22
 */
@Deprecated
@Controller
@RequestMapping(value = "/admin/salary/not/import")
public class SalaryNotImportController {
    private static Logger logger = LoggerFactory.getLogger(SalaryNotImportController.class);

    @Autowired
    private SalaryNotImportService salaryNotImportService;

    /**
     * 分页查询未导入薪资
     *
     * @param salaryNotImportCommand
     * @return
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse get(SalaryNotImportCommand salaryNotImportCommand) throws Exception {
        return new HttpResponse(ErrorCode.CODE_OK, salaryNotImportService.get(salaryNotImportCommand));
    }

    /**
     * 删除薪资
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "delete/ids", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse delete(@RequestBody CorpModel command) throws Exception {
        salaryNotImportService.delete(command);
        return new HttpResponse();
    }

    /**
     * 导出
     *
     * @param salaryNotImportCommand
     * @param response
     * @return
     */
    @RequestMapping(value = "export", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse export(SalaryNotImportCommand salaryNotImportCommand, HttpServletResponse response) throws Exception {
        salaryNotImportService.export(salaryNotImportCommand, response);
        return new HttpResponse(ErrorCode.CODE_OK);
    }


}
