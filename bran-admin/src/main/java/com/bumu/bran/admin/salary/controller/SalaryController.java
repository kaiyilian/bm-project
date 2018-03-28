package com.bumu.bran.admin.salary.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.salary.result.ImportSalaryConfirmResult;
import com.bumu.bran.admin.salary.result.SalaryResult;
import com.bumu.bran.admin.salary.service.SalaryService;
import com.bumu.bran.admin.salary.valid.RepeatSubmit;
import com.bumu.bran.admin.system.command.CorpModel;
import com.bumu.bran.esalary.command.SalaryCommand;
import com.bumu.common.validator.annotation.AddValidatedGroup;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author majun
 * @Deprecated 废弃, 使用web端的薪资
 * @date 2016/11/22
 */
@Deprecated
@Controller
//@RequestMapping(value = "/admin/salary")
public class SalaryController {

    private static Logger logger = LoggerFactory.getLogger(SalaryController.class);

    @Autowired
    private SalaryService salaryService;

    /**
     * 薪资下载
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse download(HttpServletResponse response) throws Exception {
        salaryService.download(response);
        return new HttpResponse();
    }

    /**
     * 薪资条验证接口
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "verify", method = RequestMethod.POST)
    @ResponseBody
    @RepeatSubmit(key = "com.bumu.bran.admin.controller.verify")
    public HttpResponse verify(MultipartFile file, HttpServletRequest request) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        HttpSession httpSession = request.getSession();
        String token = (String) httpSession.getAttribute("com.bumu.bran.admin.controller.verify");
        return new HttpResponse(salaryService.verify(file, branCorpId, token));
    }

    /**
     * 确认导入
     *
     * @param salaryCommand
     * @return
     */
    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    @ResponseBody
    @RepeatSubmit(verify = true, key = "com.bumu.bran.admin.controller.verify")
    public HttpResponse<ImportSalaryConfirmResult> confirm(@RequestBody @Validated(value = AddValidatedGroup.class)
                                                                   SalaryCommand salaryCommand, BindingResult bindingResult)
            throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        salaryCommand.setBranCorpId(branCorpId);
        salaryCommand.setUserId(currentUserId);
        logger.debug("file_id: " + salaryCommand.getFile_id());
        logger.debug("fileTypeStr: " + salaryCommand.getFileTypeStr());
        logger.debug("bran_corp_id: " + branCorpId);
        return new HttpResponse(salaryService.confirm(salaryCommand, bindingResult));
    }

    /**
     * 分页查询薪资
     *
     * @param release
     * @param salaryCommand
     * @return
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<SalaryResult> get(@RequestParam(value = "release", defaultValue = "0") int release,
                                          SalaryCommand salaryCommand) throws Exception {
        salaryCommand.setRelease(release);
        return new HttpResponse(salaryService.get(salaryCommand));
    }


    /**
     * 薪资详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse detail(String id) throws Exception {
        return new HttpResponse(ErrorCode.CODE_OK, salaryService.detail(id));
    }


    /**
     * 发布薪资
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "release", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<CorpModel> release(@RequestBody CorpModel command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        command.setBranCorpId(branCorpId);
        command.setOperateUserId(currentUserId);
        return new HttpResponse(salaryService.release(command));
    }

    /**
     * 一键发布
     *
     * @param command
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "release/all", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse releaseAll(@RequestBody CorpModel command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        command.setBranCorpId(branCorpId);
        command.setOperateUserId(currentUserId);
        return new HttpResponse(salaryService.releaseAll(command));
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
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        command.setOperateUserId(currentUserId);
        salaryService.delete(command);
        return new HttpResponse();
    }


}
