package com.bumu.arya.admin.econtract.controller;

import com.bumu.arya.admin.econtract.controller.command.EContractTemplateCommand;
import com.bumu.arya.admin.econtract.service.EContractTemplateService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.BaseResult;
import com.bumu.econtract.result.EContractTemplateDetailResult;
import com.bumu.econtract.result.EContractTemplateResult;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 电子合同模板管理
 *
 * @author majun
 * @date 2017/5/31
 */
@Api(value = "EContractTemplateManager", tags = {"电子合同模板管理EContractTemplateManager"})
@Controller
@RequestMapping(value = "/admin/e_contract/template/manager")
public class EContractTemplateManagerController {

    private static Logger logger = LoggerFactory.getLogger(EContractTemplateManagerController.class);

    @Autowired
    private EContractTemplateService eContractTemplateService;

    @ApiOperation(value = "查询合同模板列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "page/list", method = RequestMethod.GET)
    @ResponseBody
    @ParamCheck
    public HttpResponse<Pager<EContractTemplateResult>> getPageList(
            @ApiParam("公司id") @RequestParam("arya_corp_id") String aryaCorpId,
            @ApiParam("当前页") @RequestParam("page") Integer page,
            @ApiParam("一页数量") @RequestParam("page_size") Integer pageSize) {
        return new HttpResponse(ErrorCode.CODE_OK, eContractTemplateService.getPageList(aryaCorpId, page, pageSize));
    }

    @ApiOperation(value = "获取某个合同模板详情")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "one/detail", method = RequestMethod.GET)
    @ResponseBody
    @ParamCheck
    public HttpResponse<EContractTemplateDetailResult> oneDetail(
            @ApiParam("模板ID") @RequestParam("contract_template_id") String contractTemplateId) throws Exception {

        HttpResponse httpResponse = new HttpResponse(ErrorCode.CODE_OK, eContractTemplateService.detail(contractTemplateId));
        logger.info("httpResponse to Json: " + httpResponse.toJson());
        return httpResponse;
    }

    @ApiOperation(value = "添加合同模板")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<BaseResult.IDResult> add(
            @Valid @ApiParam @RequestBody EContractTemplateCommand.EContractTemplateAdd command,
            BindingResult bindingResult) throws Exception {

        HttpResponse<BaseResult.IDResult> httpResponse = new HttpResponse(ErrorCode.CODE_OK, eContractTemplateService.add(
                command, new SessionInfo(null, command.getAryaCorpId())));
        logger.info("result: " + httpResponse.toJson());
        return httpResponse;
    }

    @ApiOperation(value = "编辑合同模板")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<Object> update(
            @Valid @ApiParam @RequestBody EContractTemplateCommand.EContractTemplateUpdate command,
            BindingResult bindingResult) {
        eContractTemplateService.update(command, new SessionInfo(null, command.getAryaCorpId()));
        return new HttpResponse();
    }

    @ApiOperation(value = "删除合同模板")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "batch/delete", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<Object> batchDelete(@Valid @ApiParam @RequestBody BaseCommand.BatchIds command,
                                            BindingResult bindingResult) {
        eContractTemplateService.batchDelete(command);
        return new HttpResponse();
    }
}
