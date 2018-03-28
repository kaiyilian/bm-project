package com.bumu.arya.admin.econtract.controller;

import com.bumu.arya.admin.econtract.controller.command.EContractServiceInfoCommand;
import com.bumu.arya.admin.econtract.result.EContractServiceInfoResult;
import com.bumu.arya.admin.econtract.service.EContractServiceInfoService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.annotation.ParamCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 电子合同服务信息
 *
 * @author majun
 * @date 2017/7/10
 * @email 351264830@qq.com
 */
@Api(value = "EContractServiceInfoController", tags = {"电子合同服务信息EContractServiceInfoController"})
@RestController
@RequestMapping(value = "admin/e_contract_service/info")
public class EContractServiceInfoController {

    private static Logger logger = LoggerFactory.getLogger(EContractServiceInfoController.class);

    @Autowired
    private EContractServiceInfoService eContractServiceInfoService;

    @ApiOperation(httpMethod = "GET", value = "查询某个公司电子合同服务信息详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public HttpResponse<EContractServiceInfoResult> getDetail(
            @ApiParam("公司id") @RequestParam(value = "arya_corp_id", required = false) String aryaCorpId) throws Exception {
        logger.info("detail ...");
        HttpResponse<EContractServiceInfoResult> httpResponse = new HttpResponse(ErrorCode.CODE_OK, eContractServiceInfoService.getDetail(aryaCorpId));
        logger.info("httpResponse: " + httpResponse.toJson());
        return httpResponse;
    }

    @ApiOperation(httpMethod = "PUT", value = "提交某个公司电子合同服务信息提交")
    @RequestMapping(value = "submit", method = RequestMethod.PUT)
    @ParamCheck
    public HttpResponse<Void> submit(@ApiParam @Valid @RequestBody EContractServiceInfoCommand command, BindingResult bindingResult) throws Exception {
        logger.info("submit ...");
        logger.info("参数: " + command.toString());
        eContractServiceInfoService.submit(command);
        HttpResponse<Void> httpResponse = new HttpResponse();
        logger.info("httpResponse: " + httpResponse.toJson());
        return httpResponse;
    }
}
