package com.bumu.bran.admin.employee.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.employee.controller.command.WorkSnPrefixCommand;
import com.bumu.bran.admin.employee.service.WorkSnPrefixService;
import com.bumu.bran.admin.system.command.IdVersionsCommand;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 工号
 */
@Api(tags = "员工管理-工号WorkSn")
@Controller
public class WorkSnPrefixShareController {

    private static Logger logger = LoggerFactory.getLogger(WorkSnPrefixShareController.class);

    @Resource
    private WorkSnPrefixService workSnPrefixService;

    /**
     * 获取所有的工号前缀
     *
     * @return
     */
    @RequestMapping(value = "/admin/no_permission/employee/setting/work_sn_prefix/get", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse get() throws Exception {
        logger.info("start delete");
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        WorkSnPrefixCommand workSnPrefixCommand = new WorkSnPrefixCommand();
        workSnPrefixCommand.setBranCorpId(branCorpId);
        return new HttpResponse(workSnPrefixService.get(workSnPrefixCommand));
    }

    /**
     * 根据工号前缀获取分配工号
     *
     * @return
     */
    @RequestMapping(value = {
            "/admin/employee/roster/work_sn_prefix/get/id",
            "/admin/employee/prospective/work_sn_prefix/get/id"}
            , method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse getId(@RequestBody WorkSnPrefixCommand workSnPrefixCommand) throws Exception {
        logger.info("start getId");
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        workSnPrefixCommand.setBranCorpId(branCorpId);
        return new HttpResponse(workSnPrefixService.getId(workSnPrefixCommand));
    }
}
