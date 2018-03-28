package com.bumu.bran.admin.econtract.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.econtract.result.EContractDetailResult;
import com.bumu.bran.admin.econtract.result.EContractInfoResult;
import com.bumu.bran.admin.econtract.service.EContractService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.econtract.command.EContractCommand;
import com.bumu.bran.econtract.command.EContractSetStateCommand;
import com.bumu.bran.econtract.command.EContractUpdateCommand;
import com.bumu.bran.econtract.model.dao.impl.EContractMybatisQuery;
import com.bumu.bran.econtract.result.EContractResult2;
import com.bumu.econtract.constant.EContractEnum;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author majun
 * @date 2017/6/18
 */
@Api(tags = {"电子合同接口EContract"})
@Controller
@RequestMapping(value = "/admin/e_contract")
public class EContractBranAdminController {

    private static Logger logger = LoggerFactory.getLogger(EContractBranAdminController.class);

    @Autowired
    private EContractService eContractService;

    @ApiOperation(value = "新建合同")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.POST)
    @ParamCheck
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Void> create(@ApiParam @Valid @RequestBody EContractCommand eContractCommand,
                                     BindingResult bindingResult, SessionInfo sessionInfo) throws Exception {

        eContractService.create(eContractCommand, sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "更新合同")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.PUT)
    @ParamCheck
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Void> update(@ApiParam @Valid @RequestBody EContractUpdateCommand eContractCommand,
                                     BindingResult bindingResult, SessionInfo sessionInfo) throws Exception {

        eContractService.update(eContractCommand, sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "分页查询合同")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.GET)
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Pager<EContractResult2>> getPageList(
            @ApiParam("开始时间") @RequestParam(value = "start", required = false) Long start,
            @ApiParam("结束时间") @RequestParam(value = "end", required = false) Long end,
            @ApiParam("合同类型 0:劳动合同 1:社保合同") @RequestParam(value = "contract_type", required = false) Integer contractType,
            @ApiParam("合同类型 0:未发送 1:已发送 2:待审核 3:已生效 4:已过期 5:已作废 全部:'' ") @RequestParam(value = "contract_state", required = false) Integer contractState,
            @ApiParam("手机号") @RequestParam(value = "tel", required = false) String tel,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize, SessionInfo sessionInfo) {


        logger.info("参数组装成对象: ");

        EContractMybatisQuery eContractMybatisQuery = new EContractMybatisQuery();
        if (contractState != null) {
            eContractMybatisQuery.setContractState(EContractEnum.ContractState.values()[contractState]);
        }
        if (contractType != null) {
            eContractMybatisQuery.setContractType(EContractEnum.ContractType.values()[contractType]);
        }

        if (start != null) {
            eContractMybatisQuery.setStart(new DateTime(start));
        }

        if (end != null) {
            eContractMybatisQuery.setEnd(new DateTime(end));
        }
        eContractMybatisQuery.setTel(tel);
        eContractMybatisQuery.setBranCorpId(sessionInfo.getCorpId());
        logger.info("请求参数: " + eContractMybatisQuery.toString());
        return new HttpResponse<>(ErrorCode.CODE_OK, eContractService.getPageList(eContractMybatisQuery, page, pageSize, sessionInfo));
    }


    @ApiOperation(value = "查看合同详情")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<EContractDetailResult> detail(
            @ApiParam(value = "合同ID") @RequestParam(value = "id", required = false) String id) {

        return new HttpResponse<>(ErrorCode.CODE_OK, eContractService.detail(id));
    }

    @ApiOperation(value = "获取合同信息(重新填写合同的时候用到)")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "info", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<EContractInfoResult> info(
            @ApiParam(value = "合同ID") @RequestParam(value = "id", required = false) String id) {
        return new HttpResponse<>(ErrorCode.CODE_OK, eContractService.info(id));
    }

    @ApiOperation(value = "合同预览 url")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "preview", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<String> preview(
            @ApiParam(value = "合同ID") @RequestParam(value = "id", required = false) String id) throws Exception {
        HttpResponse<String> httpResponse = new HttpResponse<>();
        httpResponse.setResult(eContractService.preview(id));
        return httpResponse;
    }

    @ApiOperation(value = "发送合同")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "send", method = RequestMethod.PATCH)
    @ParamCheck
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Void> send(@ApiParam @Valid @RequestBody BaseCommand.BatchIds ids,
                                   BindingResult bindingResult, SessionInfo sessionInfo) throws Exception {
        eContractService.send(ids, sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "批量删除合同")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.DELETE)
    @ParamCheck
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Void> del(@ApiParam @Valid @RequestBody BaseCommand.BatchIds ids,
                                  BindingResult bindingResult, SessionInfo sessionInfo) {
        eContractService.del(ids, sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "设置合同状态")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "setState", method = RequestMethod.PATCH)
    @ParamCheck
    @GetSessionInfo
    @ResponseBody
    public HttpResponse<Void> setState(
            @ApiParam @Valid @RequestBody EContractSetStateCommand eContractSetStateCommand,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception {
        logger.info("参数: " + eContractSetStateCommand.toString());
        eContractService.setState(eContractSetStateCommand, sessionInfo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "下载合同")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "download", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<String> download(
            @ApiParam(value = "合同ID") @RequestParam(value = "id", required = false) String id, HttpServletResponse response) throws Exception {
        HttpResponse httpResponse = new HttpResponse<>();
        httpResponse.setResult(eContractService.download(id, response));
        return httpResponse;
    }

}
