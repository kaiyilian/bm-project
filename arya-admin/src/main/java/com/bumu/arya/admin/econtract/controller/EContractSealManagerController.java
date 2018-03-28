package com.bumu.arya.admin.econtract.controller;

import com.bumu.arya.admin.econtract.controller.command.EContractSealCommand;
import com.bumu.econtract.result.EContractSealResult;
import com.bumu.arya.admin.econtract.service.EContractSealService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.BaseResult;
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
import java.util.List;

/**
 * 电子合同印章管理
 *
 * @author majun
 * @date 2017/5/31
 */
@Api(value = "EContractSealManager", tags = {"电子合同印章EContractSealManager"})
@Controller
@RequestMapping(value = "/admin/e_contract/seal/manager")
public class EContractSealManagerController {

    private static Logger logger = LoggerFactory.getLogger(EContractSealManagerController.class);

    @Autowired
    private EContractSealService eContractSealService;

    @ApiOperation(value = "查询印章列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "page/list", method = RequestMethod.GET)
    @ResponseBody
    @ParamCheck
    public HttpResponse<Pager<EContractSealResult>> getPageList(
            @ApiParam("公司id") @RequestParam("arya_corp_id") String aryaCorpId,
            @ApiParam("当前页") @RequestParam("page") Integer page,
            @ApiParam("一页数量") @RequestParam("page_size") Integer pageSize) {
        return new HttpResponse(ErrorCode.CODE_OK, eContractSealService.getPageList(aryaCorpId, page, pageSize));
    }

    @ApiOperation(value = "批量添加印章")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "batch/add", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<List<BaseResult.IDResult>> batchAdd(
            @Valid @ApiParam @RequestBody EContractSealCommand.EContractSealAdd command,
            BindingResult bindingResult) throws Exception {

        logger.info("请求参数: " + command.toString());
        HttpResponse<List<BaseResult.IDResult>> httpResponse = new HttpResponse(ErrorCode.CODE_OK, eContractSealService.batchAdd(command));
        logger.info("result: " + httpResponse.toJson());
        return httpResponse;
    }

    @ApiOperation(value = "删除印章")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "batch/delete", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<Void> batchDelete(@Valid @ApiParam @RequestBody BaseCommand.BatchIds command,
                                          BindingResult bindingResult) throws Exception{

        logger.info("请求参数: " + command.toString());
        eContractSealService.batchDelete(command);
        return new HttpResponse();
    }
}
