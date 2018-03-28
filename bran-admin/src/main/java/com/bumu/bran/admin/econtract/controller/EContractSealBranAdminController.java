package com.bumu.bran.admin.econtract.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.econtract.service.EContractSealBranAdminService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.common.SessionInfo;
import com.bumu.econtract.result.EContractSealResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author majun
 * @date 2017/6/18
 */
@Api(tags = {"电子合同-印章接口"})
@RestController
@RequestMapping(value = "/admin/e_contract/seal")
public class EContractSealBranAdminController {

    private static Logger logger = LoggerFactory.getLogger(EContractSealBranAdminController.class);

    @Autowired
    private EContractSealBranAdminService eContractSealBranAdminService;

    @ApiOperation(value = "查询当前公司的所有印章")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.GET)
    @GetSessionInfo
    public HttpResponse<List<EContractSealResult>> getList(SessionInfo sessionInfo)
            throws Exception {
        HttpResponse<List<EContractSealResult>> httpResponse = new HttpResponse<>(ErrorCode.CODE_OK,
                eContractSealBranAdminService.getList(sessionInfo));
        logger.info("httpResponse: " + httpResponse.toString());
        return httpResponse;
    }
}
