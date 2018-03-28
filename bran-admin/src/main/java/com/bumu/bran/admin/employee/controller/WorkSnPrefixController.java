package com.bumu.bran.admin.employee.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.employee.controller.command.WorkSnPrefixCommand;
import com.bumu.bran.admin.employee.service.WorkSnPrefixService;
import com.bumu.bran.admin.system.command.IdVersionsCommand;
import com.bumu.common.annotation.ParamCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 工号
 */
@Api(tags = "员工管理-工号WorkSn")
@Controller
public class WorkSnPrefixController {

    private static Logger logger = LoggerFactory.getLogger(WorkSnPrefixController.class);

    @Resource
    private WorkSnPrefixService workSnPrefixService;

    /**
     * 新增工号前缀
     *
     * @return 响应信息
     */
    @ApiOperation(value = "新增工号前缀")
    @RequestMapping(value = "/admin/employee/setting/work_sn_prefix/add", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse add(@Valid @ApiParam @RequestBody WorkSnPrefixCommand command, BindingResult bindingResult) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        command.setBranCorpId(branCorpId);
        command.setUserId(currentUserId);
        logger.info("start add");
        return new HttpResponse(workSnPrefixService.add(command));
    }

    /**
     * 更新工号前缀
     *
     * @param command 参数
     * @return 相应信息
     */
    @ApiOperation(value = "更新工号前缀")
    @RequestMapping(value = "/admin/employee/setting/work_sn_prefix/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse update(@RequestBody WorkSnPrefixCommand command) throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        String currentUserId = session.getAttribute("user_id").toString();
        command.setBranCorpId(branCorpId);
        command.setUserId(currentUserId);
        logger.info("start update");
        return new HttpResponse(workSnPrefixService.update(command));
    }

    /**
     * 删除工号前缀
     *
     * @param command 参数
     * @return 相应信息
     */
    @ApiOperation(value = "删除工号前缀")
    @RequestMapping(value = "/admin/employee/setting/work_sn_prefix/del", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse delete(@RequestBody IdVersionsCommand command) throws Exception {
        logger.info("start delete");
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        command.setUserId(currentUserId);
        command.setBranCorpId(branCorpId);
        workSnPrefixService.delete(command);
        return new HttpResponse();
    }

}
