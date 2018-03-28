package com.bumu.bran.admin.econtract.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.econtract.result.EContractLoopsKeyValueResult;
import com.bumu.bran.admin.econtract.service.EContractTemplateBranAdminService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.common.SessionInfo;
import com.bumu.econtract.result.EContractTemplateResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author majun
 * @date 2017/6/18
 */
@Api( tags = {"电子合同-模板接口"})
@RestController
@RequestMapping(value = "/admin/e_contract/template")
public class EContractTemplateBranAdminController {

    private static Logger logger = LoggerFactory.getLogger(EContractTemplateBranAdminController.class);

    @Autowired
    private EContractTemplateBranAdminService eContractTemplateBranAdminService;

    @ApiOperation(value = "查询当前公司的所有电子合同模板")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.GET)
    @GetSessionInfo
    public HttpResponse<List<EContractTemplateResult>> getList(SessionInfo sessionInfo)
            throws Exception {
        HttpResponse<List<EContractTemplateResult>> httpResponse = new HttpResponse<>(ErrorCode.CODE_OK,
                eContractTemplateBranAdminService.getList(sessionInfo));
        logger.info("httpResponse: " + httpResponse.toString());
        return httpResponse;
    }

    @ApiOperation(value = "获取模板的填写项")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "loops", method = RequestMethod.GET)
    @GetSessionInfo
    public HttpResponse<List<EContractLoopsKeyValueResult>> getLoops(@ApiParam(value = "模板id")
                                                                     @RequestParam(value = "id", required = false) String id,
                                                                     SessionInfo sessionInfo) throws Exception {
        HttpResponse<List<EContractLoopsKeyValueResult>> httpResponse = new HttpResponse<>(ErrorCode.CODE_OK,
                eContractTemplateBranAdminService.getLoops(id, sessionInfo));
        logger.info("httpResponse: " + httpResponse.toString());
        return httpResponse;
    }
}
