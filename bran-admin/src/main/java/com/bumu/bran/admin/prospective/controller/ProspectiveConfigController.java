package com.bumu.bran.admin.prospective.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.prospective.service.ProspectiveConfigService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.employee.command.ProspectiveConfigCommand;
import com.bumu.bran.employee.result.ProspectiveConfigResult;
import com.bumu.common.SessionInfo;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 入职信息配置
 *
 * @author majun
 * @date 2017/8/14
 * @deprecated 已做好，但是没有启用
 */
@Api(tags = {"入职信息配置接口ProspectiveConfig"})
@RestController
@RequestMapping(value = "/admin/prospective/config")
public class ProspectiveConfigController {

    private static Logger logger = LoggerFactory.getLogger(ProspectiveConfigController.class);

    @Autowired
    private ProspectiveConfigService prospectiveConfigService;

    @ApiOperation(value = "新增入职信息配置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.POST)
    @GetSessionInfo
    public HttpResponse<Void> add(@ApiParam @RequestBody @Valid ProspectiveConfigCommand prospectiveConfigCommand, BindingResult bindingResult,
                                  SessionInfo sessionInfo) {
        logger.info("新增入职信息配置");
        if (prospectiveConfigCommand.getIdCardNoInfoRequired() != 1) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "身份证信息必须选择");
        }
        logger.info("参数: " + prospectiveConfigCommand.toString());
        prospectiveConfigService.add(prospectiveConfigCommand, sessionInfo);
        return new HttpResponse<>();
    }

    @ApiOperation(value = "更新入职信息配置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.PUT)
    @GetSessionInfo
    public HttpResponse<Void> update(@ApiParam @RequestBody @Valid ProspectiveConfigCommand prospectiveConfigCommand, BindingResult bindingResult,
                                     SessionInfo sessionInfo) {
        logger.info("更新入职信息配置");
        logger.info("参数: " + prospectiveConfigCommand.toString());
        if (prospectiveConfigCommand.getIdCardNoInfoRequired() != 1) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "身份证信息必须选择");
        }
        Assert.notBlank(prospectiveConfigCommand.getId(), "更新时id必填");
        prospectiveConfigService.update(prospectiveConfigCommand, sessionInfo);
        return new HttpResponse<>();
    }

    @ApiOperation(value = "获取入职信息配置")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.GET)
    @GetSessionInfo
    public HttpResponse<ProspectiveConfigResult> get(SessionInfo sessionInfo) {
        return new HttpResponse<>(ErrorCode.CODE_OK, prospectiveConfigService.get(sessionInfo));
    }
}
